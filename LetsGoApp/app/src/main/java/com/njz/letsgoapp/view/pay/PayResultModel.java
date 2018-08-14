package com.njz.letsgoapp.view.pay;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by llt on 2017/12/12.
 */

public class PayResultModel implements Serializable {

    public String appid;
    public String partnerid;
    public String prepayid;
    public String noncestr;
    public String timestamp;
    @SerializedName("package")
    public String wvpackage;
    public String sign;

    private String orderString;

    public String getOrderString() {
        return orderString;
    }

    public void setOrderString(String orderString) {
        this.orderString = orderString;
    }
}
