package com.armandgray.taap.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class SessionLog implements Parcelable {

    public static final String SESSION_LENGTH = "SESSION_LENGTH";
    public static final String ACTIVE_WORK = "ACTIVE_WORK";
    public static final String REST_TIME = "REST_TIME";
    public static final List<String> ALL_FIELDS;

    static {
        ALL_FIELDS = new ArrayList<>(3);
        ALL_FIELDS.add(SESSION_LENGTH);
        ALL_FIELDS.add(ACTIVE_WORK);
        ALL_FIELDS.add(REST_TIME);
    }

    private Date sessionDate;
    private Date sessionLength;
    private String sessionGoal;
    private Date activeWork;
    private Date restTime;
    private int setsCompleted;
    private int repsCompleted;
    private double successRate;
    private double successRecord;
    private Drill drill;
    private int sessionId;

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
        this.drill = builder.drill;
    }

    public static class Builder {

        private Date sessionDate;
        private Date sessionLength;
        private String sessionGoal;
        private Date activeWork;
        private Date restTime;
        private int setsCompleted;
        private int repsCompleted;
        private double successRate;
        private double successRecord;
        private Drill drill;

        public Builder() {
            Calendar calendar = Calendar.getInstance();
            calendar.set(0, 0, 0, 0, 0, 0);
            this.sessionDate = new Date();
            this.sessionLength = calendar.getTime();
            this.sessionGoal = "None";
            this.activeWork = calendar.getTime();
            this.restTime = calendar.getTime();
        }

        public Builder sessionLength(Date s) {
            this.sessionLength = s;
            return this;
        }

        public Builder sessionGoal(String s) {
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

        public Builder drill(Drill d) {
            this.drill = d;
            return this;
        }

        public SessionLog create() {
            return new SessionLog(this);
        }
    }

    public int getSessionId() {
        return sessionId;
    }

    public void setSessionId(int sessionId) {
        this.sessionId = sessionId;
    }

    public Date getSessionDate() {
        return sessionDate;
    }

    public void setSessionDate(Date sessionDate) {
        this.sessionDate = sessionDate;
    }

    public Date getSessionLength() {
        return sessionLength;
    }

    public String getSessionGoal() {
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

    public Drill getDrill() {
        return drill;
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
        dest.writeString(this.sessionGoal);
        dest.writeLong(this.activeWork != null ? this.activeWork.getTime() : -1);
        dest.writeLong(this.restTime != null ? this.restTime.getTime() : -1);
        dest.writeInt(this.setsCompleted);
        dest.writeInt(this.repsCompleted);
        dest.writeDouble(this.successRate);
        dest.writeDouble(this.successRecord);
        dest.writeParcelable(this.drill, flags);
        dest.writeInt(this.sessionId);
    }

    protected SessionLog(Parcel in) {
        long tmpSessionDate = in.readLong();
        this.sessionDate = tmpSessionDate == -1 ? null : new Date(tmpSessionDate);
        long tmpSessionLength = in.readLong();
        this.sessionLength = tmpSessionLength == -1 ? null : new Date(tmpSessionLength);
        this.sessionGoal = in.readString();
        long tmpActiveWork = in.readLong();
        this.activeWork = tmpActiveWork == -1 ? null : new Date(tmpActiveWork);
        long tmpRestTime = in.readLong();
        this.restTime = tmpRestTime == -1 ? null : new Date(tmpRestTime);
        this.setsCompleted = in.readInt();
        this.repsCompleted = in.readInt();
        this.successRate = in.readDouble();
        this.successRecord = in.readDouble();
        this.drill = in.readParcelable(Drill.class.getClassLoader());
        this.sessionId = in.readInt();
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
