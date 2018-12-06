package com.njz.letsgoapp.mvp.server;

import com.njz.letsgoapp.bean.EmptyModel;
import com.njz.letsgoapp.bean.server.PlayModel;
import com.njz.letsgoapp.bean.server.ServerDetailMedel;

import java.util.List;

/**
 * Created by LGQ
 * Time: 2018/12/5
 * Function:
 */

public interface ServerListContract {

    interface Presenter {
        void serveGuideServeOrderList(String serveTypeName,int limit,int page,String address,int mustPlay,int guideId,int guideServeId);

    }

    interface View {
        void serveGuideServeOrderListSuccess(List<ServerDetailMedel> str);

        void serveGuideServeOrderListFailed(String msg);

    }
}
