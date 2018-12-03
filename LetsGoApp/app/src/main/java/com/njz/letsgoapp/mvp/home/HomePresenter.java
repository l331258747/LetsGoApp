package com.njz.letsgoapp.mvp.home;

import android.content.Context;

import com.njz.letsgoapp.bean.EmptyModel;
import com.njz.letsgoapp.bean.home.BannerModel;
import com.njz.letsgoapp.bean.home.DynamicListModel;
import com.njz.letsgoapp.bean.home.GuideListModel;
import com.njz.letsgoapp.bean.home.GuideModel;
import com.njz.letsgoapp.bean.home.NoticeItem;
import com.njz.letsgoapp.bean.other.ProvinceModel;
import com.njz.letsgoapp.mvp.home.HomeContract.Presenter;
import com.njz.letsgoapp.util.http.MethodApi;
import com.njz.letsgoapp.util.http.OnSuccessAndFaultSub;
import com.njz.letsgoapp.util.http.ResponseCallback;

import java.util.List;

/**
 * Created by LGQ
 * Time: 2018/8/28
 * Function:
 */

public class HomePresenter implements Presenter {

    Context context;
    HomeContract.View iView;

    public HomePresenter(Context context, HomeContract.View iView) {
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
    public void orderReviewsSortTop(String location) {
        ResponseCallback listener = new ResponseCallback<GuideListModel>() {
            @Override
            public void onSuccess(GuideListModel data) {
                iView.orderReviewsSortTopSuccess(data);
            }

            @Override
            public void onFault(String errorMsg) {
                iView.orderReviewsSortTopFailed(errorMsg);
            }
        };
        MethodApi.orderReviewsSortTop(location, new OnSuccessAndFaultSub(listener,context,false));
    }

//    @Override
//    public void friendFindAll(String location, int limit, int page) {
//        ResponseCallback listener = new ResponseCallback<DynamicListModel>() {
//            @Override
//            public void onSuccess(DynamicListModel data) {
//                iView.friendFriendSterTopSuccess(data);
//            }
//
//            @Override
//            public void onFault(String errorMsg) {
//                iView.friendFriendSterTopFailed(errorMsg);
//            }
//        };
//        MethodApi.friendFriendSterTop(location,limit,page, new OnSuccessAndFaultSub(listener,context,false));
//    }

    @Override
    public void orderCarouselOrder() {
        ResponseCallback listener = new ResponseCallback<List<NoticeItem>>() {
            @Override
            public void onSuccess(List<NoticeItem> data) {
                iView.orderCarouselOrderSuccess(data);
            }

            @Override
            public void onFault(String errorMsg) {
                iView.orderCarouselOrderFailed(errorMsg);
            }
        };
        MethodApi.orderCarouselOrder(new OnSuccessAndFaultSub(listener,context,false));
    }
}
