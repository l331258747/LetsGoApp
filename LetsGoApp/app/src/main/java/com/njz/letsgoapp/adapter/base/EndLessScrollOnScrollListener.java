package com.njz.letsgoapp.adapter.base;

import android.support.v4.widget.NestedScrollView;

import com.njz.letsgoapp.util.log.LogUtil;

/**
 * Created by LGQ
 * Time: 2018/10/16
 * Function:
 */

public abstract class EndLessScrollOnScrollListener implements NestedScrollView.OnScrollChangeListener {
    @Override
    public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {

        onScrollChange(scrollY);

        if (scrollY == 0) {
            LogUtil.e("TOP SCROLL");
        }

        if (scrollY == (v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight())) {
            LogUtil.e("BOTTOM SCROLL");
            onLoadMore();
        }
    }

    /**
     * 加载更多回调
     */
    public abstract void onLoadMore();
    public abstract void onScrollChange(int scrollY);

}
