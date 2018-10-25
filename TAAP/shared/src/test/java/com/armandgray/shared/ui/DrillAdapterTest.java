package com.armandgray.shared.ui;

import android.view.View;

import com.armandgray.shared.R;
import com.armandgray.shared.model.Drill;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.mock;

@RunWith(RobolectricTestRunner.class)
@Config(manifest = Config.NONE)
public class DrillAdapterTest {

    private static final String FREE_THROWS = "Free Throws";
    private static final Drill TEST_DRILL;

    static {
        TEST_DRILL = new Drill(FREE_THROWS, R.drawable.ic_add_white_24dp,
                Drill.Type.SHOOTING_ONLY);
    }

    private DrillAdapter adapter;
    private DrillAdapter.DrillViewHolder holder;
    private View mockView;

    @Before
    public void setUp() {

        adapter = new DrillAdapter();
        mockView = mock(View.class);
    }

    @Ignore
    @Test
    public void canGetItemCount() throws Exception {
        // TODO implement tests
        Assert.assertThat(true, is(true));
    }

    @After
    public void tearDown() {

        adapter = null;
        holder = null;
        mockView = null;
    }

}