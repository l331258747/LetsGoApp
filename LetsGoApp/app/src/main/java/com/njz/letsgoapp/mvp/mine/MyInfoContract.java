package com.njz.letsgoapp.mvp.mine;

import com.njz.letsgoapp.bean.EmptyModel;
import com.njz.letsgoapp.bean.mine.MyInfoData;

import java.io.File;

/**
 * Created by LGQ
 * Time: 2018/9/4
 * Function:
 */

public interface MyInfoContract {

    interface Presenter {
        void userChangePersonalData(MyInfoData maps, boolean showDialog);
        void upUpload(File file);
    }

    interface View {
        void userChangePersonalDataSuccess(String str);
        void userChangePersonalDataFailed(String msg);

        void upUploadSuccess(String str);
        void upUploadFailed(String msg);
    }
}
