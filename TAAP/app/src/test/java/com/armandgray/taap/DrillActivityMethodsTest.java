package com.armandgray.taap;

import android.content.Context;
import android.widget.ArrayAdapter;

import org.junit.Test;
import org.mockito.Mock;

import java.lang.reflect.Method;

import static junit.framework.Assert.assertNotNull;

public class DrillActivityMethodsTest {

    @Mock
    private Context context;

    @Test
    public void canCreateSpinnerAdapter() throws Exception {
        Method createSpinnerAdapter = DrillActivity.class.getDeclaredMethod("createSpinnerAdapter");
        createSpinnerAdapter.setAccessible(true);
        Class<? extends ArrayAdapter> stringClass = ArrayAdapter.class;
        assertNotNull(stringClass.cast(createSpinnerAdapter.invoke(new DrillActivity())));
    }
}
