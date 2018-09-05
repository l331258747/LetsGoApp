package com.njz.letsgoapp.mvp.find;

import com.njz.letsgoapp.bean.EmptyModel;

import java.util.List;

/**
 * Created by LGQ
 * Time: 2018/9/5
 * Function:
 */

public interface ReleaseDynamicContract {

    //sendSter
    interface Presenter {
        void sendSter(String content, List<String> files);
    }

    interface View {
        void sendSterSuccess(EmptyModel models);
        void sendSterFailed(String msg);
    }
}
