package com.njz.letsgoapp.bean.mine;

/**
 * Created by LGQ
 * Time: 2018/9/28
 * Function:
 */

public class MyCommentModel {


    /**
     * imgUrl :
     * userLevel : 44
     * friendSterId : 3
     * discussTime : 1个月前
     * nickname : lm
     * discussContent : 一起去呀
     */

    private String imgUrl;
    private int userLevel;
    private int friendSterId;
    private String discussTime;
    private String nickname;
    private String discussContent;

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public int getUserLevel() {
        return userLevel;
    }

    public void setUserLevel(int userLevel) {
        this.userLevel = userLevel;
    }

    public int getFriendSterId() {
        return friendSterId;
    }

    public void setFriendSterId(int friendSterId) {
        this.friendSterId = friendSterId;
    }

    public String getDiscussTime() {
        return discussTime;
    }

    public void setDiscussTime(String discussTime) {
        this.discussTime = discussTime;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getDiscussContent() {
        return discussContent;
    }

    public void setDiscussContent(String discussContent) {
        this.discussContent = discussContent;
    }
}
