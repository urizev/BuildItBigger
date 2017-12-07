package com.udacity.gradle.builditbigger;

import android.os.AsyncTask;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.udacity.gradle.builditbigger.backend.jokeApi.JokeApi;

import java.io.IOException;
import java.lang.ref.WeakReference;

/**
 * Creado por jcvallejo en 6/12/17.
 */

public class JokeAsyncTask extends AsyncTask<Void,Void,String> {
    private final WeakReference<JokeAsyncTaskDelegate> delegate;
    private JokeApi jokeApiService = null;

    public JokeAsyncTask(JokeAsyncTaskDelegate delegate) {
        this.delegate = new WeakReference<>(delegate);
    }

    @Override
    protected final String doInBackground(Void... params) {
        if(jokeApiService == null) {  // Only do this once
            JokeApi.Builder builder = new JokeApi.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null)
                    // options for running against local devappserver
                    // - 10.0.2.2 is localhost's IP address in Android emulator
                    // - turn off compression when running against local devappserver
                    .setRootUrl("http://10.0.2.2:8080/_ah/api/")
                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                        @Override
                        public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                            abstractGoogleClientRequest.setDisableGZipContent(true);
                        }
                    });
            // end options for devappserver

            jokeApiService = builder.build();
        }

        try {
            return jokeApiService.tellJoke().execute().getData();
        } catch (IOException e) {
            return e.getMessage();
        }
    }

    @Override
    protected void onPostExecute(String result) {
        JokeAsyncTaskDelegate d = delegate.get();
        if (d != null) {
            d.onJokeResult(result);
        }
    }

    interface JokeAsyncTaskDelegate {
        void onJokeResult(String joke);
    }
}
