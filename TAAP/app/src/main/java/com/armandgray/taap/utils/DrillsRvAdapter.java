package com.armandgray.taap.utils;

import com.armandgray.taap.models.Drill;

import java.util.ArrayList;

public class DrillsRvAdapter {

    private ArrayList<Drill> drillList;

    public DrillsRvAdapter() {}

    public DrillsRvAdapter(ArrayList<Drill> drillList) {
        this.drillList = drillList;
    }

    public int getItemCount() {
        return drillList.size();
    }

    public Drill getItemAtPosition(int position) {
        if (drillList == null || drillList.size() <= position) { return null; }
        return drillList.get(position);
    }
}
