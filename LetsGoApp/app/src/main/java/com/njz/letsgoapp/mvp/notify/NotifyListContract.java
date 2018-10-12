package com.njz.letsgoapp.mvp.notify;

import com.njz.letsgoapp.bean.notify.NotifyMainModel;

import java.util.List;

/**
 * Created by LGQ
 * Time: 2018/10/12
 * Function:
 */

public interface NotifyListContract {

    interface Presenter {
        void msgPushGetReceiveMsgList(String type, int limit,int page);
    }

    interface View{
        void msgPushGetReceiveMsgListSuccess(List<NotifyMainModel> data);
        void msgPushGetReceiveMsgListFailed(String msg);
    }
}
