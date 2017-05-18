package com.armandgray.taap;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;

import com.armandgray.taap.log.LogActivity;
import com.armandgray.taap.settings.SettingsActivity;

public class MainActivity extends AppCompatActivity {

    public static final String SELECTED_DRILL = "SELECTED_DRILL";
    public MainActivityController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        controller = new MainActivityController(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        controller.views.setMenuLogColor(menu.findItem(R.id.action_log));

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                startActivity(new Intent(this, SettingsActivity.class));
                return true;
            case R.id.action_log:
                startActivity(new Intent(this, LogActivity.class));
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        controller.dispatchTouchEvent(ev);
        return super.dispatchTouchEvent(ev);
    }
}
