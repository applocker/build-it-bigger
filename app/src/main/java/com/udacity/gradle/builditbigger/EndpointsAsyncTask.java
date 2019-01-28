package com.udacity.gradle.builditbigger;

import android.content.Context;
import android.os.AsyncTask;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.udacity.gradle.builditbigger.backend.myApi.MyApi;

import java.io.IOException;

class EndpointsAsyncTask extends AsyncTask<Context, Void, String> {
    private static MyApi myApiService = null;
    private JokeReturnedHandler jokeReturnedHandler;
    private Boolean errorFecthingJoke = false;

    public interface JokeReturnedHandler {
        void joke(String joke);
    }
    @Override
    protected String doInBackground(Context... params) {
        if(myApiService == null) {  // Only do this once
            //noinspection RedundantThrows
            @SuppressWarnings("RedundantThrows") MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(),
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

            myApiService = builder.build();
        }

        Context context = params[0];
        jokeReturnedHandler = (JokeReturnedHandler)context;

        try {
            return  myApiService.getAJoke().execute().getData();
        } catch (IOException e) {
            errorFecthingJoke = true;
            String message;
            //noinspection ConstantConditions
            if(e != null){
                message = e.getMessage();
            }
            else{
                message = context.getResources().getString(R.string.error_flag);
            }
            return message;

        }
    }

    @Override
    protected void onPostExecute(String result) {
        if(jokeReturnedHandler != null && !errorFecthingJoke){
            jokeReturnedHandler.joke(result);
        }
        else {
            //return the error message
            if(jokeReturnedHandler != null){
                jokeReturnedHandler.joke(result);
            }
        }
    }

}
