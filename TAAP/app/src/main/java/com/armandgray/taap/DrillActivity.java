package com.armandgray.taap;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.armandgray.taap.drills.DrillActivityController;

public class DrillActivity extends AppCompatActivity {

    public DrillActivityController controller;

    String[] drillsArray = {"All", "Shooting", "Ball Handling", "Passing", "Fundamentals"};
    private SearchView searchView;
    private FloatingActionButton fab;
    private Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drill);

        controller = new DrillActivityController();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO Add onClick action to fab
            }
        });

        spinner = (Spinner) findViewById(R.id.spDrillsSort);
        spinner.setAdapter(createSpinnerAdapter());

        searchView = (SearchView) findViewById(R.id.searchView);
        searchView.setVisibility(View.GONE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_drill, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                searchView.setVisibility(View.VISIBLE);
                fab.setVisibility(View.GONE);
                spinner.setVisibility(View.GONE);
                return true;
            case R.id.action_log:
                startActivity(new Intent(this, LogActivity.class));
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private ArrayAdapter<String> createSpinnerAdapter() {
        return new ArrayAdapter<>(this,
                R.layout.spinner_drills_text_layout, R.id.tvSpinnerDrill, drillsArray);
    }
}
