package com.tnedutsledom.modelstudent.calender_decorator;

import android.graphics.Color;
import android.text.style.ForegroundColorSpan;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;

import org.threeten.bp.DayOfWeek;

import java.util.Calendar;

public class SundayDecorator implements DayViewDecorator {
    private Calendar calendar = Calendar.getInstance();
    @Override
    public boolean shouldDecorate(CalendarDay day) {
        int week_day = day.getDate().with(DayOfWeek.MONDAY).getDayOfMonth();
        return week_day == day.getDay();
    }

    @Override
    public void decorate(DayViewFacade view) {
        view.addSpan(new ForegroundColorSpan(Color.parseColor("#D94925")));
    }
}
