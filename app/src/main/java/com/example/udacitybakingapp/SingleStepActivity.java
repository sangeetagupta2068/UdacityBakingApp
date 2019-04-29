package com.example.udacitybakingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.example.udacitybakingapp.adapters.SingleStepAdapter;
import com.example.udacitybakingapp.models.Step;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class SingleStepActivity extends AppCompatActivity {

    public static final String STEPS = "STEPS";

    private List<Step> steps = new ArrayList<>();
    @BindView(R.id.view_pager)
    ViewPager viewPager;

    @BindView(R.id.tab)
    TabLayout tabLayout;

    private SingleStepAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_step);
        Intent intent = getIntent();
        int stepId = 0;
        if (intent != null && intent.hasExtra(STEPS)) {
            String mStep = intent.getStringExtra(STEPS);
            Type type = new TypeToken<List<Step>>() {
            }.getType();
            steps.clear();
            steps = new GsonBuilder().create().fromJson(mStep, type);
            String title = intent.getExtras().getString("current_recipe");
            setTitle(title);
            stepId = intent.getIntExtra("STEP_ID", 1);
        }

        adapter = new SingleStepAdapter(getSupportFragmentManager(), steps, this);
        viewPager.setAdapter(adapter);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        tabLayout.setTabTextColors(R.color.colorWhite, R.color.colorAccent);
        viewPager.setCurrentItem(stepId);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() > 0) {
            getFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }
}
