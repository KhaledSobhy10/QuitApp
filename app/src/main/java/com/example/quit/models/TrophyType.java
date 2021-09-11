package com.example.quit.models;

import androidx.annotation.NonNull;

import com.example.quit.R;

import java.util.Calendar;

public enum TrophyType {
    TROPHY_24HOUR, TROPHY_3DAY, TROPHY_1WEEK, TROPHY_10DAY, TROPHY_2WEEK, TROPHY_1MONTH, TROPHY_3MONTH, TROPHY_6MONTH, TROPHY_1YEAR, TROPHY_5YEAR;


    @NonNull
    @Override
    public String toString() {
        switch (this) {
            case TROPHY_24HOUR:
                return "24 Hours";
            case TROPHY_3DAY:
                return "3 Days";
            case TROPHY_1WEEK:
                return "1 Week";
            case TROPHY_10DAY:
                return "10 Days";
            case TROPHY_2WEEK:
                return "2 Weeks";
            case TROPHY_1MONTH:
                return "1 MONTH";
            case TROPHY_3MONTH:
                return "3 MONTHS";
            case TROPHY_6MONTH:
                return "6 MONTHS";
            case TROPHY_1YEAR:
                return "1 Year";
            default:
                return "5 Years";
        }
    }

    public int getIcon(boolean achieved) {
        return achieved ? getAchievedIconId() : getNotAchievedIconId();
    }

    public int getAchievedIconId() {
        switch (this) {
            case TROPHY_24HOUR:
                return R.drawable.ic_trophy_24h;
            case TROPHY_3DAY:
                return R.drawable.ic_trophy_day_3;
            case TROPHY_1WEEK:
                return R.drawable.ic_trophy_week_1;
            case TROPHY_10DAY:
                return R.drawable.ic_trophy_day_10;
            case TROPHY_2WEEK:
                return R.drawable.ic_trophy_week_2;
            case TROPHY_1MONTH:
                return R.drawable.ic_trophy_month_1;
            case TROPHY_3MONTH:
                return R.drawable.ic_trophy_month_3;
            case TROPHY_6MONTH:
                return R.drawable.ic_trophy_month_6;
            case TROPHY_1YEAR:
                return R.drawable.ic_trophy_year_1;
            default:
                return R.drawable.ic_trophy_year_5;
        }
    }


    public int getNotAchievedIconId() {
        switch (this) {
            case TROPHY_24HOUR:
                return R.drawable.ic_trophy_24h_not_achieved;
            case TROPHY_3DAY:
                return R.drawable.ic_trophy_day_3_not_achieved;
            case TROPHY_1WEEK:
                return R.drawable.ic_trophy_week_1_not_achieved;
            case TROPHY_10DAY:
                return R.drawable.ic_trophy_day_10_not_achieved;
            case TROPHY_2WEEK:
                return R.drawable.ic_trophy_week_2_not_achieved;
            case TROPHY_1MONTH:
                return R.drawable.ic_trophy_month_1_not_achieved;
            case TROPHY_3MONTH:
                return R.drawable.ic_trophy_month_3_not_achieved;
            case TROPHY_6MONTH:
                return R.drawable.ic_trophy_month_6_not_achieved;
            case TROPHY_1YEAR:
                return R.drawable.ic_trophy_year_1_not_achieved;
            default:
                return R.drawable.ic_trophy_year_5_not_achieved;
        }
    }

    public long getTargetDate(long startTime) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(startTime);
        switch (this) {
            case TROPHY_24HOUR:
                calendar.add(Calendar.HOUR_OF_DAY, 24);
                break;
            case TROPHY_3DAY:
                calendar.add(Calendar.DAY_OF_MONTH, 3);
                break;
            case TROPHY_1WEEK:
                calendar.add(Calendar.DAY_OF_MONTH, 7);
                break;
            case TROPHY_10DAY:
                calendar.add(Calendar.DAY_OF_MONTH, 10);
                break;
            case TROPHY_2WEEK:
                calendar.add(Calendar.DAY_OF_MONTH, 14);
                break;
            case TROPHY_1MONTH:
                calendar.add(Calendar.DAY_OF_MONTH, 30);
                break;
            case TROPHY_3MONTH:
                calendar.add(Calendar.DAY_OF_MONTH, 3*30);
                break;
            case TROPHY_6MONTH:
                calendar.add(Calendar.DAY_OF_MONTH, 6*30);
                break;
            case TROPHY_1YEAR:
                calendar.add(Calendar.DAY_OF_MONTH, 365);
                break;
            default:
                calendar.add(Calendar.DAY_OF_MONTH, 5*365);
        }
        return calendar.getTimeInMillis();
    }
}