package com.njz.letsgoapp.view.homeFragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.DisplayMetrics;
import android.view.KeyEvent;

import com.njz.letsgoapp.R;
import com.njz.letsgoapp.base.BaseActivity;
import com.njz.letsgoapp.base.BaseFragment;
import com.njz.letsgoapp.util.PreferencesUtils;
import com.njz.letsgoapp.util.log.LogUtil;
import com.njz.letsgoapp.view.homeFragment.fragment.FindFragment;
import com.njz.letsgoapp.view.homeFragment.fragment.HomeFragment;
import com.njz.letsgoapp.view.homeFragment.fragment.MyFragment;
import com.njz.letsgoapp.view.homeFragment.fragment.NotifyFragment;
import com.njz.letsgoapp.view.homeFragment.fragment.OrderFragment;
import com.njz.letsgoapp.widget.tab.TabItem;
import com.njz.letsgoapp.widget.tab.TabLayout;
import com.njz.letsgoapp.widget.tab.TabView;

import java.util.ArrayList;

/**
 * Created by LGQ
 * Time: 2018/7/23
 * Function:
 */

public class HomeActivity extends BaseActivity implements TabLayout.OnTabClickListener{


    private TabLayout tabLayout;
    private ArrayList<TabItem> tabItems;

    private Class[] fragmentCls = new Class[5];
    private Fragment[] fragments = new Fragment[5];

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
        tabItems.add(new TabItem(R.drawable.tab_notify, R.string.home_notify, -1, fragmentCls[3]));
        tabItems.add(new TabItem(R.drawable.tab_mine, R.string.home_my, 0, fragmentCls[4]));

        tabLayout.initData(tabItems, this);

        // 点击事件处理
        onTabItemClick(tabItems.get(0));
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
            if(badgeNum >0){
                tabView.setDotNum(-1);
            }else{
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
                setTitleSingle(true, getResString(R.string.home_find));
                break;
            case 2:
                setTitleSingle(true, getResString(R.string.home_order));
                break;
            case 3:
                setTitleSingle(true, getResString(R.string.home_notify));
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

}
