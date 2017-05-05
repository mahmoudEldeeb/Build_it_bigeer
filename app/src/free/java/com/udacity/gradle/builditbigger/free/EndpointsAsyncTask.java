package com.udacity.gradle.builditbigger.free;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.example.melde.myapplication.backend.myApi.MyApi;
import com.programs.lala.myandroidlibrary.JokeDisplayActivity;
import com.udacity.gradle.builditbigger.MainActivity;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.udacity.gradle.builditbigger.R;

import java.io.IOException;

/**
 * Created by melde on 5/3/2017.
 */

public class EndpointsAsyncTask extends AsyncTask<Pair<Context, String>, Void, String> {
    private static MyApi myApiService = null;
    private Context context;
    private ProgressBar ProgressBar;
    InterstitialAd mInterstitialAd;
    public EndpointsAsyncTask(Context c, ProgressBar p) {
       this.context=c;
this.ProgressBar=p;
    }
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if (this.ProgressBar != null) {
            this.ProgressBar.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected String doInBackground(Pair<Context, String>... params) {
        if(myApiService == null) {  // Only do this once

            MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(),
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

            myApiService = builder.build();

        }

        try {
            return myApiService.getJoke().execute().getData();

        } catch (IOException e) {
            return e.getMessage();
        }
    }

    @Override
    protected void onPostExecute(final String result) {
        if (ProgressBar != null) {
            ProgressBar.setVisibility(View.GONE);
        }
        startJokeDisplayActivity(result);
        mInterstitialAd = new InterstitialAd(context);
        mInterstitialAd.setAdUnitId(context.getString(R.string.interstitial_ad_unit_id));
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                super.onAdLoaded();

                mInterstitialAd.show();
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                super.onAdFailedToLoad(errorCode);
                if (ProgressBar != null) {
                    ProgressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onAdClosed() {
            }
        });

    }

    private void startJokeDisplayActivity(String result) {
        if(result!=null) {

            Toast.makeText(context, result, Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this.context, JokeDisplayActivity.class);
            intent.putExtra("joke", result);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            this.context.startActivity(intent);
        }
    }
    }