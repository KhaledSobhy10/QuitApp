package com.example.quit.models;

import androidx.room.Embedded;
import androidx.room.Ignore;
import androidx.room.Relation;

import com.example.quit.utilities.Time;
import com.prolificinteractive.materialcalendarview.CalendarDay;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.TreeSet;

public class AddictionWithRelapse {
    @Embedded
    public Addiction addiction;
    @Relation(
            parentColumn = "id",
            entityColumn = "addictionId"
    )
    public List<Relapse> relapseList;

    @Ignore
    public TreeSet<Relapse> sortedRelapseList(){
        return new TreeSet<>(relapseList);
    }

    @Ignore
    public HashSet<CalendarDay> getRelapseDates() {
        HashSet<CalendarDay> calendarRelapseDays = new HashSet<>();
        for (Relapse relapse : relapseList) {
            calendarRelapseDays.add(Time.getDateCalendarDayFormat(relapse.getRelapseDate()));
        }
        return calendarRelapseDays;
    }

    public List<Relapse> getRelapseListForThatDay(CalendarDay calendarDay){
        /*
        * if that relapse in that day return it
        * */
        List<Relapse> relapsesForThatDay = new ArrayList<>();
        for (Relapse relapse : relapseList) {
            if (Time.compare(calendarDay, relapse.getRelapseDate())==0)
                relapsesForThatDay.add(relapse);
        }
        return relapsesForThatDay;
    }
}
