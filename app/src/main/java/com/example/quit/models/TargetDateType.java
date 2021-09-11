package com.example.quit.models;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public enum TargetDateType {
    TD_24HOUR, TD_3DAY, TD_1WEEK, TD_10DAY, TD_2WEEK, TD_1MONTH, TD_3MONTH, TD_6MONTH, TD_1YEAR, TD_5YEAR;

    public static TargetDateType fromString(String strType) {
        switch (strType) {
            case "24 Hours":
                return TD_24HOUR;
            case "3 Days":
                return TD_3DAY;
            case "1 Week":
                return TD_1WEEK;
            case "10 Days":
                return TD_10DAY;
            case "2 Weeks":
                return TD_2WEEK;
            case "1 Month":
                return TD_1MONTH;
            case "3 Month":
                return TD_3MONTH;
            case "6 Month":
                return TD_6MONTH;
            case "1 Year":
                return TD_1YEAR;
            default:
                return TD_5YEAR;
        }
    }

    public long getTargetDateFromThatTime(long startTime) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(startTime);
        switch (this) {
            case TD_24HOUR:
                calendar.add(Calendar.HOUR_OF_DAY, 24);
                break;
            case TD_3DAY:
                calendar.add(Calendar.DAY_OF_MONTH, 3);
                break;
            case TD_1WEEK:
                calendar.add(Calendar.DAY_OF_MONTH, 7);
                break;
            case TD_10DAY:
                calendar.add(Calendar.DAY_OF_MONTH, 10);
                break;
            case TD_2WEEK:
                calendar.add(Calendar.DAY_OF_MONTH, 14);
                break;
            case TD_1MONTH:
                calendar.add(Calendar.DAY_OF_MONTH, 30);
                break;
            case TD_3MONTH:
                calendar.add(Calendar.DAY_OF_MONTH, 3*60);
                break;
            case TD_6MONTH:
                calendar.add(Calendar.DAY_OF_MONTH, 6*30);
                break;
            case TD_1YEAR:
                calendar.add(Calendar.DAY_OF_MONTH, 365);
                break;
            default:
                calendar.add(Calendar.DAY_OF_MONTH, 5*365);
                break;
        }
        return calendar.getTimeInMillis();
    }

    public static List<TargetDateType> getTargetDates() {
        List<TargetDateType> targetDateTypes = new ArrayList<>();
        targetDateTypes.add(TD_24HOUR);
        targetDateTypes.add(TD_3DAY);
        targetDateTypes.add(TD_1WEEK);
        targetDateTypes.add(TD_10DAY);
        targetDateTypes.add(TD_24HOUR);
        targetDateTypes.add(TD_1MONTH);
        targetDateTypes.add(TD_3MONTH);
        targetDateTypes.add(TD_6MONTH);
        targetDateTypes.add(TD_1YEAR);
        targetDateTypes.add(TD_5YEAR);
        return targetDateTypes;
    }

    @Override
    public String toString() {
        switch (this) {
            case TD_24HOUR:
                return "24 Hours";
            case TD_3DAY:
                return "3 Days";
            case TD_1WEEK:
                return "1 Week";
            case TD_10DAY:
                return "10 Days";
            case TD_2WEEK:
                return "2 Weeks";
            case TD_1MONTH:
                return "1 Month";
            case TD_3MONTH:
                return "3 Month";
            case TD_6MONTH:
                return "6 Month";
            case TD_1YEAR:
                return "1 Year";
            default:
                return "5 Years";

        }
    }
}
