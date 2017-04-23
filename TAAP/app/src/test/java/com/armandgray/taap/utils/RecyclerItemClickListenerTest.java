package com.armandgray.taap.utils;

import org.junit.Test;

import static junit.framework.Assert.assertNotNull;

public class RecyclerItemClickListenerTest {

    @Test
    public void canCreateRecyclerItemClickListener() throws Exception {
        assertNotNull(new RecyclerItemClickListener());
    }

}