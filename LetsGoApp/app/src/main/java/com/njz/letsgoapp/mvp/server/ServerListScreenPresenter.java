package com.njz.letsgoapp.mvp.server;

import android.content.Context;
import android.text.TextUtils;

import com.njz.letsgoapp.bean.server.ServerDetailMedel;
import com.njz.letsgoapp.constant.Constant;
import com.njz.letsgoapp.util.http.MethodApi;
import com.njz.letsgoapp.util.http.OnSuccessAndFaultSub;
import com.njz.letsgoapp.util.http.ResponseCallback;

import java.util.List;
import java.util.Map;

/**
 * Created by LGQ
 * Time: 2018/12/7
 * Function:
 */

public class ServerListScreenPresenter  implements ServerListScreenContract.Presenter{

    Context context;
    ServerListScreenContract.View iView;

    public ServerListScreenPresenter(Context context, ServerListScreenContract.View iView) {
        this.context = context;
        this.iView = iView;
    }

    @Override
    public void serveGuideServeFilterList(int serveType, int limit, int page, String address, int mustPlay, int guideId, int guideServeId, int order,Map<String, String> maps) {
        ResponseCallback listener = new ResponseCallback<List<ServerDetailMedel>>() {
            @Override
            public void onSuccess(List<ServerDetailMedel> data) {
                iView.serveGuideServeFilterListSuccess(data);
            }

            @Override
            public void onFault(String errorMsg) {
                iView.serveGuideServeFilterListFailed(errorMsg);
            }
        };
        address = TextUtils.equals(Constant.DEFAULT_CITY,address)?"":address;
        MethodApi.serveGuideServeFilterList(serveType,limit, page,address,mustPlay,guideId, guideServeId,order,maps,new OnSuccessAndFaultSub(listener, context,false));

    }
}
