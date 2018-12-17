package com.njz.letsgoapp.view.calendar;

import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.njz.letsgoapp.R;
import com.njz.letsgoapp.base.BaseActivity;
import com.njz.letsgoapp.util.DateUtil;
import com.njz.letsgoapp.util.rxbus.RxBus2;
import com.njz.letsgoapp.util.rxbus.busEvent.CalendarEvent;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnRangeSelectedListener;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by LGQ
 * Time: 2018/12/17
 * Function:
 */

public class RangeCalendarActivity extends BaseActivity implements OnRangeSelectedListener {

    MaterialCalendarView calendarView;
    TextView textView;

    private List<CalendarDay> dates;

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

        Calendar instance1 = Calendar.getInstance();

        Calendar instance2 = Calendar.getInstance();
        instance2.add(Calendar.YEAR, 1);

        calendarView
                .state()
                .edit()
                //设置一周的第一天是周日还是周一
                .setFirstDayOfWeek(Calendar.SUNDAY)
                //设置日期范围
                .setMinimumDate(instance1.getTime())
                .setMaximumDate(instance2.getTime())
                .setCalendarDisplayMode(CalendarMode.MONTHS)
                .commit();
        calendarView.setShowOtherDates(MaterialCalendarView.SHOW_OUT_OF_RANGE);

        calendarView.setSelectionMode(MaterialCalendarView.SELECTION_MODE_RANGE);

        calendarView.setOnRangeSelectedListener(this);

        if(!TextUtils.isEmpty(endTime) && !TextUtils.isEmpty(startTime)){
            calendarView.selectRange(new CalendarDay(DateUtil.str2Date(startTime)),new CalendarDay(DateUtil.str2Date(endTime)));
        }

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<CalendarDay> days = calendarView.getSelectedDates();

                if(days != null && days.size() > 0){
                    if(days.size() == 1){
                        startTime = DateUtil.dateToStr(days.get(0).getDate());
                        endTime = DateUtil.dateToStr(days.get(days.size() - 1).getDate());
                    }else {
                        days = dates;
                        startTime = DateUtil.dateToStr(days.get(0).getDate());
                        endTime = DateUtil.dateToStr(days.get(days.size() - 1).getDate());
                    }
                } else {
                    startTime = "";
                    endTime = "";
                }
                finish();
            }
        });
    }

    @Override
    public void initData() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(TextUtils.isEmpty(startTime)){
            RxBus2.getInstance().post(new CalendarEvent("","",""));
        }else{
            RxBus2.getInstance().post(new CalendarEvent(startTime, endTime,"共" + DateUtil.getGapCount(startTime,endTime) + "天"));
        }
    }


    @Override
    public void onRangeSelected(@NonNull MaterialCalendarView widget, @NonNull List<CalendarDay> dates) {
        this.dates = dates;
    }
}
