package com.example.quit.models;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.quit.R;
import com.example.quit.utilities.Time;
import com.prolificinteractive.materialcalendarview.CalendarDay;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Locale;

@Entity
public class Addiction {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String addictionName;
    private long firstDateOfQuit;
    private long lastDateOfQuit;
    private long targetDate;
    private int iconId;
    @TypeConverters(Converters.class)
    private AddictionType addictionType;
    private double timeWasting;
    private double moneyWasting;
    @TypeConverters(TargetDateTypeConverter.class)
    private TargetDateType targetDateType;


    public Addiction(String addictionName, long firstDateOfQuit, long lastDateOfQuit, long targetDate, int iconId, double timeWasting, double moneyWasting) {
        this.addictionName = addictionName;
        this.firstDateOfQuit = firstDateOfQuit;
        this.lastDateOfQuit = lastDateOfQuit;
        this.targetDate = targetDate;

        this.iconId = iconId;
        this.timeWasting = timeWasting;
        this.moneyWasting = moneyWasting;
    }

    @Ignore
    public Addiction(String addictionName, int iconId) {
        this.iconId = iconId;
        this.addictionName = addictionName;
    }

    @Ignore
    public Addiction() {
    }

    public String getAddictionName() {
        return addictionName;
    }

    public void setAddictionName(String addictionName) {
        this.addictionName = addictionName;
    }

    public long getFirstDateOfQuit() {
        return firstDateOfQuit;
    }

    public void setFirstDateOfQuit(long firstDateOfQuit) {
        this.firstDateOfQuit = firstDateOfQuit;
    }


    public double getTimeWasting() {
        return timeWasting;
    }

    public void setTimeWasting(double timeWasting) {
        this.timeWasting = timeWasting;
    }

    public double getMoneyWasting() {
        return moneyWasting;
    }

    public void setMoneyWasting(double moneyWasting) {
        this.moneyWasting = moneyWasting;
    }

    public long getLastDateOfQuit() {
        return lastDateOfQuit;
    }

    public void setLastDateOfQuit(long lastDateOfQuit) {
        this.lastDateOfQuit = lastDateOfQuit;
    }

    public void setTargetDate(long targetDate) {
        this.targetDate = targetDate;


    }

    public void setIconId(int iconId) {
        this.iconId = iconId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIconId() {
        return iconId;
    }

    public long getTargetDate() {
        return targetDate;
    }

    @Ignore
    public double getPercent() {
        long achievedTime = System.currentTimeMillis() - this.getLastDateOfQuit();
        long totalTime = this.getTargetDate() - this.getLastDateOfQuit();
        return totalTime == 0 ? 0 : (double) achievedTime / totalTime * 100;

    }

    @Ignore
    public static Addiction[] getAddictions() {

        Addiction[] addictions = new Addiction[26];
        addictions[0] = new Addiction("My addiction", R.drawable.ic_addiction_generic);
        addictions[1] = new Addiction("Facebook", R.drawable.ic_addiction_facebook);
        addictions[2] = new Addiction("Youtube", R.drawable.ic_addiction_youtube);
        addictions[3] = new Addiction("Games", R.drawable.ic_addiction_games);
        addictions[4] = new Addiction("Alcohol", R.drawable.ic_addiction_alcohol);
        addictions[5] = new Addiction("Weed", R.drawable.ic_addiction_cannabis);
        addictions[6] = new Addiction("Coffee", R.drawable.ic_addiction_coffee);
        addictions[7] = new Addiction("Cursing", R.drawable.ic_addiction_cursing);
        addictions[8] = new Addiction("Drugs", R.drawable.ic_addiction_drugs);
        addictions[9] = new Addiction("Instagram", R.drawable.ic_addiction_instagram);
        addictions[10] = new Addiction("Lie", R.drawable.ic_addiction_lie);
        addictions[11] = new Addiction("Food", R.drawable.ic_addiction_food);
        addictions[12] = new Addiction("Overeating", R.drawable.ic_addiction_overeating);
        addictions[13] = new Addiction("Gamebling", R.drawable.ic_addiction_gamebling);
        addictions[14] = new Addiction("Meet", R.drawable.ic_addiction_meat);
        addictions[15] = new Addiction("Pils", R.drawable.ic_addiction_pils);
        addictions[16] = new Addiction("Porn", R.drawable.ic_addiction_porn);
        addictions[17] = new Addiction("Procrastination", R.drawable.ic_addiction_procrastination);
        addictions[18] = new Addiction("Shopping", R.drawable.ic_addiction_shopping);
        addictions[19] = new Addiction("Twitter", R.drawable.ic_addiction_twitter);
        addictions[20] = new Addiction("Tv", R.drawable.ic_addiction_tv);
        addictions[21] = new Addiction("Sugar", R.drawable.ic_addiction_sugar);
        addictions[22] = new Addiction("Smoke", R.drawable.ic_addiction_smoke);
        addictions[23] = new Addiction("Soda", R.drawable.ic_addiction_soda);
        addictions[24] = new Addiction("reddit", R.drawable.ic_addiction_reddit);
        addictions[25] = new Addiction("Quarrel", R.drawable.ic_addiction_quarrel);

        return addictions;
    }

    public AddictionType getAddictionType() {
        return addictionType;
    }

    public void setAddictionType(AddictionType addictionType) {
        this.addictionType = addictionType;
    }

    @Ignore
    public String getQuitDate() {
        SimpleDateFormat dt = new SimpleDateFormat("dd MMMM,yyyy hh,mm a", Locale.getDefault());
        return dt.format(new Date(this.getFirstDateOfQuit()));
    }

    @Ignore
    public void goToNextTargetDate() {
        targetDate = getNextTargetDate();
    }

    @Ignore
    private long getNextTargetDate() {
        for (TargetDateType targetDateType : TargetDateType.getTargetDates()) {
            long targetDate = targetDateType.getTargetDateFromThatTime(getLastDateOfQuit());
            if (targetDate > System.currentTimeMillis()) {
                this.targetDateType = targetDateType;
                return targetDate;
            }
        }
        return targetDate;
    }


    public TargetDateType getTargetDateType() {
        return targetDateType;
    }

    public void setTargetDateType(TargetDateType targetDateType) {
        this.targetDateType = targetDateType;
    }

    public int getIcon12dp() {
        if (iconId == R.drawable.ic_addiction_generic) {
            return R.drawable.ic_addiction_generic_calendar_12dp;
        } else if (iconId == R.drawable.ic_addiction_facebook) {
            return R.drawable.ic_addiction_facebook_calendar_12dp;
        } else if (iconId == R.drawable.ic_addiction_youtube) {
            return R.drawable.ic_addiction_youtube_calendar_12dp;
        } else if (iconId == R.drawable.ic_addiction_games) {
            return R.drawable.ic_addiction_games_calendar_12dp;
        } else if (iconId == R.drawable.ic_addiction_alcohol) {
            return R.drawable.ic_addiction_alcohol_calendar_12dp;
        } else if (iconId == R.drawable.ic_addiction_cannabis) {
            return R.drawable.ic_addiction_cannabis_calendar_12dp;
        } else if (iconId == R.drawable.ic_addiction_coffee) {
            return R.drawable.ic_addiction_coffee_calendar_12dp;
        } else if (iconId == R.drawable.ic_addiction_cursing) {
            return R.drawable.ic_addiction_cursing_calendar_12dp;
        } else if (iconId == R.drawable.ic_addiction_drugs) {
            return R.drawable.ic_addiction_drugs_calendar_12dp;
        } else if (iconId == R.drawable.ic_addiction_instagram) {
            return R.drawable.ic_addiction_instagram_calendar_12dp;
        } else if (iconId == R.drawable.ic_addiction_lie) {
            return R.drawable.ic_addiction_lie_calendar_12dp;
        } else if (iconId == R.drawable.ic_addiction_food) {
            return R.drawable.ic_addiction_food_calendar_12dp;
        } else if (iconId == R.drawable.ic_addiction_overeating) {
            return R.drawable.ic_addiction_overeating_calendar_12dp;
        } else if (iconId == R.drawable.ic_addiction_gamebling) {
            return R.drawable.ic_addiction_gamebling_calendar_12dp;
        } else if (iconId == R.drawable.ic_addiction_meat) {
            return R.drawable.ic_addiction_meat_calendar_12dp;
        } else if (iconId == R.drawable.ic_addiction_pils) {
            return R.drawable.ic_addiction_pils_calendar_12dp;
        } else if (iconId == R.drawable.ic_addiction_porn) {
            return R.drawable.ic_addiction_porn_calendar_12dp;
        } else if (iconId == R.drawable.ic_addiction_procrastination) {
            return R.drawable.ic_addiction_procrastination_calendar_12dp;
        } else if (iconId == R.drawable.ic_addiction_shopping) {
            return R.drawable.ic_addiction_shopping_calendar_12dp;
        } else if (iconId == R.drawable.ic_addiction_twitter) {
            return R.drawable.ic_addiction_twitter_calendar_12dp;
        } else if (iconId == R.drawable.ic_addiction_tv) {
            return R.drawable.ic_addiction_tv_calendar_12dp;
        } else if (iconId == R.drawable.ic_addiction_sugar) {
            return R.drawable.ic_addiction_sugar_calendar_12dp;
        } else if (iconId == R.drawable.ic_addiction_smoke) {
            return R.drawable.ic_addiction_smoke_calendar_12dp;
        } else if (iconId == R.drawable.ic_addiction_soda) {
            return R.drawable.ic_addiction_soda_calendar_12dp;
        } else if (iconId == R.drawable.ic_addiction_reddit) {
            return R.drawable.ic_addiction_reddit_calendar_12dp;
        } else
            return R.drawable.ic_addiction_quarrel_calendar_12dp;
    }

    public HashSet<CalendarDay> getCleanDays(HashSet<CalendarDay> relapseDays) {
        Calendar now = Calendar.getInstance();
        Calendar startDateCalender = Calendar.getInstance();
        startDateCalender.setTimeInMillis(firstDateOfQuit);
        HashSet<CalendarDay> calendarDays = new HashSet<>();
        while (startDateCalender.before(now)) {
            CalendarDay currentDay = Time.getDateCalendarDayFormat(startDateCalender.getTimeInMillis());
            if (!relapseDays.contains(currentDay))
                calendarDays.add(currentDay);

            startDateCalender.add(Calendar.DAY_OF_MONTH, 1);
        }
        return calendarDays;
    }
}
