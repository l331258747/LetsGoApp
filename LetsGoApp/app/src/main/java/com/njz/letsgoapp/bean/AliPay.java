package com.njz.letsgoapp.bean;

/**
 * Created by LGQ
 * Time: 2018/8/2
 * Function:
 */

public class AliPay {


    /**
     * code : 0
     * message : null
     * data : alipay_sdk=alipay-sdk-java-3.0.52.ALL&app_id=2018071760720301&biz_content=%7B%22body%22%3A%22%E6%88%91%E6%98%AF%E6%B5%8B%E8%AF%95%E6%95%B0%E6%8D%AE-By+Javen%22%2C%22out_trade_no%22%3A%22080214405415331%22%2C%22passback_params%22%3A%22callback+params%22%2C%22product_code%22%3A%22QUICK_MSECURITY_PAY%22%2C%22subject%22%3A%22App%E6%94%AF%E4%BB%98%E6%B5%8B%E8%AF%95-By+Javen%22%2C%22timeout_express%22%3A%2230m%22%2C%22total_amount%22%3A%220.01%22%7D&charset=UTF-8&format=json&method=alipay.trade.app.pay&notify_url=http%3A%2F%2Fnajiuzou.uicp.net%3A26641%2Falipay%2Fnotify_url&sign=GYEVahhFLSD1JAQN4O6RlL1CpAbyeTWaH3oJJxbatAmhcaAfZc4m8CmdxXoZpNb8NQL2v0xWMwZYvm8EW0fvYKvkHaPLE%2BqDATFbhz1kocJzLzmr77Sk7Gvm76Giss4Lgk3auyfOf0IE%2BPztA3wh861vjRO7ZIx7DQch908ZxlHWmS%2FiOFpfIvKXGhyD7UM6k%2BVu8dXfh18j6lgi%2Fvfa9jBq5DUjRHGuoykiUmyrDaOoCavPUCV25eGv7%2BT4rFCMILAhMaNKWRrLcyfDYoPrSXw3%2BDgexeBztDCVf9vwDHq0C4rwKrzaNfVuhrIbAU%2FSeVMJsgwRoWwUUmHLgRmf5w%3D%3D&sign_type=RSA2&timestamp=2018-08-02+14%3A40%3A54&version=1.0
     */

    private int code;
    private Object message;
    private String data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Object getMessage() {
        return message;
    }

    public void setMessage(Object message) {
        this.message = message;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
