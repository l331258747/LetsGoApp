package com.njz.letsgoapp.bean.server;

import java.util.List;

/**
 * Created by LGQ
 * Time: 2018/12/12
 * Function:
 */

public class PriceCalendarModel {


    private List<PriceCalendarChildModel> njzGuideServeFormatOnlyPriceVOList;
    private List<String> noTimes;


    public List<PriceCalendarChildModel> getNjzGuideServeFormatOnlyPriceVOList() {
        return njzGuideServeFormatOnlyPriceVOList;
    }

    public List<String> getNoTimes() {
        return noTimes;
    }
}
