package com.njz.letsgoapp.util.http;

import com.njz.letsgoapp.bean.BaseResponse;

/**
 * Created by LGQ
 * Time: 2018/7/18
 * Function: 网络请求成功失败接口
 */

public interface ResponseCallback {
    void onSuccess(BaseResponse result);

    void onFault(String errorMsg);

}
