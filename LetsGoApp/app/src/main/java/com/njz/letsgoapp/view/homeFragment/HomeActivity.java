package com.njz.letsgoapp.view.homeFragment;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.njz.letsgoapp.R;
import com.njz.letsgoapp.base.BaseActivity;
import com.njz.letsgoapp.base.BaseFragment;
import com.njz.letsgoapp.bean.MySelfInfo;
import com.njz.letsgoapp.bean.notify.NotifyMainModel;
import com.njz.letsgoapp.constant.Constant;
import com.njz.letsgoapp.mvp.notify.NotifyMainContract;
import com.njz.letsgoapp.mvp.notify.NotifyMainPresenter;
import com.njz.letsgoapp.util.AppUtils;
import com.njz.letsgoapp.util.log.LogUtil;
import com.njz.letsgoapp.util.rxbus.RxBus2;
import com.njz.letsgoapp.util.rxbus.busEvent.NotifyEvent;
import com.njz.letsgoapp.view.homeFragment.fragment.FindFragment;
import com.njz.letsgoapp.view.homeFragment.fragment.HomeFragment;
import com.njz.letsgoapp.view.homeFragment.fragment.MyFragment;
import com.njz.letsgoapp.view.homeFragment.fragment.NotifyFragment;
import com.njz.letsgoapp.view.homeFragment.fragment.OrderFragment;
import com.njz.letsgoapp.widget.tab.TabItem;
import com.njz.letsgoapp.widget.tab.TabLayout;
import com.njz.letsgoapp.widget.tab.TabView;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by LGQ
 * Time: 2018/7/23
 * Function:
 */

public class HomeActivity extends BaseActivity implements TabLayout.OnTabClickListener,NotifyMainContract.View {


    private TabLayout tabLayout;
    private ArrayList<TabItem> tabItems;

    private Class[] fragmentCls = new Class[5];
    private Fragment[] fragments = new Fragment[5];

//    LinearLayout linear_bar;

    Disposable notifyDisposable;

    private NotifyMainPresenter mPresenter;

    //----------沉浸式 start ----------
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//
//            linear_bar = (LinearLayout) findViewById(R.id.ll_bar);
//            //获取到状态栏的高度
//            int statusHeight = AppUtils.getStateBar();
//            //动态的设置隐藏布局的高度
//            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) linear_bar.getLayoutParams();
//            params.height = statusHeight;
//            linear_bar.setLayoutParams(params);
//        }
    }

//    public void setMystatusViewShow(boolean isShow) {
//        if (linear_bar == null)
//            return;
//        linear_bar.setVisibility(isShow ? View.VISIBLE : View.GONE);
//    }

    //----------沉浸式 end ----------


    @Override
    public int getLayoutId() {
        return R.layout.activity_home;
    }

    @Override
    public void initView() {
        tabLayout = $(R.id.cus_tab_layout);

    }

    @Override
    public void initData() {
        DisplayMetrics metric = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metric);
        float density = metric.density;  // 屏幕密度（0.75 / 1.0 / 1.5）
        int densityDpi = metric.densityDpi;
        LogUtil.e("density=" + density + ",densityDpi=" + densityDpi);

        MySelfInfo.getInstance().setDefaultCity(TextUtils.isEmpty(MySelfInfo.getInstance().getDefaultCity())?Constant.DEFAULT_CITY : MySelfInfo.getInstance().getDefaultCity());


        // 初始化页面
        try {
            fragmentCls[0] = HomeFragment.class;
            fragmentCls[1] = FindFragment.class;
            fragmentCls[2] = OrderFragment.class;
            fragmentCls[3] = NotifyFragment.class;
            fragmentCls[4] = MyFragment.class;

            fragments[0] = (BaseFragment) fragmentCls[0].newInstance();
            fragments[1] = (BaseFragment) fragmentCls[1].newInstance();
            fragments[2] = (BaseFragment) fragmentCls[2].newInstance();
            fragments[3] = (BaseFragment) fragmentCls[3].newInstance();
            fragments[4] = (BaseFragment) fragmentCls[4].newInstance();

        } catch (Exception e) {
            e.printStackTrace();
        }

        tabItems = new ArrayList<>();
        tabItems.add(new TabItem(R.drawable.tab_home, R.string.home, 0, fragmentCls[0]));
        tabItems.add(new TabItem(R.drawable.tab_find, R.string.home_find, 0, fragmentCls[1]));
        tabItems.add(new TabItem(R.drawable.tab_order, R.string.home_order, 0, fragmentCls[2]));
        tabItems.add(new TabItem(R.drawable.tab_notify, R.string.home_notify, 0, fragmentCls[3]));
        tabItems.add(new TabItem(R.drawable.tab_mine, R.string.home_my, 0, fragmentCls[4]));

        tabLayout.initData(tabItems, this);

        // 点击事件处理
        onTabItemClick(tabItems.get(0));

        //小红点处理
        notifyDisposable = RxBus2.getInstance().toObservable(NotifyEvent.class, new Consumer<NotifyEvent>() {
            @Override
            public void accept(NotifyEvent notifyEvent) throws Exception {
                changeTabBadge(notifyEvent.isShowTips()?-1:0);
            }
        });

        mPresenter = new NotifyMainPresenter(context,this);
        if(MySelfInfo.getInstance().isLogin()){
            mPresenter.msgPushGetSendMsgList();
        }
    }

    public void setTabIndex(int i){
        onTabItemClick(tabItems.get(i));
    }

    public OrderFragment getOrderFragment(){
        return (OrderFragment) fragments[2];
    }

    //防止fragment混淆
    @Override
    protected void onSaveInstanceState(Bundle outState) {
//		super.onSaveInstanceState(outState);
    }

    //设置标题
    public void setTitleSingle(boolean showTitle, String title) {
        if (showTitle) {
            showTitleLayout();
            showTitleTv();
            getTitleTv().setText(title);
        } else {
            hideTitleLayout();
        }
    }

    /**
     * @param badgeNum
     */
    public void changeTabBadge(int badgeNum) {
        TabView tabView = tabLayout.getTabView();
        if (null != tabView) {
            if (badgeNum > 0) {
                tabView.setDotNum(-1);
            } else {
                tabView.setDotNum(badgeNum);
            }
        }
        tabView.requestFocus();
        tabView.invalidate();
    }

    @Override
    public void onTabItemClick(TabItem tabItem) {
        int index = tabItems.indexOf(tabItem);
        switch (index) {
            case 0:
                setTitleSingle(false, getResString(R.string.home));
                break;
            case 1:
                setTitleSingle(false, getResString(R.string.home_find));
                break;
            case 2:
                setTitleSingle(true, getResString(R.string.home_order_2));
                break;
            case 3:
                setTitleSingle(true, getResString(R.string.home_notify_2));
                RxBus2.getInstance().post(new NotifyEvent(false));
                break;
            case 4:
                setTitleSingle(false, getResString(R.string.home_my));
                break;
        }

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        for (Fragment fragment : fragments) {
            if (fragment.isAdded()) {
                transaction.hide(fragment);
            }
        }

        try {
            tabLayout.setTabSelect(index);

            if (fragments[index].isAdded()) {
                transaction.show(fragments[index]).commitAllowingStateLoss();
            } else {
                transaction.add(R.id.cus_tab_fragment, fragments[index]).commitAllowingStateLoss();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if (keyCode == KeyEvent.KEYCODE_BACK) {
//            Intent intent = new Intent(Intent.ACTION_MAIN);
//            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            intent.addCategory(Intent.CATEGORY_HOME);
//            startActivity(intent);
//            return true;
//        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(notifyDisposable !=null && !notifyDisposable.isDisposed())
            notifyDisposable.dispose();
    }

    @Override
    public void msgPushGetSendMsgListSuccess(List<NotifyMainModel> data) {
        if(data == null || data.size() == 0){
            RxBus2.getInstance().post(new NotifyEvent(false));
        }else{
            RxBus2.getInstance().post(new NotifyEvent(true));
        }
    }

    @Override
    public void msgPushGetSendMsgListFailed(String msg) {
        LogUtil.e(msg);
    }
}
