package com.udacity.gradle.builditbigger;

import android.test.AndroidTestCase;
import android.text.TextUtils;

import java.util.concurrent.TimeUnit;

public class JokeAsyncTaskTest extends AndroidTestCase {
    public void testTellJoke() {
        try {
            JokeAsyncTask task = new JokeAsyncTask(null);
            task.execute();
            String joke = task.get(15, TimeUnit.SECONDS);
            assertNotNull(joke);
            assertFalse(TextUtils.isEmpty(joke));
        } catch (Exception e) {
            fail("Operation timed out");
        }
    }
}