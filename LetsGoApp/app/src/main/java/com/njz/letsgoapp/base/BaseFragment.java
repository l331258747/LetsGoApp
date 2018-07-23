package com.njz.letsgoapp.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import com.njz.letsgoapp.util.ToastUtil;

/**
 * Created by Administrator on 2017\10\12 0012.
 */

public abstract  class BaseFragment extends Fragment {

    public BaseActivity activity;
    public Context context;
    private View view;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
        activity = (BaseActivity) getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(getLayoutId(), container, false);
        initView();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
    }

    /**
     * 动态隐藏软键盘
     */
    public void hideSoftInput() {
        View view = activity.getCurrentFocus();
        if (view == null) view = new View(activity);
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        if (imm == null) return;
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
    //---------------init start--------------------
    public abstract int getLayoutId();

    public abstract void initView();

    public abstract void initData();

    public <T extends View> T $(int id) {
        return (T) view.findViewById(id);
    }
    public <T extends View> T $(View view, int id) {
        return (T) view.findViewById(id);
    }

    public void showShortToast(String msg) {
        ToastUtil.showShortToast(context, msg);
    }

    public void showLongToast(String msg) {
        ToastUtil.showLongToast(context, msg);
    }

    public int getResColor(int resId) {
        return getResources().getColor(resId);
    }

    //----------------------init end--------------------------------------
}
