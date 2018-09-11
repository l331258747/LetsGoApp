package com.njz.letsgoapp.mvp.find;

import com.njz.letsgoapp.bean.home.DynamicListModel;
import com.njz.letsgoapp.bean.home.DynamicModel;

import java.util.List;

/**
 * Created by LGQ
 * Time: 2018/9/4
 * Function:
 */

public interface FindContract {

    interface Presenter {
        void friendFindAll(String location,int limit,int page);
        void friendFriendSter(int limit,int page);
    }

    interface View {
        void friendFindAllSuccess(DynamicListModel models);
        void friendFindAllFailed(String msg);

        void friendFriendSterSuccess(List<DynamicModel> models);
        void friendFriendSterFailed(String msg);
    }
}
