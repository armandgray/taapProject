package com.armandgray.taap.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Calendar;
import java.util.Date;

public class SessionLog implements Parcelable {

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
            Calendar calendar = Calendar.getInstance();
            calendar.set(0, 0, 0, 0, 0, 0);
            this.sessionDate = new Date();
            this.sessionLength = calendar.getTime();
            this.sessionGoal = calendar.getTime();
            this.activeWork = calendar.getTime();
            this.restTime = calendar.getTime();
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

    public static int getFieldCount() {
        return 9;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.sessionDate != null ? this.sessionDate.getTime() : -1);
        dest.writeLong(this.sessionLength != null ? this.sessionLength.getTime() : -1);
        dest.writeLong(this.sessionGoal != null ? this.sessionGoal.getTime() : -1);
        dest.writeLong(this.activeWork != null ? this.activeWork.getTime() : -1);
        dest.writeLong(this.restTime != null ? this.restTime.getTime() : -1);
        dest.writeInt(this.setsCompleted);
        dest.writeInt(this.repsCompleted);
        dest.writeDouble(this.successRate);
        dest.writeDouble(this.successRecord);
    }

    protected SessionLog(Parcel in) {
        long tmpSessionDate = in.readLong();
        this.sessionDate = tmpSessionDate == -1 ? null : new Date(tmpSessionDate);
        long tmpSessionLength = in.readLong();
        this.sessionLength = tmpSessionLength == -1 ? null : new Date(tmpSessionLength);
        long tmpSessionGoal = in.readLong();
        this.sessionGoal = tmpSessionGoal == -1 ? null : new Date(tmpSessionGoal);
        long tmpActiveWork = in.readLong();
        this.activeWork = tmpActiveWork == -1 ? null : new Date(tmpActiveWork);
        long tmpRestTime = in.readLong();
        this.restTime = tmpRestTime == -1 ? null : new Date(tmpRestTime);
        this.setsCompleted = in.readInt();
        this.repsCompleted = in.readInt();
        this.successRate = in.readDouble();
        this.successRecord = in.readDouble();
    }

    public static final Parcelable.Creator<SessionLog> CREATOR = new Parcelable.Creator<SessionLog>() {
        @Override
        public SessionLog createFromParcel(Parcel source) {
            return new SessionLog(source);
        }

        @Override
        public SessionLog[] newArray(int size) {
            return new SessionLog[size];
        }
    };
}
