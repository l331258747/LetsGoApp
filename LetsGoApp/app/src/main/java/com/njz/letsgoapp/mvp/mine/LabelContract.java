package com.njz.letsgoapp.mvp.mine;

import com.njz.letsgoapp.bean.EmptyModel;
import com.njz.letsgoapp.bean.mine.LabelModel;
import com.njz.letsgoapp.bean.mine.MyInfoData;

import java.util.List;

/**
 * Created by LGQ
 * Time: 2018/9/5
 * Function:
 */

public interface LabelContract {

    interface Presenter {
        void userLabels();
        void userChangePersonalData(MyInfoData maps);
    }

    interface View {
        void userLabelsSuccess(List<LabelModel> str);
        void userLabelsFailed(String msg);

        void userChangePersonalDataSuccess(String str);
        void userChangePersonalDataFailed(String msg);
    }
}
