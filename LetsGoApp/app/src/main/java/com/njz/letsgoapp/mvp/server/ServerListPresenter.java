package com.njz.letsgoapp.mvp.server;

import android.content.Context;
import android.text.TextUtils;

import com.njz.letsgoapp.bean.EmptyModel;
import com.njz.letsgoapp.bean.server.PlayModel;
import com.njz.letsgoapp.bean.server.ServerDetailMedel;
import com.njz.letsgoapp.constant.Constant;
import com.njz.letsgoapp.util.http.MethodApi;
import com.njz.letsgoapp.util.http.OnSuccessAndFaultSub;
import com.njz.letsgoapp.util.http.ResponseCallback;

import java.util.List;

/**
 * Created by LGQ
 * Time: 2018/12/5
 * Function:
 */

public class ServerListPresenter implements ServerListContract.Presenter{

    Context context;
    ServerListContract.View iView;

    public ServerListPresenter(Context context, ServerListContract.View iView) {
        this.context = context;
        this.iView = iView;
    }

    @Override
    public void serveGuideServeOrderList(int serveType, int limit, int page,String address,int mustPlay,int guideId,int guideServeId) {
        ResponseCallback listener = new ResponseCallback<List<ServerDetailMedel>>() {
            @Override
            public void onSuccess(List<ServerDetailMedel> data) {
                iView.serveGuideServeOrderListSuccess(data);
            }

            @Override
            public void onFault(String errorMsg) {
                iView.serveGuideServeOrderListFailed(errorMsg);
            }
        };
        address = TextUtils.equals(Constant.DEFAULT_CITY,address)?"":address;
        MethodApi.serveGuideServeOrderList(serveType,limit, page,address,mustPlay,guideId, guideServeId,new OnSuccessAndFaultSub(listener, context,false));
    }
}
