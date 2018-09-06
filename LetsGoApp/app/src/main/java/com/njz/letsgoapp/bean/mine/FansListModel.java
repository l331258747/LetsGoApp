package com.njz.letsgoapp.bean.mine;

import java.util.List;

/**
 * Created by LGQ
 * Time: 2018/9/6
 * Function:
 */

public class FansListModel {

    /**
     * totalCount : 2
     * pageSize : 10
     * totalPage : 1
     * currPage : 1
     * list : [{"userId":3,"imgUrl":null,"nickname":"clb","userLevel":23,"focusTime":1536143905000,"focusTimeToString":"16小时前"},{"userId":2,"imgUrl":null,"nickname":"lm","userLevel":44,"focusTime":1536143900000,"focusTimeToString":"16小时前"}]
     */

    private int totalCount;
    private int pageSize;
    private int totalPage;
    private int currPage;
    private List<FansModel> list;

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

    public List<FansModel> getList() {
        return list;
    }

    public void setList(List<FansModel> list) {
        this.list = list;
    }
}
