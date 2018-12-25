package com.njz.letsgoapp.mvp.server;

import com.njz.letsgoapp.bean.server.ServerDetailModel;

import java.util.List;

/**
 * Created by LGQ
 * Time: 2018/12/5
 * Function:
 */

public interface ServerListContract {

    interface Presenter {
        void serveGuideServeOrderList(int serveType,int limit,int page,String address,int mustPlay,int guideId,int guideServeId);

    }

    interface View {
        void serveGuideServeOrderListSuccess(List<ServerDetailModel> str);

        void serveGuideServeOrderListFailed(String msg);

    }
}
