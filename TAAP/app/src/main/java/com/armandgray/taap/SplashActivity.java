package com.armandgray.taap;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.VisibleForTesting;
import android.support.v7.app.AppCompatActivity;

import com.armandgray.taap.models.Drill;

import static com.armandgray.taap.db.DatabaseContentProvider.insertDrillToDatabase;
import static com.armandgray.taap.utils.DrillsHelper.getDrillsList;

public class SplashActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        insertAllDrillsToDatabase();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
        System.out.println("Create");
    }

    @VisibleForTesting
    void insertAllDrillsToDatabase() {

        for (Drill drill : getDrillsList()) { insertDrillToDatabase(drill, this); }
    }
}
