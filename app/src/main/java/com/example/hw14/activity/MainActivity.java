package com.example.hw14.activity;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import com.example.hw14.R;
import com.example.hw14.fragment.MainFragment;

public class MainActivity extends SingelFragment {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main2);
    }

    @Override
    Fragment getFragment() {
        return MainFragment.newInstance();
    }
}