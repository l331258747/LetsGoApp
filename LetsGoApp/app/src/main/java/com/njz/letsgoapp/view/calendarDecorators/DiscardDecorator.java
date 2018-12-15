package com.njz.letsgoapp.view.calendarDecorators;

import android.graphics.Color;
import android.text.style.ForegroundColorSpan;
import android.util.Log;

import com.njz.letsgoapp.util.DateUtil;
import com.njz.letsgoapp.util.log.LogUtil;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;

import java.util.Calendar;
import java.util.Date;

public class DiscardDecorator implements DayViewDecorator {

    Date startTime;
    Date endTime;

    public DiscardDecorator() {
        Calendar instance1 = Calendar.getInstance();

        Calendar instance2 = Calendar.getInstance();
        instance2.add(Calendar.YEAR, 1);

        this.startTime = instance1.getTime();
        this.endTime = instance2.getTime();

        LogUtil.e("startTime:"+DateUtil.dateToStr(startTime));
        LogUtil.e("endTime:"+DateUtil.dateToStr(endTime));
    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {
        if(DateUtil.dateToInt(day.getDate(),"yyyyMMdd") < DateUtil.dateToInt(startTime,"yyyyMMdd")){
            return true;
        }
        if(DateUtil.dateToInt(day.getDate(),"yyyyMMdd") > DateUtil.dateToInt(endTime,"yyyyMMdd")){
            return true;
        }

//        if(day.getDate().getTime() < startTime.getTime())
//            return true;
//        if(day.getDate().getTime() > endTime.getTime())
//            return true;
        return false;
    }

    @Override
    public void decorate(DayViewFacade view) {
        view.addSpan(new ForegroundColorSpan(Color.parseColor("#999999")));
        view.setDaysDisabled(true);
    }
}