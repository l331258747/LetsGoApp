package com.njz.letsgoapp.mvp.order;

import com.njz.letsgoapp.bean.EmptyModel;

/**
 * Created by LGQ
 * Time: 2018/10/11
 * Function:
 */

public interface OrderDeleteContract {

    interface Presenter {
        void orderDeleteOrder(int id,int status);
    }

    interface View{
        void orderDeleteOrderSuccess(EmptyModel str);
        void orderDeleteOrderFailed(String msg);
    }
}
