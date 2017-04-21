package com.armandgray.taap;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.armandgray.taap.models.Drill;
import com.armandgray.taap.utils.DrillsRvAdapter;

import java.util.ArrayList;

class MainActivityViews {

    MainActivity activity;
    MainViewsListener listener;

    private String[] drillsArray = {"All", "Shooting", "Ball Handling", "Passing", "Fundamentals"};
    EditText etSearch;
    FloatingActionButton fab;
    Spinner spinner;
    private ImageView ivSort;
    private ImageView ivSearch;
    RecyclerView rvDrills;

    MainActivityViews(MainActivity activity, MainViewsListener listener) {
        this.activity = activity;
        this.listener = listener;
    }

    void setupActivityInitialState() {
        activity.setContentView(R.layout.activity_main);

        assignGlobalViews();
        setupToolbar();
        setupFabClickListener();
        setupSortClickListener();
        setupSearchVisibility();
        setupSearchClickListener();
        setupRvDrills();
    }

    private void assignGlobalViews() {
        fab = (FloatingActionButton) activity.findViewById(R.id.fab);
        spinner = (Spinner) activity.findViewById(R.id.spDrillsSort);
        etSearch = (EditText) activity.findViewById(R.id.etSearch);
        ivSort = (ImageView) activity.findViewById(R.id.ivSort);
        ivSearch = (ImageView) activity.findViewById(R.id.ivSearch);
    }

    private void setupToolbar() {
        Toolbar toolbar = (Toolbar) activity.findViewById(R.id.toolbar);
        activity.setSupportActionBar(toolbar);
    }

    private void setupFabClickListener() {
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onFabClick();
            }
        });
    }

    private void setupSortClickListener() {
        ivSort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onSortClick();
            }
        });
    }

    private void setupSearchVisibility() {
        etSearch.setFocusable(true);
        etSearch.setVisibility(View.GONE);
    }

    private void setupSearchClickListener() {
        ivSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onSearchClick();
            }
        });
        etSearch.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                listener.onEtSearchFocusChange(v, hasFocus);
            }
        });
    }

    private void setupRvDrills() {
        rvDrills = (RecyclerView) activity.findViewById(R.id.rvDrills);
        ArrayList<Drill> drillList = new ArrayList<>();
        Drill drill = new Drill("2-Ball Pound Dribble", R.drawable.ic_fitness_center_white_24dp);
        drillList.add(drill);
        drillList.add(drill);
        drillList.add(drill);
        drillList.add(drill);
        drillList.add(drill);
        rvDrills.setAdapter(new DrillsRvAdapter(drillList));
        rvDrills.setLayoutManager(
                new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false));
    }

    interface MainViewsListener {
        void onFabClick();
        void onSortClick();
        void onSearchClick();
        void onEtSearchFocusChange(View v, boolean hasFocus);
    }
}
