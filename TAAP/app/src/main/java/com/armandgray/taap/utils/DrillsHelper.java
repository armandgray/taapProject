package com.armandgray.taap.utils;

import com.armandgray.taap.R;
import com.armandgray.taap.models.Drill;

import java.util.ArrayList;

import static com.armandgray.taap.models.Drill.FUNDAMENTALS;
import static com.armandgray.taap.models.Drill.PASSING_ARRAY;
import static com.armandgray.taap.models.Drill.SHOOTING;
import static com.armandgray.taap.models.Drill.SHOOTING_ARRAY;

public class DrillsHelper {

    private static String[] fundamentalShooting = {FUNDAMENTALS, SHOOTING};
    private static ArrayList<Drill> drillsList;

    static {
        drillsList = new ArrayList<>();
        drillsList.add(new Drill("5 Spots Shooting (Mid-Range)", R.drawable.ic_fitness_center_white_24dp, SHOOTING_ARRAY));
        drillsList.add(new Drill("5 Spots Spin-Rip Through (Mid-Range)", R.drawable.ic_fitness_center_white_24dp, SHOOTING_ARRAY));
        drillsList.add(new Drill("5 Spots Spin-Pull Up (Mid-Range)", R.drawable.ic_fitness_center_white_24dp, SHOOTING_ARRAY));
        drillsList.add(new Drill("Elbow-to-Elbow Shooting", R.drawable.ic_fitness_center_white_24dp, SHOOTING_ARRAY));
        drillsList.add(new Drill("Corner-to-Wing Shooting (Mid-Range)", R.drawable.ic_fitness_center_white_24dp, SHOOTING_ARRAY));
        drillsList.add(new Drill("Corner-to-Wing Shooting (3's)", R.drawable.ic_fitness_center_white_24dp, SHOOTING_ARRAY));
        drillsList.add(new Drill("Corner-to-Wing Shooting (NBA)", R.drawable.ic_fitness_center_white_24dp, SHOOTING_ARRAY));
        drillsList.add(new Drill("5 Spots Curl-Into-Shot (Mid-Range)", R.drawable.ic_fitness_center_white_24dp, SHOOTING_ARRAY));
        drillsList.add(new Drill("5 Spots Curl-Into-Shot (3's)", R.drawable.ic_fitness_center_white_24dp, SHOOTING_ARRAY));
        drillsList.add(new Drill("5 Spots Curl-Into-Shot (NBA)", R.drawable.ic_fitness_center_white_24dp, SHOOTING_ARRAY));
        drillsList.add(new Drill("5 Spots Curl-Into-Shot Hesitation-Pull Up (Mid-Range)", R.drawable.ic_fitness_center_white_24dp, SHOOTING_ARRAY));
        drillsList.add(new Drill("5 Spots Curl-Into-Shot Hesitation-Pull Up (3's)", R.drawable.ic_fitness_center_white_24dp, SHOOTING_ARRAY));
        drillsList.add(new Drill("5 Spots Curl-Into-Shot Hesitation-Pull Up (NBA)", R.drawable.ic_fitness_center_white_24dp, SHOOTING_ARRAY));
        drillsList.add(new Drill("Backpedal-to-Half Shooting (Mid-Range)", R.drawable.ic_fitness_center_white_24dp, SHOOTING_ARRAY));
        drillsList.add(new Drill("Backpedal-to-Half Shooting (3's)", R.drawable.ic_fitness_center_white_24dp, SHOOTING_ARRAY));
        drillsList.add(new Drill("Backpedal-to-Half Shooting (NBA)", R.drawable.ic_fitness_center_white_24dp, SHOOTING_ARRAY));
        drillsList.add(new Drill("Two-Man-Shooting", R.drawable.ic_fitness_center_white_24dp, SHOOTING_ARRAY));
        drillsList.add(new Drill("Three-Man-Shooting", R.drawable.ic_fitness_center_white_24dp, SHOOTING_ARRAY));
        drillsList.add(new Drill("Beat-the-Pro (Mid-Range)", R.drawable.ic_fitness_center_white_24dp, SHOOTING_ARRAY));
        drillsList.add(new Drill("Beat-the-Pro (3's)", R.drawable.ic_fitness_center_white_24dp, SHOOTING_ARRAY));
        drillsList.add(new Drill("Beat-the-Pro (NBA)", R.drawable.ic_fitness_center_white_24dp, SHOOTING_ARRAY));
        drillsList.add(new Drill("No Ball - Form Shooting", R.drawable.ic_fitness_center_white_24dp, fundamentalShooting));
        drillsList.add(new Drill("On Your Back - Form Shooting", R.drawable.ic_fitness_center_white_24dp, fundamentalShooting));
        drillsList.add(new Drill("Side of Backboard - Form Shooting", R.drawable.ic_fitness_center_white_24dp, fundamentalShooting));
        drillsList.add(new Drill("Away From Basket - Form Shooting", R.drawable.ic_fitness_center_white_24dp, fundamentalShooting));
        drillsList.add(new Drill("Shoot to a Partner - Form Shooting", R.drawable.ic_fitness_center_white_24dp, fundamentalShooting));
        drillsList.add(new Drill("Shoot to a Partner - Form Shooting (Increasing Height)", R.drawable.ic_fitness_center_white_24dp, fundamentalShooting));
        drillsList.add(new Drill("Form Shooting", R.drawable.ic_fitness_center_white_24dp, fundamentalShooting));
        drillsList.add(new Drill("Beat the Pro - Form Shooting", R.drawable.ic_fitness_center_white_24dp, SHOOTING_ARRAY));
        drillsList.add(new Drill("Chest Pass - Partner Passing", R.drawable.ic_fitness_center_white_24dp, PASSING_ARRAY));
        drillsList.add(new Drill("Bounce Pass - Partner Passing", R.drawable.ic_fitness_center_white_24dp, PASSING_ARRAY));
        drillsList.add(new Drill("Overhead Pass - Partner Passing", R.drawable.ic_fitness_center_white_24dp, PASSING_ARRAY));
        drillsList.add(new Drill("Chest Pass (1-Hand) - Partner Passing", R.drawable.ic_fitness_center_white_24dp, PASSING_ARRAY));
        drillsList.add(new Drill("Bounce Pass (1-Hand) - Partner Passing", R.drawable.ic_fitness_center_white_24dp, PASSING_ARRAY));
        drillsList.add(new Drill("Overhead Pass (1-Hand) - Partner Passing", R.drawable.ic_fitness_center_white_24dp, PASSING_ARRAY));
        drillsList.add(new Drill("Wrap-Around Pass (1-Hand) - Partner Passing", R.drawable.ic_fitness_center_white_24dp, PASSING_ARRAY));
        drillsList.add(new Drill("Chest Pass - Against the Wall", R.drawable.ic_fitness_center_white_24dp, PASSING_ARRAY));
        drillsList.add(new Drill("Bounce Pass - Against the Wall", R.drawable.ic_fitness_center_white_24dp, PASSING_ARRAY));
        drillsList.add(new Drill("Overhead Pass - Against the Wall", R.drawable.ic_fitness_center_white_24dp, PASSING_ARRAY));
        drillsList.add(new Drill("Chest Pass (1-Hand) - Against the Wall", R.drawable.ic_fitness_center_white_24dp, PASSING_ARRAY));
        drillsList.add(new Drill("Bounce Pass (1-Hand) - Against the Wall", R.drawable.ic_fitness_center_white_24dp, PASSING_ARRAY));
        drillsList.add(new Drill("Overhead Pass (1-Hand) - Against the Wall", R.drawable.ic_fitness_center_white_24dp, PASSING_ARRAY));
        drillsList.add(new Drill("Wrap-Around Pass (1-Hand) - Against the Wall", R.drawable.ic_fitness_center_white_24dp, PASSING_ARRAY)); drillsList.add(new Drill("Partner Passing - Chest Pass", R.drawable.ic_fitness_center_white_24dp, PASSING_ARRAY));
        drillsList.add(new Drill("Monkey-in-the-Middle", R.drawable.ic_fitness_center_white_24dp, PASSING_ARRAY));
        drillsList.add(new Drill("Crazy Man", R.drawable.ic_fitness_center_white_24dp, PASSING_ARRAY));
        drillsList.add(new Drill("Pass & Pass Back (Chest Pass)", R.drawable.ic_fitness_center_white_24dp, PASSING_ARRAY));
        drillsList.add(new Drill("Pass & Pass Back (Bounce Pass)", R.drawable.ic_fitness_center_white_24dp, PASSING_ARRAY));
        drillsList.add(new Drill("Pass & Pass Back (Right Layup)", R.drawable.ic_fitness_center_white_24dp, PASSING_ARRAY));
        drillsList.add(new Drill("Pass & Pass Back (Left Layup)", R.drawable.ic_fitness_center_white_24dp, PASSING_ARRAY));
        drillsList.add(new Drill("Pass & Pass Back (Left Pull Up)", R.drawable.ic_fitness_center_white_24dp, PASSING_ARRAY));
        drillsList.add(new Drill("Pass & Pass Back (Right Pull Up)", R.drawable.ic_fitness_center_white_24dp, PASSING_ARRAY));
        drillsList.add(new Drill("Pass & Pass Back (Middle Pull Up)", R.drawable.ic_fitness_center_white_24dp, PASSING_ARRAY));
        drillsList.add(new Drill("3 Man Weave", R.drawable.ic_fitness_center_white_24dp, PASSING_ARRAY));
        drillsList.add(new Drill("5 Man Weave", R.drawable.ic_fitness_center_white_24dp, PASSING_ARRAY));
        drillsList.add(new Drill("3 Man Weave - Transition Defense", R.drawable.ic_fitness_center_white_24dp, PASSING_ARRAY));
        drillsList.add(new Drill("5 Man Weave - Tranisiton Defense", R.drawable.ic_fitness_center_white_24dp, PASSING_ARRAY));
        drillsList.add(new Drill("3-2-1-0 Dribble", R.drawable.ic_fitness_center_white_24dp, PASSING_ARRAY));
        drillsList.add(new Drill("Medicine Ball Passing", R.drawable.ic_fitness_center_white_24dp, PASSING_ARRAY));
    }

    public static ArrayList<Drill> getDrillsList() {
        return drillsList;
    }
}
