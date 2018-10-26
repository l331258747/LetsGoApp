package com.njz.letsgoapp.bean.notify;

import com.njz.letsgoapp.util.DateUtil;
import com.njz.letsgoapp.util.GsonUtil;

/**
 * Created by LGQ
 * Time: 2018/10/12
 * Function:
 */

public class NotifyMainModel {


    /**
     * msgId : 662
     * title : null
     * msgType : 61
     * content : {"alert":" 按时到场 ","other":"{}"}
     * skip : null
     * sendUserId : 42
     * receiveUserId : 2
     * correlationId : 1
     * sendToReceive : U-U
     * createDate : 1539156042000
     * read : true
     * msgTypeName : 系统通知
     */

    private int msgId;
    private String title;
    private String msgType;
    private String content;
    private int sendUserId;
    private int receiveUserId;
    private String correlationId;
    private String sendToReceive;
    private long createDate;
    private boolean read;
    private String msgBroad;
    private String msgTypeName;
    private int unReadNum;

    public int getUnReadNum() {
        return unReadNum;
    }

    public void setUnReadNum(int unReadNum) {
        this.unReadNum = unReadNum;
    }

    public String getMsgBroad() {
        return msgBroad;
    }

    public void setMsgBroad(String msgBroad) {
        this.msgBroad = msgBroad;
    }

    public int getMsgId() {
        return msgId;
    }

    public void setMsgId(int msgId) {
        this.msgId = msgId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }

    public NotifyContentModel getContent() {
        return GsonUtil.convertString2Object(content,NotifyContentModel.class);
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getSendUserId() {
        return sendUserId;
    }

    public void setSendUserId(int sendUserId) {
        this.sendUserId = sendUserId;
    }

    public int getReceiveUserId() {
        return receiveUserId;
    }

    public void setReceiveUserId(int receiveUserId) {
        this.receiveUserId = receiveUserId;
    }

    public String getCorrelationId() {
        return correlationId;
    }

    public void setCorrelationId(String correlationId) {
        this.correlationId = correlationId;
    }

    public String getSendToReceive() {
        return sendToReceive;
    }

    public void setSendToReceive(String sendToReceive) {
        this.sendToReceive = sendToReceive;
    }

    public String getCreateDate() {
        return DateUtil.longToStr(createDate);
    }

    public void setCreateDate(long createDate) {
        this.createDate = createDate;
    }

    public boolean isRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }

    public String getMsgTypeName() {
        return msgTypeName;
    }

    public void setMsgTypeName(String msgTypeName) {
        this.msgTypeName = msgTypeName;
    }
}
