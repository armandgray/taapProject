package com.armandgray.taap.main;

import android.app.Service;
import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.annotation.VisibleForTesting;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import com.armandgray.taap.R;
import com.armandgray.taap.models.Drill;
import com.armandgray.taap.utils.ActivitySetupHelper;
import com.armandgray.taap.utils.DrillsRvAdapter;
import com.armandgray.taap.utils.RecyclerItemClickListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.armandgray.taap.utils.DrillsRvAdapter.SEARCH;

class MainActivityViews implements ActivitySetupHelper.ActivityViewsInterface {

    static final String ON_FAB_CLICK = "onFABClick";
    static final String ON_SORT_CLICK = "onSortClick";
    static final String ON_SEARCH_CLICK = "onSearchClick";
    static final String ON_ET_SEARCH_FOCUS_CHANGE = "onEtSearchFocusChange";
    @VisibleForTesting static final String[] ACTIONS = { ON_FAB_CLICK, ON_SORT_CLICK,
            ON_SEARCH_CLICK, ON_ET_SEARCH_FOCUS_CHANGE };

    @VisibleForTesting final View rootView;
    @VisibleForTesting MainViewsListener listener;
    @VisibleForTesting FloatingActionButton fab;

    private final Context context;

    private EditText etSearch;
    private Spinner spinner;
    private ImageView ivSort;
    private ImageView ivSearch;
    private RecyclerView rvDrills;

    MainActivityViews(View rootView, FloatingActionButton fab, Context context) {
        this.rootView = rootView;
        this.fab = fab;
        this.context = context;
    }

    @Override
    public void setListener(Object object) {
        if (object instanceof MainViewsListener) {
            this.listener = (MainViewsListener) object;
            return;
        }

        throw new IllegalArgumentException(object.getClass().getName()
                + " must implement MainViewsListener");
    }

    @Override
    public void setupActivityCoordinatorWidgets() {
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onFabClick();
            }
        });
    }

    @Override
    public void setupActivityInitialState() {
        if (listener == null) {
            throw new IllegalArgumentException(getClass().getName()
                    + " requires MainViewsListener (ie. Call setListener())");
        }

        assignGlobalViews();
        setupSortClickListener();
        setupSpinnerItemSelectedListener();
        setupSearchVisibility();
        setupSearchIconClickListener();
        setupEtSearchTextChangeListener();
        setupRvDrills();
    }

    private void assignGlobalViews() {
        spinner = (Spinner) rootView.findViewById(R.id.spDrillsSort);
        etSearch = (EditText) rootView.findViewById(R.id.etSearch);
        ivSort = (ImageView) rootView.findViewById(R.id.ivSort);
        ivSearch = (ImageView) rootView.findViewById(R.id.ivSearch);
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
                List<String> drillTypes = getAllSpinnerItems(spinner.getAdapter());
                listener.onSpinnerItemSelected(drillTypes.get(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private List<String> getAllSpinnerItems(SpinnerAdapter adapter) {
        List<String> drillTypes = new ArrayList<>();
        for (int i = 0; i < adapter.getCount(); i++) {
            drillTypes.add((String) adapter.getItem(i));
        }

        return drillTypes;
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
                listener.onEtSearchTextChanged(etSearch.getText().toString());
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
        rvDrills = (RecyclerView) rootView.findViewById(R.id.rvDrills);
        rvDrills.setAdapter(new DrillsRvAdapter(new ArrayList<Drill>()));
        rvDrills.setLayoutManager(
                new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        setupRvDrillsItemClickListener();
    }

    private void setupRvDrillsItemClickListener() {
        rvDrills.addOnItemTouchListener(new RecyclerItemClickListener(context,
                new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        DrillsRvAdapter adapter = (DrillsRvAdapter) rvDrills.getAdapter();
                        listener.onRvDrillsItemTouch(adapter.getItemAtPosition(position));
                    }
                }));
    }

    private void setMenuItemLogColor(MenuItem menuItem) {
        Drawable drawable = menuItem.getIcon();
        if (drawable != null) {
            drawable.mutate();
            drawable.setColorFilter(rootView.getResources().getColor(R.color.colorDarkGray),
                    PorterDuff.Mode.SRC_ATOP);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public void updateData(Object object) {
        if (object instanceof MenuItem) {
            setMenuItemLogColor((MenuItem) object);
            return;
        }

        if (object instanceof EditText && object.equals(etSearch)) {
            etSearch.clearFocus();
            InputMethodManager inputMethodManager = (InputMethodManager) context
                    .getSystemService(Service.INPUT_METHOD_SERVICE);
            if (inputMethodManager != null) {
                inputMethodManager.hideSoftInputFromWindow(etSearch.getWindowToken(), 0);
            }
        }

        if (object instanceof List) {
            List list = (List) object;
            if (list.size() > 0 && !(list.get(0) instanceof Drill)) {
                return;
            }

            updateData(list);
            return;
        }

        if (object instanceof String) {
            String s = (String) object;
            if (getAllSpinnerItems(spinner.getAdapter()).contains(s)) {
                updateForDrillType(s);
            } else if (Arrays.asList(ACTIONS).contains(s)) {
                updateForAction(s);
            } else {
                updateForSearchQuery(s);
            }
        }
    }

    private void updateData(List<Drill> drills) {
        DrillsRvAdapter adapter = (DrillsRvAdapter) rvDrills.getAdapter();
        adapter.swapDataSet(drills);
    }

    private void updateForDrillType(String drillType) {
        ((DrillsRvAdapter) rvDrills.getAdapter())
                .swapRvDrillsAdapterDataOnDrillType(drillType);
    }

    private void updateForAction(String action) {
        switch (action) {
            case ON_SORT_CLICK:
                spinner.performClick();
                spinner.requestFocusFromTouch();
                return;

            case ON_FAB_CLICK:
                Toast.makeText(context, "Feature Coming Soon!", Toast.LENGTH_SHORT).show();
                return;

            case ON_SEARCH_CLICK:
                etSearch.setVisibility(View.VISIBLE);
                spinner.setVisibility(View.GONE);
                fab.setVisibility(View.GONE);
                etSearch.requestFocusFromTouch();
                InputMethodManager inputMethodManager = (InputMethodManager) context
                        .getSystemService(Service.INPUT_METHOD_SERVICE);
                if (inputMethodManager != null) {
                    inputMethodManager.showSoftInput(etSearch, 0);
                }

                return;

            case ON_ET_SEARCH_FOCUS_CHANGE:
                etSearch.setVisibility(View.GONE);
                spinner.setVisibility(View.VISIBLE);
                fab.setVisibility(View.VISIBLE);
                return;
        }
    }

    private void updateForSearchQuery(String query) {
        ((DrillsRvAdapter) rvDrills.getAdapter())
                .swapRvDrillsAdapterDataOnQuery(query);
        addSearchQueryToSpinner(query);
    }

    private void addSearchQueryToSpinner(String query) {
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(
                context,
                android.R.layout.simple_spinner_item,
                android.R.id.text1);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);
        spinnerAdapter.addAll(context.getResources().getStringArray(R.array.drill_types));
        spinnerAdapter.add(SEARCH + query);
        spinnerAdapter.notifyDataSetChanged();
        spinner.setSelection(spinnerAdapter.getCount() - 1);
    }

    interface MainViewsListener {
        void onFabClick();
        void onSortClick();
        void onSpinnerItemSelected(String drillType);
        void onSearchClick();
        void onEtSearchFocusChange(boolean hasFocus);
        void onEtSearchTextChanged(String s);
        void onRvDrillsItemTouch(Drill drill);
    }
}
