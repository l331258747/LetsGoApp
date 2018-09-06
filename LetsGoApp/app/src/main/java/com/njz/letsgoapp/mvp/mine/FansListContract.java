package com.njz.letsgoapp.mvp.mine;

import com.njz.letsgoapp.bean.EmptyModel;
import com.njz.letsgoapp.bean.mine.FansListModel;

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
        void userFindFansSuccess(FansListModel data);
        void userFindFansFailed(String msg);
    }
}
