package com.example.kintai;

import java.io.Serializable;

public class KintaiData implements Serializable {
    private Integer id;
    private String startDay;
    private String startTime;
    private String endDay;
    private String endTime;
    private String breakTime;
    private String totalWork;
    private String memo;

    public KintaiData() {}

    public KintaiData(Integer id, String startDay, String startTime, String endDay, String endTime, String breakTime, String totalWork, String memo) {
        this.id = id;
        this.startDay = startDay;
        this.startTime = startTime;
        this.endDay = endDay;
        this.endTime = endTime;
        this.breakTime = breakTime;
        this.totalWork = totalWork;
        this.memo = memo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStartDay() {
        return startDay;
    }

    public void setStartDay(String startDay) {
        this.startDay = startDay;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndDay() {
        return endDay;
    }

    public void setEndDay(String endDay) {
        this.endDay = endDay;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getBreakTime() {
        return breakTime;
    }

    public void setBreakTime(String breakTime) {
        this.breakTime = breakTime;
    }

    public String getTotalWork() {
        return totalWork;
    }

    public void setTotalWork(String totalWork) {
        this.totalWork = totalWork;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }
}
