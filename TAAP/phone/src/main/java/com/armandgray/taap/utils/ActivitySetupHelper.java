package com.armandgray.taap.utils;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;

import com.armandgray.taap.R;

/**
 * Created by armandgray on 12/28/17.
 */

public class ActivitySetupHelper {

    public interface ActivityControllerInterface {
        void setupActionBar(ActionBar actionBar, Context context);
        void setupActivityViewController(ActionBar actionBar, Context context);
    }

    public interface ActivityViewsInterface {
        void setListener(Object object);
        void setupActivityCoordinatorWidgets();
        void setupActivityInitialState();
        void updateData(Object object);

    }

    public static void defaultSetupActionBar(ActionBar actionBar, Context context) {
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
            setHomeAsUpIndicatorColor(actionBar, context);
        }
    }

    private static void setHomeAsUpIndicatorColor(ActionBar actionBar, Context context) {
        final Drawable upArrow = context.getResources().getDrawable(R.drawable.ic_arrow_back_white_24dp);
        upArrow.setColorFilter(context.getResources().getColor(R.color.colorDarkGray), PorterDuff.Mode.SRC_ATOP);
        if (actionBar == null) {
            return;
        }

        actionBar.setHomeAsUpIndicator(upArrow);
    }
}
