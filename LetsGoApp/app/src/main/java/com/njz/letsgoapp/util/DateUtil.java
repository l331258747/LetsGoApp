package com.njz.letsgoapp.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by llt on 2017/11/9.
 */

public class DateUtil {

    //date 转 String 年月日
    public static String dateToStr(Date dateDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = formatter.format(dateDate);
        return dateString;
    }

    //String 转 date
    public static Date str2Date(String dateStr){
        SimpleDateFormat df;
        df = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return df.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return new Date();
    }


    //获取当前date
    public static Date getNowDate(){
        Date date = new Date(System.currentTimeMillis());
        return date;
    }


    public static Calendar getSelectedDate(String str){
        Calendar selectedDate = Calendar.getInstance();
        selectedDate.setTime(DateUtil.str2Date(str));
        return selectedDate;
    }

    public static Calendar getStartDate(){
        Calendar startDate = Calendar.getInstance();
        startDate.set(1949,0,1);
        return startDate;
    }

    public static Calendar getEndDate(){
        Calendar endDate = Calendar.getInstance();
        endDate.setTime(DateUtil.getNowDate());
        return endDate;
    }

}

