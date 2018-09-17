package com.njz.letsgoapp.view.homeFragment.fragment;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.njz.letsgoapp.R;
import com.njz.letsgoapp.adapter.base.BaseFragmentAdapter;
import com.njz.letsgoapp.base.BaseFragment;
import com.njz.letsgoapp.bean.MySelfInfo;
import com.njz.letsgoapp.constant.Constant;
import com.njz.letsgoapp.util.rxbus.RxBus2;
import com.njz.letsgoapp.util.rxbus.busEvent.CityPickEvent;
import com.njz.letsgoapp.view.find.DynamicFragment;
import com.njz.letsgoapp.view.find.ReleaseDynamicActivity;
import com.njz.letsgoapp.view.login.LoginActivity;
import com.njz.letsgoapp.view.other.MyCityPickActivity;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by LGQ
 * Time: 2018/7/23
 * Function:
 */

public class FindFragment extends BaseFragment implements View.OnClickListener {


    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private TextView tvCityPick, tvSearch;
    private ImageView ivRelease;


    private Disposable desDisposable;

    private List<Fragment> mFragments;
    private String[] titles = {"全部", "关注"};

    private String city = Constant.DEFAULT_CITY;

    DynamicFragment dynamicAll;
    DynamicFragment dynamicFollow;


    @Override
    public int getLayoutId() {
        return R.layout.fragment_find;
    }

    @Override
    public void initView() {
        mTabLayout = $(R.id.tabs);
        mViewPager = $(R.id.viewpager);
        tvCityPick = $(R.id.tv_city_pick);
        tvSearch = $(R.id.tv_search);
        ivRelease = $(R.id.iv_release);

        tvCityPick.setOnClickListener(this);
        tvSearch.setOnClickListener(this);
        ivRelease.setOnClickListener(this);
    }

    @Override
    public void initData() {
        tvCityPick.setText(city);

        mFragments = new ArrayList<>();
        dynamicAll = (DynamicFragment) DynamicFragment.newInstance(DynamicFragment.DYNAMIC_ALL);
        dynamicFollow = (DynamicFragment) DynamicFragment.newInstance(DynamicFragment.DYNAMIC_FOLLOW);
        mFragments.add(dynamicAll);
        mFragments.add(dynamicFollow);

        BaseFragmentAdapter adapter = new BaseFragmentAdapter(getChildFragmentManager(), mFragments, titles);
        mViewPager.setAdapter(adapter);
        mViewPager.setOffscreenPageLimit(1);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_city_pick:
                cityPick();
                break;
            case R.id.tv_search:

                break;
            case R.id.iv_release:
                if (!MySelfInfo.getInstance().isLogin()) {//登录状态
                    startActivity(new Intent(context,LoginActivity.class));
                    return;
                }
                startActivity(new Intent(context, ReleaseDynamicActivity.class));
                break;
        }
    }


    //城市选择
    private void cityPick(){
        Intent intent = new Intent(context, MyCityPickActivity.class);
        intent.putExtra(MyCityPickActivity.LOCATION,tvCityPick.getText().toString());
        startActivity(intent);
        desDisposable = RxBus2.getInstance().toObservable(CityPickEvent.class, new Consumer<CityPickEvent>() {
            @Override
            public void accept(CityPickEvent cityPickEvent) throws Exception {
                desDisposable.dispose();
                if(TextUtils.isEmpty(cityPickEvent.getCity()))
                    return;
                tvCityPick.setText(cityPickEvent.getCity());
                city = cityPickEvent.getCity();
                setCityChange();

            }
        });
    }

    public void setCityChange(){
        dynamicAll.setCityChange(city);
    }
}



//修改tablayout  Indicator 宽度
//    public void reflex(final TabLayout tabLayout){
//        //了解源码得知 线的宽度是根据 tabView的宽度来设置的
//        tabLayout.post(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    //拿到tabLayout的mTabStrip属性
//                    LinearLayout mTabStrip = (LinearLayout) tabLayout.getChildAt(0);
//
//                    int dp10 = AppUtils.dip2px(50);
//
//                    for (int i = 0; i < mTabStrip.getChildCount(); i++) {
//                        View tabView = mTabStrip.getChildAt(i);
//
//                        //拿到tabView的mTextView属性  tab的字数不固定一定用反射取mTextView
//                        Field mTextViewField = tabView.getClass().getDeclaredField("mTextView");
//                        mTextViewField.setAccessible(true);
//
//                        TextView mTextView = (TextView) mTextViewField.get(tabView);
//
//                        tabView.setPadding(0, 0, 0, 0);
//
//                        //因为我想要的效果是   字多宽线就多宽，所以测量mTextView的宽度
//                        int width = 0;
//                        width = mTextView.getWidth();
//                        if (width == 0) {
//                            mTextView.measure(0, 0);
//                            width = mTextView.getMeasuredWidth();
//                        }
//
//                        //设置tab左右间距为10dp  注意这里不能使用Padding 因为源码中线的宽度是根据 tabView的宽度来设置的
//                        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) tabView.getLayoutParams();
//                        params.width = width ;
//                        params.leftMargin = dp10;
//                        params.rightMargin = dp10;
//                        tabView.setLayoutParams(params);
//
//                        tabView.invalidate();
//                    }
//
//                } catch (NoSuchFieldException e) {
//                    e.printStackTrace();
//                } catch (IllegalAccessException e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//
//    }