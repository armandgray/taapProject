package com.armandgray.taap.models;

import com.armandgray.taap.R;

import org.junit.Test;

import static com.armandgray.taap.models.Drill.BALL_HANDLING;
import static junit.framework.Assert.assertNotNull;

public class DrillTest {

    @Test
    public void canCreateDrill() throws Exception {
        String[] ballHandling = {BALL_HANDLING};
        Drill drill = new Drill("2-Ball Pound Dribble",
                R.drawable.ic_fitness_center_white_24dp, ballHandling);
        assertNotNull(drill);
        assertNotNull(drill.getTitle());
        assertNotNull(drill.getImageId());
        assertNotNull(drill.getCategory());
    }

}