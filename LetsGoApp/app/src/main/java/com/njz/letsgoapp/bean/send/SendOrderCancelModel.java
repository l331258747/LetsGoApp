package com.njz.letsgoapp.bean.send;

/**
 * Created by LGQ
 * Time: 2018/10/9
 * Function:
 */

public class SendOrderCancelModel {

    int id;
    int isMainly;
    String cancelReason;
    String cancelExplain;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIsMainly() {
        return isMainly;
    }

    public void setIsMainly(int isMainly) {
        this.isMainly = isMainly;
    }

    public String getCancelReason() {
        return cancelReason;
    }

    public void setCancelReason(String cancelReason) {
        this.cancelReason = cancelReason;
    }

    public String getCancelExplain() {
        return cancelExplain;
    }

    public void setCancelExplain(String cancelExplain) {
        this.cancelExplain = cancelExplain;
    }
}
