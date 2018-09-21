package com.njz.letsgoapp.map;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.njz.letsgoapp.bean.other.LocationModel;
import com.njz.letsgoapp.util.AppUtils;
import com.njz.letsgoapp.util.log.LogUtil;
import com.zaaach.citypicker.model.City;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by LGQ
 * Time: 2018/7/25
 * Function: 定位工具类
 */

public class LocationUtil {

    //定位对象
    private AMapLocationClient mLocationClient = null;
    //定位参数
    public AMapLocationClientOption mLocationOption = null;


    public void initLocationClient(final LocationListener locationListener) {
        if (mLocationClient != null) return;

        mLocationClient = new AMapLocationClient(AppUtils.getContext());

        mLocationClient.setLocationListener(new AMapLocationListener() {
            @Override
            public void onLocationChanged(AMapLocation aMapLocation) {
                if (aMapLocation != null) {
                    if (aMapLocation.getErrorCode() == 0) {
                        //可在其中解析amapLocation获取相应内容。

                        LogUtil.e("获取当前定位结果来源，如网络定位结果，详见定位类型表:" + aMapLocation.getLocationType());
                        LogUtil.e("获取纬度" + aMapLocation.getLatitude());
                        LogUtil.e("获取经度" + aMapLocation.getLongitude());
                        LogUtil.e("获取精度信息" + aMapLocation.getAccuracy());
                        LogUtil.e("地址，如果option中设置isNeedAddress为false，则没有此结果，网络定位结果中会有地址信息，GPS定位不返回地址信息。" + aMapLocation.getAddress());
                        LogUtil.e("国家信息" + aMapLocation.getCountry());
                        LogUtil.e("省信息" + aMapLocation.getProvince());
                        LogUtil.e("城市信息" + aMapLocation.getCity());
                        LogUtil.e("城区信息" + aMapLocation.getDistrict());
                        LogUtil.e("街道信息" + aMapLocation.getStreet());
                        LogUtil.e("街道门牌号信息" + aMapLocation.getStreetNum());
                        LogUtil.e("城市编码" + aMapLocation.getCityCode());
                        LogUtil.e("地区编码" + aMapLocation.getAdCode());
                        LogUtil.e("获取当前定位点的AOI信息" + aMapLocation.getAoiName());
                        LogUtil.e("获取当前室内定位的建筑物Id" + aMapLocation.getBuildingId());
                        LogUtil.e("获取当前室内定位的楼层" + aMapLocation.getFloor());
                        LogUtil.e("获取GPS的当前状态" + aMapLocation.getGpsAccuracyStatus());
                        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        Date date = new Date(aMapLocation.getTime());
                        LogUtil.e("获取定位时间" + df.format(date));
                        String city = aMapLocation.getCity();
                        if(city.endsWith("市"))
                            city = city.substring(0,city.length() - 1);
                        locationListener.getAdress(aMapLocation.getErrorCode(), new LocationModel(city
                                ,aMapLocation.getDistrict()
                                ,aMapLocation.getLongitude()
                                ,aMapLocation.getLatitude()));
                    } else {
                        //定位失败时，可通过ErrCode（错误码）信息来确定失败的原因，errInfo是错误信息，详见错误码表。
                        LogUtil.e("location Error, ErrCode:"
                                + aMapLocation.getErrorCode() + ", errInfo:"
                                + aMapLocation.getErrorInfo());
                        locationListener.getAdress(aMapLocation.getErrorCode(), new LocationModel(aMapLocation.getErrorInfo()));
                    }
                }else{
                    locationListener.getAdress(-1, new LocationModel("aMapLocation null"));
                }
            }
        });
    }


    private void initLocationOption() {
        if (mLocationOption != null) return;

        mLocationOption = new AMapLocationClientOption();
        //设置定位模式为AMapLocationMode.Hight_Accuracy，高精度模式。
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //设置定位缓存策略
        mLocationOption.setLocationCacheEnable(false);
        //获取最近3s内精度最高的一次定位结果：如果设置其为true，setOnceLocation(boolean b)接口也会被设置为true，反之不会，默认为false。
        mLocationOption.setOnceLocation(true);
//        mLocationOption.setOnceLocationLatest(true);
        //设置是否返回地址信息（默认返回地址信息）网络定位有结果
        mLocationOption.setNeedAddress(true);
        mLocationOption.setHttpTimeOut(3000);
        //给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);
    }


    public void startLocation(LocationListener locationListener) {
        initLocationClient(locationListener);
        initLocationOption();
        //启动定位
        mLocationClient.startLocation();
    }

    public void stopLocation() {
        if(mLocationClient == null) return;
        mLocationClient.stopLocation();
        mLocationClient.onDestroy();
        mLocationClient = null;
        mLocationOption = null;
    }

    public interface LocationListener {
        void getAdress(int code, LocationModel adress);
    }
}
