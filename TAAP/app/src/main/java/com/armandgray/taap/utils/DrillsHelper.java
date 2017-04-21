package com.armandgray.taap.utils;

import com.armandgray.taap.R;
import com.armandgray.taap.models.Drill;

import java.util.ArrayList;

public class DrillsHelper {

    public static ArrayList<Drill> getDrillsList() {
        ArrayList<Drill> drillsList = new ArrayList<>();
        drillsList.add(new Drill("5 Spots Shooting (Mid-Range)", R.drawable.ic_fitness_center_white_24dp));
        drillsList.add(new Drill("5 Spots Spin-Rip Through (Mid-Range)", R.drawable.ic_fitness_center_white_24dp));
        drillsList.add(new Drill("5 Spots Spin-Pull Up (Mid-Range)", R.drawable.ic_fitness_center_white_24dp));
        drillsList.add(new Drill("Elbow-to-Elbow Shooting", R.drawable.ic_fitness_center_white_24dp));
        drillsList.add(new Drill("Corner-to-Wing Shooting (Mid-Range)", R.drawable.ic_fitness_center_white_24dp));
        drillsList.add(new Drill("Corner-to-Wing Shooting (3's)", R.drawable.ic_fitness_center_white_24dp));
        drillsList.add(new Drill("Corner-to-Wing Shooting (NBA)", R.drawable.ic_fitness_center_white_24dp));
        drillsList.add(new Drill("5 Spots Curl-Into-Shot (Mid-Range)", R.drawable.ic_fitness_center_white_24dp));
        drillsList.add(new Drill("5 Spots Curl-Into-Shot (3's)", R.drawable.ic_fitness_center_white_24dp));
        drillsList.add(new Drill("5 Spots Curl-Into-Shot (NBA)", R.drawable.ic_fitness_center_white_24dp));
        drillsList.add(new Drill("5 Spots Curl-Into-Shot Hesitation-Pull Up (Mid-Range)", R.drawable.ic_fitness_center_white_24dp));
        drillsList.add(new Drill("5 Spots Curl-Into-Shot Hesitation-Pull Up (3's)", R.drawable.ic_fitness_center_white_24dp));
        drillsList.add(new Drill("5 Spots Curl-Into-Shot Hesitation-Pull Up (NBA)", R.drawable.ic_fitness_center_white_24dp));
        drillsList.add(new Drill("Backpedal-to-Half Shooting (Mid-Range)", R.drawable.ic_fitness_center_white_24dp));
        drillsList.add(new Drill("Backpedal-to-Half Shooting (3's)", R.drawable.ic_fitness_center_white_24dp));
        drillsList.add(new Drill("Backpedal-to-Half Shooting (NBA)", R.drawable.ic_fitness_center_white_24dp));
        drillsList.add(new Drill("Beat-the-Pro (Mid-Range)", R.drawable.ic_fitness_center_white_24dp));
        drillsList.add(new Drill("Beat-the-Pro (3's)", R.drawable.ic_fitness_center_white_24dp));
        drillsList.add(new Drill("Beat-the-Pro (NBA)", R.drawable.ic_fitness_center_white_24dp));
        drillsList.add(new Drill("", R.drawable.ic_fitness_center_white_24dp));
        return drillsList;
    }
}
