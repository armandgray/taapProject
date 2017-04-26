package com.armandgray.taap.models;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class SessionLog {

    private Date sessionDate;
    private Date sessionLength;
    private Date sessionGoal;
    private Date activeWork;
    private Date restTime;
    private int setsCompleted;
    private int repsCompleted;
    private double successRate;
    private double successRecord;

    private SessionLog(Builder builder) {
        this.sessionDate = builder.sessionDate;
        this.sessionLength = builder.sessionLength;
        this.sessionGoal = builder.sessionGoal;
        this.activeWork = builder.activeWork;
        this.restTime = builder.restTime;
        this.setsCompleted = builder.setsCompleted;
        this.repsCompleted = builder.repsCompleted;
        this.successRate = builder.successRate;
        this.successRecord = builder.successRecord;
    }

    public static class Builder {

        private Date sessionDate;
        private Date sessionLength;
        private Date sessionGoal;
        private Date activeWork;
        private Date restTime;
        private int setsCompleted;
        private int repsCompleted;
        private double successRate;
        private double successRecord;

        public Builder() {
            // date --> timeinMilliseconds
            this.sessionDate = new Date();
        }

        public Builder sessionLength(Date s) {
            this.sessionLength = s;
            return this;
        }

        public Builder sessionGoal(Date s) {
            this.sessionGoal = s;
            return this;
        }

        public Builder activeWork(Date s) {
            this.activeWork = s;
            return this;
        }

        public Builder restTime(Date s) {
            this.restTime = s;
            return this;
        }

        public Builder setsCompleted(int s) {
            this.setsCompleted = s;
            return this;
        }

        public Builder repsCompleted(int s) {
            this.repsCompleted = s;
            return this;
        }

        public Builder successRate(double s) {
            this.successRate = s;
            return this;
        }

        public Builder successRecord(double s) {
            this.successRecord = s;
            return this;
        }

        public SessionLog create() {
            return new SessionLog(this);
        }

    }

    public Date getSessionDate() {
        SimpleDateFormat defaultDateFormat = new SimpleDateFormat("00:00:00", Locale.US);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm:ss", Locale.US);
        String currentDateTimeString = defaultDateFormat.format(new Date(0));
        System.out.println(currentDateTimeString);
        return sessionDate;
    }
    public Date getSessionLength() {
        return sessionLength;
    }

    public Date getSessionGoal() {
        return sessionGoal;
    }

    public Date getActiveWork() {
        return activeWork;
    }

    public Date getRestTime() {
        return restTime;
    }

    public int getSetsCompleted() {
        return setsCompleted;
    }

    public int getRepsCompleted() {
        return repsCompleted;
    }


    public double getSuccessRate() {
        return successRate;
    }

    public double getSuccessRecord() {
        return successRecord;
    }

}
