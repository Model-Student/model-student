package com.tnedutsledom.modelstudent;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatButton;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

import nl.joery.animatedbottombar.AnimatedBottomBar;

public class ThemeColorAdaptor {

    private static ThemeColorAdaptor instance;
    int themeColor, textColor, backColor, imgColor, btnColor;
    int[] sentence_moon = new int[]{
            R.drawable.sentence_moon_1, R.drawable.sentence_moon_2, R.drawable.sentence_moon_3, R.drawable.sentence_moon_4,
            R.drawable.sentence_moon_5, R.drawable.sentence_moon_6, R.drawable.sentence_moon_7, R.drawable.sentence_moon_8,
    };
    SharedPreferences preferences;
    Context context;
    int theme = 0;

    public static ThemeColorAdaptor getInstance(Context context) {
        if (instance == null) {
            instance = new ThemeColorAdaptor(context);
        }
        return instance;
    }

    private ThemeColorAdaptor(Context context) {
        this.context = context;
        preferences = context.getSharedPreferences("user_info", MODE_PRIVATE);
        setTheme(preferences.getInt("theme", 0));
    }

    public void setTheme(int theme) {
        this.theme = theme;
        switch (theme) {
            case 0:
                setTheme0();
                break;
            case 1:
                setTheme1();
                break;
            case 2:
                setTheme2();
                break;
            case 3:
                setTheme3();
                break;
            case 4:
                setTheme4();
                break;
            case 5:
                setTheme5();
                break;
            case 6:
                setTheme6();
                break;
            case 7:
                setTheme7();
                break;
        }
    }

    // 하단바 색 지정
    public void setBottomBarColor(AnimatedBottomBar bottomBar) {
        bottomBar.setIndicatorColor(textColor);
        bottomBar.setTabColorSelected(themeColor);
        bottomBar.setTabColor(themeColor);
    }

    // 문장추천 달 색 변경
    public void setSentenceMoon(ImageView iv) {
        iv.setImageResource(sentence_moon[theme]);
    }

    // View를 theme 색으로 설정
    public void setViewColorTheme(TextView tv) {
        tv.setTextColor(themeColor);
    }

    public void setViewColorTheme(View v) {
        v.setBackgroundColor(themeColor);
    }

    public void setViewColorTheme(ImageView iv) {
        iv.setColorFilter(themeColor);
    }

    public void setViewColorTheme(GradientDrawable drawable, LinearLayout ll) {
        drawable.setColor(themeColor);
        ll.setBackground(drawable);
    }

    // View를 theme_text 색으로 설정
    public void setViewColorText(TextView tv) {
        tv.setTextColor(textColor);
    }

    public void setViewColorText(EditText et) {
        et.setTextColor(textColor);
    }

    public void setViewColorText(ImageView iv) {
        iv.setColorFilter(textColor);
    }

    public void setViewColorText(View v) {
        v.setBackgroundColor(textColor);
    }

    public void setViewColorText(GradientDrawable drawable, TextView tv) {
        drawable.setColor(textColor);
        tv.setBackground(drawable);
    }

    public void setViewColorText(GradientDrawable drawable, ConstraintLayout cl) {
        drawable.setColor(textColor);
        cl.setBackground(drawable);
    }

    public void setViewColorText(GradientDrawable drawable, ImageView iv) {
        drawable.setColor(textColor);
        iv.setBackground(drawable);
    }

    public void setViewColorText(GradientDrawable drawable, AppCompatButton btn) {
        drawable.setColor(textColor);
        btn.setBackground(drawable);
    }

    // View를 theme_back 색으로 설정
    public void setViewColorBack(LinearLayout ll) {
        ll.setBackgroundColor(backColor);
    }

    public void setViewColorBack(ConstraintLayout cl) {
        cl.setBackgroundColor(backColor);
    }

    // View를 theme_img 색으로 설정
    public void setViewColorImg(EditText et) {
        et.setHintTextColor(imgColor);
    }

    public void setViewColorImg(ImageView iv) {
        iv.setColorFilter(imgColor, PorterDuff.Mode.SRC_IN);
    }

    public void setViewColorBtn(GradientDrawable drawable, LinearLayout ll) {
        drawable.setColor(btnColor);
        ll.setBackground(drawable);
    }

    public void setCalenderTheme(MaterialCalendarView cv) {
        int tmp = theme+1;
        cv.setHeaderTextAppearance(R.style.CalendarWidgetHeader+tmp);
        cv.setWeekDayTextAppearance(R.style.CalenderViewWeekCustomText+tmp);
        cv.setDateTextAppearance(R.style.CalenderViewDateCustomText+tmp);
    }

    public Drawable getCalenderSelector() {
        Drawable drawable = ContextCompat.getDrawable(context, R.drawable.calender_selector1);
        switch (theme) {
            case 0: drawable = ContextCompat.getDrawable(context, R.drawable.calender_selector1); break;
            case 1: drawable = ContextCompat.getDrawable(context, R.drawable.calender_selector2); break;
            case 2: drawable = ContextCompat.getDrawable(context, R.drawable.calender_selector3); break;
            case 3: drawable = ContextCompat.getDrawable(context, R.drawable.calender_selector4); break;
            case 4: drawable = ContextCompat.getDrawable(context, R.drawable.calender_selector5); break;
            case 5: drawable = ContextCompat.getDrawable(context, R.drawable.calender_selector6); break;
            case 6: drawable = ContextCompat.getDrawable(context, R.drawable.calender_selector7); break;
            case 7: drawable = ContextCompat.getDrawable(context, R.drawable.calender_selector8); break;
        }
        return drawable;
    }

    void setTheme0() {
        themeColor = ContextCompat.getColor(context, R.color.Theme_Indigo);
        textColor = ContextCompat.getColor(context, R.color.Theme_Indigo_Text);
        backColor = ContextCompat.getColor(context, R.color.Theme_Indigo_Back);
        imgColor = ContextCompat.getColor(context, R.color.Theme_Indigo_Img);
        btnColor = ContextCompat.getColor(context, R.color.Theme_Indigo_Btn);
    }

    void setTheme1() {
        themeColor = ContextCompat.getColor(context, R.color.Theme_Modern);
        textColor = ContextCompat.getColor(context, R.color.Theme_Modern_Text);
        backColor = ContextCompat.getColor(context, R.color.Theme_Modern_Back);
        imgColor = ContextCompat.getColor(context, R.color.Theme_Modern_Img);
        btnColor = ContextCompat.getColor(context, R.color.Theme_Modern_Btn);

    }

    void setTheme2() {
        themeColor = ContextCompat.getColor(context, R.color.Theme_Chic);
        textColor = ContextCompat.getColor(context, R.color.Theme_Chic_Text);
        backColor = ContextCompat.getColor(context, R.color.Theme_Chic_Back);
        imgColor = ContextCompat.getColor(context, R.color.Theme_Chic_Img);
        btnColor = ContextCompat.getColor(context, R.color.Theme_Chic_Btn);

    }

    void setTheme3() {
        themeColor = ContextCompat.getColor(context, R.color.Theme_Mono);
        textColor = ContextCompat.getColor(context, R.color.Theme_Mono_Text);
        backColor = ContextCompat.getColor(context, R.color.Theme_Mono_Back);
        imgColor = ContextCompat.getColor(context, R.color.Theme_Mono_Img);
        btnColor = ContextCompat.getColor(context, R.color.Theme_Mono_Btn);

    }

    void setTheme4() {
        themeColor = ContextCompat.getColor(context, R.color.Theme_Barry);
        textColor = ContextCompat.getColor(context, R.color.Theme_Barry_Text);
        backColor = ContextCompat.getColor(context, R.color.Theme_Barry_Nack);
        imgColor = ContextCompat.getColor(context, R.color.Theme_Barry_Img);
        btnColor = ContextCompat.getColor(context, R.color.Theme_Barry_Btn);

    }

    void setTheme5() {
        themeColor = ContextCompat.getColor(context, R.color.Theme_Pink);
        textColor = ContextCompat.getColor(context, R.color.Theme_Pink_Text);
        backColor = ContextCompat.getColor(context, R.color.Theme_Pink_Back);
        imgColor = ContextCompat.getColor(context, R.color.Theme_Pink_Img);
        btnColor = ContextCompat.getColor(context, R.color.Theme_Pink_Btn);

    }

    void setTheme6() {
        themeColor = ContextCompat.getColor(context, R.color.Theme_Fresh);
        textColor = ContextCompat.getColor(context, R.color.Theme_Fresh_Text);
        backColor = ContextCompat.getColor(context, R.color.Theme_Fresh_Back);
        imgColor = ContextCompat.getColor(context, R.color.Theme_Fresh_Img);
        btnColor = ContextCompat.getColor(context, R.color.Theme_Fresh_Btn);

    }

    void setTheme7() {
        themeColor = ContextCompat.getColor(context, R.color.Theme_Grass);
        textColor = ContextCompat.getColor(context, R.color.Theme_Grass_Text);
        backColor = ContextCompat.getColor(context, R.color.Theme_Grass_Back);
        imgColor = ContextCompat.getColor(context, R.color.Theme_Grass_Img);
        btnColor = ContextCompat.getColor(context, R.color.Theme_Grass_Btn);

    }

}
