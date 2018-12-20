package com.njz.letsgoapp.constant;

import com.njz.letsgoapp.util.AppUtils;

/**
 * Created by LGQ
 * Time: 2018/7/17
 * Function: url常量
 */

public class URLConstant {

    public static final String BASE_URL = getUrl();//本地

    public static final String SHARE_GUIDE = BASE_URL+"/share/guideDeta.html";
    public static final String SHARE_DYNAMIC = BASE_URL+"/share/moveDeta.html";

    public static String getUrl(){
        if(AppUtils.getVersionCodeInt() % 100 == 0){
            return "http://www.njzou.net/travel-framework/";
        }else{
//            return "http://192.168.100.156:8090/travel-framework/";//测试地址
            return "http://192.168.100.162:8080/";//本地 //18826420934  //941740
//            return "http://192.168.100.246:8088/travel-framework/";//测试地址

        }
    }
}
