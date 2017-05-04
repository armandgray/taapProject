package com.armandgray.taap.log;

import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.armandgray.taap.BuildConfig;
import com.armandgray.taap.R;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.android.controller.ActivityController;
import org.robolectric.annotation.Config;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;
import static org.robolectric.Shadows.shadowOf;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class LogActivityViewsTest {

    private ActivityController<LogActivity> activityController;
    private LogActivity activity;
    private Toolbar toolbar;
    private LogActivityViews views;

    @Before
    public void setUp() {
        System.out.println("Running Set Up!");
        activityController = Robolectric.buildActivity(LogActivity.class);
        activity = activityController.create().visible().get();
        toolbar = (Toolbar) activity.findViewById(R.id.toolbar);
        views = activity.controller.views;
    }

    @Test
    public void activityInstanceOfMainActivity_TestConstructor() throws Exception {
        assertEquals("log.LogActivity", views.activity.getLocalClassName());
    }

    @Test
    public void doesSetContentView_MethodTest_SetupActivityInitialState() throws Exception {
        assertEquals(R.id.activityLogLayout, shadowOf(activity).getContentView().getId());
    }

    @Test
    public void doesSetHomeAsUpEnabled_MethodTest_SetupActivityInitialState() throws Exception {
        assertNotNull(activity.getSupportActionBar());
        final int displayOptions = activity.getSupportActionBar().getDisplayOptions();
        assertTrue((displayOptions & ActionBar.DISPLAY_SHOW_HOME) != 0);
        assertTrue((displayOptions & ActionBar.DISPLAY_HOME_AS_UP) != 0);
    }

    @Test
    public void doesSetupHideToolbarTitle_MethodTest_SetupActivityInitialState() throws Exception {
        ActionBar actionBar = activity.getSupportActionBar();
        assertNotNull(actionBar);
        final int displayOptions = activity.getSupportActionBar().getDisplayOptions();
        assertTrue((displayOptions & ActionBar.DISPLAY_SHOW_TITLE) == 0);
    }

    @Test
    public void hasCustomToolbarTitle() throws Exception {
        Toolbar toolbar = (Toolbar) activity.findViewById(R.id.toolbar);
        TextView tvTitle = (TextView) toolbar.findViewById(R.id.tvTitle);
        assertNotNull(tvTitle);
    }

    @Test
    public void doesSetCustomToolbarTitleText_MethodTest_SetupActivityInitialState() throws Exception {
        Toolbar toolbar = (Toolbar) activity.findViewById(R.id.toolbar);
        TextView tvTitle = (TextView) toolbar.findViewById(R.id.tvTitle);
        assertEquals(activity.getResources()
                .getString(R.string.session_history), tvTitle.getText());
    }

    @Test
    public void doesSetLayoutText_DetailItem_TotalSessionTime() throws Exception {
        TextView header = (TextView) views.layoutTotalSessionTime.findViewById(R.id.header);
        TextView tvText = (TextView) views.layoutTotalSessionTime.findViewById(R.id.tvText);
        assertNotNull(header);
        assertNotNull(tvText);
        assertEquals(activity.getString(R.string.total_session_time), header.getText());
        assertEquals(activity.getString(R.string.zero_time), tvText.getText());
    }

    @Test
    public void doesSetLayoutText_DetailItem_TotalActiveTime() throws Exception {
        TextView header = (TextView) views.layoutTotalActiveTime.findViewById(R.id.header);
        TextView tvText = (TextView) views.layoutTotalActiveTime.findViewById(R.id.tvText);
        assertNotNull(header);
        assertNotNull(tvText);
        assertEquals(activity.getString(R.string.total_active_time), header.getText());
        assertEquals(activity.getString(R.string.zero_time), tvText.getText());
    }

    @Test
    public void doesSetLayoutText_DetailItem_TotalRestTime() throws Exception {
        TextView header = (TextView) views.layoutTotalRestTime.findViewById(R.id.header);
        TextView tvText = (TextView) views.layoutTotalRestTime.findViewById(R.id.tvText);
        assertNotNull(header);
        assertNotNull(tvText);
        assertEquals(activity.getString(R.string.total_rest_time), header.getText());
        assertEquals(activity.getString(R.string.zero_time), tvText.getText());
    }

    @Test
    public void doesSetLayoutText_DetailItem_ExercisesCompleted() throws Exception {
        TextView header = (TextView) views.layoutExercisesCompleted.findViewById(R.id.header);
        TextView tvText = (TextView) views.layoutExercisesCompleted.findViewById(R.id.tvText);
        assertNotNull(header);
        assertNotNull(tvText);
        assertEquals(activity.getString(R.string.exercises_completed), header.getText());
        assertEquals(String.valueOf(0), tvText.getText());
    }

    @Test
    public void doesSetLayoutText_DetailItem_RepsCompleted() throws Exception {
        TextView header = (TextView) views.layoutRepsCompleted.findViewById(R.id.header);
        TextView tvText = (TextView) views.layoutRepsCompleted.findViewById(R.id.tvText);
        assertNotNull(header);
        assertNotNull(tvText);
        assertEquals(activity.getString(R.string.reps_completed), header.getText());
        assertEquals(String.valueOf(0), tvText.getText());
    }

    @Test
    public void doesSetLayoutText_RecordItem_Date() throws Exception {
        String dateString = ">  ";
        dateString += new SimpleDateFormat("EEE, MMM d, ''yy", Locale.US).format(new Date()) + "  <";
        assertNotNull(views.tvDate);
        assertEquals(dateString, views.tvDate.getText());
    }

    @Test
    public void doesSetLayoutText_RecordItem_Fundamentals() throws Exception {
        ImageView ivImage = (ImageView) views.layoutFundamentals.findViewById(R.id.ivImage);
        TextView tvTime = (TextView) views.layoutFundamentals.findViewById(R.id.tvTime);
        TextView tvSuccessRate = (TextView) views.layoutFundamentals.findViewById(R.id.tvSuccessRate);
        TextView tvHeader = (TextView) views.layoutFundamentals.findViewById(R.id.tvHeader);

        assertNotNull(ivImage);
        assertNotNull(tvTime);
        assertNotNull(tvSuccessRate);
        assertNotNull(tvHeader);
        // TODO add correct assertion for image
//        assertEquals(RuntimeEnvironment.application.getResources().getDrawable(
//                R.drawable.ic_key_white_48dp), ivImage.getDrawable());
        assertEquals(activity.getString(R.string.zero_time), tvTime.getText());
        assertEquals("0%", tvSuccessRate.getText());
        assertEquals(activity.getString(R.string.fundamentals), tvHeader.getText());
    }

    @Test
    public void doesSetLayoutText_RecordItem_Defense() throws Exception {
        ImageView ivImage = (ImageView) views.layoutDefense.findViewById(R.id.ivImage);
        TextView tvTime = (TextView) views.layoutDefense.findViewById(R.id.tvTime);
        TextView tvSuccessRate = (TextView) views.layoutDefense.findViewById(R.id.tvSuccessRate);
        TextView tvHeader = (TextView) views.layoutDefense.findViewById(R.id.tvHeader);

        assertNotNull(ivImage);
        assertNotNull(tvTime);
        assertNotNull(tvSuccessRate);
        assertNotNull(tvHeader);
        // TODO add correct assertion for image
//        assertEquals(RuntimeEnvironment.application.getResources().getDrawable(
//                R.drawable.ic_account_multiple_outline_white_48dp), ivImage.getDrawable());
        assertEquals(activity.getString(R.string.zero_time), tvTime.getText());
        assertEquals("0%", tvSuccessRate.getText());
        assertEquals(activity.getString(R.string.defense), tvHeader.getText());
    }

    @Test
    public void doesSetLayoutText_RecordItem_OffBallOffense() throws Exception {
        ImageView ivImage = (ImageView) views.layoutOffBallOffense.findViewById(R.id.ivImage);
        TextView tvTime = (TextView) views.layoutOffBallOffense.findViewById(R.id.tvTime);
        TextView tvSuccessRate = (TextView) views.layoutOffBallOffense.findViewById(R.id.tvSuccessRate);
        TextView tvHeader = (TextView) views.layoutOffBallOffense.findViewById(R.id.tvHeader);

        assertNotNull(ivImage);
        assertNotNull(tvTime);
        assertNotNull(tvSuccessRate);
        assertNotNull(tvHeader);
        // TODO add correct assertion for image
//        assertEquals(RuntimeEnvironment.application.getResources().getDrawable(
//                R.drawable.ic_human_handsup_white_48dp), ivImage.getDrawable());
        assertEquals(activity.getString(R.string.zero_time), tvTime.getText());
        assertEquals("0%", tvSuccessRate.getText());
        assertEquals(activity.getString(R.string.off_ball_offense), tvHeader.getText());
    }

    @Test
    public void doesSetLayoutText_RecordItem_Conditioning() throws Exception {
        ImageView ivImage = (ImageView) views.layoutConditioning.findViewById(R.id.ivImage);
        TextView tvTime = (TextView) views.layoutConditioning.findViewById(R.id.tvTime);
        TextView tvSuccessRate = (TextView) views.layoutConditioning.findViewById(R.id.tvSuccessRate);
        TextView tvHeader = (TextView) views.layoutConditioning.findViewById(R.id.tvHeader);

        assertNotNull(ivImage);
        assertNotNull(tvTime);
        assertNotNull(tvSuccessRate);
        assertNotNull(tvHeader);
        // TODO add correct assertion for image
//        assertEquals(RuntimeEnvironment.application.getResources().getDrawable(
//                R.drawable.ic_run_fast_white_48dp), ivImage.getDrawable());
        assertEquals(activity.getString(R.string.zero_time), tvTime.getText());
        assertEquals("0%", tvSuccessRate.getText());
        assertEquals(activity.getString(R.string.conditioning), tvHeader.getText());
    }

    @Test
    public void doesSetLayoutText_RecordItem_Shooting() throws Exception {
        ImageView ivImage = (ImageView) views.layoutShooting.findViewById(R.id.ivImage);
        TextView tvTime = (TextView) views.layoutShooting.findViewById(R.id.tvTime);
        TextView tvSuccessRate = (TextView) views.layoutShooting.findViewById(R.id.tvSuccessRate);
        TextView tvHeader = (TextView) views.layoutShooting.findViewById(R.id.tvHeader);

        assertNotNull(ivImage);
        assertNotNull(tvTime);
        assertNotNull(tvSuccessRate);
        assertNotNull(tvHeader);
        // TODO add correct assertion for image
//        assertEquals(RuntimeEnvironment.application.getResources().getDrawable(
//                R.drawable.ic_dribbble_white_48dp), ivImage.getDrawable());
        assertEquals(activity.getString(R.string.zero_time), tvTime.getText());
        assertEquals("0%", tvSuccessRate.getText());
        assertEquals(activity.getString(R.string.shooting), tvHeader.getText());
    }

    @Test
    public void doesSetLayoutText_RecordItem_BallHandling() throws Exception {
        ImageView ivImage = (ImageView) views.layoutBallHandling.findViewById(R.id.ivImage);
        TextView tvTime = (TextView) views.layoutBallHandling.findViewById(R.id.tvTime);
        TextView tvSuccessRate = (TextView) views.layoutBallHandling.findViewById(R.id.tvSuccessRate);
        TextView tvHeader = (TextView) views.layoutBallHandling.findViewById(R.id.tvHeader);

        assertNotNull(ivImage);
        assertNotNull(tvTime);
        assertNotNull(tvSuccessRate);
        assertNotNull(tvHeader);
        // TODO add correct assertion for image
//        assertEquals(RuntimeEnvironment.application.getResources().getDrawable(
//                R.drawable.ic_gesture_two_double_tap_white_48dp), ivImage.getDrawable());
        assertEquals(activity.getString(R.string.zero_time), tvTime.getText());
        assertEquals("0%", tvSuccessRate.getText());
        assertEquals(activity.getString(R.string.ball_handling), tvHeader.getText());
    }

    @After
    public void tearDown() {
        System.out.println("Running TearDown!");
        activityController.pause().stop().destroy();
        activity = null;
        toolbar = null;
        views = null;
    }

}