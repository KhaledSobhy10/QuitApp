package com.example.quit.utilities;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;

import androidx.slidingpanelayout.widget.SlidingPaneLayout;

import com.example.quit.R;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;

import java.util.HashSet;

public class CalenderDaysDecorator implements DayViewDecorator {
    private Drawable drawable;
    private HashSet<CalendarDay> calendarDays;
    private boolean hideText;

    public CalenderDaysDecorator(HashSet<CalendarDay> calendarDays, Drawable drawable, boolean hideText) {
        this.calendarDays = calendarDays;
        this.drawable = drawable;
        this.hideText = hideText;
    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {
        return calendarDays.contains(day);
    }

    @Override
    public void decorate(DayViewFacade view) {
        if (hideText)
            view.addSpan(new ImageSpan(drawable));

        view.setSelectionDrawable(drawable);
    }
}
