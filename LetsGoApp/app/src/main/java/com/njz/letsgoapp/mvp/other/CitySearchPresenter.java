package com.njz.letsgoapp.mvp.other;

import android.content.Context;

import com.njz.letsgoapp.bean.other.ProvinceModel;
import com.njz.letsgoapp.bean.other.SearchCityModel;
import com.njz.letsgoapp.util.http.MethodApi;
import com.njz.letsgoapp.util.http.OnSuccessAndFaultSub;
import com.njz.letsgoapp.util.http.ResponseCallback;

import java.util.List;

/**
 * Created by LGQ
 * Time: 2018/10/12
 * Function:
 */

public class CitySearchPresenter implements CitySearchContract.Presenter {

    Context context;
    CitySearchContract.View iView;

    public CitySearchPresenter(Context context, CitySearchContract.View iView) {
        this.context = context;
        this.iView = iView;
    }

    @Override
    public void regionFuzzyBySpell(String spell) {
        ResponseCallback listener = new ResponseCallback<List<SearchCityModel>>() {
            @Override
            public void onSuccess(List<SearchCityModel> data) {
                iView.regionFuzzyBySpellSuccess(data);
            }

            @Override
            public void onFault(String errorMsg) {
                iView.regionFuzzyBySpellFailed(errorMsg);
            }
        };
        MethodApi.regionFuzzyBySpell(spell,new OnSuccessAndFaultSub(listener,context));
    }
}
