package com.udacity.gradle.builditbigger;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;
import android.support.test.espresso.IdlingResource;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.lib.appjokevisualizer.JokesVisualizerActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.lib.appjokevisualizer.utility.Constants.JOKE;


public class MainActivity extends AppCompatActivity implements EndpointsAsyncTask.JokeReturnedHandler {
    @SuppressWarnings("WeakerAccess")
    @BindView(R.id.pb_loading_indicator)   ProgressBar mLoadingIndicator;

    @SuppressWarnings("WeakerAccess")
    @BindView(R.id.textView_error_loading_message)   TextView mErrorLoadingMessage;

    private SimpleIdlingResource mIdlingResource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        dismissLoadingIndicators();
    }

    private void dismissLoadingIndicators() {
        mLoadingIndicator.setVisibility(View.INVISIBLE);
        mErrorLoadingMessage.setVisibility(View.INVISIBLE);
    }

    private void showProgress() {
        mLoadingIndicator.setVisibility(View.VISIBLE);
        mErrorLoadingMessage.setVisibility(View.INVISIBLE);
    }

    private void showErrorMessage() {
        mLoadingIndicator.setVisibility(View.INVISIBLE);
        mErrorLoadingMessage.setVisibility(View.VISIBLE);
        mErrorLoadingMessage.setText(getResources().getString(R.string.error_message));
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void tellJoke(View view) {
        if (mIdlingResource == null) {
            mIdlingResource = new SimpleIdlingResource();
        }
        mIdlingResource.setIdleState(false);
        new EndpointsAsyncTask().execute(this);
        showProgress();
    }


    @Override
    public void joke(String aRandomJoke) {
        mIdlingResource.setIdleState(true);
        if(aRandomJoke.contains(getResources().getString(R.string.joke_prefix))){
            dismissLoadingIndicators();
            Intent intent = new Intent(this, JokesVisualizerActivity.class);
            intent.putExtra(JOKE,aRandomJoke);
            startActivity(intent);
        }
        else{
            showErrorMessage();
        }

    }

    @VisibleForTesting
    @NonNull
    public IdlingResource getIdlingResource() {
        if (mIdlingResource == null) {
            mIdlingResource = new SimpleIdlingResource();
        }
        return mIdlingResource;
    }
}
