package com.tnedutsledom.modelstudent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.prolificinteractive.materialcalendarview.format.ArrayWeekDayFormatter;
import com.prolificinteractive.materialcalendarview.format.MonthArrayTitleFormatter;
import com.prolificinteractive.materialcalendarview.format.TitleFormatter;
import com.tnedutsledom.modelstudent.calender_decorator.SelectDayDecorator;
import com.tnedutsledom.modelstudent.calender_decorator.SundayDecorator;

import org.threeten.bp.DayOfWeek;
import org.threeten.bp.LocalDate;

import java.util.Calendar;

public class LastTimeActivity extends AppCompatActivity {

    private MaterialCalendarView cv_calender;
    TextView tv_diary_date;
    LinearLayout ll_diary, ll_test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_last_time);
        init();
        calenderDesignInit();
        selectDate();
        dragDiary();
    }


    @SuppressLint("ClickableViewAccessibility")
    void dragDiary() {
        int view_y_size = getResources().getDisplayMetrics().heightPixels;
        ll_diary.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    // 뷰 누름7
                    float oldYvalue = event.getY();
                    Log.d("상태", "onTouch: 누름");
                } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
                        if (v.getY() < 0) {
                            Log.d("상태", "onTouch: 드래그mmmmmmmmmmmmmmmmmmmmmmmmmmmmm");
                            v.setY(0 + (view_y_size / 3));
                        } else {
                            v.setY(v.getY() + (event.getY()) - (v.getHeight() / 2) + (view_y_size / 3));
                            Log.d("상태", "onTouch: 드래그");
                        }
                } else if(event.getAction() == MotionEvent.ACTION_UP) {

                }
                return true;
            }
        });
    }

    // 요일 선택 시
    void selectDate() {
        cv_calender.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                tv_diary_date.setText(date.getDate().toString());
                Log.d("캘린더 선택", "onDateSelected: " + date.getDate());
            }
        });
    }

    void init() {
        cv_calender = findViewById(R.id.cv_last_time_calender);
        tv_diary_date = findViewById(R.id.tv_diary_date);
        ll_diary = findViewById(R.id.ll_last_time_diary);
        ll_test = findViewById(R.id.ll_test);
    }

    // 캘린더뷰 디자인 초기세팅
    void calenderDesignInit() {
        // 첫 시작 요일이 일요일이 되도록 설정
        cv_calender.state()
                .edit()
                .setFirstDayOfWeek(DayOfWeek.of(Calendar.SUNDAY))
                .commit();

        // 월, 요일을 한글로 보이게 설정 (MonthArrayTitleFormatter의 작동을 확인하려면 밑의 setTitleFormatter()를 지운다)
        cv_calender.setTitleFormatter(new MonthArrayTitleFormatter(getResources().getTextArray(R.array.custom_months)));
        cv_calender.setWeekDayFormatter(new ArrayWeekDayFormatter(getResources().getTextArray(R.array.custom_weekdays)));
//        cv_calender.setWeekDayTextAppearance(Color.parseColor("#D94925"));

        // 좌우 화살표 사이 연, 월의 폰트 스타일 설정
        cv_calender.setHeaderTextAppearance(R.style.CalendarWidgetHeader);

        cv_calender.setCurrentDate(CalendarDay.today());


        // 일자 선택 시 내가 정의한 드로어블이 적용되도록 한다
        cv_calender.addDecorators(
                new SelectDayDecorator(this),
                new SundayDecorator()
        );

        // 좌우 화살표 가운데의 연/월이 보이는 방식 커스텀
        cv_calender.setTitleFormatter(new TitleFormatter() {
            @Override
            public CharSequence format(CalendarDay day) {
                // CalendarDay라는 클래스는 LocalDate 클래스를 기반으로 만들어진 클래스다
                // 때문에 MaterialCalendarView에서 연/월 보여주기를 커스텀하려면 CalendarDay 객체의 getDate()로 연/월을 구한 다음 LocalDate 객체에 넣어서
                // LocalDate로 변환하는 처리가 필요하다
                LocalDate inputText = day.getDate();
                String[] calendarHeaderElements = inputText.toString().split("-");
                StringBuilder calendarHeaderBuilder = new StringBuilder();
                calendarHeaderBuilder.append(calendarHeaderElements[0])
                        .append(" ")
                        .append(calendarHeaderElements[1]);
                return calendarHeaderBuilder.toString();
            }
        });
    }

}