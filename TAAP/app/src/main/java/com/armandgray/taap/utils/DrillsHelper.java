package com.armandgray.taap.utils;

import com.armandgray.taap.R;
import com.armandgray.taap.models.Drill;

import java.util.ArrayList;

public class DrillsHelper {

    public static ArrayList<Drill> getDrillsList() {
        ArrayList<Drill> drillsList = new ArrayList<>();
        drillsList.add(new Drill("2-Ball Pound Dribble", R.drawable.ic_fitness_center_white_24dp));
        return drillsList;
    }
}
