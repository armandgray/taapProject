package com.armandgray.taap.detail;

import android.app.Activity;
import android.os.Bundle;

import org.junit.Test;

import static junit.framework.Assert.assertNotNull;

public class DetailSummaryDialogTest {

    @Test
    public void canCreateDetailSummaryDialog_TestOnCreateDialog() {
        DetailSummaryDialog dialog = new DetailSummaryDialog();
        Bundle savedInstanceState = new Bundle();
        assertNotNull(dialog.onCreateDialog(savedInstanceState));
    }

    @Test
    public void hasTemporaryConstructorWithActivity() {
        Activity activity = null;
        assertNotNull(new DetailSummaryDialog(activity));
    }

}