package com.armandgray.shared.model;

import com.armandgray.shared.R;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Locale;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;

public class SettingTest {

    private static final String TEST_TITLE = "TEST_TITLE";

    private Setting testSetting;

    @Before
    public void setUp() {
        testSetting = new Setting(TEST_TITLE, R.drawable.ic_clock_white_24dp,
                UXPreference.Category.DATA);
    }

    @Test
    public void testGetId() {
        Assert.assertThat(testSetting.getId(), is(0));
    }

    @Test
    public void testSetId() {
        testSetting.setId(2);
        Assert.assertThat(testSetting.getId(), is(2));
    }

    @Test
    public void testGetTitle() {
        Assert.assertThat(testSetting.getTitle(), is(TEST_TITLE));
    }

    @Test
    public void testGetImageResId() {
        Assert.assertThat(testSetting.getImageResId(), is(R.drawable.ic_clock_white_24dp));
    }

    @Test
    public void testGetPreference() {
        Assert.assertThat(testSetting.getPreference().getCategory(),
                is(UXPreference.Category.DATA));
    }

    @Test
    public void testSetTitle() {
        testSetting.setTitle("");
        Assert.assertThat(testSetting.getTitle(), is(""));
    }

    @Test
    public void testSetImageResId() {
        testSetting.setImageResId(R.drawable.ic_dribbble_white_48dp);
        Assert.assertThat(testSetting.getImageResId(), is(R.drawable.ic_dribbble_white_48dp));
    }

    @Test
    public void testSetPreference() {
        UXPreference expected = new UXPreference(TEST_TITLE, UXPreference.Category.LOCATION);
        testSetting.setPreference(expected);
        Assert.assertThat(testSetting.getPreference(), is(expected));
    }

    @Test
    public void testHashCode() {
        Assert.assertThat(testSetting.hashCode(), is(0));
    }

    @Test
    public void testEquals() {
        Setting expected = new Setting(TEST_TITLE, R.drawable.ic_clock_white_24dp,
                UXPreference.Category.DATA);
        Assert.assertThat(testSetting, is(expected));
    }

    @SuppressWarnings({"ConstantConditions", "ObjectEqualsNull"})
    @Test
    public void testEquals_NotEqual_IfNull() {
        Assert.assertThat(testSetting.equals(null), is(false));
    }

    @Test
    public void testEquals_NotEqual_IfNotSetting() {
        Assert.assertThat(testSetting, is(not("")));
    }

    @Test
    public void testEquals_NotEqual_IfHashCodesMismatch() {
        Setting expected = new Setting(TEST_TITLE, R.drawable.ic_clock_white_24dp,
                UXPreference.Category.DATA);
        expected.setId(1);
        Assert.assertThat(testSetting, is(not(expected)));
    }

    @Test
    public void testEquals_NotEqual_IfTitleMismatch() {
        Setting expected = new Setting("", R.drawable.ic_clock_white_24dp,
                UXPreference.Category.DATA);
        Assert.assertThat(testSetting, is(not(expected)));
    }

    @Test
    public void testEquals_NotEqual_IfCategoryMismatch() {
        Setting expected = new Setting(TEST_TITLE, R.drawable.ic_clock_white_24dp,
                UXPreference.Category.LOCATION);
        expected.setId(1);
        Assert.assertThat(testSetting, is(not(expected)));
    }

    @Test
    public void testToString() {
        Assert.assertThat(testSetting.toString(), is(String.format(Locale.getDefault(),
                "Setting(%d){%s, %s}", testSetting.getId(), testSetting.getTitle(),
                testSetting.getPreference())));
    }

    @Test
    public void testCompareTo() {
        Setting expected = new Setting(TEST_TITLE, R.drawable.ic_clock_white_24dp,
                UXPreference.Category.LOCATION);
        expected.setId(4);
        Assert.assertThat(testSetting.compareTo(expected), is(-4));
    }

    @Test
    public void testCompareTo_IsOne_IfNull() {
        Assert.assertThat(testSetting.compareTo(null), is(1));
    }

    @Test
    public void testDefaultsGetDefaults() {
        Assert.assertThat(Setting.Defaults.getDefaults().size(), is(4));
        Assert.assertThat(Setting.Defaults.getDefaults(), hasItem(new Setting("Voice",
                R.drawable.ic_dribbble_white_48dp, UXPreference.Category.VOICE)));
        Assert.assertThat(Setting.Defaults.getDefaults(), hasItem(new Setting("Workout",
                R.drawable.ic_dumbbell_white_24dp, UXPreference.Category.WORKOUT)));
        Assert.assertThat(Setting.Defaults.getDefaults(), hasItem(new Setting("Data",
                R.drawable.ic_data_usage_white_24dp, UXPreference.Category.DATA)));
        Assert.assertThat(Setting.Defaults.getDefaults(), hasItem(new Setting("Location",
                R.drawable.ic_location_white_24dp, UXPreference.Category.LOCATION)));
    }

    @After
    public void tearDown() {
        testSetting = null;
    }
}
