package com.armandgray.shared.model;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Collections;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;

public class DrillTest {

    private static final String TEST_TITLE = "TEST_TITLE";
    private static final int TEST_IMAGE_RES_ID = 3;
    private static final List<Drill.Category> TEST_CATEGORY;

    static {
        TEST_CATEGORY = Collections.singletonList(Drill.Category.SHOOTING);
    }

    private Drill testDrill;

    @Before
    public void setUp() {
        testDrill = new Drill(TEST_TITLE, TEST_IMAGE_RES_ID, TEST_CATEGORY);
    }

    @Test
    public void testGetId() {
        Assert.assertThat(testDrill.getId(), is(0));
    }

    @Test
    public void testGetTitle() {
        Assert.assertThat(testDrill.getTitle(), is(TEST_TITLE));
    }

    @Test
    public void testGetImageResId() {
        Assert.assertThat(testDrill.getImageResId(), is(TEST_IMAGE_RES_ID));
    }

    @Test
    public void testGetCategory() {
        Assert.assertThat(testDrill.getCategory(), is(TEST_CATEGORY));
    }

    @Test
    public void testGetPerformance() {
        Assert.assertThat(testDrill.getPerformance(), is(notNullValue()));
    }

    @Test
    public void testIsActive() {
        Assert.assertThat(testDrill.isActive(), is(false));
    }

    @Test
    public void testSetId() {
        int expected = 12;
        testDrill.setId(expected);
        Assert.assertThat(testDrill.getId(), is(expected));
    }

    @Test
    public void testSetTitle() {
        String expected = "12";
        testDrill.setTitle(expected);
        Assert.assertThat(testDrill.getTitle(), is(expected));
    }

    @Test
    public void testSetImageResId() {
        int expected = 12;
        testDrill.setImageResId(expected);
        Assert.assertThat(testDrill.getImageResId(), is(expected));
    }

    @Test
    public void testSetCategory() {
        int expected = 12;
        testDrill.setId(expected);
        Assert.assertThat(testDrill.getId(), is(expected));
    }

    @Test
    public void testSetActive() {
        testDrill.setActive(true);
        Assert.assertThat(testDrill.isActive(), is(true));
    }

    @Test
    public void testToString() {
        Assert.assertThat(testDrill.toString(), is(String.format("Drill{%s:%s}",
                TEST_TITLE, TEST_CATEGORY.toString())));
    }

    @Test
    public void testCategoryAsList() {
        List<Drill.Category> actual = Drill.Category.asList(Drill.Category.SHOOTING);
        Assert.assertThat(actual.size(), is(1));
        Assert.assertThat(actual.contains(Drill.Category.SHOOTING), is(true));
    }

    @Test
    public void testCategoryConverter_ToCategories() {
        Assert.assertThat(Drill.Category.Converter.toCategories("[\"SHOOTING\",\"FUNDAMENTALS\"]"),
                is(Drill.Category.SHOOTING_FUNDAMENTALS));
    }

    @Test
    public void testCategoryConverter_ToString() {
        Assert.assertThat(Drill.Category.Converter.toString(Drill.Category.SHOOTING_FUNDAMENTALS),
                is("[\"SHOOTING\",\"FUNDAMENTALS\"]"));
    }

    @Test
    public void testDefaultsGetDefaults() {
        Assert.assertThat(Drill.Defaults.getDefaults().size(), is(19));
    }

    @After
    public void tearDown() {
        testDrill = null;
    }
}