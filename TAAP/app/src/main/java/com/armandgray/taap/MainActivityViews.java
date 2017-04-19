package com.armandgray.taap;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;

class MainActivityViews {

    MainActivity activity;

    private String[] drillsArray = {"All", "Shooting", "Ball Handling", "Passing", "Fundamentals"};
    EditText etSearch;
    FloatingActionButton fab;
    Spinner spinner;
    private ImageButton ibSearch;

    MainActivityViews(MainActivity activity) {
        this.activity = activity;
    }

    void setupActivityInitialState() {
        activity.setContentView(R.layout.activity_main);

        assignGlobalViews();
        setupToolbar();
        setupFabClickListener();
        setupSearchVisibility();
        setupSearchClickListener();
    }

    private void assignGlobalViews() {
        fab = (FloatingActionButton) activity.findViewById(R.id.fab);
        spinner = (Spinner) activity.findViewById(R.id.spDrillsSort);
        etSearch = (EditText) activity.findViewById(R.id.etSearch);
        ibSearch = (ImageButton) activity.findViewById(R.id.ibSearch);
    }

    private void setupToolbar() {
        Toolbar toolbar = (Toolbar) activity.findViewById(R.id.toolbar);
        activity.setSupportActionBar(toolbar);
    }

    private void setupFabClickListener() {
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO Add onClick action to fab
            }
        });
    }

    private void setupSearchVisibility() {
        etSearch.setVisibility(View.GONE);
    }

    private void setupSearchClickListener() {
        ibSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etSearch.setVisibility(View.VISIBLE);
                spinner.setVisibility(View.GONE);
                fab.setVisibility(View.GONE);
            }
        });
    }
}
