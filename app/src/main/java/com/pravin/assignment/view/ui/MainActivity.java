package com.pravin.assignment.view.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.pravin.assignment.R;

/**
 * MainActivity of application
 * In this activity we inflate the fragment to show the feeds.
 */
public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
