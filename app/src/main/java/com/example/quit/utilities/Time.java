package com.example.quit.utilities;

import android.util.Log;

import com.prolificinteractive.materialcalendarview.CalendarDay;

import org.threeten.bp.LocalDate;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

public class Time {
    private final static String TAG = Time.class.getSimpleName();


    public static String formatTimeLongToHMS(long time) {
        StringBuilder formattedTime = new StringBuilder();

        if (time >= 86400000) {
            long days = TimeUnit.MILLISECONDS.toDays(time);
            time -= 86400000 * days;
            formattedTime.append(days).append("d ");
        }

        if (time >= 3600000) {
            long hours = TimeUnit.MILLISECONDS.toHours(time);
            time -= 3600000 * hours;
            formattedTime.append(hours).append("h ");
        }

        if (time >= 60000) {
            long min = TimeUnit.MILLISECONDS.toMinutes(time);
            time -= 60000 * min;
            formattedTime.append(min).append("m ");
        }

        if (time >= 1000) {
            long sec = TimeUnit.MILLISECONDS.toSeconds(time);
            formattedTime.append(sec).append("s");
        }
        return formattedTime.toString().isEmpty() ? "0m" : formattedTime.toString();

    }

    public static String getAchievedTime(long lastQuitDateInMillieSecond) {
        if (lastQuitDateInMillieSecond > 0)
            return formatTimeLongToHMS(System.currentTimeMillis() - lastQuitDateInMillieSecond);
        else return "Invalid date !!";
    }

    public static CalendarDay getDateCalendarDayFormat(long firstDateOfQuit) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(firstDateOfQuit);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        return CalendarDay.from(year, month, day);
    }

    public static String getNowTime() {
        return getSimpleDateFormatByThatPattern("hh:mm a").format(new Date());
    }

    public static String formatCalenderToHM(Calendar c) {
        return getSimpleDateFormatByThatPattern("hh:mm a").format(c.getTime());
    }

    public static String formatTimeToHM(long date) {
        return formatTimeByPattern(date, "hh:mm a");
    }

    public static String formatTimeByPattern(long date, String pattern) {
        return getSimpleDateFormatByThatPattern(pattern).format(new Date(date));
    }

    public static String formatTimeToDMYHMA(long date) {
        return formatTimeByPattern(date, "dd MMMM,yyyy hh,mm a");
    }

    public static SimpleDateFormat getSimpleDateFormatByThatPattern(String pattern) {
        return new SimpleDateFormat(pattern, Locale.getDefault());
    }


    /**
     * @param date          that should be after the second date
     * @param date2InMillis second date in millis
     * @return 0 if date1 = date2 , positive num if date1 after date2 , negative num if date1 before date2
     */
    public static int compare(CalendarDay date, long date2InMillis) {
        Calendar date2 = Calendar.getInstance();
        date2.setTimeInMillis(date2InMillis);
        int result = date.getYear() - date2.get(Calendar.YEAR);
        if (result == 0) {
            result = date.getMonth() - (date2.get(Calendar.MONTH) + 1);
            if (result == 0)
                result = date.getDay() - date2.get(Calendar.DAY_OF_MONTH);
        }
        return result;
    }

    public static long fromCalenderDayToLong(CalendarDay date) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(date.getYear(), date.getMonth() - 1, date.getDay());
        return calendar.getTimeInMillis();
    }
}
