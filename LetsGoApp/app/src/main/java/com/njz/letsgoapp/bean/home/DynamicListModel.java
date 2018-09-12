package com.njz.letsgoapp.bean.home;

import java.util.List;

/**
 * Created by LGQ
 * Time: 2018/8/28
 * Function:
 */

public class DynamicListModel {
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
    private List<DynamicModel> list;
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
    public List<DynamicModel> getList() {
        return list;
    }

    public void setList(List<DynamicModel> list) {
        this.list = list;
    }


}
