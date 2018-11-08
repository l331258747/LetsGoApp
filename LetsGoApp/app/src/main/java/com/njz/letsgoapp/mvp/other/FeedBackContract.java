package com.njz.letsgoapp.mvp.other;

import com.njz.letsgoapp.bean.EmptyModel;

import java.util.List;

/**
 * Created by LGQ
 * Time: 2018/11/8
 * Function:
 */

public interface FeedBackContract {

    interface Presenter {
        void upUserIdea(String mobile, String content, List<String> files);
    }

    interface View {
        void upUserIdeaSuccess(EmptyModel models);
        void upUserIdeaFailed(String msg);
    }

}
