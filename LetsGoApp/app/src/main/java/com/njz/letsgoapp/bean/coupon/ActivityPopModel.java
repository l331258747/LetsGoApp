package com.njz.letsgoapp.bean.coupon;

import com.njz.letsgoapp.bean.MySelfInfo;
import com.njz.letsgoapp.util.DateUtil;

/**
 * Created by LGQ
 * Time: 2019/2/26
 * Function:
 */

public class ActivityPopModel {

    /**
     * popoutImage :        弹框背景图
     * isRemind : 0         是否弹框1：提醒 0:不提醒
     * remindScheme : 0     提醒方案 1：一次， 2:每天， 3：每周， 4：每月
     * id : 0               活动id
     */

    private String popoutImage;
    private int isRemind;
    private int remindScheme;
    private int id;

    public String getPopoutImage() {
        return popoutImage;
    }

    public void setPopoutImage(String popoutImage) {
        this.popoutImage = popoutImage;
    }

    public int getIsRemind() {
        return isRemind;
    }

    public void setIsRemind(int isRemind) {
        this.isRemind = isRemind;
    }

    public int getRemindScheme() {
        return remindScheme;
    }

    public void setRemindScheme(int remindScheme) {
        this.remindScheme = remindScheme;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean showDialog() {

        long currentTime = System.currentTimeMillis();

        if(remindScheme == 5){
            saveActivityPopData(currentTime);
            return true;
        }

        if (MySelfInfo.getInstance().getActivityId() != getId()){
            saveActivityPopData(currentTime);
            return true;
        }

        if(MySelfInfo.getInstance().getActivityUserId() != MySelfInfo.getInstance().getUserId()){
            saveActivityPopData(currentTime);
            return true;
        }

        int dateDiffer = DateUtil.getDistanceTime(MySelfInfo.getInstance().getActivityTime(),currentTime);
        switch (remindScheme){//1：一次， 2:每天， 3：每周， 4：每月
            case 1:

                break;
            case 2:
                if(dateDiffer >= 1){
                    saveActivityPopData(currentTime);
                    return true;
                }
                break;
            case 3:
                if(dateDiffer >= 7){
                    saveActivityPopData(currentTime);
                    return true;
                }
                break;
            case 4:
                if(dateDiffer >= 30){
                    saveActivityPopData(currentTime);
                    return true;
                }
                break;
        }
        return false;
    }

    public void saveActivityPopData(long currentTime){
        MySelfInfo.getInstance().setActivityId(getId());
        MySelfInfo.getInstance().setActivityUserId(MySelfInfo.getInstance().getUserId());
        MySelfInfo.getInstance().setActivityTime(currentTime);
    }
}
