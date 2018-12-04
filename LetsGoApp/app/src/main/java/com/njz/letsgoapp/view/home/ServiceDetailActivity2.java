package com.njz.letsgoapp.view.home;

import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.njz.letsgoapp.R;
import com.njz.letsgoapp.adapter.base.BaseFragmentAdapter;
import com.njz.letsgoapp.bean.home.ServiceDetailModel;
import com.njz.letsgoapp.constant.Constant;
import com.njz.letsgoapp.util.glide.GlideUtil;
import com.njz.letsgoapp.view.home.serverFragment.ServerEvaluateFragment;
import com.njz.letsgoapp.view.home.serverFragment.ServerFeatureFragment;
import com.njz.letsgoapp.view.home.serverFragment.ServerOtherFragment;
import com.njz.letsgoapp.widget.GuideLabelView;
import com.njz.letsgoapp.widget.MyRatingBar;
import com.njz.letsgoapp.widget.ServiceTagView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LGQ
 * Time: 2018/12/4
 * Function: ServiceDetailActivity2单个服务详情，ServiceDetailActivity为导游进入的服务详情。
 */

public class ServiceDetailActivity2 extends ServiceDetailActivity {
    public RelativeLayout rl_person_info;
    public ImageView iv_head,iv_sex;
    public TextView tv_name;
    public MyRatingBar myRatingBar;
    public GuideLabelView guideLabelView;
    public ServiceTagView serviceTagView;

    public String[] titles = {"服务特色", "TA的评价", "其他服务"};

    public  void initViewPage(ServiceDetailModel model){
        mFragments = new ArrayList<>();
        mFragments.add(ServerFeatureFragment.newInstance(model));
        mFragments.add(ServerEvaluateFragment.newInstance());
        mFragments.add(ServerOtherFragment.newInstance());

        BaseFragmentAdapter adapter = new BaseFragmentAdapter(getSupportFragmentManager(), mFragments, titles);
        mViewPager.setAdapter(adapter);
        mViewPager.setOffscreenPageLimit(2);
        mTabLayout.setupWithViewPager(mViewPager);

    }

    @Override
    public void initData() {
        super.initData();
        initPersonInfo();
    }

    public void initPersonInfo(){
        rl_person_info = $(R.id.rl_person_info);
        rl_person_info.setVisibility(View.VISIBLE);

        iv_head = $(R.id.iv_head);
        iv_sex = $(R.id.iv_sex);
        tv_name = $(R.id.tv_name);
        myRatingBar = $(R.id.my_rating_bar);
        guideLabelView = $(R.id.guide_label);
        serviceTagView = $(R.id.stv_tag);

        GlideUtil.LoadCircleImage(context, Constant.TEST_IMG_URL,iv_head);
        iv_sex.setImageDrawable(ContextCompat.getDrawable(context,R.mipmap.icon_girl));
        tv_name.setText("那就走");
        myRatingBar.setRating((int) 4);
        List<String> sign = new ArrayList<>();
        String ss = "幸福";
        sign.add(ss);
        sign.add(ss);
        guideLabelView.setTabel(sign);
        List<String> sign2 = new ArrayList<>();
        String ss2 = "幸福";
        sign2.add(ss2);
        sign2.add(ss2);
        serviceTagView.setServiceTag(sign2);

        rl_person_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO 导游详情
            }
        });

    }

    @Override
    public void initDetail(ServiceDetailModel model) {
        super.initDetail(model);

        //TODO personinfo 信息
    }
}
