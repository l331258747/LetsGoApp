package com.njz.letsgoapp.bean.find;

/**
 * Created by LGQ
 * Time: 2018/9/7
 * Function:
 */

public class DynamicCommentModel {

    /**
     * toUserUrl :
     * discussUserUrl : https://img2.woyaogexing.com/2018/07/19/376a578c2cf44b9f!400x400_big.jpg
     * discussUserName : ldy11111111
     * discussTime : 2018-09-07 09:48:10
     * toUserName :
     * discussContent : 学教育到华大华大教育
     * toUserId : 0
     * discussUserId : 1
     */

    private String toUserUrl;
    private String discussUserUrl;
    private String discussUserName;
    private String discussTime;
    private String toUserName;
    private String discussContent;
    private int toUserId;
    private int discussUserId;
    private int id;

    public int getId() {
        return id;
    }

    public String getToUserUrl() {
        return toUserUrl;
    }

    public void setToUserUrl(String toUserUrl) {
        this.toUserUrl = toUserUrl;
    }

    public String getDiscussUserUrl() {
        return discussUserUrl;
    }

    public void setDiscussUserUrl(String discussUserUrl) {
        this.discussUserUrl = discussUserUrl;
    }

    public String getDiscussUserName() {
        return discussUserName;
    }

    public void setDiscussUserName(String discussUserName) {
        this.discussUserName = discussUserName;
    }

    public String getDiscussTime() {
        return discussTime;
    }

    public void setDiscussTime(String discussTime) {
        this.discussTime = discussTime;
    }

    public String getToUserName() {
        return toUserName;
    }

    public void setToUserName(String toUserName) {
        this.toUserName = toUserName;
    }

    public String getDiscussContent() {
        return discussContent;
    }

    public void setDiscussContent(String discussContent) {
        this.discussContent = discussContent;
    }

    public int getToUserId() {
        return toUserId;
    }

    public void setToUserId(int toUserId) {
        this.toUserId = toUserId;
    }

    public int getDiscussUserId() {
        return discussUserId;
    }

    public void setDiscussUserId(int discussUserId) {
        this.discussUserId = discussUserId;
    }
}
