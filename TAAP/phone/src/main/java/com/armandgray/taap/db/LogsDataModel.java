package com.armandgray.taap.db;

import android.content.Context;
import androidx.annotation.VisibleForTesting;
import androidx.core.util.Pair;

import com.armandgray.taap.models.SessionLog;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import static com.armandgray.taap.models.Drill.BALL_HANDLING;
import static com.armandgray.taap.models.Drill.CONDITIONING;
import static com.armandgray.taap.models.Drill.DEFENSE;
import static com.armandgray.taap.models.Drill.FUNDAMENTALS;
import static com.armandgray.taap.models.Drill.OFFENSE;
import static com.armandgray.taap.models.Drill.SHOOTING;
import static com.armandgray.taap.models.SessionLog.ACTIVE_WORK;
import static com.armandgray.taap.models.SessionLog.REST_TIME;
import static com.armandgray.taap.models.SessionLog.SESSION_LENGTH;
import static com.armandgray.taap.utils.DateTimeHelper.getDateFormattedAsString;
import static com.armandgray.taap.utils.DateTimeHelper.getTotalTimeAsDate;
import static com.armandgray.taap.utils.MathHelper.getAveragePercentage;
import static com.armandgray.taap.utils.MathHelper.getPercentFormattedAsString;

/**
 * Created by armandgray on 12/27/17.
 */

public class LogsDataModel {

    private static ArrayList<SessionLog> allLogs;
    private static ArrayList<SessionLog> listFundamentalLogs;
    private static ArrayList<SessionLog> listDefenseLogs;
    private static ArrayList<SessionLog> listOffenseLogs;
    private static ArrayList<SessionLog> listConditioningLogs;
    private static ArrayList<SessionLog> listShootingLogs;
    private static ArrayList<SessionLog> listBallHandlingLogs;

    public static void retrieveAllLogs(Context context) {
        if (allLogs == null) {
            allLogs = new ArrayList<>();
        }

        allLogs = CursorDataHelper.getAllLogsFromDatabase(context);
        parseLogsIntoCategories(context);
    }

    private static void parseLogsIntoCategories(Context context) {
        createLists(context);

        for (SessionLog log : allLogs) {
            for (String category : log.getDrill().getCategory()) {
                if (FUNDAMENTALS.equalsIgnoreCase(category)) {
                    listFundamentalLogs.add(log);
                }

                if (DEFENSE.equalsIgnoreCase(category)) {
                    listDefenseLogs.add(log);
                }

                if (OFFENSE.equalsIgnoreCase(category)) {
                    listOffenseLogs.add(log);
                }

                if (CONDITIONING.equalsIgnoreCase(category)) {
                    listConditioningLogs.add(log);
                }

                if (SHOOTING.equalsIgnoreCase(category)) {
                    listShootingLogs.add(log);
                }

                if (BALL_HANDLING.equalsIgnoreCase(category)) {
                    listBallHandlingLogs.add(log);
                }
            }
        }
    }

    private static void createLists(Context context) {
        if (allLogs == null) {
            retrieveAllLogs(context);
        }

        if (listFundamentalLogs == null) {
            listFundamentalLogs = new ArrayList<>();
        }

        if (listDefenseLogs == null) {
            listDefenseLogs = new ArrayList<>();
        }

        if (listOffenseLogs == null) {
            listOffenseLogs = new ArrayList<>();
        }

        if (listConditioningLogs == null) {
            listConditioningLogs = new ArrayList<>();
        }

        if (listShootingLogs == null) {
            listShootingLogs = new ArrayList<>();
        }

        if (listBallHandlingLogs == null) {
            listBallHandlingLogs = new ArrayList<>();
        }
    }

    public static List<SessionLog> getAllLogs(Context context) {
        if (allLogs == null) {
            retrieveAllLogs(context);
        }

        return allLogs;
    }

    public static class LogDataContainer {

        public static final String TOTAL_SESSION_TIME = "TOTAL_SESSION_TIME";
        public static final String TOTAL_ACTIVE_WORK = "TOTAL_ACTIVE_WORK";
        public static final String TOTAL_REST_TIME = "TOTAL_REST_TIME";
        public static final String TOTAL_REPS_COMPLETED = "TOTAL_REPS_COMPLETED";
        public static final String TOTAL_EXERCISES_COMPLETED = "TOTAL_EXERCISES_COMPLETED";

        private HashMap<String, String> detailsDataMap;
        private HashMap<String, Pair<String, String>> categoriesDataMap;

        public LogDataContainer(Context context) {
            if (LogsDataModel.allLogs == null) {
                LogsDataModel.retrieveAllLogs(context);
            }

            populateLogDetailMap();
            populateCategoriesDataMap();
        }

        @VisibleForTesting
        public LogDataContainer(HashMap<String, String> detailsDataMap,
                                HashMap<String, Pair<String, String>> categoriesDataMap) {
            this.detailsDataMap = detailsDataMap;
            this.categoriesDataMap = categoriesDataMap;
        }

        private void populateLogDetailMap() {
            this.detailsDataMap = new HashMap<>();
            Date totalSessionTimeAsDate = getTotalTimeAsDate(LogsDataModel.allLogs, SESSION_LENGTH);
            Date totalActiveWorkAsDate = getTotalTimeAsDate(LogsDataModel.allLogs, ACTIVE_WORK);
            Date totalRestTimeAsDate = getTotalTimeAsDate(LogsDataModel.allLogs, REST_TIME);
            detailsDataMap.put(TOTAL_SESSION_TIME, getDateFormattedAsString(totalSessionTimeAsDate));
            detailsDataMap.put(TOTAL_ACTIVE_WORK, getDateFormattedAsString(totalActiveWorkAsDate));
            detailsDataMap.put(TOTAL_REST_TIME, getDateFormattedAsString(totalRestTimeAsDate));
            detailsDataMap.put(TOTAL_REPS_COMPLETED, String.valueOf(getTotalReps()));
            detailsDataMap.put(TOTAL_EXERCISES_COMPLETED, String.valueOf(allLogs.size()));
        }

        private int getTotalReps() {
            int reps = 0;
            for (SessionLog log : allLogs) {
                reps += log.getRepsCompleted() > 0
                        ? log.getSetsCompleted() * log.getRepsCompleted()
                        : log.getSetsCompleted();
            }
            return reps;
        }

        private void populateCategoriesDataMap() {
            this.categoriesDataMap = new HashMap<>();
            Pair<String, String> fundamentalsDataPair = new Pair<>(
                    getDateFormattedAsString(getTotalTimeAsDate(listFundamentalLogs)),
                    getPercentFormattedAsString(getAveragePercentage(listFundamentalLogs)));
            Pair<String, String> defenseDataPair = new Pair<>(
                    getDateFormattedAsString(getTotalTimeAsDate(listDefenseLogs)),
                    getPercentFormattedAsString(getAveragePercentage(listDefenseLogs)));
            Pair<String, String> offenseDataPair = new Pair<>(
                    getDateFormattedAsString(getTotalTimeAsDate(listOffenseLogs)),
                    getPercentFormattedAsString(getAveragePercentage(listOffenseLogs)));
            Pair<String, String> conditioningDataPair = new Pair<>(
                    getDateFormattedAsString(getTotalTimeAsDate(listConditioningLogs)),
                    getPercentFormattedAsString(getAveragePercentage(listConditioningLogs)));
            Pair<String, String> shootingDataPair = new Pair<>(
                    getDateFormattedAsString(getTotalTimeAsDate(listShootingLogs)),
                    getPercentFormattedAsString(getAveragePercentage(listShootingLogs)));
            Pair<String, String> ballHandlingDataPair = new Pair<>(
                    getDateFormattedAsString(getTotalTimeAsDate(listBallHandlingLogs)),
                    getPercentFormattedAsString(getAveragePercentage(listBallHandlingLogs)));
            categoriesDataMap.put(FUNDAMENTALS, fundamentalsDataPair);
            categoriesDataMap.put(DEFENSE, defenseDataPair);
            categoriesDataMap.put(OFFENSE, offenseDataPair);
            categoriesDataMap.put(CONDITIONING, conditioningDataPair);
            categoriesDataMap.put(SHOOTING, shootingDataPair);
            categoriesDataMap.put(BALL_HANDLING, ballHandlingDataPair);
        }

        public HashMap<String, String> getDetailsDataMap() {
            return detailsDataMap;
        }

        public HashMap<String, Pair<String, String>> getCategoriesDataMap() {
            return categoriesDataMap;
        }
    }
}
