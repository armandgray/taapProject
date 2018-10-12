package com.armandgray.shared.application;

public interface UIComponent {

    default void onSetupContent() {
        assignGlobalFields();
        setupVisualElements();
        setupEventListeners();
        setupViewModel();
    }

    void assignGlobalFields();

    void setupVisualElements();

    void setupEventListeners();

    void setupViewModel();
}
