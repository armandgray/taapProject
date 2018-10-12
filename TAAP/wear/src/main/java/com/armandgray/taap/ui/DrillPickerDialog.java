package com.armandgray.taap.ui;

import android.os.Bundle;

import com.armandgray.shared.application.UIComponent;
import com.armandgray.shared.model.Drill;
import com.armandgray.shared.navigation.NavigationActivity;
import com.armandgray.shared.ui.DrillAdapter;
import com.armandgray.shared.ui.RecyclerItemClickListener;
import com.armandgray.shared.viewModel.DrillViewModel;
import com.armandgray.taap.R;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProviders;
import androidx.wear.widget.WearableLinearLayoutManager;
import androidx.wear.widget.WearableRecyclerView;
import dagger.Module;
import dagger.Provides;
import dagger.android.AndroidInjection;

public class DrillPickerDialog extends NavigationActivity implements UIComponent {

    @Inject
    DrillViewModel drillViewModel;
    @Inject
    DrillAdapter drillAdapter;

    private WearableRecyclerView recyclerPicker;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Dagger Injection
        AndroidInjection.inject(this);

        // Super
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.dialog_drill_picker);
        onSetupContent();
    }

    @Override
    public void assignGlobalFields() {
        recyclerPicker = findViewById(R.id.recycler_picker);
    }

    @Override
    public void setupVisualElements() {
        drillAdapter.updateData(Drill.Defaults.getDefaults());
        recyclerPicker.setAdapter(drillAdapter);
        recyclerPicker.setEdgeItemsCenteringEnabled(true);
        recyclerPicker.setLayoutManager(new WearableLinearLayoutManager(this));
    }

    @Override
    public void setupEventListeners() {
        recyclerPicker.addOnItemTouchListener(new RecyclerItemClickListener(this,
                (view, position) -> {
                    drillViewModel.onDrillSelected(drillAdapter.getItem(position));
                    onBackPressed();
                    finish();
                }));
    }

    @Override
    public void setupViewModel() {
        super.setupViewModel();

        drillViewModel.getDrills().observe(this, drillAdapter::updateData);
        drillViewModel.getActiveDrill().observe(this,
                drill -> recyclerPicker.smoothScrollToPosition(drillAdapter.indexOf(drill)));
    }

    @NonNull
    @Override
    public String toString() {
        return getClass().getSimpleName() + "@" + Integer.toHexString(hashCode());
    }

    @Module
    public static class ActivityModule
            extends NavigationActivity.NavigationModule<DrillPickerDialog> {

        @Provides
        @NonNull
        DrillViewModel provideViewModel(DrillPickerDialog activity) {
            return ViewModelProviders.of(activity).get(DrillViewModel.class);
        }
    }
}
