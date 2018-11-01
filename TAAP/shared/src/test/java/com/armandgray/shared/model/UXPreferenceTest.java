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
        testValue = new UXPreference.Value(UXPreference.Item.ICONS);
        testCategory = UXPreference.Category.REPS_BASED;
        testItem = UXPreference.Item.ICONS;
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
        Assert.assertThat(testValue.getItem(), is(UXPreference.Item.ICONS));
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
                UXPreference.Item.VOICE_TIMEOUT,
                UXPreference.Item.BREAK_LIMIT,
                UXPreference.Item.TIMEOUT,
                UXPreference.Item.VIBRATE,
                UXPreference.Item.SCREEN_TAPS,
                UXPreference.Item.AUTO,
                UXPreference.Item.ICONS,
                UXPreference.Item.CLEAR,
                UXPreference.Item.REPS,
                UXPreference.Item.GOAL,
                UXPreference.Item.TIME,
                UXPreference.Item.RESET,
                UXPreference.Item.TEST));
    }

    @Test
    public void testGetText() {
        Assert.assertThat(testItem.getText(), is("Icons"));
    }

    @Test
    public void testGetDescription() {
        Assert.assertThat(testItem.getDescription(), is("Enable Plus/Minus Icon For Make/Miss"));
    }

    @Test
    public void testGetTypeConstant() {
        Assert.assertThat(testItem.getTypeConstant(), is(UXPreference.TypeConstant.TOGGLE));
    }

    @Test
    public void testGetImageResId() {
        Assert.assertThat(testItem.getImageResId(), is(R.drawable.ic_remove_white_24dp));
    }

    @Test
    public void testGetDefault() {
        Assert.assertThat(testItem.getDefault(), is(0));
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
        Assert.assertThat(UXPreference.Item.GOAL.getScale(),
                is(UXPreference.Scale.PERCENT_SCALE));
    }

    @Test
    public void testHasWarning() {
        Assert.assertThat(UXPreference.Item.AUTO.hasWarning(), is(true));
    }

    @Test
    public void testGetWarning() {
        Assert.assertThat(UXPreference.Item.AUTO.getWarning(), is("Feature May Impact Battery"));
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
        UXPreference.Item item = UXPreference.Item.ICONS;
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
     * Inner Class - TypeConstant
     */

    @Test
    public void testTypeConstant_Instances() {
        UXPreference.TypeConstant[] constants = UXPreference.TypeConstant.values();
        Assert.assertThat(constants.length, is(4));
        Assert.assertThat(Arrays.asList(constants), containsInAnyOrder(
                UXPreference.TypeConstant.NONE,
                UXPreference.TypeConstant.TRIGGERED,
                UXPreference.TypeConstant.NUMBER_RANGE,
                UXPreference.TypeConstant.TOGGLE));
    }

    /**
     * Inner Class - Scale
     */

    @Test
    public void testScale_Instances() {
        UXPreference.Scale[] scales = UXPreference.Scale.values();
        Assert.assertThat(scales.length, is(6));
        Assert.assertThat(Arrays.asList(scales), containsInAnyOrder(
                UXPreference.Scale.NONE,
                UXPreference.Scale.TENTHS_OF_SECONDS_SCALE,
                UXPreference.Scale.SECONDS_SCALE,
                UXPreference.Scale.MINUTES_SCALE,
                UXPreference.Scale.INT_SCALE,
                UXPreference.Scale.PERCENT_SCALE));
    }

    @Test
    public void testScale_NONE_SCALE() {
        Assert.assertThat(UXPreference.Scale.NONE.getFactor(), is(0));
    }

    @Test
    public void testScale_TENTHS_OF_SECONDS_SCALE() {
        Assert.assertThat(UXPreference.Scale.TENTHS_OF_SECONDS_SCALE.getFactor(), is(100));
    }
    
    @Test
    public void testScale_SECONDS_SCALE() {
        Assert.assertThat(UXPreference.Scale.SECONDS_SCALE.getFactor(), is(1000));
    }

    @Test
    public void testScale_MINUTES_SCALE() {
        Assert.assertThat(UXPreference.Scale.MINUTES_SCALE.getFactor(), is(60 * 1000));
    }

    @Test
    public void testScale_INT_SCALE() {
        Assert.assertThat(UXPreference.Scale.INT_SCALE.getFactor(), is(1));
    }

    @Test
    public void testScale_PERCENT_SCALE() {
        Assert.assertThat(UXPreference.Scale.PERCENT_SCALE.getFactor(), is(100));
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