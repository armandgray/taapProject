package com.armandgray.taap.permission;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.armandgray.shared.application.UIComponent;
import com.armandgray.shared.permission.DangerousPermission;
import com.armandgray.shared.permission.PermissionActivity;
import com.armandgray.taap.R;

import java.util.Locale;

import androidx.appcompat.app.AppCompatActivity;
import dagger.Module;
import dagger.android.AndroidInjection;

public class PermissionRationaleDialog extends AppCompatActivity implements UIComponent {

    private DangerousPermission permission;
    private boolean wasRevoked = true;

    private TextView textTitle;
    private TextView textRationale;
    private ImageButton buttonCancel;
    private ImageButton buttonOk;

    private boolean isResponseDeferred;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Dagger Injection
        AndroidInjection.inject(this);

        // Super
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.dialog_permission_rationale);
        onSetupContent();
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (isResponseDeferred) {
            finish();
        }
    }

    @Override
    public void assignGlobalFields() {
        permission = (DangerousPermission) getIntent().getSerializableExtra(
                PermissionActivity.PERMISSION_KEY);
        wasRevoked = getIntent().getBooleanExtra(PermissionActivity.REVOKED_KEY, true);

        textTitle = findViewById(R.id.text_title);
        textRationale = findViewById(R.id.text_rationale);
        buttonCancel = findViewById(R.id.button_cancel);
        buttonOk = findViewById(R.id.button_ok);
    }

    @Override
    public void setupVisualElements() {
        textTitle.setText(permission.getTitle());
        textRationale.setText(permission.getRationale());
    }

    @Override
    public void setupEventListeners() {
        buttonCancel.setOnClickListener(view -> finish());
        buttonOk.setOnClickListener(view -> onOkClick());
    }

    private void onOkClick() {
        if (!wasRevoked) {
            setResult(RESULT_OK);
            finish();
            return;
        }

        isResponseDeferred = true;
        Toast.makeText(this, String.format(Locale.getDefault(), "Allow %s in Permissions Settings",
                permission.getTitle()), Toast.LENGTH_SHORT).show();
        openSettings();
    }

    private void openSettings() {
        Intent intent = new Intent();
        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getPackageName(), null);
        intent.setData(uri);
        startActivity(intent);
    }

    @Override
    public void setupViewModel() {

    }

    @Module
    public static class ActivityModule {
    }
}
