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

    public void setupActivityInitialState() {
        activity.setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) activity.findViewById(R.id.toolbar);
        activity.setSupportActionBar(toolbar);

        fab = (FloatingActionButton) activity.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO Add onClick action to fab
            }
        });

        spinner = (Spinner) activity.findViewById(R.id.spDrillsSort);
        spinner.setAdapter(createSpinnerAdapter());

        searchView = (SearchView) activity.findViewById(R.id.searchView);
        searchView.setVisibility(View.GONE);
    }

    private ArrayAdapter<String> createSpinnerAdapter() {
        return new ArrayAdapter<>(activity,
                R.layout.spinner_drills_text_layout, R.id.tvSpinnerDrill, drillsArray);
    }
}
