package com.armandgray.taap.models;

public class SessionLog {

    private Object sessionLength;
    private Object sessionGoal;
    private Object activeWork;
    private Object restTime;
    private Object setsCompleted;
    private Object repsCompleted;
    private Object successRate;
    private Object successRecord;

    public SessionLog(Builder builder) {
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

    public Object getSetsCompleted() {
        return setsCompleted;
    }

    public Object getRepsCompleted() {
        return repsCompleted;
    }


    public Object getSuccessRate() {
        return successRate;
    }

    public Object getSuccessRecord() {
        return successRecord;
    }

    public static class Builder {

        public SessionLog create() {
            return new SessionLog(this);
        }
    }

}
