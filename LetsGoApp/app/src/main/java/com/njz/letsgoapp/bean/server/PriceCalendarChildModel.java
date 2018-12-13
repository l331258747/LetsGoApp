package com.njz.letsgoapp.bean.server;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.njz.letsgoapp.util.DateUtil;

import java.util.Date;
import java.util.List;

/**
 * Created by LGQ
 * Time: 2018/12/12
 * Function:
 */

public class PriceCalendarChildModel implements Parcelable, Comparable<PriceCalendarChildModel> {

    /**
     * monthInt : 12
     * yearInt : 2018
     * dateInt : 12
     * addPrice : 200.0
     */

    private int monthInt;
    private int yearInt;
    private int dateInt;
    private float addPrice;
    private boolean isSelect;

    public PriceCalendarChildModel() {
    }

    protected PriceCalendarChildModel(Parcel in) {
        monthInt = in.readInt();
        yearInt = in.readInt();
        dateInt = in.readInt();
        addPrice = in.readFloat();
        isSelect = in.readByte() != 0;
    }

    public static final Creator<PriceCalendarChildModel> CREATOR = new Creator<PriceCalendarChildModel>() {
        @Override
        public PriceCalendarChildModel createFromParcel(Parcel in) {
            return new PriceCalendarChildModel(in);
        }

        @Override
        public PriceCalendarChildModel[] newArray(int size) {
            return new PriceCalendarChildModel[size];
        }
    };

    public void setMonthInt(int monthInt) {
        this.monthInt = monthInt;
    }

    public void setYearInt(int yearInt) {
        this.yearInt = yearInt;
    }

    public void setDateInt(int dateInt) {
        this.dateInt = dateInt;
    }

    public void setAddPrice(float addPrice) {
        this.addPrice = addPrice;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public int getMonthInt() {
        return monthInt;
    }

    public int getYearInt() {
        return yearInt;
    }

    public int getDateInt() {
        return dateInt;
    }

    public float getAddPrice() {
        return addPrice;
    }

    public String getTime() {
        String monthStr = monthInt + "";
        if (monthInt < 10) {
            monthStr = "0" + monthInt;
        }
        String dateStr = dateInt + "";
        if(dateInt < 10){
            dateStr = "0" + dateInt;
        }
        return yearInt + "-" + monthStr + "-" + dateStr;
    }

    public String getTime(String format) {
        String monthStr = monthInt + "";
        if (monthInt < 10) {
            monthStr = "0" + monthInt;
        }
        String dateStr = dateInt + "";
        if(dateInt < 10){
            dateStr = "0" + dateInt;
        }
        return yearInt + format + monthStr + format + dateStr;
    }

    public String getTimeMD() {
        String monthStr = monthInt + "";
        if (monthInt < 10) {
            monthStr = "0" + monthInt;
        }
        String dateStr = dateInt + "";
        if(dateInt < 10){
            dateStr = "0" + dateInt;
        }
        return monthStr + "-" + dateStr;
    }

    public Date getDate() {
        return DateUtil.str2Date(getTime());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(monthInt);
        dest.writeInt(yearInt);
        dest.writeInt(dateInt);
        dest.writeFloat(addPrice);
        dest.writeByte((byte) (isSelect ? 1 : 0));
    }

    @Override
    public int compareTo(@NonNull PriceCalendarChildModel o) {
        return Integer.valueOf(getTime("")) - Integer.valueOf(o.getTime(""));
        //自定义比较方法，如果认为此实体本身大则返回1，否则返回-1

    }
}
