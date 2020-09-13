package com.example.hw14.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.hw14.R;

public abstract class SingelFragment extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_fragment);
        FragmentManager manager = getSupportFragmentManager();
        if (savedInstanceState == null)
            manager.beginTransaction()
                    .add(R.id.single_fragment_place, getFragment())
                    .commit();
    }
    abstract Fragment getFragment();
}