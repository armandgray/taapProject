package com.armandgray.taap.detail;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class DrillDetailActivity extends AppCompatActivity {

    DrillDetailController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        controller = new DrillDetailController(this);
    }

}
