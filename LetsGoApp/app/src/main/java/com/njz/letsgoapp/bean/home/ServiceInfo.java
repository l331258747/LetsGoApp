package com.njz.letsgoapp.bean.home;

import java.util.List;

/**
 * Created by LGQ
 * Time: 2018/8/13
 * Function:
 */

public class ServiceInfo {

    List<ServiceItem> guideServices;//向导
    List<ServiceItem> customServices;//定制
    List<ServiceItem> carServices;//包车
    List<ServiceItem> hotelServices;//酒店
    List<ServiceItem> ticketServices;//门票

    public List<ServiceItem> getGuideServices() {
        return guideServices;
    }

    public void setGuideServices(List<ServiceItem> guideServices) {
        this.guideServices = guideServices;
    }

    public List<ServiceItem> getCustomServices() {
        return customServices;
    }

    public void setCustomServices(List<ServiceItem> customServices) {
        this.customServices = customServices;
    }

    public List<ServiceItem> getCarServices() {
        return carServices;
    }

    public void setCarServices(List<ServiceItem> carServices) {
        this.carServices = carServices;
    }

    public List<ServiceItem> getHotelServices() {
        return hotelServices;
    }

    public void setHotelServices(List<ServiceItem> hotelServices) {
        this.hotelServices = hotelServices;
    }

    public List<ServiceItem> getTicketServices() {
        return ticketServices;
    }

    public void setTicketServices(List<ServiceItem> ticketServices) {
        this.ticketServices = ticketServices;
    }



}
