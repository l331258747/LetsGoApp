package com.dali.custompicker;

import com.dali.custompicker.bean.DayTimeEntity;

import java.util.List;

/**
 * Created by LGQ
 * Time: 2018/8/1
 * Function:
 */

public class CalendarData {

    public static int today = 0;

    public static DayTimeEntity startDay;
    public static DayTimeEntity stopDay;
    public static List<DayTimeEntity> markerDays;

    public static DayTimeEntity oneDay;

    public static DayTimeEntity isHas(DayTimeEntity dayTime){
        DayTimeEntity ishas = null;
        for (DayTimeEntity item : markerDays){
            if(item.getYear() == dayTime.getYear() && item.getMonth() == dayTime.getMonth() && item.getDay() == dayTime.getDay()){
                ishas = item;
            }
        }
        return ishas;
    }

}
