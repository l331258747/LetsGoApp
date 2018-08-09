package com.njz.letsgoapp.bean.home;

import java.util.List;

/**
 * Created by LGQ
 * Time: 2018/8/6
 * Function:
 */

public class HomeData {

    List<HomeBanner> homeBanners;
    List<Guide> guides;

    public HomeData(List<HomeBanner> homeBanners, List<Guide> guides) {
        this.homeBanners = homeBanners;
        this.guides = guides;
    }


    public List<HomeBanner> getHomeBanners() {
        return homeBanners;
    }

    public List<Guide> getGuides() {
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

    public static class Guide {
        public String backgroundImg;
        public String headImg;
        public String name;
        public int level;
        public int times;
        public int CommentTimes;
        public double price;
        public String content;
        private List<String> serviceTags;

        public Guide(String backgroundImg, String headImg, String name, int level, int times, int commentTimes, double price, String content) {
            this.backgroundImg = backgroundImg;
            this.headImg = headImg;
            this.name = name;
            this.level = level;
            this.times = times;
            CommentTimes = commentTimes;
            this.price = price;
            this.content = content;
        }


        public String getBackgroundImg() {
            return backgroundImg;
        }

        public String getHeadImg() {
            return headImg;
        }

        public String getName() {
            return name;
        }

        public int getLevel() {
            return level;
        }

        public int getTimes() {
            return times;
        }

        public int getCommentTimes() {
            return CommentTimes;
        }

        public double getPrice() {
            return price;
        }

        public String getContent() {
            return content;
        }

        public List<String> getServiceTags() {
            return serviceTags;
        }

        public void setServiceTags(List<String> serviceTags) {
            this.serviceTags = serviceTags;
        }
    }

}
