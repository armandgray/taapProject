package com.armandgray.taap.splash;

import android.content.Intent;
import android.os.Bundle;

import com.armandgray.shared.model.Drill;
import com.armandgray.taap.main.MainActivity;

import java.util.List;

import androidx.annotation.VisibleForTesting;
import androidx.appcompat.app.AppCompatActivity;

import static com.armandgray.taap.db.CursorDataHelper.getDrillsListFromDatabase;
//import static com.armandgray.taap.db.DrillsDataHelper.getDrillsList;

public class SplashActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        insertAllDrillsToDatabase();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @VisibleForTesting
    void insertAllDrillsToDatabase() {
        List<Drill> drills = getDrillsListFromDatabase(this);
        if (drills != null && drills.size() > 0) { return; }
//        for (Drill drill : getDrillsList()) { insertDrillToDatabase(drill, this); }
    }
}
