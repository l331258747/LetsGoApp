package com.njz.letsgoapp.bean.order;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by LGQ
 * Time: 2018/9/20
 * Function:
 */

public class PayModel implements Parcelable{


    /**
     * lastPayTime : 2018-09-21 16:01:32
     * totalAmount : 224
     * subject : 长沙lm导游为您服务！
     * outTradeNo : CS201809201601327810
     * body :
     */

    private String lastPayTime;
    private String totalAmount;
    private String subject;
    private String outTradeNo;
    private String body;

    public PayModel() {
    }

    public String getLastPayTime() {
        return lastPayTime;
    }

    public void setLastPayTime(String lastPayTime) {
        this.lastPayTime = lastPayTime;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getOutTradeNo() {
        return outTradeNo;
    }

    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    protected PayModel(Parcel in) {
        lastPayTime = in.readString();
        totalAmount = in.readString();
        subject = in.readString();
        outTradeNo = in.readString();
        body = in.readString();
    }

    public static final Creator<PayModel> CREATOR = new Creator<PayModel>() {
        @Override
        public PayModel createFromParcel(Parcel in) {
            return new PayModel(in);
        }

        @Override
        public PayModel[] newArray(int size) {
            return new PayModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(lastPayTime);
        dest.writeString(totalAmount);
        dest.writeString(subject);
        dest.writeString(outTradeNo);
        dest.writeString(body);
    }
}
