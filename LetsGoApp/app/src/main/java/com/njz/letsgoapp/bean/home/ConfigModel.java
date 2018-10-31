package com.njz.letsgoapp.bean.home;

import com.njz.letsgoapp.bean.mine.LabelItemModel;

import java.util.List;

/**
 * Created by LGQ
 * Time: 2018/9/5
 * Function:
 */

public class ConfigModel {

    /**
     * name : 技能
     * sysMacroEntitys : [{"id":12,"name":"会攀岩"},{"id":11,"name":"熟悉当地"},{"id":13,"name":"会溪涧运动"},{"id":14,"name":"喜欢自驾"}]
     */

    private String name;
    private String value;
    private List<ConfigChildModel> childModels;


    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ConfigChildModel> getChildModels() {
        return childModels;
    }

    public void setChildModels(List<ConfigChildModel> childModels) {
        this.childModels = childModels;
    }

    public static class ConfigChildModel{
        private String value;
        private String name;

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

}
