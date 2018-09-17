package com.njz.letsgoapp.util.rxbus.busEvent;

import java.util.List;

/**
 * Created by LGQ
 * Time: 2018/8/8
 * Function:
 */

public class CalendarEvent {
    String startTime;
    String endTime;
    String days;
    public List<String> markerDays;
    String oneTime;

    public CalendarEvent(String startTime, String endTime, String days) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.days = days;
    }

    public CalendarEvent(List<String> markerDays) {
        this.markerDays = markerDays;
    }

    public CalendarEvent(String oneTime) {
        this.oneTime = oneTime;
    }

    public String getOneTime() {
        return oneTime;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public String getDays() {
        return days;
    }

    public List<String> getMarkerDays() {
        return markerDays;
    }
}
