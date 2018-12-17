package com.njz.letsgoapp.bean.server;

import android.text.TextUtils;

import com.njz.letsgoapp.util.DateUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by LGQ
 * Time: 2018/12/17
 * Function:
 */

public class SubmitOrderChilderItemModel {

    int serveNum;
    List<String> selectTimeValueList;
    int njzGuideServeId;
    List<Integer> njzGuideServeFormatId;

    public int getServeNum() {
        return serveNum;
    }

    public void setServeNum(int serveNum) {
        this.serveNum = serveNum;
    }

    public List<String> getSelectTimeValueList() {
        return selectTimeValueList;
    }

    public void setSelectTimeValueList(String str) {
        List<String> list = new ArrayList<>();
        if (!TextUtils.isEmpty(str)) {
            String[] ss = str.split(",");
            for (String s : ss) {
                list.add(DateUtil.dateToStr(DateUtil.str2Date(s), "yyyyMMdd"));
            }
        }
        selectTimeValueList = list;
    }

    public int getNjzGuideServeId() {
        return njzGuideServeId;
    }

    public void setNjzGuideServeId(int njzGuideServeId) {
        this.njzGuideServeId = njzGuideServeId;
    }

    public List<Integer> getNjzGuideServeFormatId() {
        return njzGuideServeFormatId;
    }

    public void setNjzGuideServeFormatId(String str) {
        List<Integer> list = new ArrayList<>();
        if (!TextUtils.isEmpty(str)) {
            String[] ss = str.split(",");
            for (String s : ss) {
                list.add(Integer.valueOf(s));
            }
        }
        njzGuideServeFormatId = list;
    }
}
