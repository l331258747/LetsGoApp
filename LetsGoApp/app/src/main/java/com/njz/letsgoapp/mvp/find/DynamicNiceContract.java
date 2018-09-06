package com.njz.letsgoapp.mvp.find;

import com.njz.letsgoapp.bean.EmptyModel;

/**
 * Created by LGQ
 * Time: 2018/9/6
 * Function:
 */

public interface DynamicNiceContract {

    interface Presenter {
        void friendQueryLikes(boolean isNick, int friendSterId);
    }

    interface View {
        void friendQueryLikesSuccess(EmptyModel models);
        void friendQueryLikesFailed(String msg);
    }
}
