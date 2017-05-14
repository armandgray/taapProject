package com.armandgray.taap.detail;

import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.armandgray.taap.R;
import com.armandgray.taap.models.Drill;

import static com.armandgray.taap.MainActivity.SELECTED_DRILL;

class DrillDetailViews {

    public DrillDetailActivity activity;
    DrillDetailViewsListener listener;
    Drill drill;

    FloatingActionButton fab;
    NumberPicker npSets;
    NumberPicker npReps;
    NumberPicker npSuccesses;
    Button btnFinished;

    DrillDetailViews(DrillDetailActivity activity, DrillDetailViewsListener listener) {
        this.activity = activity;
        this.listener = listener;

        setupActivityInitialState();
    }

    private void setupActivityInitialState() {
        activity.setContentView(R.layout.activity_drill_detail);
        assignGlobalViews();
        setupToolbar();
        setupNumberPickers();
        setupInitialViewVisibility();
        setupFabClickListener();
        setupBtnFinishClickListener();
    }

    private void assignGlobalViews() {
        fab = (FloatingActionButton) activity.findViewById(R.id.fab);
        npSets = (NumberPicker) activity.findViewById(R.id.npSets);
        npReps = (NumberPicker) activity.findViewById(R.id.npReps);
        npSuccesses = (NumberPicker) activity.findViewById(R.id.npSuccesses);
        btnFinished = (Button) activity.findViewById(R.id.btnFinished);
    }

    private void setupToolbar() {
        Toolbar toolbar = (Toolbar) activity.findViewById(R.id.toolbar);
        setToolbarTitle(toolbar);
        activity.setSupportActionBar(toolbar);
        if (activity.getSupportActionBar() != null) {
            activity.getSupportActionBar().setDisplayShowTitleEnabled(false);
            activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            setHomeAsUpIndicatorColor();
        }
    }

    private void setToolbarTitle(Toolbar toolbar) {
        drill = activity.getIntent().getParcelableExtra(SELECTED_DRILL);
        if (drill != null && drill.getTitle() != null && !drill.getTitle().equals("")) {
            ((TextView) toolbar.findViewById(R.id.tvTitle))
                    .setText(drill.getTitle());
        }
    }

    private void setHomeAsUpIndicatorColor() {
        final Drawable upArrow = activity.getResources().getDrawable(R.drawable.abc_ic_ab_back_material);
        upArrow.setColorFilter(activity.getResources().getColor(R.color.colorDarkGray), PorterDuff.Mode.SRC_ATOP);
        if (activity.getSupportActionBar() == null) { return; }
        activity.getSupportActionBar().setHomeAsUpIndicator(upArrow);
    }

    private void setupNumberPickers() {
        npSets.setMinValue(1);
        npSets.setMaxValue(10);
        npSets.setWrapSelectorWheel(true);

        npReps.setMinValue(1);
        npReps.setMaxValue(100);
        npReps.setWrapSelectorWheel(true);

        npSuccesses.setMinValue(0);
        npSuccesses.setMaxValue(100);
        npSuccesses.setWrapSelectorWheel(true);
    }

    private void setupInitialViewVisibility() {
        btnFinished.setVisibility(View.GONE);
    }

    private void setupFabClickListener() {
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onFabClick(v);
            }
        });
    }

    private void setupBtnFinishClickListener() {
        btnFinished.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onBtnFinishedClick(v);
            }
        });
    }

    interface DrillDetailViewsListener {
        void onFabClick(View v);
        void onBtnFinishedClick(View v);
    }

}
