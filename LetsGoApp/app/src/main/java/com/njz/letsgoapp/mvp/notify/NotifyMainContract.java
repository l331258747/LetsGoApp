package com.njz.letsgoapp.mvp.notify;

import com.njz.letsgoapp.bean.EmptyModel;

import java.util.List;

/**
 * Created by LGQ
 * Time: 2018/10/11
 * Function:
 */

public interface NotifyMainContract {
    interface Presenter {
        void msgPushGetSendMsgList();
    }

    interface View{
        void msgPushGetSendMsgListSuccess(List<EmptyModel> data);
        void msgPushGetSendMsgListFailed(String msg);
    }

}
