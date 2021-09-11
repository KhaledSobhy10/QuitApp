package com.example.quit.models;

import android.util.Log;

import com.example.quit.utilities.Time;

import java.util.ArrayList;
import java.util.List;

public class Trophy {
    private final TrophyType trophyType;
    private final int trophyImageId;
    private final boolean achieved;
    private final String estimatedTime;
    private final int progress;

    public Trophy(TrophyType trophyType ,long startTime) {
        this.trophyType = trophyType;

        long targetDate = trophyType.getTargetDate(startTime);
        long estimated = targetDate - System.currentTimeMillis();
        long totalTime = targetDate - startTime;


        achieved = System.currentTimeMillis() >= targetDate;

        trophyImageId = trophyType.getIcon(achieved);

        estimatedTime = achieved ? "Achieved!" : Time.formatTimeLongToHMS(estimated);

        long achievedTime= System.currentTimeMillis()-startTime;
        progress = achievedTime < 0 ? 100 : (int) ((double) achievedTime / totalTime * 100);
    }

    public TrophyType getTrophyType() {
        return trophyType;
    }

    public int getTrophyImageId() {
        return trophyImageId;
    }

    public int getProgress() {
        return progress;
    }

    public String getEstimatedTime() {
        return estimatedTime;
    }

    public boolean isAchieved() {
        return achieved;
    }

    public static List<Trophy> getTrophyListToCurrentAchievedTime(long startDate){
        List<Trophy> trophies = new ArrayList<>();
        trophies.add(new Trophy(TrophyType.TROPHY_24HOUR,startDate));
        trophies.add(new Trophy(TrophyType.TROPHY_3DAY,startDate));
        trophies.add(new Trophy(TrophyType.TROPHY_1WEEK,startDate));
        trophies.add(new Trophy(TrophyType.TROPHY_10DAY,startDate));
        trophies.add(new Trophy(TrophyType.TROPHY_2WEEK,startDate));
        trophies.add(new Trophy(TrophyType.TROPHY_1MONTH,startDate));
        trophies.add(new Trophy(TrophyType.TROPHY_3MONTH,startDate));
        trophies.add(new Trophy(TrophyType.TROPHY_6MONTH,startDate));
        trophies.add(new Trophy(TrophyType.TROPHY_1YEAR,startDate));
        trophies.add(new Trophy(TrophyType.TROPHY_5YEAR,startDate));
        return trophies;
    }
}
