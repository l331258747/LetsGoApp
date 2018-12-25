package com.njz.letsgoapp.mvp.server;

import com.njz.letsgoapp.bean.server.ServerDetailModel;

import java.util.List;
import java.util.Map;

/**
 * Created by LGQ
 * Time: 2018/12/7
 * Function:
 */

public interface ServerListScreenContract {

    interface Presenter {
        void serveGuideServeFilterList(int serveType, int limit, int page, String address, int mustPlay, int guideId, int guideServeId, int order,Map<String,String> maps);

    }

    interface View {
        void serveGuideServeFilterListSuccess(List<ServerDetailModel> str);

        void serveGuideServeFilterListFailed(String msg);

    }

}
