package com.armandgray.taap.utils;

import android.content.Context;
import androidx.appcompat.app.ActionBar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by armandgray on 1/14/18.
 */

public abstract class MVCActivityController implements ActivitySetupHelper.ActivityControllerInterface {

    public void setupActionBar(ActionBar actionBar, Context context) {
    }

    public abstract void setupActivityViewController(ActionBar actionBar, Context context);

    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    public void onOptionsItemSelected(MenuItem item) {
    }

    public void dispatchTouchEvent(View v, MotionEvent ev) {
    }

}
