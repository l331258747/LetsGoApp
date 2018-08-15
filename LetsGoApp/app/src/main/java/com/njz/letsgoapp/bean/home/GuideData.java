package com.njz.letsgoapp.bean.home;

import java.util.List;

/**
 * Created by LGQ
 * Time: 2018/8/8
 * Function:
 */

public class GuideData {

    String name;
    String headUrl;
    int stars;
    String sex;
    double price;
    int serviceNum;
    int comment;
    String content;
    List<String> serviceItems;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHeadUrl() {
        return headUrl;
    }

    public void setHeadUrl(String headUrl) {
        this.headUrl = headUrl;
    }


    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }


    public int getServiceNum() {
        return serviceNum;
    }

    public void setServiceNum(int serviceNum) {
        this.serviceNum = serviceNum;
    }

    public int getComment() {
        return comment;
    }

    public void setComment(int comment) {
        this.comment = comment;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<String> getServiceItems() {
        return serviceItems;
    }

    public void setServiceItems(List<String> serviceItems) {
        this.serviceItems = serviceItems;
    }

    public int getStars() {
        return stars;
    }

    public void setStars(int stars) {
        this.stars = stars;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
