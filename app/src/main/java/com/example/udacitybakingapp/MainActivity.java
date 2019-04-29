package com.example.udacitybakingapp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.example.udacitybakingapp.IdlingResource.SimpleIdlingResource;
import com.example.udacitybakingapp.fragments.RecipeFragment;

public class MainActivity extends AppCompatActivity {

    @Nullable
    private SimpleIdlingResource simpleIdlingResource;

    @VisibleForTesting
    @NonNull
    public SimpleIdlingResource setIdlingResource(){
        if(simpleIdlingResource !=null){
            return simpleIdlingResource;
        } else {
            simpleIdlingResource = new SimpleIdlingResource();
            return simpleIdlingResource;
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setIdlingResource();

        RecipeFragment rf = new RecipeFragment();
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(R.id.fragment_container, rf, RecipeFragment.class.getSimpleName());
        ft.commit();
    }
}
