package com.njz.letsgoapp.view.calendarDecorators;

import android.text.TextUtils;

import com.njz.letsgoapp.util.DateUtil;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;

import java.util.List;

public class PrimeDayDisableDecorator implements DayViewDecorator {

    List<String> noTimes;

    public PrimeDayDisableDecorator(List<String> noTimes) {
        this.noTimes = noTimes;
    }

    public void setNoTimes(List<String> noTimes){
        this.noTimes = noTimes;
    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {
        for (int i =0;i<noTimes.size();i++){
            if(TextUtils.equals(DateUtil.dateToStr(day.getDate()),noTimes.get(i))){
                return true;
            }
        }

        return false;

    }

    @Override
    public void decorate(DayViewFacade view) {
        view.setDaysDisabled(true);
    }

}

