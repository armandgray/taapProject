package com.armandgray.taap.log;

import android.content.Context;
import androidx.annotation.VisibleForTesting;
import androidx.appcompat.app.ActionBar;

import com.armandgray.taap.db.LogsDataModel;
import com.armandgray.taap.utils.ActivitySetupHelper;
import com.armandgray.taap.utils.MVCActivityController;

public class LogActivityController extends MVCActivityController {

    @VisibleForTesting final ActivitySetupHelper.ActivityViewsInterface viewsInterface;

    LogActivityController(Context context, ActionBar actionBar,
                          ActivitySetupHelper.ActivityViewsInterface viewsInterface) {
        this.viewsInterface = viewsInterface;
        setupActivityViewController(actionBar, context);
    }

    @Override
    public void setupActivityViewController(ActionBar actionBar, Context context) {
        setupActionBar(actionBar, context);
        viewsInterface.setupActivityCoordinatorWidgets();
        viewsInterface.setupActivityInitialState();
        viewsInterface.updateData(new LogsDataModel.LogDataContainer(context));
    }

    @Override
    public void setupActionBar(ActionBar actionBar, Context context) {
        ActivitySetupHelper.defaultSetupActionBar(actionBar, context);
    }
}
