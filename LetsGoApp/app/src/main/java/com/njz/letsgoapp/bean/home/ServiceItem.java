package com.njz.letsgoapp.bean.home;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ServiceItem implements Parcelable{

    int id;
    String titile;
    String img;
    String content;
    float price;
    String startTime;
    String endTime;
    int number;
    String serviceType;
    int serveType;
    int timeDay;
    List<String> days;
    String oneTime;

    public ServiceItem() {
    }

    public int getServeType() {
        return serveType;
    }

    public void setServeType(int serveType) {
        this.serveType = serveType;
    }

    public String getTitile() {
        return titile;
    }

    public void setTitile(String titile) {
        this.titile = titile;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
    public int getTimeDay() {
        return timeDay;
    }

    public void setTimeDay(int timeDay) {
        this.timeDay = timeDay;
    }

    public List<String> getDays() {
        return days;
    }

    //用来展示数据有换行
    public String getDaysStr(){
        if(days == null) return "";
        StringBuffer sb = new StringBuffer();
        for (int i = 0;i<days.size();i++){
            sb.append(days.get(i));
            sb.append(",");
            if((i+1)%3 == 0) sb.append("\n");
        }
        String result = sb.toString();
        if(result.endsWith("\n")){
            result = sb.substring(0,result.length() - 1);
        }
        if(result.endsWith(",")){
            result = sb.substring(0,result.length() - 1);
        }
        return result;
    }

    //用来传递给服务器的数据
    public String getDaysStr2(){
        if(days == null) return "";
        StringBuffer sb = new StringBuffer();
        for (int i = 0;i<days.size();i++){
            sb.append(days.get(i));
            sb.append(",");
        }
        String result = sb.toString();
        if(result.endsWith(",")){
            result = sb.substring(0,result.length() - 1);
        }
        return result;
    }

    public void setDays(List<String> days) {
        this.days = days;

        if(days == null || days.size() < 1)
            return;
        Collections.sort(days, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                String[] str1= o1.split("-");
                String[] str2= o2.split("-");
                if (Integer.valueOf(str1[0] + str1[1] + str1[2]) > Integer.valueOf(str2[0] + str2[1] + str2[2])){
                    return 1;
                }else if(Integer.valueOf(str1[0] + str1[1] + str1[2]) == Integer.valueOf(str2[0] + str2[1] + str2[2])){
                    return 0;
                }
                return -1;
            }
        });
    }

    public String getOneTime() {
        return oneTime;
    }

    public void setOneTime(String oneTime) {
        this.oneTime = oneTime;
    }


    protected ServiceItem(Parcel in) {
        id = in.readInt();
        titile = in.readString();
        img = in.readString();
        content = in.readString();
        price = in.readFloat();
        startTime = in.readString();
        endTime = in.readString();
        number = in.readInt();
        serviceType = in.readString();
        serveType = in.readInt();
        timeDay = in.readInt();
        days = in.createStringArrayList();
        oneTime = in.readString();
    }

    public static final Creator<ServiceItem> CREATOR = new Creator<ServiceItem>() {
        @Override
        public ServiceItem createFromParcel(Parcel in) {
            return new ServiceItem(in);
        }

        @Override
        public ServiceItem[] newArray(int size) {
            return new ServiceItem[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(titile);
        dest.writeString(img);
        dest.writeString(content);
        dest.writeFloat(price);
        dest.writeString(startTime);
        dest.writeString(endTime);
        dest.writeInt(number);
        dest.writeString(serviceType);
        dest.writeInt(serveType);
        dest.writeInt(timeDay);
        dest.writeStringList(days);
        dest.writeString(oneTime);
    }
}