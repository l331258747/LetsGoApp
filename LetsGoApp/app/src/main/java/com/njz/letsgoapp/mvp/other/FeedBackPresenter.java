package com.njz.letsgoapp.mvp.other;

import android.content.Context;

import com.njz.letsgoapp.bean.EmptyModel;
import com.njz.letsgoapp.util.http.MethodApi;
import com.njz.letsgoapp.util.http.OnSuccessAndFaultSub;
import com.njz.letsgoapp.util.http.ResponseCallback;

import java.util.List;

/**
 * Created by LGQ
 * Time: 2018/11/8
 * Function:
 */

public class FeedBackPresenter implements FeedBackContract.Presenter {

    Context context;
    FeedBackContract.View iView;

    public FeedBackPresenter(Context context, FeedBackContract.View iView) {
        this.context = context;
        this.iView = iView;
    }


    @Override
    public void upUserIdea(String mobile, String content, List<String> files) {
        ResponseCallback listener = new ResponseCallback<EmptyModel>() {
            @Override
            public void onSuccess(EmptyModel data) {
                iView.upUserIdeaSuccess(data);
            }

            @Override
            public void onFault(String errorMsg) {
                iView.upUserIdeaFailed(errorMsg);
            }
        };
        MethodApi.upUserIdea(mobile,content,files,new OnSuccessAndFaultSub(listener,context,false));
    }
}
