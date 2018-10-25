package com.armandgray.shared.model;

import com.armandgray.shared.R;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.containsInAnyOrder;

public class UXPreferenceTest {

    private static final String TEST_TITLE = "TEST_TITLE";

    private UXPreference testPreference;
    private UXPreference.Value testValue;
    private UXPreference.Category testCategory;
    private UXPreference.Item testItem;

    @Before
    public void setUp() {
        testPreference = new UXPreference(TEST_TITLE, UXPreference.Category.REPS_BASED);
        testValue = new UXPreference.Value(UXPreference.Item.MINUS);
        testCategory = UXPreference.Category.REPS_BASED;
        testItem = UXPreference.Item.MINUS;
    }

    @Test
    public void testGetTitle() {
        Assert.assertThat(testPreference.getTitle(), is(TEST_TITLE));
    }

    @Test
    public void testGetCategory() {
        Assert.assertThat(testPreference.getCategory(), is(UXPreference.Category.REPS_BASED));
    }

    @SuppressWarnings("SimplifyStreamApiCallChains")
    @Test
    public void testGetValues() {
        Assert.assertThat(testPreference.getValues().size(), is(3));
    }

    @Test
    public void testGetValue() {
        Assert.assertThat(testPreference.getValue(UXPreference.Item.GOAL), is(70));
    }

    @Test
    public void testGetValue_WithScaleValue() {
        Assert.assertThat(testPreference.getValue(UXPreference.Item.GOAL, true), is(7000));

    }

    @Test
    public void testIsEnabled() {
        Assert.assertThat(testPreference.isEnabled(UXPreference.Item.GOAL), is(false));
    }

    @Test
    public void testToString() {
        Assert.assertThat(testPreference.toString(), is(String.format(Locale.getDefault(),
                "UXPreference{%s}", testPreference.getValues())));
    }

    /**
     * Inner Class - Value
     */

    @Test
    public void testValue_GetItem() {
        Assert.assertThat(testValue.getItem(), is(UXPreference.Item.MINUS));
    }

    @Test
    public void testValue_GetValue() {
        Assert.assertThat(testValue.getValue(), is(0));
    }

    @Test
    public void testValue_IsEnabled() {
        Assert.assertThat(testValue.isEnabled(), is(true));
    }

    @Test
    public void testValue_IsEnabled_ReturnsFalseForNonToggle() {
        UXPreference.Value value = new UXPreference.Value(UXPreference.Item.REPS);
        Assert.assertThat(value.isEnabled(), is(false));
    }

    @Test
    public void testValue_SetEnabled() {
        testValue.setEnabled(false);
        Assert.assertThat(testValue.isEnabled(), is(false));
        testValue.setEnabled(true);
    }

    @Test
    public void testValue_ToString_NumberFormat() {
        UXPreference.Value value = new UXPreference.Value(UXPreference.Item.REPS);
        Assert.assertThat(value.toString(), is(String.format(Locale.getDefault(),
                "Value{%s -> %d}", value.getItem(), value.getValue())));
    }

    @Test
    public void testValue_ToString_Toggle() {
        Assert.assertThat(testValue.toString(), is(String.format(Locale.getDefault(),
                "Value{%s -> %s}", testValue.getItem(),
                testValue.isEnabled() ? "Enabled" : "Disabled")));
    }

    @Test
    public void testValue_ToString_Triggered() {
        UXPreference.Value value = new UXPreference.Value(UXPreference.Item.RESET);
        Assert.assertThat(value.toString(), is(String.format(Locale.getDefault(),
                "Value{%s}", value.getItem())));
    }

    @Test
    public void testValue_ToString_Default() {
        UXPreference.Value value = new UXPreference.Value(UXPreference.Item.TEST);
        Assert.assertThat(value.toString(), is("Value@" + Integer.toHexString(value.hashCode())));
    }

    /**
     * Inner Class - Category
     */

    @Test
    public void testCategory_Instances() {
        UXPreference.Category[] values = UXPreference.Category.values();
        Assert.assertThat(values.length, is(6));
        Assert.assertThat(Arrays.asList(values), containsInAnyOrder(
                UXPreference.Category.VOICE,
                UXPreference.Category.DATA,
                UXPreference.Category.LOCATION,
                UXPreference.Category.WORKOUT,
                UXPreference.Category.REPS_BASED,
                UXPreference.Category.TIME_BASED));
    }

    @Test
    public void testCategory_IsDrillCategory() {
        Assert.assertThat(testCategory.isDrillCategory(), is(true));
    }

    @Test
    public void testCategory_ToString() {
        Assert.assertThat(testCategory.toString(), is(String.format(Locale.getDefault(), "Category{%s:%s}",
                testCategory.name(), testCategory.items)));
    }

    /**
     * Inner Class - Items
     */

    @Test
    public void testItem_Instances() {
        UXPreference.Item[] items = UXPreference.Item.values();
        Assert.assertThat(items.length, is(17));
        Assert.assertThat(Arrays.asList(items), containsInAnyOrder(
                UXPreference.Item.GYM_LOCATION,
                UXPreference.Item.COURT_LOCATION,
                UXPreference.Item.CLAP,
                UXPreference.Item.CALL_OUT,
                UXPreference.Item.BREAK_LIMIT,
                UXPreference.Item.TIMEOUT,
                UXPreference.Item.SCREEN_TAPS,
                UXPreference.Item.AUTO,
                UXPreference.Item.MINUS,
                UXPreference.Item.VIBRATE,
                UXPreference.Item.CLEAR,
                UXPreference.Item.REPS,
                UXPreference.Item.GOAL,
                UXPreference.Item.TIME,
                UXPreference.Item.PERMISSION,
                UXPreference.Item.RESET,
                UXPreference.Item.TEST));
    }

    @Test
    public void testGetText() {
        Assert.assertThat(testItem.getText(), is("Minus"));
    }

    @Test
    public void testGetDescription() {
        Assert.assertThat(testItem.getDescription(), is("Enable Minus Icon For Miss"));
    }

    @Test
    public void testGetTypeConstant() {
        Assert.assertThat(testItem.getTypeConstant(), is(UXPreference.Constants.TOGGLE));
    }

    @Test
    public void testGetImageResId() {
        Assert.assertThat(testItem.getImageResId(), is(R.drawable.ic_remove_white_24dp));
    }

    @Test
    public void testGetDefault() {
        Assert.assertThat(testItem.getDescription(), is("Enable Minus Icon For Miss"));
    }

    @Test
    public void testGetDefault_WithScaleValue() {
        Assert.assertThat(UXPreference.Item.GOAL.getDefault(true), is(7000));
    }

    @Test
    public void testGetMin() {
        Assert.assertThat(UXPreference.Item.GOAL.getMin(), is(0));
    }

    @Test
    public void testGetMax() {
        Assert.assertThat(UXPreference.Item.GOAL.getMax(), is(100));
    }

    @Test
    public void testGetScaleFactor() {
        Assert.assertThat(UXPreference.Item.GOAL.getScaleFactor(),
                is(UXPreference.Constants.PERCENT_SCALE));
    }

    @Test
    public void testItem_ToString_NumberFormat() {
        UXPreference.Item item = UXPreference.Item.REPS;
        Assert.assertThat(item.toString(), is(String.format(Locale.getDefault(),
                "Item{%s:%d < %d < %d}", item.name(), item.getMin(),
                item.getDefault(), item.getMax())));
    }

    @Test
    public void testItem_ToString_Toggle() {
        UXPreference.Item item = UXPreference.Item.MINUS;
        Assert.assertThat(item.toString(), is(String.format(Locale.getDefault(),
                "Item{%s: %s}", item.name(), "Enabled")));
    }

    @Test
    public void testItem_ToString_Triggered() {
        UXPreference.Item item = UXPreference.Item.RESET;
        Assert.assertThat(item.toString(), is(String.format(Locale.getDefault(),
                "Item{%s}", item.name())));
    }

    @Test
    public void testItem_ToString_Default() {
        UXPreference.Item item = UXPreference.Item.TEST;
        Assert.assertThat(item.toString(), is("Item@" + Integer.toHexString(item.hashCode())));
    }

    /**
     * Inner Class - Constants
     */

    @Test
    public void testConstants_TRIGGERED() {
        Assert.assertThat(UXPreference.Constants.TRIGGERED, is(0));
    }

    @Test
    public void testConstants_NUMBER_RANGE() {
        Assert.assertThat(UXPreference.Constants.NUMBER_RANGE, is(1));
    }

    @Test
    public void testConstants_TOGGLE() {
        Assert.assertThat(UXPreference.Constants.TOGGLE, is(2));
    }

    @Test
    public void testConstants_SECONDS_SCALE() {
        Assert.assertThat(UXPreference.Constants.SECONDS_SCALE, is(1000));
    }

    @Test
    public void testConstants_MINUTES_SCALE() {
        Assert.assertThat(UXPreference.Constants.MINUTES_SCALE, is(60 * 1000));
    }

    @Test
    public void testConstants_INT_SCALE() {
        Assert.assertThat(UXPreference.Constants.INT_SCALE, is(1));
    }

    @Test
    public void testConstants_PERCENT_SCALE() {
        Assert.assertThat(UXPreference.Constants.PERCENT_SCALE, is(100));
    }

    @Test
    public void testConstants_DEFAULT_REPS() {
        Assert.assertThat(UXPreference.Constants.DEFAULT_REPS, is(10));
    }

    @Test
    public void testConstants_DEFAULT_GOAL() {
        Assert.assertThat(UXPreference.Constants.DEFAULT_GOAL, is(70));
    }

    @Test
    public void testConstants_DEFAULT_TIME() {
        Assert.assertThat(UXPreference.Constants.DEFAULT_TIME, is(60));
    }

    /**
     * Inner Class - Converter
     */

    @Test
    public void testTypesAsList() {
        List<Drill.Type> actual = Drill.Type.asList(Drill.Type.SHOOTING);
        Assert.assertThat(actual.size(), is(1));
        Assert.assertThat(actual.contains(Drill.Type.SHOOTING), is(true));
    }

    @Test
    public void testTypeConverter_ToTypes() {
        Assert.assertThat(Drill.Type.Converter.toTypes("[\"SHOOTING\",\"FUNDAMENTALS\"]"),
                is(Drill.Type.SHOOTING_FUNDAMENTALS));
    }

    @Test
    public void testTypeConverter_ToString() {
        Assert.assertThat(Drill.Type.Converter.toString(Drill.Type.SHOOTING_FUNDAMENTALS),
                is("[\"SHOOTING\",\"FUNDAMENTALS\"]"));
    }

    @After
    public void tearDown() {
        testPreference = null;
        testValue = null;
        testCategory = null;
        testItem = null;
    }
}