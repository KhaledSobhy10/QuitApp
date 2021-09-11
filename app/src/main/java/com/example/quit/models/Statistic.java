package com.example.quit.models;

import android.util.Log;

import com.example.quit.R;
import com.example.quit.utilities.Time;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.concurrent.TimeUnit;

public class Statistic {
    private StatisticType statisticType;
    private String statisticValue;
    private int statisticIconId;

    public Statistic(StatisticType statisticType, String statisticValue, int statisticIconId) {
        this.statisticType = statisticType;
        this.statisticValue = statisticValue;
        this.statisticIconId = statisticIconId;
    }

    public void setStatisticIconId(int statisticIconId) {
        this.statisticIconId = statisticIconId;
    }

    public StatisticType getStatisticType() {
        return statisticType;
    }

    public String getStatisticValue() {
        return statisticValue;
    }

    public int getStatisticIconId() {
        return statisticIconId;
    }


    public static List<Statistic> getAllStatistic(AddictionWithRelapse addictionWithRelapse) {
        List<Statistic> statisticList = new ArrayList<>();

        Addiction addiction = addictionWithRelapse.addiction;
        TreeSet<Relapse> relapseHashSet = addictionWithRelapse.sortedRelapseList();

        long maxPeriod = System.currentTimeMillis() - addiction.getFirstDateOfQuit();
        long minPeriod = maxPeriod;
        long avgPeriod = maxPeriod;
        long prevPeriod = 0;


        if (relapseHashSet.size() > 0) {
            avgPeriod = 0;
            maxPeriod = 0;


            List<Long> periods = new ArrayList<>();

            relapseHashSet.add(new Relapse(System.currentTimeMillis()));

            long startDate = addiction.getFirstDateOfQuit();

            for (Relapse relapse : relapseHashSet) {
                long currentPeriod = relapse.getRelapseDate() - startDate;
                periods.add(currentPeriod);

                startDate = relapse.getRelapseDate();

                avgPeriod += currentPeriod;
                maxPeriod = Math.max(maxPeriod, currentPeriod);
                minPeriod = Math.min(minPeriod, currentPeriod);
            }


            avgPeriod /= periods.size();
            prevPeriod = periods.get(periods.size() - 2);


        }
        statisticList.add(new Statistic(StatisticType.DAY_YOU_QUIT, addictionWithRelapse.addiction.getQuitDate(), R.drawable.ic_quit_date));
        statisticList.add(new Statistic(StatisticType.MAX_PERIOD, Time.formatTimeLongToHMS(maxPeriod), R.drawable.ic_abstinence_max));
        statisticList.add(new Statistic(StatisticType.MIN_PERIOD, Time.formatTimeLongToHMS(minPeriod), R.drawable.ic_abstinence_min));
        statisticList.add(new Statistic(StatisticType.AVE_PERIOD, Time.formatTimeLongToHMS(avgPeriod), R.drawable.ic_abstinence_average));
        statisticList.add(new Statistic(StatisticType.PREV_PERIOD, Time.formatTimeLongToHMS(prevPeriod), R.drawable.ic_abstinence_previous));
        statisticList.add(new Statistic(StatisticType.NUM_TIMER_RES, String.valueOf(addictionWithRelapse.relapseList.size()), R.drawable.ic_timer_reset));

        if (addictionWithRelapse.addiction.getAddictionType() == AddictionType.MONEY_WASTING) {
            statisticList.add(new Statistic(StatisticType.MONEY_SPENT, String.valueOf(addictionWithRelapse.addiction.getMoneyWasting()), R.drawable.ic_money_wasted));
            statisticList.add(new Statistic(StatisticType.MONEY_SAVED, String.valueOf(addictionWithRelapse.addiction.getMoneyWasting()), R.drawable.ic_money_rewards));
        } else if (addictionWithRelapse.addiction.getAddictionType() == AddictionType.TIME_WASTING) {
            statisticList.add(new Statistic(StatisticType.TIME_SPENT, String.valueOf(addictionWithRelapse.addiction.getTimeWasting()), R.drawable.ic_time_wasted_24dp));
            statisticList.add(new Statistic(StatisticType.TIME_INVESTED, "TODO", R.drawable.ic_time_investment));
        }
        return statisticList;
    }



}
