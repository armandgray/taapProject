package com.armandgray.taap;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.armandgray.taap.utils.DrillsRvAdapter;
import com.armandgray.taap.utils.RecyclerItemClickListener;

import static com.armandgray.taap.utils.DrillsHelper.getDrillsList;

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
        setupSpinnerItemSelectedListener();
        setupSearchVisibility();
        setupSearchIconClickListener();
        setupEtSearchTextChangeListener();
        setupRvDrills();
        setupRvDrillsItemClickListener();
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

    private void setupSpinnerItemSelectedListener() {
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                listener.onSpinnerItemSelected(parent, view, position, id);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void setupSearchVisibility() {
        etSearch.setFocusable(true);
        etSearch.setVisibility(View.GONE);
    }

    private void setupSearchIconClickListener() {
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

    private void setupEtSearchTextChangeListener() {
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                listener.onEtSearchTextChanged(s, start, before, count);
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void setupRvDrills() {
        rvDrills = (RecyclerView) activity.findViewById(R.id.rvDrills);
        rvDrills.setAdapter(new DrillsRvAdapter(getDrillsList()));
        rvDrills.setLayoutManager(
                new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false));
    }

    private void setupRvDrillsItemClickListener() {
        rvDrills.addOnItemTouchListener(new RecyclerItemClickListener(activity,
                new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Intent intent = new Intent();
                        activity.startActivity(intent);
                    }
                }));
    }

    interface MainViewsListener {
        void onFabClick();
        void onSortClick();
        void onSpinnerItemSelected(AdapterView<?> parent, View view, int position, long id);
        void onSearchClick();
        void onEtSearchFocusChange(View v, boolean hasFocus);
        void onEtSearchTextChanged(CharSequence s, int start, int before, int count);
    }
}
