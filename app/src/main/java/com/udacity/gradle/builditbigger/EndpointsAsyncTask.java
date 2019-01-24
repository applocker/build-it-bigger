package com.udacity.gradle.builditbigger;

import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.v4.util.Pair;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.udacity.gradle.builditbigger.backend.myApi.MyApi;

import java.io.IOException;

class EndpointsAsyncTask extends AsyncTask<Context, Void, String> {
    private static MyApi myApiService = null;
    private Context context;
    private SimpleIdlingResource simpleIdlingResource;
    private JokeReturnedHandler jokeReturnedHandler;
    private Boolean errorFecthingJoke = false;

    public interface JokeReturnedHandler {
        void joke(String joke);
    }
    @Override
    protected String doInBackground(Context... params) {
        if(myApiService == null) {  // Only do this once
            MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null)
                    // options for running against local devappserver
                    // - 10.0.2.2 is localhost's IP address in Android emulator
                    // - turn off compression when running against local devappserver
                    //.setRootUrl("http://10.0.2.2:8080/_ah/api/")
                    .setRootUrl("http://10.0.3.2:8080/_ah/api/") // for genymotion emulators
                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                        @Override
                        public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                            abstractGoogleClientRequest.setDisableGZipContent(true);
                        }
                    });
            // end options for devappserver

            myApiService = builder.build();
        }

        context = params[0];


        try {
            return  myApiService.getAJoke().execute().getData();
        } catch (IOException e) {
            errorFecthingJoke = true;
            return e.getMessage();

        }
    }

    @Override
    protected void onPostExecute(String result) {
        if(context instanceof  JokeReturnedHandler && !errorFecthingJoke){
            jokeReturnedHandler = (JokeReturnedHandler)context;
            jokeReturnedHandler.joke(result);
        }
        else {
            //return the error message
            jokeReturnedHandler.joke(result);
        }
    }

}
