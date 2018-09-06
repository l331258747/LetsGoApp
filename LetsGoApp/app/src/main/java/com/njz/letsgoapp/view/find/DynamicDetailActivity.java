package com.njz.letsgoapp.view.find;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.njz.letsgoapp.R;
import com.njz.letsgoapp.base.BaseActivity;
import com.njz.letsgoapp.view.mine.FansListActivity;
import com.njz.letsgoapp.widget.DynamicImageView;
import com.njz.letsgoapp.widget.DynamicNiceImageView;
import com.njz.letsgoapp.widget.popupwindow.PopComment;

/**
 * Created by LGQ
 * Time: 2018/9/6
 * Function:
 */

public class DynamicDetailActivity extends BaseActivity {

    ImageView ivImg;
    TextView tvName, tvTime, tvFollow, tvContent, tvNice, tvComment;
    DynamicImageView dynamicImageView;
    DynamicNiceImageView dynamicNiceImgView;
    RelativeLayout rlNice;
    RecyclerView recyclerView;

    LinearLayout btnNice, btnComment;

    PopComment popComment;


    @Override
    public int getLayoutId() {
        return R.layout.activity_dynamic_detail;
    }

    @Override
    public void initView() {

        showLeftAndTitle("动态详情");

        ivImg = $(R.id.iv_img);
        tvName = $(R.id.tv_name);
        tvTime = $(R.id.tv_time);
        tvFollow = $(R.id.tv_follow);
        tvContent = $(R.id.tv_content);
        tvNice = $(R.id.tv_nice);
        tvComment = $(R.id.tv_comment);
        dynamicImageView = $(R.id.dynamic_image_view);
        dynamicNiceImgView = $(R.id.dynamic_nice_img_view);
        rlNice = $(R.id.rl_nice);
        recyclerView = $(R.id.recycler_view);
        btnNice = $(R.id.btn_nice);
        btnComment = $(R.id.btn_comment);

        recyclerView.setNestedScrollingEnabled(false);

//        dynamicImageView.setOnItemClickListener(new DynamicImageView.OnItemClickListener() {
//            @Override
//            public void onClick(int position) {
//                BigImageActivity.startActivity((Activity) context,position,data.getImgUrls());
//            }
//        });

        dynamicNiceImgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentFans = new Intent(context, FansListActivity.class);
                intentFans.putExtra("FansListActivity_title", "我的粉丝");
                intentFans.putExtra("type", 0);
                startActivity(intentFans);
            }
        });


        btnComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(popComment == null)
                    popComment = new PopComment(context,btnComment);
                popComment.showPop(btnComment);
            }
        });


    }

    @Override
    public void initData() {

    }
}
