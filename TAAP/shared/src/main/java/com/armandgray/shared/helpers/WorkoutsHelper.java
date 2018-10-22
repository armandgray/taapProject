package com.armandgray.shared.helpers;

import com.armandgray.shared.db.PerformanceDao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.TreeMap;

import androidx.annotation.NonNull;

public class WorkoutsHelper {

    private WorkoutsHelper() {
        // Strict Helper Class
    }

    @NonNull
    public static TreeMap<Long, List<PerformanceDao.DaoLog>> toWorkoutsTreeMap(
            @NonNull List<PerformanceDao.DaoLog> logs, int maxBreak, int limit) {
        TreeMap<Long, List<PerformanceDao.DaoLog>> treeMap = new TreeMap<>();
        if (logs.size() == 0 || limit < 1) {
            return treeMap;
        }

        Collections.sort(logs);

        for (PerformanceDao.DaoLog log : logs) {
            long endTime = log.getPerformance().getEndTime();
            Long ceilingKey = treeMap.ceilingKey(endTime);
            List<PerformanceDao.DaoLog> list = new ArrayList<>();

            if (ceilingKey != null && ceilingKey - endTime < maxBreak) {
                list = treeMap.get(ceilingKey);
            } else {
                treeMap.put(endTime, list);
            }

            if (list != null) {
                list.add(log);
            }

            if (treeMap.size() > limit) {
                treeMap.remove(treeMap.firstKey());
                break;
            }
        }

        return treeMap;
    }
}
