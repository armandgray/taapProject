package com.armandgray.taap.activity.main;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageButton;
import android.widget.TextView;

import com.armandgray.shared.db.ShootingPercentageViewModel;
import com.armandgray.taap.R;

public class MainActivity extends AppCompatActivity {

    private ShootingPercentageViewModel viewModel;

    private TextView textDrill;
    private FloatingActionButton buttonMinus;
    private TextView textRate;
    private FloatingActionButton buttonPlus;
    private ImageButton buttonMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        assignGlobalFields();
        setupButtons();
        setupEventListeners();
        setupViewModel();
    }

    private void assignGlobalFields() {
        textDrill = findViewById(R.id.textDrill);
        buttonMinus = findViewById(R.id.buttonMinus);
        textRate = findViewById(R.id.textRate);
        buttonPlus = findViewById(R.id.buttonPlus);
        buttonMenu = findViewById(R.id.buttonMenu);
    }

    private void setupButtons() {
        buttonMinus.setImageResource(R.drawable.ic_remove_white_24dp);
        buttonPlus.setImageResource(R.drawable.ic_add_white_24dp);
    }

    private void setupEventListeners() {
        buttonMinus.setOnClickListener(view -> viewModel.addMiss());
        buttonPlus.setOnClickListener(view -> viewModel.addMake());
    }

    private void setupViewModel() {
        viewModel = ViewModelProviders.of(this).get(ShootingPercentageViewModel.class);
        viewModel.getCurrentRate().observe(this, rate -> {
            if (rate != null) {
                textDrill.setText(rate.getDrill());
                textRate.setText(getString(R.string.performanceRate,
                        rate.getCount(), rate.getTotal()));
            }
        });
    }
}
