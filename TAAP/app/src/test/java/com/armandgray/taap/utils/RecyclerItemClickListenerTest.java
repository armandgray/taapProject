package com.armandgray.taap.utils;

import android.content.Context;

import org.junit.Test;
import org.mockito.Mock;

import static junit.framework.Assert.assertNotNull;

public class RecyclerItemClickListenerTest {

    @Mock
    Context context;

    @Test
    public void canCreateRecyclerItemClickListener_TestConstructor() throws Exception {
        assertNotNull(new RecyclerItemClickListener(context,
                new RecyclerItemClickListener.OnItemClickListener() {}));
    }

}