package com.armandgray.taap.activity;

import android.os.Bundle;

import com.armandgray.shared.application.TAAPActivity;
import com.armandgray.shared.model.Drill;
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

public class DrillPickerActivity extends TAAPActivity {

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
        super.setContentView(R.layout.activity_drill_picker);
        super.onSetupContent();
    }

    @Override
    protected void assignGlobalFields() {
        recyclerPicker = findViewById(R.id.recycler_picker);
    }

    @Override
    protected void setupVisualElements() {
        drillAdapter.updateData(Drill.Defaults.getDefaults());
        recyclerPicker.setAdapter(drillAdapter);
        recyclerPicker.setEdgeItemsCenteringEnabled(true);
        recyclerPicker.setLayoutManager(new WearableLinearLayoutManager(this));
    }

    @Override
    protected void setupEventListeners() {
        recyclerPicker.addOnItemTouchListener(new RecyclerItemClickListener(this,
                (view, position) -> {
                    drillViewModel.onDrillSelected(drillAdapter.getItem(position));
                    onBackPressed();
                }));
    }

    @Override
    protected void setupViewModel() {
    }

    @NonNull
    @Override
    public String toString() {
        return getClass().getSimpleName() + "@" + Integer.toHexString(hashCode());
    }

    @Module
    public static class ActivityModule {

        @Provides
        DrillViewModel provideDrillViewModel(DrillPickerActivity activity) {
            return ViewModelProviders.of(activity).get(DrillViewModel.class);
        }
    }
}
