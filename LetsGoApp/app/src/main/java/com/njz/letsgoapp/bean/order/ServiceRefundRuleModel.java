package com.njz.letsgoapp.bean.order;

/**
 * Created by LGQ
 * Time: 2018/10/19
 * Function:
 */

public class ServiceRefundRuleModel {


    /**
     * cost_explain :
     * id : 36
     * renegePriceFive : 1,6
     * renegePriceThree : 7,13
     */

    private String cost_explain;
    private int id;
    private String renegePriceFive;
    private String renegePriceThree;

    public String getCostExplain() {
        return cost_explain;
    }

    public void setCostExplain(String cost_explain) {
        this.cost_explain = cost_explain;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRenegePriceFive() {
        return renegePriceFive;
    }

    public void setRenegePriceFive(String renegePriceFive) {
        this.renegePriceFive = renegePriceFive;
    }

    public String getRenegePriceThree() {
        return renegePriceThree;
    }

    public void setRenegePriceThree(String renegePriceThree) {
        this.renegePriceThree = renegePriceThree;
    }
}
