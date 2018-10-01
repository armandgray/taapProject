package com.armandgray.taap.models;

import android.os.Parcelable;

import com.armandgray.taap.R;

import org.junit.Ignore;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static com.armandgray.taap.db.DrillsDataHelper.getDrillsList;
import static com.armandgray.taap.models.Drill.BALL_HANDLING;
import static com.armandgray.taap.models.Drill.BALL_HANDLING_ARRAY;
import static com.armandgray.taap.models.Drill.getQueryResultList;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;

public class DrillTest {

    @Test @Ignore
    public void canCreateDrill() throws Exception {
        String[] ballHandling = {BALL_HANDLING};
        Drill drill = new Drill("2-Ball Pound Dribble",
                R.drawable.ic_fitness_center_white_24dp, ballHandling);
        assertNotNull(drill);
        assertNotNull(drill.getDrillId());
        assertNotNull(drill.getTitle());
        assertNotNull(drill.getImageId());
        assertNotNull(drill.getCategory());
    }

    @Test @Ignore
    public void doesImplementParcelable() throws Exception {
        Parcelable drill = new Drill("2-Ball Pound Dribble",
                R.drawable.ic_fitness_center_white_24dp, BALL_HANDLING_ARRAY);
        assertNotNull(drill);
    }

    @Test @Ignore
    public void canGetQueryResultList() throws Exception {
        ArrayList<Drill> expectedList = getDrillsList();
        for (int i = 0; i < expectedList.size(); i++) {
            if (!expectedList.get(i).getTitle().toLowerCase().contains("pro")) {
                expectedList.remove(i);
                i--;
            }
        }

        List<Drill> returnList = getQueryResultList(getDrillsList(), "pro");
        assertNotNull(returnList);
        assertEquals(expectedList.size(), returnList.size());
        for (int i = 0; i < expectedList.size(); i++) {
            assertTrue(expectedList.get(i).getTitle().equals(returnList.get(i).getTitle()));
        }
    }

    @Test @Ignore
    public void canSetDrillId() throws Exception {
        String[] ballHandling = {BALL_HANDLING};
        Drill drill = new Drill("2-Ball Pound Dribble",
                R.drawable.ic_fitness_center_white_24dp, ballHandling);
        drill.setDrillId(100);
        assertNotNull(drill);
        assertNotNull(drill.getDrillId());
        assertEquals(100, drill.getDrillId());
    }
}