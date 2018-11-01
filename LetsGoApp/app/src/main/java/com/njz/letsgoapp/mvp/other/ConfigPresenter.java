package com.njz.letsgoapp.mvp.other;

import android.content.Context;

import com.njz.letsgoapp.bean.EmptyModel;
import com.njz.letsgoapp.bean.other.ConfigModel;
import com.njz.letsgoapp.util.http.MethodApi;
import com.njz.letsgoapp.util.http.OnSuccessAndFaultSub;
import com.njz.letsgoapp.util.http.ResponseCallback;

import java.util.List;

/**
 * Created by LGQ
 * Time: 2018/11/1
 * Function:
 */

public class ConfigPresenter implements ConfigContract.Presenter {

    Context context;
    ConfigContract.View iView;

    public ConfigPresenter(Context context, ConfigContract.View iView) {
        this.context = context;
        this.iView = iView;
    }

    @Override
    public void guideGetGuideMacros(List<String> values) {
        if(values == null || values.size() == 0) return;
        ResponseCallback listener = new ResponseCallback<List<ConfigModel>>() {
            @Override
            public void onSuccess(List<ConfigModel> data) {
                iView.guideGetGuideMacrosSuccess(data);
            }

            @Override
            public void onFault(String errorMsg) {
                iView.guideGetGuideMacrosFailed(errorMsg);
            }
        };
        StringBuffer sb = new StringBuffer();
        for (String str:values){
            sb.append(str+",");
        }
        String sbStr = sb.toString();
        if(sbStr.endsWith(",")){
            sbStr = sbStr.substring(0,sbStr.length()-1);
        }
        MethodApi.guideGetGuideMacros(sbStr, new OnSuccessAndFaultSub(listener, context,false));
    }
}
