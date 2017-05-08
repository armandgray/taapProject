package com.armandgray.taap.utils;

import org.junit.Test;

import java.util.ArrayList;

import static com.armandgray.taap.utils.MathHelper.getAveragePercentage;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

public class MathHelperTest {

    @Test
    public void canGetAveragePercentage() throws Exception {
        ArrayList<Double> percentages = new ArrayList<>();
        percentages.add(0.20);
        percentages.add(0.40);
        percentages.add(0.90);
        double total = 0.0;
        for (Double percent : percentages ) { total += percent; }

        assertNotNull(getAveragePercentage(percentages));
        assertEquals(total/percentages.size() , getAveragePercentage(percentages));
    }

}