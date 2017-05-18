package com.armandgray.taap;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.VisibleForTesting;
import android.support.v7.app.AppCompatActivity;

import com.armandgray.taap.models.Drill;

import java.util.List;

import static com.armandgray.taap.db.DatabaseContentProvider.insertDrillToDatabase;
import static com.armandgray.taap.utils.CursorDataHelper.getDrillsListFromDatabase;
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
        List<Drill> drills = getDrillsListFromDatabase(this);
        if (drills != null && drills.size() > 0) { return; }
        for (Drill drill : getDrillsList()) { insertDrillToDatabase(drill, this); }
    }
}
