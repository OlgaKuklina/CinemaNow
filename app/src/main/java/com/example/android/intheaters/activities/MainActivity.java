package com.example.android.intheaters.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.android.intheaters.data.MovieData;
import com.example.android.intheaters.fragments.InTheatersFragment;
import com.example.android.intheaters.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle(R.string.app_name);
    }

}
