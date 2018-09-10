package com.njz.letsgoapp.mvp.mine;

import android.content.Context;

import com.njz.letsgoapp.bean.EmptyModel;
import com.njz.letsgoapp.bean.mine.FansListModel;
import com.njz.letsgoapp.util.http.MethodApi;
import com.njz.letsgoapp.util.http.OnSuccessAndFaultSub;
import com.njz.letsgoapp.util.http.ResponseCallback;

/**
 * Created by LGQ
 * Time: 2018/9/5
 * Function:
 */

public class FansListPresenter implements FansListContract.Presenter {

    Context context;
    FansListContract.View iView;

    public FansListPresenter(Context context, FansListContract.View iView) {
        this.context = context;
        this.iView = iView;
    }

    @Override
    public void userFindFans(int type, int userId,int limit, int page) {
        if(type == 0){
            ResponseCallback listener = new ResponseCallback<FansListModel>() {
                @Override
                public void onSuccess(FansListModel data) {
                    iView.userFindFansSuccess(data);
                }

                @Override
                public void onFault(String errorMsg) {
                    iView.userFindFansFailed(errorMsg);
                }
            };
            MethodApi.userFindFans(userId,limit, page,new OnSuccessAndFaultSub(listener));
        }else{
            ResponseCallback listener = new ResponseCallback<FansListModel>() {
                @Override
                public void onSuccess(FansListModel data) {
                    iView.userFindFansSuccess(data);
                }

                @Override
                public void onFault(String errorMsg) {
                    iView.userFindFansFailed(errorMsg);
                }
            };
            MethodApi.userFindFocus(userId,limit, page,new OnSuccessAndFaultSub(listener));
        }



    }
}
