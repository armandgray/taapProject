package com.armandgray.shared.application;

import android.app.Activity;
import android.app.Application;

import com.armandgray.shared.db.DatabaseManager;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.robolectric.annotation.Config;

import dagger.android.AndroidInjector;

import static org.hamcrest.CoreMatchers.is;

@RunWith(PowerMockRunner.class)
@PrepareForTest({TAAPApplication.class, Application.class})
@Config(manifest = Config.NONE)
public class TAAPApplicationTest {

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Mock
    TAAPAppComponent mockAppComponent;

    @Mock
    DatabaseManager mockDatabaseManager;

    private TAAPApplication testApplication;

    @Before
    public void setUp() {
        testApplication = new TAAPApplication() {
            @Override
            public AndroidInjector<Activity> activityInjector() {
                return null;
            }
        };

        testApplication.databaseManager = mockDatabaseManager;

        PowerMockito.mockStatic(Application.class);
    }

    @Test
    public void testOnCreate_DoesPopulateDefaults() {
        // TODO Implement Test
//        testApplication.onCreate();
//        Mockito.verify(mockDatabaseManager, Mockito.times(1)).populateDefaults();
    }

    @Test
    public void testGetAppComponent() {
        TAAPApplication.appComponent = mockAppComponent;
        Assert.assertThat(TAAPApplication.getAppComponent(), is(TAAPApplication.appComponent));
    }

    @After
    public void tearDown() {
        testApplication = null;
    }
}