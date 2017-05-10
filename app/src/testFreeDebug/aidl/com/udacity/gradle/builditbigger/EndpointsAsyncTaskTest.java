package com.udacity.gradle.builditbigger;

import android.test.AndroidTestCase;
import android.util.Log;

import static org.junit.Assert.*;

/**
 * Created by melde on 5/10/2017.
 */
final class EndpointsAsyncTaskTest extends AndroidTestCase{

        @SuppressWarnings("unchecked")
        public void test() {

            String result = null;
            EndpointsAsyncTask endpointsAsyncTask = new EndpointsAsyncTask(getContext(), null);
            endpointsAsyncTask.execute();
            try {
                result = endpointsAsyncTask.get();
                Log.d("non empty result", "Retrieved a non-empty string successfully: " + result);
            } catch (Exception e) {
                e.printStackTrace();
            }
            assertNotNull(result);
        }



}