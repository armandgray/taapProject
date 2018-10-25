package com.armandgray.shared.util;

import android.content.Context;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;

import com.armandgray.shared.TestUtils;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.robolectric.annotation.Config;

@Config(manifest = Config.NONE)
@PrepareForTest({VibrateUtil.class, VibrationEffect.class})
@RunWith(PowerMockRunner.class)
public class VibrateUtilTest {

    public static final int TEST_LENGTH = 1000;
    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Mock
    private Vibrator mockVibrator;

    @Mock
    private VibrationEffect mockVibrationEffect;

    @Mock
    private Context mockContext;

    private VibrateUtil testVibrateUtil;

    @Before
    public void setUp() {
        PowerMockito.mockStatic(VibrationEffect.class);
        PowerMockito.when(VibrationEffect.createOneShot(TEST_LENGTH,
                VibrationEffect.DEFAULT_AMPLITUDE)).thenReturn(mockVibrationEffect);

        Mockito.when(mockContext.getSystemService(Context.VIBRATOR_SERVICE))
                .thenReturn(mockVibrator);

        testVibrateUtil = new VibrateUtil(mockContext);
    }

    @Test
    public void testVibrate() throws Exception {
        TestUtils.setStaticField(Build.VERSION.class.getField("SDK_INT"), 26);
        testVibrateUtil.vibrate(TEST_LENGTH);
        Mockito.verify(mockVibrator, Mockito.times(1)).vibrate(mockVibrationEffect);
    }

    @Test
    public void testVibrate_PreOreo() throws Exception {
        TestUtils.setStaticField(Build.VERSION.class.getField("SDK_INT"), 22);
        testVibrateUtil.vibrate(TEST_LENGTH);
        Mockito.verify(mockVibrator, Mockito.times(1)).vibrate(TEST_LENGTH);
    }

    @Test
    public void testVibrate_DoesNothing_IfVibratorIsNull() {
        Mockito.when(mockContext.getSystemService(Context.VIBRATOR_SERVICE)).thenReturn(null);
        testVibrateUtil = new VibrateUtil(mockContext);
        testVibrateUtil.vibrate(1000);
        Mockito.verify(mockVibrator, Mockito.never()).vibrate(Mockito.anyInt());
    }

    @Test
    public void testVibrate_DoesNothing_IfLengthIsLessThan100() {
        testVibrateUtil.vibrate(99);
        Mockito.verify(mockVibrator, Mockito.never()).vibrate(Mockito.anyInt());
    }

    @After
    public void tearDown() {
        testVibrateUtil = null;
    }
}