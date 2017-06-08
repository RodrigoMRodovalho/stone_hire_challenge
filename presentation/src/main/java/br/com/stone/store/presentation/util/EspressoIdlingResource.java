package br.com.stone.store.presentation.util;

import android.support.test.espresso.IdlingResource;

/**
 * Created by rrodovalho on 07/06/17.
 */

public class EspressoIdlingResource {

    private static final String RESOURCE = "GLOBAL";

    private static SimpleCountingIdlingResource countingIdlingResource =
            new SimpleCountingIdlingResource(RESOURCE);

    public static void increment() {
        countingIdlingResource.increment();
    }

    public static void decrement() {
        countingIdlingResource.decrement();
    }

    public static IdlingResource getIdlingResource() {
        return countingIdlingResource;
    }
}
