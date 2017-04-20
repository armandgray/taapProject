package com.armandgray.taap.utils;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.armandgray.taap.R;
import com.armandgray.taap.models.Drill;

import java.util.ArrayList;

public class DrillsRvAdapter {

    private ArrayList<Drill> drillList;

    DrillsRvAdapter() {}

    public DrillsRvAdapter(ArrayList<Drill> drillList) {
        this.drillList = drillList;
    }

    int getItemCount() {
        return drillList.size();
    }

    Drill getItemAtPosition(int position) {
        if (drillList == null || drillList.size() <= position) { return null; }
        return drillList.get(position);
    }

    static class DrillViewHolder extends RecyclerView.ViewHolder {
        View itemView;
        ImageView ivImage;
        TextView tvTitle;

        public DrillViewHolder(View itemView) {
            super(itemView);

            this.itemView = itemView;
            ivImage = itemView.findViewById(R.id.ivImage);
            tvTitle = itemView.findViewById(R.id.tvTitle);
        }
    }
}
