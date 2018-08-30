package com.njz.letsgoapp.bean.other;

/**
 * Created by LGQ
 * Time: 2018/8/29
 * Function:
 */

public class CityModel {


    /**
     * id : 36
     * parentId : 2
     * name : 长沙市
     * spell : changshashi
     * initialism : CS
     * imgUrl : xxx
     * imgLink : xxx
     * type : 2
     */

    private int id;
    private int parentId;
    private String name;
    private String spell;
    private String initialism;
    private String imgUrl;
    private String imgLink;
    private int type;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpell() {
        return spell;
    }

    public void setSpell(String spell) {
        this.spell = spell;
    }

    public String getInitialism() {
        return initialism;
    }

    public void setInitialism(String initialism) {
        this.initialism = initialism;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getImgLink() {
        return imgLink;
    }

    public void setImgLink(String imgLink) {
        this.imgLink = imgLink;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
