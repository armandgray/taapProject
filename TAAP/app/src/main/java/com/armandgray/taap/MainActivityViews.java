package com.armandgray.taap;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

class MainActivityViews {

    MainActivity activity;

    private String[] drillsArray = {"All", "Shooting", "Ball Handling", "Passing", "Fundamentals"};
    SearchView searchView;
    FloatingActionButton fab;
    Spinner spinner;

    MainActivityViews(MainActivity activity) {
        this.activity = activity;
    }

    void setupActivityInitialState() {
        activity.setContentView(R.layout.activity_main);

        assignGlobalViews();
        setupToolbar();
        setupFabClickListener();
        setupSortAndSearch();
    }

    private void setupSortAndSearch() {
        spinner.setAdapter(createSpinnerAdapter());
        searchView.setVisibility(View.GONE);
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

    private void assignGlobalViews() {
        fab = (FloatingActionButton) activity.findViewById(R.id.fab);
        spinner = (Spinner) activity.findViewById(R.id.spDrillsSort);
        searchView = (SearchView) activity.findViewById(R.id.searchView);
    }

    private ArrayAdapter<String> createSpinnerAdapter() {
        return new ArrayAdapter<>(activity,
                R.layout.spinner_drills_text_layout, R.id.tvSpinnerDrill, drillsArray);
    }
}
