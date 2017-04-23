package com.armandgray.taap;

import android.app.Service;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.SpinnerAdapter;

import com.armandgray.taap.utils.DrillsRvAdapter;

class MainActivityController implements MainActivityViews.MainViewsListener {

    MainActivity activity;
    MainActivityViews views;
    private boolean isQueryCall;

    MainActivityController(MainActivity activity) {
        this.activity = activity;
        this.views = new MainActivityViews(activity, this);

        views.setupActivityInitialState();
    }

    @Override
    public void onFabClick() {
        // TODO add FAB click action
    }

    @Override
    public void onSortClick() {
        views.spinner.performClick();
        views.spinner.requestFocusFromTouch();
    }

    @Override
    public void onSpinnerItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (isQueryCall) {
            isQueryCall = false;
            return;
        }
        String[] drillTypes = getAllSpinnerItems(views.spinner.getAdapter());
        ((DrillsRvAdapter) views.rvDrills.getAdapter())
                .swapRvDrillsAdapterDataOnDrillType(drillTypes[position]);
    }

    String[] getAllSpinnerItems(SpinnerAdapter adapter) {
        String[] drillTypes = new String[adapter.getCount()];
        for (int i = 0; i < adapter.getCount(); i++) {
            drillTypes[i] = (String) adapter.getItem(i);
        }
        return drillTypes;
    }

    @Override
    public void onSearchClick() {
        views.etSearch.setVisibility(View.VISIBLE);
        views.spinner.setVisibility(View.GONE);
        views.fab.setVisibility(View.GONE);
        views.etSearch.requestFocusFromTouch();
        ((InputMethodManager) activity.getSystemService(Service.INPUT_METHOD_SERVICE))
                .showSoftInput(views.etSearch, 0);
    }

    @Override
    public void onEtSearchFocusChange(View v, boolean hasFocus) {
        if (!hasFocus) {
            views.etSearch.setVisibility(View.GONE);
            views.spinner.setVisibility(View.VISIBLE);
            views.fab.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onEtSearchTextChanged(CharSequence s, int start, int before, int count) {
        String query = views.etSearch.getText().toString();
        ((DrillsRvAdapter) views.rvDrills.getAdapter())
                .swapRvDrillsAdapterDataOnQuery(query);
        addSearchQueryToSpinner(query);
    }

    private void addSearchQueryToSpinner(String query) {
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(
                activity,
                android.R.layout.simple_spinner_item,
                android.R.id.text1);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        isQueryCall = true;
        views.spinner.setAdapter(spinnerAdapter);
        spinnerAdapter.addAll(activity.getResources().getStringArray(R.array.drill_types));
        spinnerAdapter.add("Search: " + query);
        spinnerAdapter.notifyDataSetChanged();
        isQueryCall = true;
        views.spinner.setSelection(spinnerAdapter.getCount() - 1);
    }

    void dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            View v = activity.getCurrentFocus();
            if ( v instanceof EditText) {
                Rect outRect = new Rect();
                v.getGlobalVisibleRect(outRect);
                if (!outRect.contains((int) event.getRawX(), (int) event.getRawY())) {
                    v.clearFocus();
                    ((InputMethodManager) activity.getSystemService(Service.INPUT_METHOD_SERVICE))
                            .hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        }
    }
}
