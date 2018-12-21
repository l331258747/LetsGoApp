package com.njz.letsgoapp.mvp.home;

import android.content.Context;
import android.text.TextUtils;

import com.njz.letsgoapp.bean.EmptyModel;
import com.njz.letsgoapp.bean.home.BannerModel;
import com.njz.letsgoapp.bean.home.GuideDetailModel;
import com.njz.letsgoapp.constant.Constant;
import com.njz.letsgoapp.util.http.MethodApi;
import com.njz.letsgoapp.util.http.OnSuccessAndFaultSub;
import com.njz.letsgoapp.util.http.ResponseCallback;

import java.util.List;

/**
 * Created by LGQ
 * Time: 2018/8/29
 * Function:
 */

public class GuideDetailPresenter implements GuideDetailContract.Presenter {

    Context context;
    GuideDetailContract.View iView;

    public GuideDetailPresenter(Context context, GuideDetailContract.View iView) {
        this.context = context;
        this.iView = iView;
    }

    @Override
    public void bannerFindByType(int type, int guideId) {
        ResponseCallback listener = new ResponseCallback<List<BannerModel>>() {
            @Override
            public void onSuccess(List<BannerModel> data) {
                iView.bannerFindByTypeSuccess(data);
            }

            @Override
            public void onFault(String errorMsg) {
                iView.bannerFindByTypeFailed(errorMsg);
            }
        };
        MethodApi.bannerFindByType(type, guideId, new OnSuccessAndFaultSub(listener,context,false));
    }

    @Override
    public void guideFindGuideDetails(String location, int guideId) {
        ResponseCallback listener = new ResponseCallback<GuideDetailModel>() {
            @Override
            public void onSuccess(GuideDetailModel data) {
                iView.guideFindGuideDetailsSuccess(data);
            }

            @Override
            public void onFault(String errorMsg) {
                iView.guideFindGuideDetailsFailed(errorMsg);
            }
        };
        MethodApi.guideFindGuideDetails(location, guideId, new OnSuccessAndFaultSub(listener,context));
    }

}
