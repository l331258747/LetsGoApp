package com.njz.letsgoapp.util.rxbus.busEvent;

/**
 * Created by LGQ
 * Time: 2018/10/25
 * Function:
 */

public class NotifyEvent {

    boolean isShowTips;

    public NotifyEvent(boolean isShowTips) {
        this.isShowTips = isShowTips;
    }

    public boolean isShowTips() {
        return isShowTips;
    }

}
