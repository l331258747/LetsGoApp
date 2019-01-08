package com.njz.letsgoapp.mvp.find;

import android.content.Context;

import com.njz.letsgoapp.bean.BasePageModel;
import com.njz.letsgoapp.bean.EmptyModel;
import com.njz.letsgoapp.bean.home.DynamicListModel;
import com.njz.letsgoapp.bean.home.DynamicModel;
import com.njz.letsgoapp.bean.home.EvaluateModel2;
import com.njz.letsgoapp.util.http.MethodApi;
import com.njz.letsgoapp.util.http.OnSuccessAndFaultSub;
import com.njz.letsgoapp.util.http.ResponseCallback;

import java.util.List;

/**
 * Created by LGQ
 * Time: 2018/9/4
 * Function:
 */

public class FindPresenter implements FindContract.Presenter {

    Context context;
    FindContract.View iView;

    public FindPresenter(Context context, FindContract.View iView) {
        this.context = context;
        this.iView = iView;
    }

    @Override
    public void friendFindAll(String location, int limit, int page,String str) {
        ResponseCallback listener = new ResponseCallback<DynamicListModel>() {
            @Override
            public void onSuccess(DynamicListModel data) {
                iView.friendFindAllSuccess(data);
            }

            @Override
            public void onFault(String errorMsg) {
                iView.friendFindAllFailed(errorMsg);
            }
        };
        MethodApi.friendFindAll(location,limit,page,str, new OnSuccessAndFaultSub(listener,context,false));
    }

    @Override
    public void friendFriendSter(int limit, int page) {
        ResponseCallback listener = new ResponseCallback<List<DynamicModel>>() {
            @Override
            public void onSuccess(List<DynamicModel> data) {
                iView.friendFriendSterSuccess(data);
            }

            @Override
            public void onFault(String errorMsg) {
                iView.friendFriendSterFailed(errorMsg);
            }
        };
        MethodApi.friendFriendSter(limit,page, new OnSuccessAndFaultSub(listener,context,false));
    }

    @Override
    public void friendSterSortByLikeAndReview(String location, int limit, int page, String content) {
        ResponseCallback listener = new ResponseCallback<BasePageModel<DynamicModel>>() {
            @Override
            public void onSuccess(BasePageModel<DynamicModel> data) {
                iView.friendSterSortByLikeAndReviewSuccess(data.getList());
            }

            @Override
            public void onFault(String errorMsg) {
                iView.friendSterSortByLikeAndReviewFailed(errorMsg);
            }
        };
        MethodApi.friendSterSortByLikeAndReview(location,limit,page,content, new OnSuccessAndFaultSub(listener,context,false));
    }
}
