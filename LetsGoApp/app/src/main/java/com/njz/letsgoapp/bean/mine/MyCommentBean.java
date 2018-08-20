package com.njz.letsgoapp.bean.mine;

/**
 * Created by LGQ
 * Time: 2018/8/20
 * Function:
 */

public class MyCommentBean {

    String headUrl;
    String name;
    String content;
    String time;

    String bodyUrl;
    String bodyContent;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getHeadUrl() {
        return headUrl;
    }

    public void setHeadUrl(String headUrl) {
        this.headUrl = headUrl;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getBodyUrl() {
        return bodyUrl;
    }

    public void setBodyUrl(String bodyUrl) {
        this.bodyUrl = bodyUrl;
    }

    public String getBodyContent() {
        return bodyContent;
    }

    public void setBodyContent(String bodyContent) {
        this.bodyContent = bodyContent;
    }
}
