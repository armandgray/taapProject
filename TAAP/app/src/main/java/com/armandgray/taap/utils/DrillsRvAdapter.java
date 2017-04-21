package com.armandgray.taap.utils;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.armandgray.taap.R;
import com.armandgray.taap.models.Drill;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.armandgray.taap.utils.DrillsHelper.getDrillsList;

public class DrillsRvAdapter extends RecyclerView.Adapter<DrillsRvAdapter.DrillViewHolder> {

    ArrayList<Drill> drillList;

    DrillsRvAdapter() {}

    public DrillsRvAdapter(ArrayList<Drill> drillList) {
        this.drillList = drillList;
    }

    @Override
    public DrillViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new DrillViewHolder(getLayout(parent));
    }

    @Override
    public void onBindViewHolder(DrillViewHolder viewHolder, int position) {
        final Drill drill = getItemAtPosition(position);

        ImageView ivImage = viewHolder.ivImage;
        TextView tvTitle = viewHolder.tvTitle;

        ivImage.setImageResource(drill.getImageId());
        tvTitle.setText(drill.getTitle());
    }

    @Override
    public int getItemCount() {
        return drillList.size();
    }

    View getLayout(ViewGroup parent) {
        return LayoutInflater.from(parent.getContext()).inflate(R.layout.drill_listitem, parent, false);
    }

    public Drill getItemAtPosition(int position) {
        if (drillList == null || drillList.size() <= position) { return null; }
        return drillList.get(position);
    }

    public void swapRvDrillsAdapterData(String drillType) {
        swapDataSet(getListFilteredOnType(drillType));
    }

    private void swapDataSet(List<Drill> newDataList) {
        drillList.clear();
        drillList.addAll(newDataList);
        notifyDataSetChanged();
    }

    private List<Drill> getListFilteredOnType(String drillType) {
        ArrayList<Drill> originalList = getDrillsList();
        for (int i = 0; i < originalList.size(); i++) {
            filterDrillOnType(drillType, originalList, i);
        }
        return originalList;
    }

    private void filterDrillOnType(String drillType, ArrayList<Drill> originalList, int i) {
        if (!Arrays.asList(originalList.get(i).getCategory()).contains(drillType)) {
            originalList.remove(i);
        }
    }

    static class DrillViewHolder extends RecyclerView.ViewHolder {
        View itemView;
        ImageView ivImage;
        TextView tvTitle;

        public DrillViewHolder(View itemView) {
            super(itemView);

            this.itemView = itemView;
            ivImage = (ImageView) itemView.findViewById(R.id.ivImage);
            tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
        }
    }
}