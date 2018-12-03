package com.njz.letsgoapp.bean.home;

/**
 * Created by LGQ
 * Time: 2018/12/3
 * Function:
 */

public class PlayData {

    String img;
    String title;
    float price;
    String location;
    float score;
    int count;
    int comment;

    public PlayData(String img, String title, float price, String location, float score, int count, int comment) {
        this.img = img;
        this.title = title;
        this.price = price;
        this.location = location;
        this.score = score;
        this.count = count;
        this.comment = comment;
    }

    public String getImg() {
        return img;
    }

    public String getTitle() {
        return title;
    }

    public float getPrice() {
        return price;
    }

    public String getLocation() {
        return location;
    }

    public float getScore() {
        return score;
    }

    public int getCount() {
        return count;
    }

    public int getComment() {
        return comment;
    }
}
