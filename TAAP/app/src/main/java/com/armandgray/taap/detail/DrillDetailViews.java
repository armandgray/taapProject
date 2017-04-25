package com.armandgray.taap.detail;

import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.armandgray.taap.R;
import com.armandgray.taap.models.Drill;

import static com.armandgray.taap.MainActivity.SELECTED_DRILL;

class DrillDetailViews {

    public DrillDetailActivity activity;
    private FloatingActionButton fab;
    private NumberPicker npSets;
    private NumberPicker npReps;

    DrillDetailViews(DrillDetailActivity activity) {
        this.activity = activity;

        setupActivityInitialState();
    }

    private void setupActivityInitialState() {
        activity.setContentView(R.layout.activity_drill_detail);
        assignGlobalViews();
        setupToolbar();
        setupNumberPickers();
    }

    private void assignGlobalViews() {
        fab = (FloatingActionButton) activity.findViewById(R.id.fab);
        npSets = (NumberPicker) activity.findViewById(R.id.npSets);
        npReps = (NumberPicker) activity.findViewById(R.id.npReps);
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
        Drill drill = activity.getIntent().getParcelableExtra(SELECTED_DRILL);
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

        npReps.setMinValue(0);
        npReps.setMaxValue(100);
        npReps.setWrapSelectorWheel(true);
    }

}
