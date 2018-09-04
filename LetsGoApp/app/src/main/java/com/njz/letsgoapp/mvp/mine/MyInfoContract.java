package com.njz.letsgoapp.mvp.mine;

import com.njz.letsgoapp.bean.mine.MyInfoData;

import java.util.Map;

/**
 * Created by LGQ
 * Time: 2018/9/4
 * Function:
 */

public interface MyInfoContract {

    interface Presenter {
        void userChangePersonalData(MyInfoData maps);
    }

    interface View {
        void userChangePersonalDataSuccess(String str);
        void userChangePersonalDataFailed(String msg);
    }
}
