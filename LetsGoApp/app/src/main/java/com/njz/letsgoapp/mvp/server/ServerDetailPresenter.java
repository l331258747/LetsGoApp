package com.njz.letsgoapp.mvp.server;

import android.content.Context;

import com.njz.letsgoapp.bean.server.ServerDetailModel;
import com.njz.letsgoapp.util.http.MethodApi;
import com.njz.letsgoapp.util.http.OnSuccessAndFaultSub;
import com.njz.letsgoapp.util.http.ResponseCallback;

/**
 * Created by LGQ
 * Time: 2018/12/5
 * Function:
 */

public class ServerDetailPresenter implements ServerDetailContract.Presenter {

    Context context;
    ServerDetailContract.View iView;

    public ServerDetailPresenter(Context context, ServerDetailContract.View iView) {
        this.context = context;
        this.iView = iView;
    }

    @Override
    public void serveGuideServeOrder(int id) {
        ResponseCallback listener = new ResponseCallback<ServerDetailModel>() {
            @Override
            public void onSuccess(ServerDetailModel data) {
                iView.serveGuideServeOrderSuccess(data);
            }

            @Override
            public void onFault(String errorMsg) {
                iView.serveGuideServeOrderFailed(errorMsg);
            }
        };
        MethodApi.serveGuideServeOrder(id, new OnSuccessAndFaultSub(listener, context));
    }
}
