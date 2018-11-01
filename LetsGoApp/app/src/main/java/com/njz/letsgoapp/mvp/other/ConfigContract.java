package com.njz.letsgoapp.mvp.other;

import com.njz.letsgoapp.bean.EmptyModel;
import com.njz.letsgoapp.bean.other.ConfigModel;

import java.util.List;

/**
 * Created by LGQ
 * Time: 2018/11/1
 * Function:
 */

public interface ConfigContract {

    interface Presenter {
        void guideGetGuideMacros(List<String> values);
    }

    interface View {
        void guideGetGuideMacrosSuccess(List<ConfigModel> models);

        void guideGetGuideMacrosFailed(String msg);
    }

}
