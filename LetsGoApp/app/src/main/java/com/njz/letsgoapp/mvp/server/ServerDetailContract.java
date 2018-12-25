package com.njz.letsgoapp.mvp.server;

import com.njz.letsgoapp.bean.server.ServerDetailModel;

/**
 * Created by LGQ
 * Time: 2018/12/5
 * Function:
 */

public interface ServerDetailContract {

    interface Presenter {
        void serveGuideServeOrder(int id);

    }

    interface View {
        void serveGuideServeOrderSuccess(ServerDetailModel str);

        void serveGuideServeOrderFailed(String msg);

    }
}
