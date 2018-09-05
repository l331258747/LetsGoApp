package com.njz.letsgoapp.bean.mine;

import java.util.List;

/**
 * Created by LGQ
 * Time: 2018/9/5
 * Function:
 */

public class LabelModel {

    /**
     * name : 技能
     * sysMacroEntitys : [{"id":12,"name":"会攀岩"},{"id":11,"name":"熟悉当地"},{"id":13,"name":"会溪涧运动"},{"id":14,"name":"喜欢自驾"}]
     */

    private String name;
    private List<LabelItemModel> sysMacroEntitys;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<LabelItemModel> getSysMacroEntitys() {
        return sysMacroEntitys;
    }

    public void setSysMacroEntitys(List<LabelItemModel> sysMacroEntitys) {
        this.sysMacroEntitys = sysMacroEntitys;
    }


}
