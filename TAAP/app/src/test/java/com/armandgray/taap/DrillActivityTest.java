package com.armandgray.taap;

import android.content.Context;
import android.widget.ArrayAdapter;

import org.junit.Test;
import org.mockito.Mock;

import java.lang.reflect.Method;

import static junit.framework.Assert.assertNotNull;

public class DrillActivityTest {

    @Mock
    private Context context;

    @Test
    public void canCreateSpinnerAdapter() throws Exception {
        Method createSpinnerAdapter = DrillActivity.class.getDeclaredMethod("createSpinnerAdapter", (Class<?>) null);
        createSpinnerAdapter.setAccessible(true);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(context, 0);
        createSpinnerAdapter.invoke(adapter, (Object) null);
        assertNotNull(adapter);
    }
}
