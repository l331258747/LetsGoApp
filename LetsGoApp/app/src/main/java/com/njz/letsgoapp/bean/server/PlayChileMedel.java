package com.njz.letsgoapp.bean.server;

import java.util.List;

/**
 * Created by LGQ
 * Time: 2018/12/5
 * Function:
 */

public class PlayChileMedel {

    private String njzGuideServeFormatDic;
    private String servePriceSelect;
    private float serveDefaultPrice;
    private String formatUnit;
    private int njzGuideServeId;
    private String guideServeFormatName;
    private PlayChile2Medel njzGuideFormatDicNumVo;
    private int id;
    private boolean isSelect;

    public PlayChile2Medel getNjzGuideFormatDicNumVo() {
        return njzGuideFormatDicNumVo;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public String getNjzGuideServeFormatDic() {
        return njzGuideServeFormatDic;
    }

    public String getServePriceSelect() {
        return servePriceSelect;
    }

    public float getServeDefaultPrice() {
        return serveDefaultPrice;
    }

    public String getFormatUnit() {
        return formatUnit;
    }

    public int getNjzGuideServeId() {
        return njzGuideServeId;
    }

    public String getGuideServeFormatName() {
        return guideServeFormatName;
    }

    public int getId() {
        return id;
    }

    public void setNjzGuideServeFormatDic(String njzGuideServeFormatDic) {
        this.njzGuideServeFormatDic = njzGuideServeFormatDic;
    }

    public void setServePriceSelect(String servePriceSelect) {
        this.servePriceSelect = servePriceSelect;
    }

    public void setServeDefaultPrice(float serveDefaultPrice) {
        this.serveDefaultPrice = serveDefaultPrice;
    }

    public void setFormatUnit(String formatUnit) {
        this.formatUnit = formatUnit;
    }

    public void setNjzGuideServeId(int njzGuideServeId) {
        this.njzGuideServeId = njzGuideServeId;
    }

    public void setGuideServeFormatName(String guideServeFormatName) {
        this.guideServeFormatName = guideServeFormatName;
    }

    public void setId(int id) {
        this.id = id;
    }
}
