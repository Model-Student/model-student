package com.tnedutsledom.modelstudent.calender_decorator;

import android.content.Context;
import android.graphics.drawable.Drawable;

import androidx.core.content.ContextCompat;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.tnedutsledom.modelstudent.R;
import com.tnedutsledom.modelstudent.ThemeColorAdaptor;

public class SelectDayDecorator implements DayViewDecorator {

    private final Drawable drawable;
    ThemeColorAdaptor colorAdaptor;

    public SelectDayDecorator(Context context) {
        colorAdaptor = ThemeColorAdaptor.getInstance(context.getApplicationContext());
        drawable = ContextCompat.getDrawable(context, R.drawable.calender_selector1);
    }

    // true를 리턴 시 모든 요일에 내가 설정한 드로어블이 적용된다
    @Override
    public boolean shouldDecorate(CalendarDay day) {
        return true;
    }

    // 일자 선택 시 내가 정의한 드로어블이 적용되도록 한다
    @Override
    public void decorate(DayViewFacade view) {
        view.setSelectionDrawable(colorAdaptor.getCalenderSelector());
    }
}
