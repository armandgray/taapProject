package com.armandgray.taap.models;

public class SessionLog {

    private Object sessionDate;
    private Object sessionLength;
    private Object sessionGoal;
    private Object activeWork;
    private Object restTime;
    private int setsCompleted;
    private int repsCompleted;
    private double successRate;
    private double successRecord;

    public SessionLog(Builder builder) {
    }

    public static class Builder {

        private Object sessionDate;
        private Object sessionLength;
        private Object sessionGoal;
        private Object activeWork;
        private Object restTime;
        private int setsCompleted;
        private int repsCompleted;
        private double successRate;
        private double successRecord;
        public Builder sessionLength(Object s) {
            this.sessionLength = s;
            return this;
        }

        public Builder sessionGoal(Object s) {
            this.sessionGoal = s;
            return this;
        }

        public Builder activeWork(Object s) {
            this.activeWork = s;
            return this;
        }

        public Builder restTime(Object s) {
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

    public Object getSessionDate() {
        return sessionDate;
    }
    public Object getSessionLength() {
        return sessionLength;
    }

    public Object getSessionGoal() {
        return sessionGoal;
    }

    public Object getActiveWork() {
        return activeWork;
    }

    public Object getRestTime() {
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
