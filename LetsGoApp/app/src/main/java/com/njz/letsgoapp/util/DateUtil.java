package com.njz.letsgoapp.util;

import android.provider.ContactsContract;

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
    public static Date str2Date(String dateStr) {
        SimpleDateFormat df;
        df = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return df.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return new Date();
    }

    public static String longToStr(String dateLong) {
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        long lt = new Long(dateLong);
        Date date = new Date(lt);
        res = simpleDateFormat.format(date);
        return res;
    }


    //获取当前date
    public static Date getNowDate() {
        Calendar calendar = Calendar.getInstance();
        return calendar.getTime();
    }

    //获取
    public static Date getDate(int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, day); //向后走一天
        return calendar.getTime();
    }


    public static Calendar getSelectedDate(String str) {
        Calendar selectedDate = Calendar.getInstance();
        selectedDate.setTime(DateUtil.str2Date(str));
        return selectedDate;
    }

    public static Calendar getStartDate() {
        Calendar startDate = Calendar.getInstance();
        startDate.set(1949, 0, 1);
        return startDate;
    }

    public static Calendar getEndDate() {
        Calendar endDate = Calendar.getInstance();
        endDate.setTime(DateUtil.getNowDate());
        return endDate;
    }


    // 根据年月日计算年龄,birthTimeString:"1994-11-14"
    public static int getAgeFromBirthTime(String birthTimeString) {
        Date date = str2Date(birthTimeString);
        // 得到当前时间的年、月、日
        if (date != null) {
            Calendar cal = Calendar.getInstance();
            int yearNow = cal.get(Calendar.YEAR);
            int monthNow = cal.get(Calendar.MONTH) + 1;
            int dayNow = cal.get(Calendar.DATE);
            //得到输入时间的年，月，日
            cal.setTime(date);
            int selectYear = cal.get(Calendar.YEAR);
            int selectMonth = cal.get(Calendar.MONTH) + 1;
            int selectDay = cal.get(Calendar.DATE);
            // 用当前年月日减去生日年月日
            int yearMinus = yearNow - selectYear;
            int monthMinus = monthNow - selectMonth;
            int dayMinus = dayNow - selectDay;
            int age = yearMinus;// 先大致赋值
            if (yearMinus <= 0) {
                age = 0;
            }
            if (monthMinus < 0) {
                age = age - 1;
            } else if (monthMinus == 0) {
                if (dayMinus < 0) {
                    age = age - 1;
                }
            }
            return age;
        }
        return 0;
    }
}

