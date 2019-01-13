package com.example.lib.appjokevisualizer;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import static com.example.lib.appjokevisualizer.utility.Constants.JOKE;

/**
 * A placeholder fragment containing a simple view.
 */
public class JokeVisulizerActivityFragment extends Fragment {

    public JokeVisulizerActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_jokes_visualizer, container, false);
        Intent intent = getActivity().getIntent();
        if(intent.hasExtra(JOKE)){
            String joke = intent.getStringExtra(JOKE);
            TextView textView = (TextView) view.findViewById(R.id.textViewJoke);
            textView.setText(joke);
        }
        return view;
    }
}
