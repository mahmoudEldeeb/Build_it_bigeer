package com.udacity.gradle;

import android.test.AndroidTestCase;
import android.util.Log;

import com.udacity.gradle.builditbigger.EndpointsAsyncTask;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by melde on 5/15/2017.
 */
public class EndpointsAsyncTaskTest extends AndroidTestCase {
    @Test
    public void onPostExecute() throws Exception {
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