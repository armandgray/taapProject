package com.armandgray.taap;

import android.content.Context;
import android.widget.ArrayAdapter;

import org.junit.Test;
import org.mockito.Mock;

import java.lang.reflect.Method;

import static junit.framework.Assert.assertNotNull;

public class MainActivityMethodsTest {

    @Mock
    private Context context;

    @Test
    public void canCreateSpinnerAdapter() throws Exception {
        Method createSpinnerAdapter = MainActivity.class.getDeclaredMethod("createSpinnerAdapter");
        createSpinnerAdapter.setAccessible(true);
        Class<? extends ArrayAdapter> stringClass = ArrayAdapter.class;
        assertNotNull(stringClass.cast(createSpinnerAdapter.invoke(new MainActivity())));
    }
}
