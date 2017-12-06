package com.udacity.gradle.builditbigger;

import java.util.Random;

public class Jokes {
    private static final String [] JOKES = {
            "How many ears does Spock have?\n3. The left ear, the right ear, and the final front-ear!",
            "I miss my umbilical cord,\nI grew attached to it.",
            "What's the difference between a Zippo and a hippo?\nOnes really heavy, and the other's a little lighter!",
            "How do you make a glow worm happy?\nCut off it's tail... it will be delighted!",
            "A friend of mine died recently after drinking a gallon of varnish.\nIt was a horrible end, but a lovely finish."
    };

    private final Random random;

    public Jokes() {
        random = new Random(System.currentTimeMillis());
    }

    public String tellJoke() {
        return JOKES[Math.abs(random.nextInt()) % JOKES.length];
    }
}
