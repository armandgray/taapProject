package com.armandgray.shared.db;

import com.armandgray.shared.R;
import com.armandgray.shared.model.Setting;
import com.armandgray.shared.model.UXPreference;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.List;

import io.reactivex.Single;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;

public class SettingsDaoTest {

    private static final Setting TEST_SETTING;

    static {
        TEST_SETTING = new Setting("TEST",
                R.drawable.ic_clock_white_24dp, UXPreference.Category.DATA);
    }

    private SettingsDao testDao;
    private SettingsDao.SettingsDaoWrapper testDaoWrapper;

    @Before
    public void setUp() {
        testDao = new SettingsDao() {
            @Override
            public Single<List<Setting>> all() {
                return null;
            }

            @Override
            public Single<Setting> setting(int id) {
                return null;
            }

            @Override
            public Single<List<Long>> insert(Setting... arr) {
                return null;
            }

            @Override
            public Single<Integer> update(Setting... arr) {
                return null;
            }

            @Override
            public Single<Integer> delete(Setting... arr) {
                return null;
            }
        };

        testDaoWrapper = new SettingsDao.SettingsDaoWrapper();
    }

    @Test
    public void testInterfaceMethods() {
        Assert.assertThat(testDao, is(notNullValue()));
    }

    @Test
    public void testFindSetting() {
        Assert.assertThat(testDao.findSetting(TEST_SETTING.getPreference()), is(nullValue()));
    }

    @Test
    public void testWrapper_FindSetting() {
        SettingsDao.SettingsDaoWrapper.CACHE.clear();
        SettingsDao.SettingsDaoWrapper.CACHE.add(TEST_SETTING);
        Assert.assertThat(testDaoWrapper.findSetting(TEST_SETTING.getPreference()), is(TEST_SETTING));
    }

    @Test
    public void testWrapper_FindSetting_ReturnsNullForUnhandledPreferences() {
        SettingsDao.SettingsDaoWrapper.CACHE.add(TEST_SETTING);
        UXPreference testPreference = new Setting("TEST", R.drawable.ic_clock_white_24dp,
                UXPreference.Category.WORKOUT).getPreference();
        Assert.assertThat(testDaoWrapper.findSetting(testPreference), is(nullValue()));
    }

    @Ignore
    @Test
    public void testDaoWrapper() {
    }

    @After
    public void tearDown() {
        testDao = null;
    }
}