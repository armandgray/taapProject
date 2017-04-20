package com.armandgray.taap.models;

import com.armandgray.taap.R;

import org.junit.Test;

import static junit.framework.Assert.assertNotNull;

public class DrillTest {

    @Test
    public void canCreateDrill() throws Exception {
        Drill drill = new Drill("2-Ball Pound Dribble", R.drawable.ic_fitness_center_white_24dp);
        assertNotNull(drill);
        assertNotNull(drill.getTitle());
        assertNotNull(drill.getImage());
    }

}