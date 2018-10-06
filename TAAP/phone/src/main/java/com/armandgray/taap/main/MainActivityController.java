package com.armandgray.taap.main;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import androidx.annotation.VisibleForTesting;
import androidx.appcompat.app.ActionBar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

import com.armandgray.taap.R;
import com.armandgray.taap.detail.DrillDetailActivity;
import com.armandgray.taap.log.LogActivity;
import com.armandgray.taap.models.Drill;
import com.armandgray.taap.settings.SettingsActivity;
import com.armandgray.taap.utils.ActivitySetupHelper;
import com.armandgray.taap.utils.MVCActivityController;

import static com.armandgray.taap.db.CursorDataHelper.getDrillsListFromDatabase;

class MainActivityController extends MVCActivityController
        implements MainActivityViews.MainViewsListener {

    @VisibleForTesting final ActivitySetupHelper.ActivityViewsInterface viewsInterface;
    @VisibleForTesting Context context;

    MainActivityController(Context context, ActionBar actionBar,
                           ActivitySetupHelper.ActivityViewsInterface viewsInterface) {
        this.context = context;
        this.viewsInterface = viewsInterface;

        setupActivityViewController(actionBar, context);
    }

    @Override
    public void setupActivityViewController(ActionBar actionBar, Context context) {
        viewsInterface.setListener(this);
        viewsInterface.setupActivityCoordinatorWidgets();
        viewsInterface.setupActivityInitialState();
        viewsInterface.updateData(getDrillsListFromDatabase(context));
    }

    @Override
    public void onFabClick() {
        viewsInterface.updateData(MainActivityViews.ON_FAB_CLICK);
    }

    @Override
    public void onSortClick() {
        viewsInterface.updateData(MainActivityViews.ON_SORT_CLICK);
    }

    @Override
    public void onSpinnerItemSelected(String drillType) {
        viewsInterface.updateData(drillType);
    }

    @Override
    public void onSearchClick() {
        viewsInterface.updateData(MainActivityViews.ON_SEARCH_CLICK);
    }

    @Override
    public void onEtSearchFocusChange(boolean hasFocus) {
        if (!hasFocus) {
            viewsInterface.updateData(MainActivityViews.ON_ET_SEARCH_FOCUS_CHANGE);
        }
    }

    @Override
    public void onEtSearchTextChanged(String query) {
        viewsInterface.updateData(query);
    }

    @Override
    public void onRvDrillsItemTouch(Drill drill) {
        Intent intent = new Intent(context, DrillDetailActivity.class);
        intent.putExtra(MainActivity.SELECTED_DRILL, drill);
        context.startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        viewsInterface.updateData(menu.findItem(R.id.action_log));
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                context.startActivity(new Intent(context, SettingsActivity.class));
                return;
            case R.id.action_log:
                context.startActivity(new Intent(context, LogActivity.class));
                return;
        }
    }

    @Override
    public void dispatchTouchEvent(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN && v instanceof EditText) {
            Rect outRect = new Rect();
            v.getGlobalVisibleRect(outRect);
            if (!outRect.contains((int) event.getRawX(), (int) event.getRawY())) {
                viewsInterface.updateData(v);
            }
        }
    }
}
