package com.njz.letsgoapp.bean.home;

/**
 * Created by LGQ
 * Time: 2018/8/13
 * Function:
 */

public class ServiceInfoGroup {

    public static final int LABEL_TAB_TITLE = 1;
    public static final int LABEL_TAB_DEFAULT = 2;
//    public static final int LABEL_TAB_FOOT = 3;
//    public static final int LABEL_TAB_DEFAULT_2 = 4;

    private int labelTab;
    private ServiceItem serviceItem;
    private boolean serviceTitleColor;
    private String serviceTitle;
    private String serviceFoot;
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isServiceTitleColor() {
        return serviceTitleColor;
    }

    public void setServiceTitleColor(boolean serviceTitleColor) {
        this.serviceTitleColor = serviceTitleColor;
    }

    public int getLabelTab() {
        return labelTab;
    }

    public void setLabelTab(int labelTab) {
        this.labelTab = labelTab;
    }

    public ServiceItem getServiceItem() {
        return serviceItem;
    }

    public void setServiceItem(ServiceItem serviceItem) {
        this.serviceItem = serviceItem;
    }

    public String getServiceTitle() {
        return serviceTitle;
    }

    public void setServiceTitle(String serviceTitle) {
        this.serviceTitle = serviceTitle;
    }

    public String getServiceFoot() {
        return serviceFoot;
    }

    public void setServiceFoot(String serviceFoot) {
        this.serviceFoot = serviceFoot;
    }
}
