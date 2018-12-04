package com.njz.letsgoapp.bean.home;

/**
 * Created by LGQ
 * Time: 2018/10/15
 * Function:
 */

public class EvaluateServicesModel {

    private int id;
    private String title;

    public EvaluateServicesModel(int id, String title) {
        this.id = id;
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }
}
