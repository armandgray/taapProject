package com.armandgray.taap.utils;

import com.armandgray.taap.R;
import com.armandgray.taap.models.Drill;

import java.util.ArrayList;

import static com.armandgray.taap.models.Drill.BALL_HANDLING;
import static com.armandgray.taap.models.Drill.BALL_HANDLING_ARRAY;
import static com.armandgray.taap.models.Drill.CONDITIONING;
import static com.armandgray.taap.models.Drill.CONDITIONING_ARRAY;
import static com.armandgray.taap.models.Drill.DEFENSE;
import static com.armandgray.taap.models.Drill.DEFENSE_ARRAY;
import static com.armandgray.taap.models.Drill.DRIVING;
import static com.armandgray.taap.models.Drill.FUNDAMENTALS;
import static com.armandgray.taap.models.Drill.OFFENSE;
import static com.armandgray.taap.models.Drill.PASSING_ARRAY;
import static com.armandgray.taap.models.Drill.SHOOTING;
import static com.armandgray.taap.models.Drill.SHOOTING_ARRAY;

public class DrillsHelper {

    private static final String[] fundamentalShooting = {FUNDAMENTALS, SHOOTING};
    private static final String[] fundamentalBallHandling = {FUNDAMENTALS, BALL_HANDLING};
    private static final String[] fundamentalDriving = {FUNDAMENTALS, DRIVING};
    private static final String[] fundamentalDefense = {FUNDAMENTALS, DEFENSE};
    private static final String[] fundamentalDefenseConditioning = {FUNDAMENTALS, DEFENSE, CONDITIONING};
    private static final String[] defenseOffense = {DEFENSE, OFFENSE};
    private static final ArrayList<Drill> drillsList;

    static {
        drillsList = new ArrayList<>();
        drillsList.add(new Drill("Free Throws", R.drawable.ic_fitness_center_white_24dp, fundamentalShooting));
        drillsList.add(new Drill("5 Spots Shooting (Mid-Range)", R.drawable.ic_fitness_center_white_24dp, fundamentalShooting));
        drillsList.add(new Drill("5 Spots Spin-Rip Through (Mid-Range)", R.drawable.ic_fitness_center_white_24dp, fundamentalShooting));
        drillsList.add(new Drill("5 Spots Spin-Pull Up (Mid-Range)", R.drawable.ic_fitness_center_white_24dp, fundamentalShooting));
        drillsList.add(new Drill("Elbow-to-Elbow Shooting", R.drawable.ic_fitness_center_white_24dp, fundamentalShooting));
        drillsList.add(new Drill("Corner-to-Wing Shooting (Mid-Range)", R.drawable.ic_fitness_center_white_24dp, fundamentalShooting));
        drillsList.add(new Drill("Corner-to-Wing Shooting (3's)", R.drawable.ic_fitness_center_white_24dp, fundamentalShooting));
        drillsList.add(new Drill("Corner-to-Wing Shooting (NBA)", R.drawable.ic_fitness_center_white_24dp, SHOOTING_ARRAY));
        drillsList.add(new Drill("5 Spots Curl-Into-Shot (Mid-Range)", R.drawable.ic_fitness_center_white_24dp, fundamentalShooting));
        drillsList.add(new Drill("5 Spots Curl-Into-Shot (3's)", R.drawable.ic_fitness_center_white_24dp, fundamentalShooting));
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
        drillsList.add(new Drill("5 Man Weave - Transition Defense", R.drawable.ic_fitness_center_white_24dp, PASSING_ARRAY));
        drillsList.add(new Drill("3-2-1-0 Dribble", R.drawable.ic_fitness_center_white_24dp, PASSING_ARRAY));
        drillsList.add(new Drill("Medicine Ball Passing", R.drawable.ic_fitness_center_white_24dp, PASSING_ARRAY));

        drillsList.add(new Drill("1-Ball Pound Dribble (Stationary)", R.drawable.ic_fitness_center_white_24dp, BALL_HANDLING_ARRAY));
        drillsList.add(new Drill("1-Ball Front Cross (Stationary)", R.drawable.ic_fitness_center_white_24dp, BALL_HANDLING_ARRAY));
        drillsList.add(new Drill("1-Ball Side Cross (Stationary)", R.drawable.ic_fitness_center_white_24dp, BALL_HANDLING_ARRAY));
        drillsList.add(new Drill("1-Ball Back Cross (Stationary)", R.drawable.ic_fitness_center_white_24dp, BALL_HANDLING_ARRAY));
        drillsList.add(new Drill("1-Ball Repeat Crossover (Stationary)", R.drawable.ic_fitness_center_white_24dp, BALL_HANDLING_ARRAY));
        drillsList.add(new Drill("1-Ball Pound Crossover (Stationary)", R.drawable.ic_fitness_center_white_24dp, BALL_HANDLING_ARRAY));
        drillsList.add(new Drill("1-Ball Repeat Between The Legs (Stationary)", R.drawable.ic_fitness_center_white_24dp, BALL_HANDLING_ARRAY));
        drillsList.add(new Drill("1-Ball Pound Between The Legs (Stationary)", R.drawable.ic_fitness_center_white_24dp, BALL_HANDLING_ARRAY));
        drillsList.add(new Drill("1-Ball Repeat Flat Back (Stationary)", R.drawable.ic_fitness_center_white_24dp, BALL_HANDLING_ARRAY));
        drillsList.add(new Drill("1-Ball Pound Flat Back (Stationary)", R.drawable.ic_fitness_center_white_24dp, BALL_HANDLING_ARRAY));
        drillsList.add(new Drill("1-Ball Repeat Wrap-Around (Stationary)", R.drawable.ic_fitness_center_white_24dp, BALL_HANDLING_ARRAY));
        drillsList.add(new Drill("1-Ball Pound Wrap-Around (Stationary)", R.drawable.ic_fitness_center_white_24dp, BALL_HANDLING_ARRAY));
        drillsList.add(new Drill("1-Ball In-N-Out (Stationary)", R.drawable.ic_fitness_center_white_24dp, BALL_HANDLING_ARRAY));
        drillsList.add(new Drill("1-Ball In-N-Out Crossover (Stationary)", R.drawable.ic_fitness_center_white_24dp, BALL_HANDLING_ARRAY));
        drillsList.add(new Drill("1-Ball In-N-Out Between The Legs (Stationary)", R.drawable.ic_fitness_center_white_24dp, BALL_HANDLING_ARRAY));
        drillsList.add(new Drill("1-Ball In-N-Out Flat Back (Stationary)", R.drawable.ic_fitness_center_white_24dp, BALL_HANDLING_ARRAY));
        drillsList.add(new Drill("1-Ball In-N-Out Wrap-Around (Stationary)", R.drawable.ic_fitness_center_white_24dp, BALL_HANDLING_ARRAY));
        drillsList.add(new Drill("2-Ball Pound Dribble (Stationary)", R.drawable.ic_fitness_center_white_24dp, BALL_HANDLING_ARRAY));
        drillsList.add(new Drill("2-Ball Front Cross (Stationary)", R.drawable.ic_fitness_center_white_24dp, BALL_HANDLING_ARRAY));
        drillsList.add(new Drill("2-Ball Side Cross (Stationary)", R.drawable.ic_fitness_center_white_24dp, BALL_HANDLING_ARRAY));
        drillsList.add(new Drill("2-Ball Back Cross (Stationary)", R.drawable.ic_fitness_center_white_24dp, BALL_HANDLING_ARRAY));
        drillsList.add(new Drill("1-Ball Pound Dribble (ZigZag)(Full Court)", R.drawable.ic_fitness_center_white_24dp, BALL_HANDLING_ARRAY));
        drillsList.add(new Drill("1-Ball Front Cross (ZigZag)(Full Court)", R.drawable.ic_fitness_center_white_24dp, BALL_HANDLING_ARRAY));
        drillsList.add(new Drill("1-Ball Side Cross (ZigZag)(Full Court)", R.drawable.ic_fitness_center_white_24dp, BALL_HANDLING_ARRAY));
        drillsList.add(new Drill("2-Ball Pound Dribble (ZigZag)(Full Court)", R.drawable.ic_fitness_center_white_24dp, BALL_HANDLING_ARRAY));
        drillsList.add(new Drill("2-Ball Front Cross (ZigZag)(Full Court)", R.drawable.ic_fitness_center_white_24dp, BALL_HANDLING_ARRAY));
        drillsList.add(new Drill("2-Ball Side Cross (ZigZag)(Full Court)", R.drawable.ic_fitness_center_white_24dp, BALL_HANDLING_ARRAY));
        drillsList.add(new Drill("2-Ball Alternating Dribble (Stationary)", R.drawable.ic_fitness_center_white_24dp, BALL_HANDLING_ARRAY));
        drillsList.add(new Drill("2-Ball Alternating Dribble (ZigZag)(Full Court)", R.drawable.ic_fitness_center_white_24dp, BALL_HANDLING_ARRAY));
        drillsList.add(new Drill("2-Ball 1-Hi/1-Lo (Stationary)", R.drawable.ic_fitness_center_white_24dp, BALL_HANDLING_ARRAY));
        drillsList.add(new Drill("2-Ball 1-Hi/1-Lo (ZigZag)(Full Court)", R.drawable.ic_fitness_center_white_24dp, BALL_HANDLING_ARRAY));
        drillsList.add(new Drill("2-Ball Front Cross Opposite (Stationary)", R.drawable.ic_fitness_center_white_24dp, BALL_HANDLING_ARRAY));
        drillsList.add(new Drill("2-Ball Side Cross Opposite (Stationary)", R.drawable.ic_fitness_center_white_24dp, BALL_HANDLING_ARRAY));
        drillsList.add(new Drill("2-Ball Front Cross Opposite (ZigZag)(Full Court)", R.drawable.ic_fitness_center_white_24dp, BALL_HANDLING_ARRAY));
        drillsList.add(new Drill("2-Ball Side Cross Opposite (ZigZag)(Full Court)", R.drawable.ic_fitness_center_white_24dp, BALL_HANDLING_ARRAY));
        drillsList.add(new Drill("2-Ball Back Cross Opposite (Stationary)", R.drawable.ic_fitness_center_white_24dp, BALL_HANDLING_ARRAY));
        drillsList.add(new Drill("1-Ball Freestyle Combo (ZigZag)(Full Court)", R.drawable.ic_fitness_center_white_24dp, BALL_HANDLING_ARRAY));
        drillsList.add(new Drill("1-Ball Freestyle Combo (Stationary)", R.drawable.ic_fitness_center_white_24dp, BALL_HANDLING_ARRAY));
        drillsList.add(new Drill("2-Ball Freestyle Combo (ZigZag)(Full Court)", R.drawable.ic_fitness_center_white_24dp, BALL_HANDLING_ARRAY));
        drillsList.add(new Drill("2-Ball Freestyle Combo (Stationary)", R.drawable.ic_fitness_center_white_24dp, BALL_HANDLING_ARRAY));
        drillsList.add(new Drill("1-Ball Pound Dribble (ZigZag)(Full Court)", R.drawable.ic_fitness_center_white_24dp, BALL_HANDLING_ARRAY));
        drillsList.add(new Drill("1-Ball Front Cross (ZigZag)(Full Court)", R.drawable.ic_fitness_center_white_24dp, BALL_HANDLING_ARRAY));
        drillsList.add(new Drill("1-Ball Side Cross (ZigZag)(Full Court)", R.drawable.ic_fitness_center_white_24dp, BALL_HANDLING_ARRAY));
        drillsList.add(new Drill("2-Ball Pound Dribble (ZigZag)(Full Court)", R.drawable.ic_fitness_center_white_24dp, BALL_HANDLING_ARRAY));
        drillsList.add(new Drill("2-Ball Front Cross (ZigZag)(Full Court)", R.drawable.ic_fitness_center_white_24dp, BALL_HANDLING_ARRAY));
        drillsList.add(new Drill("2-Ball Side Cross (ZigZag)(Full Court)", R.drawable.ic_fitness_center_white_24dp, BALL_HANDLING_ARRAY));
        drillsList.add(new Drill("2-Ball Alternating Dribble (ZigZag)(Full Court)", R.drawable.ic_fitness_center_white_24dp, BALL_HANDLING_ARRAY));
        drillsList.add(new Drill("2-Ball 1-Hi/1-Lo (ZigZag)(Full Court)", R.drawable.ic_fitness_center_white_24dp, BALL_HANDLING_ARRAY));
        drillsList.add(new Drill("2-Ball Front Cross Opposite (ZigZag)(Full Court)", R.drawable.ic_fitness_center_white_24dp, BALL_HANDLING_ARRAY));
        drillsList.add(new Drill("2-Ball Side Cross Opposite (ZigZag)(Full Court)", R.drawable.ic_fitness_center_white_24dp, BALL_HANDLING_ARRAY));
        drillsList.add(new Drill("1-Ball Freestyle Combo (ZigZag)(Full Court)", R.drawable.ic_fitness_center_white_24dp, BALL_HANDLING_ARRAY));
        drillsList.add(new Drill("2-Ball Freestyle Combo (ZigZag)(Full Court)", R.drawable.ic_fitness_center_white_24dp, BALL_HANDLING_ARRAY));
        drillsList.add(new Drill("1-Ball Pound Front Cross (Stationary)", R.drawable.ic_fitness_center_white_24dp, BALL_HANDLING_ARRAY));
        drillsList.add(new Drill("1-Ball Pound Side Cross (Stationary)", R.drawable.ic_fitness_center_white_24dp, BALL_HANDLING_ARRAY));
        drillsList.add(new Drill("1-Ball Pound Back Cross (Stationary)", R.drawable.ic_fitness_center_white_24dp, BALL_HANDLING_ARRAY));
        drillsList.add(new Drill("2-Ball Pound Front Cross (Stationary)", R.drawable.ic_fitness_center_white_24dp, BALL_HANDLING_ARRAY));
        drillsList.add(new Drill("2-Ball Pound Side Cross (Stationary)", R.drawable.ic_fitness_center_white_24dp, BALL_HANDLING_ARRAY));
        drillsList.add(new Drill("2-Ball Pound Back Cross (Stationary)", R.drawable.ic_fitness_center_white_24dp, BALL_HANDLING_ARRAY));
        drillsList.add(new Drill("1-Ball Pound Front Cross (ZigZag)(Full Court)", R.drawable.ic_fitness_center_white_24dp, BALL_HANDLING_ARRAY));
        drillsList.add(new Drill("1-Ball Pound Side Cross (ZigZag)(Full Court)", R.drawable.ic_fitness_center_white_24dp, BALL_HANDLING_ARRAY));
        drillsList.add(new Drill("2-Ball Pound Front Cross (ZigZag)(Full Court)", R.drawable.ic_fitness_center_white_24dp, BALL_HANDLING_ARRAY));
        drillsList.add(new Drill("2-Ball Pound Side Cross (ZigZag)(Full Court)", R.drawable.ic_fitness_center_white_24dp, BALL_HANDLING_ARRAY));
        drillsList.add(new Drill("2-Ball Pound Alternating Dribble (Stationary)", R.drawable.ic_fitness_center_white_24dp, BALL_HANDLING_ARRAY));
        drillsList.add(new Drill("2-Ball Pound Alternating Dribble (ZigZag)(Full Court)", R.drawable.ic_fitness_center_white_24dp, BALL_HANDLING_ARRAY));
        drillsList.add(new Drill("2-Ball Pound 1-Hi/1-Lo (Stationary)", R.drawable.ic_fitness_center_white_24dp, BALL_HANDLING_ARRAY));
        drillsList.add(new Drill("2-Ball Pound 1-Hi/1-Lo (ZigZag)(Full Court)", R.drawable.ic_fitness_center_white_24dp, BALL_HANDLING_ARRAY));
        drillsList.add(new Drill("2-Ball Pound Front Cross Opposite (Stationary)", R.drawable.ic_fitness_center_white_24dp, BALL_HANDLING_ARRAY));
        drillsList.add(new Drill("2-Ball Pound Side Cross Opposite (Stationary)", R.drawable.ic_fitness_center_white_24dp, BALL_HANDLING_ARRAY));
        drillsList.add(new Drill("2-Ball Pound Front Cross Opposite (ZigZag)(Full Court)", R.drawable.ic_fitness_center_white_24dp, BALL_HANDLING_ARRAY));
        drillsList.add(new Drill("2-Ball Pound Side Cross Opposite (ZigZag)(Full Court)", R.drawable.ic_fitness_center_white_24dp, BALL_HANDLING_ARRAY));
        drillsList.add(new Drill("2-Ball Pound Back Cross Opposite (Stationary)", R.drawable.ic_fitness_center_white_24dp, BALL_HANDLING_ARRAY));
        drillsList.add(new Drill("1-Ball Pound Freestyle Combo (ZigZag)(Full Court)", R.drawable.ic_fitness_center_white_24dp, BALL_HANDLING_ARRAY));
        drillsList.add(new Drill("1-Ball Pound Freestyle Combo (Stationary)", R.drawable.ic_fitness_center_white_24dp, BALL_HANDLING_ARRAY));
        drillsList.add(new Drill("2-Ball Pound Freestyle Combo (ZigZag)(Full Court)", R.drawable.ic_fitness_center_white_24dp, BALL_HANDLING_ARRAY));
        drillsList.add(new Drill("2-Ball Pound Freestyle Combo (Stationary)", R.drawable.ic_fitness_center_white_24dp, BALL_HANDLING_ARRAY));
        drillsList.add(new Drill("1-Ball Pound Pound Dribble (ZigZag)(Full Court)", R.drawable.ic_fitness_center_white_24dp, BALL_HANDLING_ARRAY));
        drillsList.add(new Drill("1-Ball Pound Front Cross (ZigZag)(Full Court)", R.drawable.ic_fitness_center_white_24dp, BALL_HANDLING_ARRAY));
        drillsList.add(new Drill("1-Ball Pound Side Cross (ZigZag)(Full Court)", R.drawable.ic_fitness_center_white_24dp, BALL_HANDLING_ARRAY));
        drillsList.add(new Drill("2-Ball Pound Pound Dribble (ZigZag)(Full Court)", R.drawable.ic_fitness_center_white_24dp, BALL_HANDLING_ARRAY));
        drillsList.add(new Drill("2-Ball Pound Front Cross (ZigZag)(Full Court)", R.drawable.ic_fitness_center_white_24dp, BALL_HANDLING_ARRAY));
        drillsList.add(new Drill("2-Ball Pound Side Cross (ZigZag)(Full Court)", R.drawable.ic_fitness_center_white_24dp, BALL_HANDLING_ARRAY));
        drillsList.add(new Drill("2-Ball Pound Alternating Dribble (ZigZag)(Full Court)", R.drawable.ic_fitness_center_white_24dp, BALL_HANDLING_ARRAY));
        drillsList.add(new Drill("2-Ball Pound 1-Hi/1-Lo (ZigZag)(Full Court)", R.drawable.ic_fitness_center_white_24dp, BALL_HANDLING_ARRAY));
        drillsList.add(new Drill("2-Ball Pound Front Cross Opposite (ZigZag)(Full Court)", R.drawable.ic_fitness_center_white_24dp, BALL_HANDLING_ARRAY));
        drillsList.add(new Drill("2-Ball Pound Side Cross Opposite (ZigZag)(Full Court)", R.drawable.ic_fitness_center_white_24dp, BALL_HANDLING_ARRAY));
        drillsList.add(new Drill("1-Ball Pound Freestyle Combo (ZigZag)(Full Court)", R.drawable.ic_fitness_center_white_24dp, BALL_HANDLING_ARRAY));
        drillsList.add(new Drill("2-Ball Pound Freestyle Combo (ZigZag)(Full Court)", R.drawable.ic_fitness_center_white_24dp, BALL_HANDLING_ARRAY));

        drillsList.add(new Drill("Ball Wraps - Around the Waist", R.drawable.ic_fitness_center_white_24dp, fundamentalBallHandling));
        drillsList.add(new Drill("Ball Wraps - Around the Head", R.drawable.ic_fitness_center_white_24dp, fundamentalBallHandling));
        drillsList.add(new Drill("Ball Wraps - Around Both Feet", R.drawable.ic_fitness_center_white_24dp, fundamentalBallHandling));
        drillsList.add(new Drill("Ball Wraps - Around Each Foot", R.drawable.ic_fitness_center_white_24dp, fundamentalBallHandling));
        drillsList.add(new Drill("Ball Wraps - Full Body", R.drawable.ic_fitness_center_white_24dp, fundamentalBallHandling));
        drillsList.add(new Drill("Ball Wraps - Figure 8", R.drawable.ic_fitness_center_white_24dp, fundamentalBallHandling));
        drillsList.add(new Drill("Ball Wraps - Feet Together/Apart", R.drawable.ic_fitness_center_white_24dp, fundamentalBallHandling));
        drillsList.add(new Drill("Ball Slaps", R.drawable.ic_fitness_center_white_24dp, fundamentalBallHandling));
        drillsList.add(new Drill("Ball Pinches (Above Head) - Around the Waist", R.drawable.ic_fitness_center_white_24dp, fundamentalBallHandling));
        drillsList.add(new Drill("Ball Pinches (Full Body) - Around the Waist", R.drawable.ic_fitness_center_white_24dp, fundamentalBallHandling));
        drillsList.add(new Drill("Drop & Catch - Front/Back", R.drawable.ic_fitness_center_white_24dp, fundamentalBallHandling));
        drillsList.add(new Drill("Drop & Catch - Diagonal", R.drawable.ic_fitness_center_white_24dp, fundamentalBallHandling));
        drillsList.add(new Drill("Ball Whips - Crossover", R.drawable.ic_fitness_center_white_24dp, fundamentalBallHandling));
        drillsList.add(new Drill("Ball Whips - Between the Legs", R.drawable.ic_fitness_center_white_24dp, fundamentalBallHandling));
        drillsList.add(new Drill("Ball Whips - Behind the Back", R.drawable.ic_fitness_center_white_24dp, fundamentalBallHandling));
        drillsList.add(new Drill("No Look Toss Overhead", R.drawable.ic_fitness_center_white_24dp, fundamentalBallHandling));
        drillsList.add(new Drill("Finger Dribble", R.drawable.ic_fitness_center_white_24dp, fundamentalBallHandling));
        drillsList.add(new Drill("Single Moves (Stationary)", R.drawable.ic_fitness_center_white_24dp, fundamentalBallHandling));
        drillsList.add(new Drill("Double Moves (Stationary)", R.drawable.ic_fitness_center_white_24dp, BALL_HANDLING_ARRAY));
        drillsList.add(new Drill("Triple Moves (Stationary)", R.drawable.ic_fitness_center_white_24dp, BALL_HANDLING_ARRAY));
        drillsList.add(new Drill("Single Moves (Full Court)", R.drawable.ic_fitness_center_white_24dp, BALL_HANDLING_ARRAY));
        drillsList.add(new Drill("Double Moves (Full Court)", R.drawable.ic_fitness_center_white_24dp, BALL_HANDLING_ARRAY));
        drillsList.add(new Drill("Triple Moves (Full Court)", R.drawable.ic_fitness_center_white_24dp, BALL_HANDLING_ARRAY));
        drillsList.add(new Drill("Single Moves (Full Court - Against Chair)", R.drawable.ic_fitness_center_white_24dp, BALL_HANDLING_ARRAY));
        drillsList.add(new Drill("Double Moves (Full Court - Against Chair)", R.drawable.ic_fitness_center_white_24dp, BALL_HANDLING_ARRAY));
        drillsList.add(new Drill("Triple Moves (Full Court - Against Chair)", R.drawable.ic_fitness_center_white_24dp, BALL_HANDLING_ARRAY));

        drillsList.add(new Drill("Mikan Drill", R.drawable.ic_fitness_center_white_24dp, fundamentalDriving));
        drillsList.add(new Drill("Elbow Crossover Layups - Strong Side", R.drawable.ic_fitness_center_white_24dp, fundamentalDriving));
        drillsList.add(new Drill("Elbow Crossover Layups - Opposite Hand", R.drawable.ic_fitness_center_white_24dp, fundamentalDriving));
        drillsList.add(new Drill("Elbow Crossover Layups - Reverse", R.drawable.ic_fitness_center_white_24dp, fundamentalDriving));
        drillsList.add(new Drill("Elbow Crossover Layups - Opposite Side", R.drawable.ic_fitness_center_white_24dp, fundamentalDriving));
        drillsList.add(new Drill("Fancy Layups - Opposite Hand", R.drawable.ic_fitness_center_white_24dp, fundamentalDriving));
        drillsList.add(new Drill("Fancy Layups - Reverse", R.drawable.ic_fitness_center_white_24dp, fundamentalDriving));
        drillsList.add(new Drill("Fancy Layups - Opposite Side", R.drawable.ic_fitness_center_white_24dp, fundamentalDriving));
        drillsList.add(new Drill("Fancy Layups - Reverse", R.drawable.ic_fitness_center_white_24dp, fundamentalDriving));
        drillsList.add(new Drill("Layup Lines", R.drawable.ic_fitness_center_white_24dp, fundamentalDriving));

        drillsList.add(new Drill("No Wall, Wall-Sits", R.drawable.ic_fitness_center_white_24dp, fundamentalDefenseConditioning));
        drillsList.add(new Drill("Wall-Sits", R.drawable.ic_fitness_center_white_24dp, fundamentalDefenseConditioning));
        drillsList.add(new Drill("King of the Court", R.drawable.ic_fitness_center_white_24dp, defenseOffense));
        drillsList.add(new Drill("Dog Eat Dog", R.drawable.ic_fitness_center_white_24dp, fundamentalDefense));
        drillsList.add(new Drill("Defensive Slides", R.drawable.ic_fitness_center_white_24dp, fundamentalDefense));
        drillsList.add(new Drill("Box Run", R.drawable.ic_fitness_center_white_24dp, fundamentalDefenseConditioning));
        drillsList.add(new Drill("Speed Slides", R.drawable.ic_fitness_center_white_24dp, fundamentalDefenseConditioning));
        drillsList.add(new Drill("Defensive Shuffle", R.drawable.ic_fitness_center_white_24dp, fundamentalDefense));
        drillsList.add(new Drill("Closeout Drill", R.drawable.ic_fitness_center_white_24dp, DEFENSE_ARRAY));
        drillsList.add(new Drill("Shell Drill", R.drawable.ic_fitness_center_white_24dp, DEFENSE_ARRAY));
        drillsList.add(new Drill("5-on-O/ 4-on-D", R.drawable.ic_fitness_center_white_24dp, DEFENSE_ARRAY));
        drillsList.add(new Drill("1-1 Full Court", R.drawable.ic_fitness_center_white_24dp, DEFENSE_ARRAY));
        drillsList.add(new Drill("Ladders", R.drawable.ic_fitness_center_white_24dp, CONDITIONING_ARRAY));
    }

    public static ArrayList<Drill> getDrillsList() {
        return drillsList;
    }
}
