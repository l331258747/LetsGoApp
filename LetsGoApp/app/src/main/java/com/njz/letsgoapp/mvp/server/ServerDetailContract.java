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

public interface ServerDetailContract {

    interface Presenter {
        void serveGuideServeOrder(int id);

    }

    interface View {
        void serveGuideServeOrderSuccess(ServerDetailMedel str);

        void serveGuideServeOrderFailed(String msg);

    }
}
