package com.armandgray.shared.model;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;

public class DrillTest {

    private static final String TEST_TITLE = "TEST_TITLE";
    private static final int TEST_IMAGE_RES_ID = 3;
    private static final List<Drill.Type> TEST_TYPE;

    static {
        TEST_TYPE = Drill.Type.SHOOTING_ONLY;
    }

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Mock
    UXPreference mockPreference;

    private Drill testDrill;

    @Before
    public void setUp() {
        testDrill = new Drill(TEST_TITLE, TEST_IMAGE_RES_ID, TEST_TYPE);
    }

    @Test
    public void testGetId() {
        Assert.assertThat(testDrill.getId(), is(0));
    }

    @Test
    public void testGetReps() {
        Assert.assertThat(testDrill.getReps(), is(10));
    }

    @Test
    public void testGetGoal() {
        Assert.assertThat(testDrill.getGoal(), is(0.7));
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
    public void testGetType() {
        Assert.assertThat(testDrill.getType(), is(TEST_TYPE));
    }

    @Test
    public void testGetPreference() {
        Assert.assertThat(testDrill.getPreference(), is(notNullValue()));
        Assert.assertThat(testDrill.getPreference().getTitle(), is(TEST_TITLE));
        Assert.assertThat(testDrill.getPreference().getCategory(),
                is(UXPreference.Category.REPS_BASED));
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
    public void testSetType() {
        int expected = 12;
        testDrill.setId(expected);
        Assert.assertThat(testDrill.getId(), is(expected));
    }

    @Test
    public void testSetPreference() {
        testDrill.setPreference(mockPreference);
        Assert.assertThat(testDrill.getPreference(), is(mockPreference));
    }

    @Test
    public void testHashCode() {
        Drill expected = new Drill(TEST_TITLE, 2, Drill.Type.SHOOTING_ONLY);
        Assert.assertThat(testDrill.hashCode(), is(expected.hashCode()));
    }

    @Test
    public void testEqual() {
        Drill expected = new Drill(TEST_TITLE, 2, Drill.Type.SHOOTING_ONLY);
        Assert.assertThat(testDrill, is(expected));
    }

    @Test
    public void testToString() {
        Assert.assertThat(testDrill.toString(), is(String.format("Drill{%s:%s}",
                TEST_TITLE, TEST_TYPE.toString())));
    }

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

    @Test
    public void testDefaultsGetDefaults() {
        Assert.assertThat(Drill.Defaults.getDefaults().size(), is(19));
    }

    @After
    public void tearDown() {
        testDrill = null;
    }
}