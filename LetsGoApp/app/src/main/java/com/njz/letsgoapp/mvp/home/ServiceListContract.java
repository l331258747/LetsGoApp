package com.njz.letsgoapp.mvp.home;

import com.njz.letsgoapp.bean.EmptyModel;
import com.njz.letsgoapp.bean.home.ServiceListModel;

import java.util.List;

/**
 * Created by LGQ
 * Time: 2018/8/31
 * Function:
 */

public interface ServiceListContract {

    interface Presenter {
        void getServiceList(int guideId,String serviceType);
    }

    interface View {
        void getServiceListSuccess(List<ServiceListModel> models);
        void getServiceListFailed(String msg);

    }
}
