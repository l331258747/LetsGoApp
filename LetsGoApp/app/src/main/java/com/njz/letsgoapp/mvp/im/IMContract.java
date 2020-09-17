package com.njz.letsgoapp.mvp.im;

import com.njz.letsgoapp.bean.EmptyModel;

/**
 * Created by LGQ
 * Time: 2018/10/12
 * Function:
 */

public interface IMContract {

    interface Presenter {
        void saveMessage(String fromId,String toId,String chatType,String msg);
    }

    interface View{
        void saveMessageSuccess(EmptyModel model);
        void saveMessageFailed(String msg);
    }
}
