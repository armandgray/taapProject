package com.armandgray.taap.log;

import android.content.Context;
import android.support.v4.util.Pair;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.armandgray.taap.BuildConfig;
import com.armandgray.taap.R;
import com.armandgray.taap.db.LogsDataModel.LogDataContainer;
import com.armandgray.taap.utils.ActivitySetupHelper;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;

import static com.armandgray.taap.db.LogsDataModel.LogDataContainer.TOTAL_ACTIVE_WORK;
import static com.armandgray.taap.db.LogsDataModel.LogDataContainer.TOTAL_EXERCISES_COMPLETED;
import static com.armandgray.taap.db.LogsDataModel.LogDataContainer.TOTAL_REPS_COMPLETED;
import static com.armandgray.taap.db.LogsDataModel.LogDataContainer.TOTAL_REST_TIME;
import static com.armandgray.taap.db.LogsDataModel.LogDataContainer.TOTAL_SESSION_TIME;
import static com.armandgray.taap.models.Drill.BALL_HANDLING;
import static com.armandgray.taap.models.Drill.CONDITIONING;
import static com.armandgray.taap.models.Drill.DEFENSE;
import static com.armandgray.taap.models.Drill.FUNDAMENTALS;
import static com.armandgray.taap.models.Drill.OFFENSE;
import static com.armandgray.taap.models.Drill.SHOOTING;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class LogActivityViewsTest {

    private static final Context context = RuntimeEnvironment.application;

    private View testRootView;
    private LogActivityViews views;

    @Before
    public void setUp() {
        System.out.println("Running Set Up!");
        LayoutInflater inflater = LayoutInflater.from(RuntimeEnvironment.application);
        testRootView = inflater.inflate(R.layout.content_log, null);
        Toolbar toolbar = new Toolbar(context);
        TextView textView = new TextView(context);
        textView.setId(R.id.tvTitle);
        textView.setLayoutParams(new Toolbar.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        toolbar.addView(textView);
        toolbar.setLayoutParams(new Toolbar.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        views = new LogActivityViews(testRootView, toolbar);
    }

    @SuppressWarnings("all")
    @Test
    public void doesImplementActivityViewsInterface_TestConstructor() throws Exception {
        assertTrue(views instanceof ActivitySetupHelper.ActivityViewsInterface);
    }

    @Test
    public void doesSetRootView_TestConstructor() throws Exception {
        assertNotNull(views);
        assertNotNull(views.rootView);
        assertEquals(views.rootView, testRootView);
    }

    @Test
    public void canSetupToolbar_MethodTest() throws Exception {
        assertNotNull(views.toolbar);
        views.setupActivityCoordinatorWidgets();
        assertEquals(
                context.getResources().getString(R.string.session_history),
                ((TextView) views.toolbar.findViewById(R.id.tvTitle)).getText());
    }

    @Test
    public void canSetLayoutText_DetailItem_TotalSessionTime() throws Exception {
        views.setupActivityInitialState();

        View layoutTotalSessionTime = views.rootView.findViewById(R.id.layoutTotalSessionTime);
        TextView header = (TextView) layoutTotalSessionTime.findViewById(R.id.header);
        TextView tvText = (TextView) layoutTotalSessionTime.findViewById(R.id.tvText);
        assertNotNull(header);
        assertNotNull(tvText);
        assertEquals(context.getString(R.string.total_session_time), header.getText());
        assertEquals(context.getString(R.string.zero_time), tvText.getText());
    }

    @Test
    public void canSetLayoutText_DetailItem_TotalActiveTime() throws Exception {
        views.setupActivityInitialState();

        View layoutTotalActiveTime = views.rootView.findViewById(R.id.layoutTotalActiveTime);
        TextView header = (TextView) layoutTotalActiveTime.findViewById(R.id.header);
        TextView tvText = (TextView) layoutTotalActiveTime.findViewById(R.id.tvText);
        assertNotNull(header);
        assertNotNull(tvText);
        assertEquals(context.getString(R.string.total_active_time), header.getText());
        assertEquals(context.getString(R.string.zero_time), tvText.getText());
    }

    @Test
    public void canSetLayoutText_DetailItem_TotalRestTime() throws Exception {
        views.setupActivityInitialState();

        View layoutTotalRestTime = views.rootView.findViewById(R.id.layoutTotalRestTime);
        TextView header = (TextView) layoutTotalRestTime.findViewById(R.id.header);
        TextView tvText = (TextView) layoutTotalRestTime.findViewById(R.id.tvText);
        assertNotNull(header);
        assertNotNull(tvText);
        assertEquals(context.getString(R.string.total_rest_time), header.getText());
        assertEquals(context.getString(R.string.zero_time), tvText.getText());
    }

    @Test
    public void canSetLayoutText_DetailItem_ExercisesCompleted() throws Exception {
        views.setupActivityInitialState();

        View layoutExercisesCompleted = views.rootView.findViewById(R.id.layoutExercisesCompleted);
        TextView header = (TextView) layoutExercisesCompleted.findViewById(R.id.header);
        TextView tvText = (TextView) layoutExercisesCompleted.findViewById(R.id.tvText);
        assertNotNull(header);
        assertNotNull(tvText);
        assertEquals(context.getString(R.string.exercises_completed), header.getText());
        assertEquals(String.valueOf(0), tvText.getText());
    }

    @Test
    public void canSetLayoutText_DetailItem_RepsCompleted() throws Exception {
        views.setupActivityInitialState();

        View layoutRepsCompleted = views.rootView.findViewById(R.id.layoutRepsCompleted);
        TextView header = (TextView) layoutRepsCompleted.findViewById(R.id.header);
        TextView tvText = (TextView) layoutRepsCompleted.findViewById(R.id.tvText);
        assertNotNull(header);
        assertNotNull(tvText);
        assertEquals(context.getString(R.string.reps_completed), header.getText());
        assertEquals(String.valueOf(0), tvText.getText());
    }

    @Test
    public void canSetLayoutText_RecordItem_Date() throws Exception {
        views.setupActivityInitialState();

        String dateString = ">  ";
        dateString += new SimpleDateFormat("EEE, MMM d, ''yy", Locale.US).format(new Date()) + "  <";
        TextView tvDate = (TextView) views.rootView.findViewById(R.id.tvDate);
        assertNotNull(tvDate);
        assertEquals(dateString, tvDate.getText());
    }

    @Test
    public void canSetLayoutText_RecordItem_Fundamentals() throws Exception {
        views.setupActivityInitialState();

        View layoutFundamentals = views.rootView.findViewById(R.id.layoutFundamentals);
        ImageView ivImage = (ImageView) layoutFundamentals.findViewById(R.id.ivImage);
        TextView tvTime = (TextView) layoutFundamentals.findViewById(R.id.tvTime);
        TextView tvSuccessRate = (TextView) layoutFundamentals.findViewById(R.id.tvSuccessRate);
        TextView tvHeader = (TextView) layoutFundamentals.findViewById(R.id.tvHeader);

        assertNotNull(ivImage);
        assertNotNull(tvTime);
        assertNotNull(tvSuccessRate);
        assertNotNull(tvHeader);
        assertEquals(R.drawable.ic_key_white_48dp, ivImage.getTag());
        assertEquals(context.getString(R.string.zero_time), tvTime.getText());
        assertEquals("0%", tvSuccessRate.getText());
        assertEquals(context.getString(R.string.fundamentals), tvHeader.getText());
    }

    @Test
    public void canSetLayoutText_RecordItem_Defense() throws Exception {
        views.setupActivityInitialState();

        View layoutDefense = views.rootView.findViewById(R.id.layoutDefense);
        ImageView ivImage = (ImageView) layoutDefense.findViewById(R.id.ivImage);
        TextView tvTime = (TextView) layoutDefense.findViewById(R.id.tvTime);
        TextView tvSuccessRate = (TextView) layoutDefense.findViewById(R.id.tvSuccessRate);
        TextView tvHeader = (TextView) layoutDefense.findViewById(R.id.tvHeader);

        assertNotNull(ivImage);
        assertNotNull(tvTime);
        assertNotNull(tvSuccessRate);
        assertNotNull(tvHeader);
        assertEquals(R.drawable.ic_account_multiple_outline_white_48dp, ivImage.getTag());
        assertEquals(context.getString(R.string.zero_time), tvTime.getText());
        assertEquals("0%", tvSuccessRate.getText());
        assertEquals(context.getString(R.string.defense), tvHeader.getText());
    }

    @Test
    public void canSetLayoutText_RecordItem_OffBallOffense() throws Exception {
        views.setupActivityInitialState();

        View layoutOffense = views.rootView.findViewById(R.id.layoutOffense);
        ImageView ivImage = (ImageView) layoutOffense.findViewById(R.id.ivImage);
        TextView tvTime = (TextView) layoutOffense.findViewById(R.id.tvTime);
        TextView tvSuccessRate = (TextView) layoutOffense.findViewById(R.id.tvSuccessRate);
        TextView tvHeader = (TextView) layoutOffense.findViewById(R.id.tvHeader);

        assertNotNull(ivImage);
        assertNotNull(tvTime);
        assertNotNull(tvSuccessRate);
        assertNotNull(tvHeader);
        assertEquals(R.drawable.ic_human_handsup_white_48dp, ivImage.getTag());
        assertEquals(context.getString(R.string.zero_time), tvTime.getText());
        assertEquals("0%", tvSuccessRate.getText());
        assertEquals(context.getString(R.string.offense), tvHeader.getText());
    }

    @Test
    public void canSetLayoutText_RecordItem_Conditioning() throws Exception {
        views.setupActivityInitialState();

        View layoutConditioning = views.rootView.findViewById(R.id.layoutConditioning);
        ImageView ivImage = (ImageView) layoutConditioning.findViewById(R.id.ivImage);
        TextView tvTime = (TextView) layoutConditioning.findViewById(R.id.tvTime);
        TextView tvSuccessRate = (TextView) layoutConditioning.findViewById(R.id.tvSuccessRate);
        TextView tvHeader = (TextView) layoutConditioning.findViewById(R.id.tvHeader);

        assertNotNull(ivImage);
        assertNotNull(tvTime);
        assertNotNull(tvSuccessRate);
        assertNotNull(tvHeader);
        assertEquals(R.drawable.ic_run_fast_white_48dp, ivImage.getTag());
        assertEquals(context.getString(R.string.zero_time), tvTime.getText());
        assertEquals("0%", tvSuccessRate.getText());
        assertEquals(context.getString(R.string.conditioning), tvHeader.getText());
    }

    @Test
    public void canSetLayoutText_RecordItem_Shooting() throws Exception {
        views.setupActivityInitialState();

        View layoutShooting = views.rootView.findViewById(R.id.layoutShooting);
        ImageView ivImage = (ImageView) layoutShooting.findViewById(R.id.ivImage);
        TextView tvTime = (TextView) layoutShooting.findViewById(R.id.tvTime);
        TextView tvSuccessRate = (TextView) layoutShooting.findViewById(R.id.tvSuccessRate);
        TextView tvHeader = (TextView) layoutShooting.findViewById(R.id.tvHeader);

        assertNotNull(ivImage);
        assertNotNull(tvTime);
        assertNotNull(tvSuccessRate);
        assertNotNull(tvHeader);
        assertEquals(R.drawable.ic_dribbble_white_48dp, ivImage.getTag());
        assertEquals(context.getString(R.string.zero_time), tvTime.getText());
        assertEquals("0%", tvSuccessRate.getText());
        assertEquals(context.getString(R.string.shooting), tvHeader.getText());
    }

    @Test
    public void canSetLayoutText_RecordItem_BallHandling() throws Exception {
        views.setupActivityInitialState();

        View layoutBallHandling = views.rootView.findViewById(R.id.layoutBallHandling);
        ImageView ivImage = (ImageView) layoutBallHandling.findViewById(R.id.ivImage);
        TextView tvTime = (TextView) layoutBallHandling.findViewById(R.id.tvTime);
        TextView tvSuccessRate = (TextView) layoutBallHandling.findViewById(R.id.tvSuccessRate);
        TextView tvHeader = (TextView) layoutBallHandling.findViewById(R.id.tvHeader);

        assertNotNull(ivImage);
        assertNotNull(tvTime);
        assertNotNull(tvSuccessRate);
        assertNotNull(tvHeader);
        assertEquals(R.drawable.ic_gesture_two_double_tap_white_48dp, ivImage.getTag());
        assertEquals(context.getString(R.string.zero_time), tvTime.getText());
        assertEquals("0%", tvSuccessRate.getText());
        assertEquals(context.getString(R.string.ball_handling), tvHeader.getText());
    }

    @Test
    public void canUpdateData_MethodTest() throws Exception {
        views.setupActivityInitialState();
        HashMap<String, String> detailsDataMap = new HashMap<>();
        HashMap<String, Pair<String, String>> categoriesDataMap = new HashMap<>();
        populateLogDetailMap(detailsDataMap);
        populateCategoriesDataMap(categoriesDataMap);
        LogDataContainer dataContainer = new LogDataContainer(detailsDataMap, categoriesDataMap);
        views.updateData(dataContainer);

        LinearLayout layoutTotalSessionTime = (LinearLayout) views.rootView.findViewById(R.id.layoutTotalSessionTime);
        LinearLayout layoutTotalActiveTime = (LinearLayout) views.rootView.findViewById(R.id.layoutTotalActiveTime);
        LinearLayout layoutTotalRestTime = (LinearLayout) views.rootView.findViewById(R.id.layoutTotalRestTime);
        LinearLayout layoutExercisesCompleted = (LinearLayout) views.rootView.findViewById(R.id.layoutExercisesCompleted);
        LinearLayout layoutRepsCompleted = (LinearLayout) views.rootView.findViewById(R.id.layoutRepsCompleted);

        LinearLayout layoutFundamentals = (LinearLayout) views.rootView.findViewById(R.id.layoutFundamentals);
        LinearLayout layoutDefense = (LinearLayout) views.rootView.findViewById(R.id.layoutDefense);
        LinearLayout layoutOffense = (LinearLayout) views.rootView.findViewById(R.id.layoutOffense);
        LinearLayout layoutConditioning = (LinearLayout) views.rootView.findViewById(R.id.layoutConditioning);
        LinearLayout layoutShooting = (LinearLayout) views.rootView.findViewById(R.id.layoutShooting);
        LinearLayout layoutBallHandling = (LinearLayout) views.rootView.findViewById(R.id.layoutBallHandling);

        assertNotNull(layoutTotalSessionTime);
        assertNotNull(layoutTotalActiveTime);
        assertNotNull(layoutTotalRestTime);
        assertNotNull(layoutExercisesCompleted);
        assertNotNull(layoutRepsCompleted);

        assertNotNull(layoutFundamentals);
        assertNotNull(layoutDefense);
        assertNotNull(layoutOffense);
        assertNotNull(layoutConditioning);
        assertNotNull(layoutShooting);
        assertNotNull(layoutBallHandling);

        assertEquals("21:20:5", ((TextView) layoutTotalSessionTime.findViewById(R.id.tvText)).getText());
        assertEquals("21:20:9", ((TextView) layoutTotalActiveTime.findViewById(R.id.tvText)).getText());
        assertEquals("21:2:59", ((TextView) layoutTotalRestTime.findViewById(R.id.tvText)).getText());
        assertEquals("9000", ((TextView) layoutExercisesCompleted.findViewById(R.id.tvText)).getText());
        assertEquals("12", ((TextView) layoutRepsCompleted.findViewById(R.id.tvText)).getText());

        assertEquals("20:43:57", ((TextView) layoutFundamentals.findViewById(R.id.tvTime)).getText());
        assertEquals("24%", ((TextView) layoutFundamentals.findViewById(R.id.tvSuccessRate)).getText());

        assertEquals("20:43:57", ((TextView) layoutDefense.findViewById(R.id.tvTime)).getText());
        assertEquals("2%", ((TextView) layoutDefense.findViewById(R.id.tvSuccessRate)).getText());

        assertEquals("20:43:57", ((TextView) layoutOffense.findViewById(R.id.tvTime)).getText());
        assertEquals("4%", ((TextView) layoutOffense.findViewById(R.id.tvSuccessRate)).getText());

        assertEquals("20:43:7", ((TextView) layoutConditioning.findViewById(R.id.tvTime)).getText());
        assertEquals("24%", ((TextView) layoutConditioning.findViewById(R.id.tvSuccessRate)).getText());

        assertEquals("20:4:57", ((TextView) layoutShooting.findViewById(R.id.tvTime)).getText());
        assertEquals("24%", ((TextView) layoutShooting.findViewById(R.id.tvSuccessRate)).getText());

        assertEquals("2:43:57", ((TextView) layoutBallHandling.findViewById(R.id.tvTime)).getText());
        assertEquals("24%", ((TextView) layoutBallHandling.findViewById(R.id.tvSuccessRate)).getText());
    }

    private void populateLogDetailMap(HashMap<String, String> detailsDataMap) {
        detailsDataMap.put(TOTAL_SESSION_TIME, "21:20:5");
        detailsDataMap.put(TOTAL_ACTIVE_WORK, "21:20:9");
        detailsDataMap.put(TOTAL_REST_TIME, "21:2:59");
        detailsDataMap.put(TOTAL_REPS_COMPLETED, String.valueOf(12));
        detailsDataMap.put(TOTAL_EXERCISES_COMPLETED, String.valueOf(9000));
    }
    private void populateCategoriesDataMap(HashMap<String, Pair<String, String>> categoriesDataMap) {
        Pair<String, String> fundamentalsDataPair = new Pair<>("20:43:57", "24%");
        Pair<String, String> defenseDataPair = new Pair<>("20:43:57", "2%");
        Pair<String, String> offenseDataPair = new Pair<>("20:43:57", "4%");
        Pair<String, String> conditioningDataPair = new Pair<>("20:43:7", "24%");
        Pair<String, String> shootingDataPair = new Pair<>("20:4:57", "24%");
        Pair<String, String> ballHandlingDataPair = new Pair<>("2:43:57", "24%");
        categoriesDataMap.put(FUNDAMENTALS, fundamentalsDataPair);
        categoriesDataMap.put(DEFENSE, defenseDataPair);
        categoriesDataMap.put(OFFENSE, offenseDataPair);
        categoriesDataMap.put(CONDITIONING, conditioningDataPair);
        categoriesDataMap.put(SHOOTING, shootingDataPair);
        categoriesDataMap.put(BALL_HANDLING, ballHandlingDataPair);
    }

    @Test
    public void canUpdateData_MethodTest_RejectNull() throws Exception {
        views.setupActivityInitialState();
        views.updateData(null);

        HashSet<View> detailLayouts = new HashSet<>();
        HashSet<View> recordLayouts = new HashSet<>();
        List<Integer> detailTotalIds = new ArrayList<>();
        detailTotalIds.add(R.id.layoutTotalSessionTime);
        detailTotalIds.add(R.id.layoutTotalActiveTime);
        detailTotalIds.add(R.id.layoutTotalRestTime);
        detailLayouts.add(views.rootView.findViewById(R.id.layoutTotalSessionTime));
        detailLayouts.add(views.rootView.findViewById(R.id.layoutTotalActiveTime));
        detailLayouts.add(views.rootView.findViewById(R.id.layoutTotalRestTime));
        detailLayouts.add(views.rootView.findViewById(R.id.layoutExercisesCompleted));
        detailLayouts.add(views.rootView.findViewById(R.id.layoutRepsCompleted));

        recordLayouts.add(views.rootView.findViewById(R.id.layoutFundamentals));
        recordLayouts.add(views.rootView.findViewById(R.id.layoutDefense));
        recordLayouts.add(views.rootView.findViewById(R.id.layoutOffense));
        recordLayouts.add(views.rootView.findViewById(R.id.layoutConditioning));
        recordLayouts.add(views.rootView.findViewById(R.id.layoutShooting));
        recordLayouts.add(views.rootView.findViewById(R.id.layoutBallHandling));
        for (View layout : detailLayouts) {
            TextView tvText = (TextView) layout.findViewById(R.id.tvText);
            assertNotNull(tvText);
            if (detailTotalIds.contains(layout.getId())) {
                assertEquals(context.getString(R.string.zero_time), tvText.getText());
            } else {
                assertEquals(String.valueOf(0), tvText.getText());
            }
        }

        for (View layout : recordLayouts) {
            TextView tvTime = (TextView) layout.findViewById(R.id.tvTime);
            TextView tvSuccessRate = (TextView) layout.findViewById(R.id.tvSuccessRate);
            assertNotNull(tvTime);
            assertNotNull(tvSuccessRate);
            assertEquals(context.getString(R.string.zero_time), tvTime.getText());
            assertEquals("0%", tvSuccessRate.getText());
        }
    }

    @After
    public void tearDown() {
        System.out.println("Running TearDown!");
        testRootView = null;
        views = null;
    }

}
