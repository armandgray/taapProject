package com.armandgray.taap.models;

import com.armandgray.taap.R;

import org.junit.Test;

import java.util.List;

import static com.armandgray.taap.models.Drill.BALL_HANDLING;
import static com.armandgray.taap.models.Drill.getQueryResultList;
import static com.armandgray.taap.utils.DrillsHelper.getDrillsList;
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

    @Test
    public void canGetQueryResultList() throws Exception {
        List<Drill> returnList = getQueryResultList(getDrillsList(), "pro");
        assertNotNull(returnList);
    }
}