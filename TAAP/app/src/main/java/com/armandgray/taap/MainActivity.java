package com.armandgray.taap;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    public MainActivityController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        controller = new MainActivityController(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                controller.views.etSearch.setVisibility(View.VISIBLE);
                controller.views.fab.setVisibility(View.GONE);
                controller.views.spinner.setVisibility(View.GONE);
                return true;
            case R.id.action_log:
                startActivity(new Intent());
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
