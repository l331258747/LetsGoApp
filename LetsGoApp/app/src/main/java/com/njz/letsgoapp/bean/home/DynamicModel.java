package com.njz.letsgoapp.bean.home;

import java.util.List;

/**
 * Created by LGQ
 * Time: 2018/8/28
 * Function:
 */

public class DynamicModel {
    /**
     * totalCount : 3
     * pageSize : 5
     * totalPage : 1
     * currPage : 1
     * list : [{"imgUrl":"https://img2.woyaogexing.com/2018/07/19/376a578c2cf44b9f!400x400_big.jpg","nickname":"ldy","userLevel":null,"content":"我要吃肉肉","startTime":"3周前","lon":null,"lat":null,"location":"长沙","imgUrls":null,"friendSterId":1,"replyCount":1,"likeCount":null,"gender":1,"travelDiscussEntity":null,"userId":1},{"imgUrl":"https://img2.woyaogexing.com/2018/07/19/376a578c2cf44b9f!400x400_big.jpg","nickname":"ldy","userLevel":null,"content":"沐浴露和香香皂今天用哪个好","startTime":"2周前","lon":null,"lat":null,"location":"长沙","imgUrls":"{\"url1\":\"www.baidu.com\",\"url2\":\"www.qq.com\",\"url3\":\"hahahaha\"}","friendSterId":6,"replyCount":null,"likeCount":null,"gender":1,"travelDiscussEntity":null,"userId":1},{"imgUrl":"https://img2.woyaogexing.com/2018/07/19/376a578c2cf44b9f!400x400_big.jpg","nickname":"ldy","userLevel":null,"content":"无敌宇哥热！","startTime":"2周前","lon":null,"lat":null,"location":"长沙","imgUrls":"{\"url1\":\"www.baidu.com\",\"url2\":\"www.qq.com\",\"url3\":\"hahahaha\"}","friendSterId":10,"replyCount":null,"likeCount":null,"gender":1,"travelDiscussEntity":null,"userId":1}]
     */

    private int totalCount;
    private int pageSize;
    private int totalPage;
    private int currPage;
    private List<ListBean> list;
    /**
     * imgUrl : https://img2.woyaogexing.com/2018/07/19/376a578c2cf44b9f!400x400_big.jpg
     * nickname : ldy
     * userLevel : null
     * content : 无敌宇哥热！
     * startTime : 2周前
     * lon : null
     * lat : null
     * location : 长沙
     * imgUrls : ["asdfasdfasdf","asdgasdgadgag"]
     * friendSterId : 10
     * replyCount : null
     * likeCount : null
     * gender : 1
     * travelDiscussEntity : null
     * userId : 1
     */

    private String imgUrl;
    private String nickname;
    private Object userLevel;
    private String content;
    private String startTime;
    private Object lon;
    private Object lat;
    private String location;
    private int friendSterId;
    private Object replyCount;
    private Object likeCount;
    private int gender;
    private Object travelDiscussEntity;
    private int userId;
    private List<String> imgUrls;

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getCurrPage() {
        return currPage;
    }

    public void setCurrPage(int currPage) {
        this.currPage = currPage;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Object getUserLevel() {
        return userLevel;
    }

    public void setUserLevel(Object userLevel) {
        this.userLevel = userLevel;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public Object getLon() {
        return lon;
    }

    public void setLon(Object lon) {
        this.lon = lon;
    }

    public Object getLat() {
        return lat;
    }

    public void setLat(Object lat) {
        this.lat = lat;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getFriendSterId() {
        return friendSterId;
    }

    public void setFriendSterId(int friendSterId) {
        this.friendSterId = friendSterId;
    }

    public Object getReplyCount() {
        return replyCount;
    }

    public void setReplyCount(Object replyCount) {
        this.replyCount = replyCount;
    }

    public Object getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(Object likeCount) {
        this.likeCount = likeCount;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public Object getTravelDiscussEntity() {
        return travelDiscussEntity;
    }

    public void setTravelDiscussEntity(Object travelDiscussEntity) {
        this.travelDiscussEntity = travelDiscussEntity;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public List<String> getImgUrls() {
        return imgUrls;
    }

    public void setImgUrls(List<String> imgUrls) {
        this.imgUrls = imgUrls;
    }

    public static class ListBean {

        /**
         * imgUrl : null
         * nickname : null
         * userLevel : 0
         * content : 无敌宇哥热！
         * startTime : 2周前
         * lon : 0
         * lat : 0
         * location : 长沙
         * imgUrls : ["https://goss4.vcg.com/creative/vcg/800/new/gic13822671.jpg","https://goss4.vcg.com/creative/vcg/800/version23/VCG41482576946.jpg","https://goss.vcg.com/creative/vcg/800/version23/VCG21gic13799690.jpg"]
         * friendSterId : 10
         * replyCount : 0
         * likeCount : 0
         * gender : 0
         * travelDiscussEntity : null
         * userId : 1
         */

        private String imgUrl;
        private String nickname;
        private int userLevel;
        private String content;
        private String startTime;
        private int lon;
        private int lat;
        private String location;
        private int friendSterId;
        private int replyCount;
        private int likeCount;
        private int gender;
        private Object travelDiscussEntity;
        private int userId;
        private List<String> imgUrls;

        public String getImgUrl() {
            return imgUrl;
        }

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public int getUserLevel() {
            return userLevel;
        }

        public void setUserLevel(int userLevel) {
            this.userLevel = userLevel;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getStartTime() {
            return startTime;
        }

        public void setStartTime(String startTime) {
            this.startTime = startTime;
        }

        public int getLon() {
            return lon;
        }

        public void setLon(int lon) {
            this.lon = lon;
        }

        public int getLat() {
            return lat;
        }

        public void setLat(int lat) {
            this.lat = lat;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public int getFriendSterId() {
            return friendSterId;
        }

        public void setFriendSterId(int friendSterId) {
            this.friendSterId = friendSterId;
        }

        public int getReplyCount() {
            return replyCount;
        }

        public void setReplyCount(int replyCount) {
            this.replyCount = replyCount;
        }

        public int getLikeCount() {
            return likeCount;
        }

        public void setLikeCount(int likeCount) {
            this.likeCount = likeCount;
        }

        public int getGender() {
            return gender;
        }

        public void setGender(int gender) {
            this.gender = gender;
        }

        public Object getTravelDiscussEntity() {
            return travelDiscussEntity;
        }

        public void setTravelDiscussEntity(Object travelDiscussEntity) {
            this.travelDiscussEntity = travelDiscussEntity;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public List<String> getImgUrls() {
            return imgUrls;
        }

        public void setImgUrls(List<String> imgUrls) {
            this.imgUrls = imgUrls;
        }
    }


}
