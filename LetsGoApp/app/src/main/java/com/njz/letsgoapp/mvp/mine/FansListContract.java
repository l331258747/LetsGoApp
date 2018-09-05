package com.njz.letsgoapp.mvp.mine;

import com.njz.letsgoapp.bean.EmptyModel;

/**
 * Created by LGQ
 * Time: 2018/9/5
 * Function:
 */

public interface FansListContract {

    interface Presenter{
        void userFindFans(int type, int limit,int page);
    }

    interface View{
        void userFindFansSuccess(EmptyModel data);
        void userFindFansFailed(String msg);
    }
}
