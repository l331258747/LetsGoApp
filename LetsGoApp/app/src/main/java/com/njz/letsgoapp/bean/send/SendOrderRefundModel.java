package com.njz.letsgoapp.bean.send;

import java.util.List;

/**
 * Created by LGQ
 * Time: 2018/10/10
 * Function:
 */

public class SendOrderRefundModel {
    int id;
    List<Integer> childIds;
    String refundReason;
    String refundContent;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Integer> getChildIds() {
        return childIds;
    }

    public void setChildIds(List<Integer> childIds) {
        this.childIds = childIds;
    }

    public String getRefundReason() {
        return refundReason;
    }

    public void setRefundReason(String refundReason) {
        this.refundReason = refundReason;
    }

    public String getRefundContent() {
        return refundContent;
    }

    public void setRefundContent(String refundContent) {
        this.refundContent = refundContent;
    }
}
