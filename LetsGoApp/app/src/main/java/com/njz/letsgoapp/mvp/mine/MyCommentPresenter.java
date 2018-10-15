package com.njz.letsgoapp.mvp.mine;

import android.content.Context;

import com.njz.letsgoapp.bean.EmptyModel;
import com.njz.letsgoapp.bean.mine.MyCommentModel;
import com.njz.letsgoapp.util.http.MethodApi;
import com.njz.letsgoapp.util.http.OnSuccessAndFaultSub;
import com.njz.letsgoapp.util.http.ResponseCallback;

import java.util.List;

/**
 * Created by LGQ
 * Time: 2018/9/28
 * Function:
 */

public class MyCommentPresenter implements MyCommentContract.Presenter {

    Context context;
    MyCommentContract.View iView;

    public MyCommentPresenter(Context context, MyCommentContract.View iView) {
        this.context = context;
        this.iView = iView;
    }

    @Override
    public void friendMyDiscuss(int type) {
        ResponseCallback listener = new ResponseCallback<List<MyCommentModel>>() {
            @Override
            public void onSuccess(List<MyCommentModel> data) {
                iView.friendMyDiscussSuccess(data);
            }

            @Override
            public void onFault(String errorMsg) {
                iView.friendMyDiscussFailed(errorMsg);
            }
        };
        MethodApi.friendMyDiscuss(type,new OnSuccessAndFaultSub(listener,context,false));
    }
}
