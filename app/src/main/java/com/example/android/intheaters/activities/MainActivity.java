package com.example.android.intheaters.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.android.intheaters.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle(R.string.title_activity);
    }

}
