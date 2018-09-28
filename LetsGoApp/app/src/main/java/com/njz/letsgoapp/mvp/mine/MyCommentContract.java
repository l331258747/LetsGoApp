package com.njz.letsgoapp.mvp.mine;

import com.njz.letsgoapp.bean.EmptyModel;
import com.njz.letsgoapp.bean.mine.MyCommentModel;

import java.util.List;

/**
 * Created by LGQ
 * Time: 2018/9/28
 * Function:
 */

public interface MyCommentContract {

    interface Presenter{
        void friendMyDiscuss(int type);

    }

    interface View{
        void friendMyDiscussSuccess(List<MyCommentModel> data);
        void friendMyDiscussFailed(String msg);

    }
}
