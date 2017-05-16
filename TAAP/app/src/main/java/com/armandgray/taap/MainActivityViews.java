package com.armandgray.taap;

import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.armandgray.taap.utils.DrillsRvAdapter;
import com.armandgray.taap.utils.RecyclerItemClickListener;

import static com.armandgray.taap.utils.CursorDataHelper.getDrillsListFromDatabase;

class MainActivityViews {

    private final MainActivity activity;
    private final MainViewsListener listener;

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
                listener.onSpinnerItemSelected(position);
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
                listener.onEtSearchFocusChange(hasFocus);
            }
        });
    }

    private void setupEtSearchTextChangeListener() {
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                listener.onEtSearchTextChanged();
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
        rvDrills.setAdapter(new DrillsRvAdapter(getDrillsListFromDatabase(activity)));
        rvDrills.setLayoutManager(
                new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false));
        setupRvDrillsItemClickListener();
    }

    private void setupRvDrillsItemClickListener() {
        rvDrills.addOnItemTouchListener(new RecyclerItemClickListener(activity,
                new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        listener.onRvDrillsItemTouch(position);
                    }
                }));
    }

    void setMenuLogColor(MenuItem menuItem) {
        Drawable drawable = menuItem.getIcon();
        if (drawable != null) {
            drawable.mutate();
            //noinspection deprecation
            drawable.setColorFilter(activity.getResources().getColor(R.color.colorDarkGray),
                    PorterDuff.Mode.SRC_ATOP);
        }
    }

    interface MainViewsListener {
        void onFabClick();
        void onSortClick();
        void onSpinnerItemSelected(int position);
        void onSearchClick();
        void onEtSearchFocusChange(boolean hasFocus);
        void onEtSearchTextChanged();
        void onRvDrillsItemTouch(int position);
    }
}
