package com.armandgray.taap.log;

import android.content.Context;
import android.graphics.drawable.Drawable;
import androidx.appcompat.app.ActionBar;

import com.armandgray.taap.BuildConfig;
import com.armandgray.taap.R;
import com.armandgray.taap.db.LogsDataModel;
import com.armandgray.taap.utils.ActivitySetupHelper;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import static com.armandgray.taap.db.DatabaseContentProvider.CONTENT_URI_DELETE_ALL_DATA;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;

@RunWith(RobolectricTestRunner.class)
@Config(manifest = Config.NONE)
public class LogActivityControllerTest {

    private static final Context CONTEXT = RuntimeEnvironment.application;

    @Mock
    private ActionBar mockActionBar;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    private LogActivityController controller;
    private boolean[] testFlag;
    private final ActivitySetupHelper.ActivityViewsInterface testViewsInterface =
            new ActivitySetupHelper.ActivityViewsInterface() {
        @Override
        public void setListener(Object object) {
        }

        @Override
        public void setupActivityCoordinatorWidgets() {
            testFlag[0] = true;
        }

        @Override
        public void setupActivityInitialState() {
            testFlag[1] = true;
        }

        @Override
        public void updateData(Object object) {
            testFlag[2] = object instanceof LogsDataModel.LogDataContainer;
        }
    };

    @Before
    public void setUp() {

        testFlag = new boolean[3];
        controller = new LogActivityController(CONTEXT, mockActionBar, testViewsInterface);
    }

    @Test @Ignore
    @SuppressWarnings("all")
    public void doesImplementActivityControllerInterface_TestConstructor() throws Exception {
        assertTrue(controller instanceof ActivitySetupHelper.ActivityControllerInterface);
    }

    @Test @Ignore
    public void doesSetupActivityViewController_TestConstructor() throws Exception {
        assertNotNull(controller.viewsInterface);
        assertEquals(testViewsInterface, controller.viewsInterface);
        for (int i = 0; i < testFlag.length; i++) {
            assertTrue(testFlag[i]);
        }

        Drawable arrow = CONTEXT.getResources().getDrawable(R.drawable.ic_arrow_back_white_24dp);
        Mockito.verify(mockActionBar, Mockito.times(1)).setDisplayHomeAsUpEnabled(true);
        Mockito.verify(mockActionBar, Mockito.times(1)).setDisplayShowTitleEnabled(false);
        Mockito.verify(mockActionBar, Mockito.times(1)).setHomeAsUpIndicator(arrow);
        Mockito.verifyNoMoreInteractions(mockActionBar);
    }

    @After
    public void tearDown() {

        CONTEXT.getContentResolver().delete(CONTENT_URI_DELETE_ALL_DATA, null, null);
        testFlag = null;
        controller = null;
    }

}
