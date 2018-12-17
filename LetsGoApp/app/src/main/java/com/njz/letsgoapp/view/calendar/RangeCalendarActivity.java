package com.njz.letsgoapp.view.calendar;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.TextView;

import com.njz.letsgoapp.R;
import com.njz.letsgoapp.base.BaseActivity;
import com.njz.letsgoapp.util.DateUtil;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * Created by LGQ
 * Time: 2018/12/17
 * Function:
 */

public class RangeCalendarActivity extends BaseActivity implements OnDateSelectedListener {

    private static final DateFormat FORMATTER = SimpleDateFormat.getDateInstance();

    MaterialCalendarView calendarView;
    TextView textView;

    private String startTime;
    private String endTime;

    @Override
    public void getIntentData() {
        super.getIntentData();
        startTime = intent.getStringExtra("startTime");
        endTime = intent.getStringExtra("endTime");
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_price_calendarview;
    }

    @Override
    public void initView() {
        calendarView = $(R.id.calendarView);
        textView = $(R.id.textView);
        calendarView.setSelectionMode(MaterialCalendarView.SELECTION_MODE_RANGE);
        calendarView.setOnDateChangedListener(this);

        if(!TextUtils.isEmpty(endTime) && !TextUtils.isEmpty(startTime)){
//            calendarView.selectRange(new CalendarDay());
        }
    }

    @Override
    public void initData() {

    }

    @Override
    public void onDateSelected(@NonNull MaterialCalendarView widget, @Nullable CalendarDay date, boolean selected) {

    }

}
