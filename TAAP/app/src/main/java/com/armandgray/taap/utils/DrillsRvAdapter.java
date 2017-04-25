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

import static com.armandgray.taap.models.Drill.DRILL_TYPES;
import static com.armandgray.taap.models.Drill.getQueryResultList;
import static com.armandgray.taap.utils.DrillsHelper.getDrillsList;

public class DrillsRvAdapter extends RecyclerView.Adapter<DrillsRvAdapter.DrillViewHolder> {

    public static final String SEARCH = "Search: ";
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

    public void swapRvDrillsAdapterDataOnQuery(String query) {
        swapDataSet(getQueryResultList(getDrillsList(), query));
    }

    public void swapRvDrillsAdapterDataOnDrillType(String drillType) {
        swapDataSet(getListFilteredOnType(drillType));
    }

    private void swapDataSet(ArrayList<Drill> newDataList) {
        this.drillList = newDataList;
        notifyDataSetChanged();
    }

    private ArrayList<Drill> getListFilteredOnType(String drillType) {
        ArrayList<Drill> originalList = getDrillsList();
        if (Arrays.asList(DRILL_TYPES).contains(drillType)) {
            return getTypedList(drillType, originalList);
        }
        return isSearchType(drillType) ?
                getQueryResultList(originalList, getParsedTypeForQuery(drillType)) : originalList;
    }

    private boolean isSearchType(String drillType) {
        return drillType != null && drillType.contains(SEARCH);
    }

    private String getParsedTypeForQuery(String drillType) {
        return drillType.substring(SEARCH.length(), drillType.length() - 1);
    }

    private ArrayList<Drill> getTypedList(String drillType, ArrayList<Drill> originalList) {
        for (int i = 0; i < originalList.size(); i++) {
            if (!hasMatchingDrillType(drillType, originalList.get(i))) {
                originalList.remove(i);
                i--;
            }
        }
        return originalList;
    }

    private boolean hasMatchingDrillType(String drillType, Drill drill) {
        return Arrays.asList(drill.getCategory()).contains(drillType);

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