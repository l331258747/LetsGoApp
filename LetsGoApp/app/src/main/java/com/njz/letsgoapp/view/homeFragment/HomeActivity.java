package com.njz.letsgoapp.view.homeFragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.KeyEvent;

import com.njz.letsgoapp.R;
import com.njz.letsgoapp.base.BaseActivity;
import com.njz.letsgoapp.base.BaseFragment;
import com.njz.letsgoapp.bean.MySelfInfo;
import com.njz.letsgoapp.bean.coupon.ActivityPopModel;
import com.njz.letsgoapp.bean.notify.NotifyMainModel;
import com.njz.letsgoapp.constant.Constant;
import com.njz.letsgoapp.dialog.ActivityDialog;
import com.njz.letsgoapp.mvp.coupon.ActivityPopContract;
import com.njz.letsgoapp.mvp.coupon.ActivityPopPresenter;
import com.njz.letsgoapp.mvp.notify.NotifyMainContract;
import com.njz.letsgoapp.mvp.notify.NotifyMainPresenter;
import com.njz.letsgoapp.util.log.LogUtil;
import com.njz.letsgoapp.util.rxbus.RxBus2;
import com.njz.letsgoapp.util.rxbus.busEvent.NotifyEvent;
import com.njz.letsgoapp.view.find.DynamicDetailActivity;
import com.njz.letsgoapp.view.home.GuideDetailActivity;
import com.njz.letsgoapp.view.homeFragment.fragment.FindFragment;
import com.njz.letsgoapp.view.homeFragment.fragment.HomeFragment;
import com.njz.letsgoapp.view.homeFragment.fragment.MyFragment;
import com.njz.letsgoapp.view.homeFragment.fragment.NotifyFragment;
import com.njz.letsgoapp.view.homeFragment.fragment.OrderFragment;
import com.njz.letsgoapp.view.mine.SpaceActivity;
import com.njz.letsgoapp.view.order.OrderDetailActivity;
import com.njz.letsgoapp.view.order.OrderRefundDetailActivity;
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

public class HomeActivity extends BaseActivity implements TabLayout.OnTabClickListener, NotifyMainContract.View, ActivityPopContract.View {


    private TabLayout tabLayout;
    private ArrayList<TabItem> tabItems;

    private Class[] fragmentCls = new Class[5];
    private Fragment[] fragments = new Fragment[5];

//    LinearLayout linear_bar;

    Disposable notifyDisposable;

    private NotifyMainPresenter mPresenter;
    private ActivityPopPresenter activityPopPresenter;

    @Override
    public void getIntentData() {
        super.getIntentData();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getNewIntent();
    }

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

        MySelfInfo.getInstance().setDefaultCity(TextUtils.isEmpty(MySelfInfo.getInstance().getDefaultCity()) ? Constant.DEFAULT_CITY : MySelfInfo.getInstance().getDefaultCity());


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
                changeTabBadge(notifyEvent.isShowTips() ? -1 : 0);
            }
        });

        mPresenter = new NotifyMainPresenter(context, this);
        if (MySelfInfo.getInstance().isLogin()) {
            mPresenter.msgPushGetSendMsgList();
        }

        activityPopPresenter = new ActivityPopPresenter(context, this);
        activityPopPresenter.orderPopup();
    }

    public void setTabIndex(int i) {
        onTabItemClick(tabItems.get(i));
    }

    public OrderFragment getOrderFragment() {
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
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (notifyDisposable != null && !notifyDisposable.isDisposed())
            notifyDisposable.dispose();
    }

    @Override
    public void msgPushGetSendMsgListSuccess(List<NotifyMainModel> data) {
        if (data == null || data.size() == 0) {
            RxBus2.getInstance().post(new NotifyEvent(false));
        } else {
            RxBus2.getInstance().post(new NotifyEvent(true));
        }
    }

    @Override
    public void msgPushGetSendMsgListFailed(String msg) {
        LogUtil.e(msg);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        LogUtil.e("onNewIntent");
        setIntent(intent);
        getNewIntent();
    }

    private void getNewIntent() {
        Intent intent = getIntent(); //use the data received here
        int key = intent.getIntExtra("key", -1);
        String skip = intent.getStringExtra("skip");

        LogUtil.e("key:" + key);
        LogUtil.e("skip:" + skip);

        setSkip(skip, key);
    }

    public void setSkip(final String skip, final int correlationId) {
        if (correlationId == -1) {
            LogUtil.e("不能进行跳转correlationId");
            return;
        }
        if (TextUtils.isEmpty(skip)) {
            LogUtil.e("不能进行跳转skip 空");
            return;
        }
        Intent intent;
        switch (skip) {
            case Constant.NOTIFY_SKIP_FSD:
                intent = new Intent(context, DynamicDetailActivity.class);
                intent.putExtra(DynamicDetailActivity.FRIENDSTERID, correlationId);
                startActivity(intent);
                break;
            case Constant.NOTIFY_SKIP_GD:
                intent = new Intent(context, GuideDetailActivity.class);
                intent.putExtra(GuideDetailActivity.GUIDEID, correlationId);
                startActivity(intent);
                break;
            case Constant.NOTIFY_SKIP_OD:
                intent = new Intent(context, OrderDetailActivity.class);
                intent.putExtra("ORDER_ID", correlationId);
                startActivity(intent);
                break;
            case Constant.NOTIFY_SKIP_UD:
                intent = new Intent(context, SpaceActivity.class);
                intent.putExtra(SpaceActivity.USER_ID, correlationId);
                startActivity(intent);
                break;
            case Constant.NOTIFY_SKIP_ORD:
                intent = new Intent(context, OrderRefundDetailActivity.class);
                intent.putExtra("ORDER_ID", correlationId);
                startActivity(intent);
                break;
            default:
                LogUtil.e("不能进行跳转skip：" + skip);
                break;
        }
    }

    @Override
    public void orderPopupSuccess(ActivityPopModel model) {
        if (model.getIsRemind() == 0) return;//是否弹框1：提醒 0:不提醒
        if (model.showDialog()) {
            ActivityDialog activityDialog = new ActivityDialog(context);
            activityDialog.setData(model.getPopoutImage(),model.getId());
            activityDialog.show();
        }
    }

    @Override
    public void orderPopupFailed(String msg) {
        LogUtil.e(msg);
    }
}
