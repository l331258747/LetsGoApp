package com.njz.letsgoapp.bean.home;

import java.util.List;

/**
 * Created by LGQ
 * Time: 2018/8/6
 * Function:
 */

public class HomeData {

    List<HomeBanner> homeBanners;
    List<Dynamic> guides;

    public HomeData(List<HomeBanner> homeBanners, List<Dynamic> guides) {
        this.homeBanners = homeBanners;
        this.guides = guides;
    }

    public List<HomeBanner> getHomeBanners() {
        return homeBanners;
    }

    public List<Dynamic> getGuides() {
        return guides;
    }

    public static class HomeBanner {
        public String imgUrl;
        public HomeBanner(String imgUrl) {
            this.imgUrl = imgUrl;
        }

        public String getImgUrl() {
            return imgUrl;
        }
    }

    public static class Dynamic{
        public String headImg;
        public String name;
        public String time;
        public String content;
        public List<String> dynamicImgs;
        public String location;
        public int comment;
        public int nice;

        public String getHeadImg() {
            return headImg;
        }

        public void setHeadImg(String headImg) {
            this.headImg = headImg;
        }

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

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public List<String> getDynamicImgs() {
            return dynamicImgs;
        }

        public void setDynamicImgs(List<String> dynamicImgs) {
            this.dynamicImgs = dynamicImgs;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public int getComment() {
            return comment;
        }

        public void setComment(int comment) {
            this.comment = comment;
        }

        public int getNice() {
            return nice;
        }

        public void setNice(int nice) {
            this.nice = nice;
        }
    }

}
