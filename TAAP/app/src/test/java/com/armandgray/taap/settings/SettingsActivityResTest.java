package com.armandgray.taap.settings;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.armandgray.taap.BuildConfig;
import com.armandgray.taap.R;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.android.controller.ActivityController;
import org.robolectric.annotation.Config;

import static com.armandgray.taap.db.DatabaseContentProvider.CONTENT_URI_DELETE_ALL_DATA;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class SettingsActivityResTest {

    private ActivityController<SettingsActivity> activityController;
    private SettingsActivity activity;
    private LinearLayout settingsContainer;

    @Before
    public void setUp() {
        System.out.println("Running Set Up!");
        activityController = Robolectric.buildActivity(SettingsActivity.class);
        activity = activityController.create().visible().get();

        ScrollView layout = (ScrollView) View.inflate(activity, R.layout.content_settings, null);
        settingsContainer = (LinearLayout) layout.findViewById(R.id.settingsContainer);
    }

    @Test @Ignore
    public void hasLayout_AboutContainer() throws Exception {
        assertNotNull(View.inflate(activity, R.layout.content_settings, null));
    }

    @Test @Ignore
    public void hasView_AboutContainer_FirstTopBorder() throws Exception {
        assertNotNull(settingsContainer.findViewById(R.id.firstTopBorder));
    }

    @Test @Ignore
    public void hasView_AboutContainer_TvRateThisApp() throws Exception {
        
        assertNotNull(settingsContainer.findViewById(R.id.tvRateThisApp));
    }

    @Test @Ignore
    public void hasDrawable_AboutTextBackground() throws Exception {
        
        TextView textView = (TextView) settingsContainer.findViewById(R.id.tvRateThisApp);
        assertEquals(activity.getResources().getDrawable(R.drawable.about_item_background), textView.getBackground());
    }

    @Test @Ignore
    public void hasView_AboutContainer_TvSeeMore() throws Exception {
        assertNotNull(settingsContainer.findViewById(R.id.tvSeeMore));
    }

    @Test @Ignore
    public void hasView_AboutContainer_LegalHeader() throws Exception {
        assertNotNull(settingsContainer.findViewById(R.id.tvLegalHeader));
    }

    @Test @Ignore
    public void hasView_AboutContainer_SecondTopBorder() throws Exception {
        assertNotNull(settingsContainer.findViewById(R.id.secondTopBorder));
    }

    @Test @Ignore
    public void hasView_AboutContainer_CopyRight() throws Exception {
        assertNotNull(settingsContainer.findViewById(R.id.tvClearData));
    }

    @Test @Ignore
    public void hasView_AboutContainer_TermsAndConditions() throws Exception {
        assertNotNull(settingsContainer.findViewById(R.id.tvTermsConditions));
    }

    @Test @Ignore
    public void hasView_AboutContainer_SoftwareLicenses() throws Exception {
        assertNotNull(settingsContainer.findViewById(R.id.tvSoftwareLicenses));
    }

    @Test @Ignore
    public void hasView_AboutContainer_AppInfoHeader() throws Exception {
        assertNotNull(settingsContainer.findViewById(R.id.tvAppInfo));
    }

    @Test @Ignore
    public void hasView_AboutContainer_VersionNumber() throws Exception {
        assertNotNull(settingsContainer.findViewById(R.id.tvVersionHeader));
        assertNotNull(settingsContainer.findViewById(R.id.tvVersionNumber));
    }

    @After
    public void tearDown() {
        System.out.println("Running TearDown!");
        activity.getContentResolver().delete(CONTENT_URI_DELETE_ALL_DATA, null, null);
        activity.finish();
        activityController.pause().stop().destroy();
        activity = null;
    }

}