package com.njz.letsgoapp.view.find;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.njz.letsgoapp.R;
import com.njz.letsgoapp.adapter.base.LoadMoreWrapper;
import com.njz.letsgoapp.adapter.find.DynamicCommentAdapter;
import com.njz.letsgoapp.adapter.home.CommentAdapter;
import com.njz.letsgoapp.base.BaseActivity;
import com.njz.letsgoapp.bean.EmptyModel;
import com.njz.letsgoapp.bean.MySelfInfo;
import com.njz.letsgoapp.bean.find.DynamicCommentModel;
import com.njz.letsgoapp.bean.home.DynamicModel;
import com.njz.letsgoapp.bean.mine.FansModel;
import com.njz.letsgoapp.bean.mine.MyInfoData;
import com.njz.letsgoapp.constant.Constant;
import com.njz.letsgoapp.mvp.find.DynamicDetailContract;
import com.njz.letsgoapp.mvp.find.DynamicDetailPresenter;
import com.njz.letsgoapp.mvp.find.DynamicNiceContract;
import com.njz.letsgoapp.mvp.find.DynamicNicePresenter;
import com.njz.letsgoapp.util.glide.GlideUtil;
import com.njz.letsgoapp.view.mine.FansListActivity;
import com.njz.letsgoapp.view.other.BigImageActivity;
import com.njz.letsgoapp.widget.DynamicImageView;
import com.njz.letsgoapp.widget.DynamicNiceImageView;
import com.njz.letsgoapp.widget.popupwindow.PopComment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LGQ
 * Time: 2018/9/6
 * Function:
 */

public class DynamicDetailActivity extends BaseActivity implements DynamicDetailContract.View, DynamicNiceContract.View {

    ImageView ivImg;
    TextView tvName, tvTime, tvContent, tvNice, tvComment, btnNiceContent, tvLocation;
    DynamicImageView dynamicImageView;
    DynamicNiceImageView dynamicNiceImgView;
    RelativeLayout rlNice;
    RecyclerView recyclerView;

    LinearLayout btnNice, btnComment;

    PopComment popComment;

    DynamicDetailPresenter mPresenter;
    DynamicNicePresenter nicePresenter;

    int friendSterId;
    DynamicCommentAdapter mAdapter;

    DynamicModel model;

    int toId;

    @Override
    public void getIntentData() {
        super.getIntentData();
        friendSterId = intent.getIntExtra("friendSterId", 0);
    }

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
        tvContent = $(R.id.tv_content);
        tvNice = $(R.id.tv_nice);
        tvComment = $(R.id.tv_comment);
        dynamicImageView = $(R.id.dynamic_image_view);
        dynamicNiceImgView = $(R.id.dynamic_nice_img_view);
        rlNice = $(R.id.rl_nice);
        recyclerView = $(R.id.recycler_view);
        btnNice = $(R.id.btn_nice);
        btnComment = $(R.id.btn_comment);
        tvLocation = $(R.id.tv_location);
        btnNiceContent = $(R.id.btn_nice_content);
        recyclerView.setNestedScrollingEnabled(false);
        initRecycler();

        popComment = new PopComment(context, btnComment);
        popComment.setSendClickLisener(new PopComment.OnSendClickLisener() {
            @Override
            public void send(String content) {
                //id,toId,content,dynamicId
//                MySelfInfo.getInstance().getUserId();//id
//                model.getFriendSterId();//dynamicId
//                toId;//toId


            }
        });

        btnComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popComment.setEtHint("说点什么吧...");
                popComment.showPop(btnComment);
                toId = 0;
            }
        });

        dynamicImageView.setOnItemClickListener(new DynamicImageView.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                BigImageActivity.startActivity((Activity) context, position, model.getImgUrls());
            }
        });

        rlNice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentFans = new Intent(context, FansListActivity.class);
                intentFans.putExtra("FansListActivity_title", "我的粉丝");
                intentFans.putExtra("type", 0);
                intentFans.putExtra("userId", model.getUserId());
                startActivity(intentFans);
            }
        });

        btnNice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nicePresenter.friendQueryLikes(model.isLike(), model.getFriendSterId());
            }
        });
    }

    //初始化recyclerview
    private void initRecycler() {
        recyclerView.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false));
        mAdapter = new DynamicCommentAdapter(activity, new ArrayList<DynamicCommentModel>());
        recyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new DynamicCommentAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                popComment.setEtHint("回复 " + model.getDynamicComments().get(position).getName());
                popComment.showPop(btnComment);
                toId = model.getDynamicComments().get(position).getId();
            }
        });
    }

    public void initViewData() {
        GlideUtil.LoadCircleImage(context, model.getImgUrl(), ivImg);
        tvName.setText(model.getNickname());
        tvTime.setText(model.getStartTime());
        tvContent.setText(model.getContent());
        tvNice.setText(model.getLikeCount() + "人点赞");
        tvComment.setText(model.getReplyCount() + "条评论");
        dynamicImageView.setImages(model.getImgUrls());
        dynamicNiceImgView.setImages(model.getImgUrls());
        setNice(model.isLike());
        tvLocation.setText(model.getLocation());

        mAdapter.setData(model.getDynamicComments());
    }

    public void setNice(boolean isNice) {
        if (isNice) {
            btnNiceContent.setText("已赞");
        } else {
            btnNiceContent.setText("赞");
        }
    }

    @Override
    public void initData() {
        mPresenter = new DynamicDetailPresenter(context, this);
        nicePresenter = new DynamicNicePresenter(context, this);
        mPresenter.friendPersonalFriendSter(friendSterId);
    }

    @Override
    public void friendPersonalFriendSterSuccess(DynamicModel model) {
        this.model = model;
        DynamicCommentModel dynamicComment = new DynamicCommentModel();
        dynamicComment.setId(1);
        dynamicComment.setName("那就走");
        dynamicComment.setTime("9.25 8:23");
        dynamicComment.setHeadImg("https://cdn.duitang.com/uploads/item/201508/30/20150830105732_nZCLV.jpeg");
        dynamicComment.setContent("你报警阿斯兰的可见光发");
        dynamicComment.setToId(1);
        dynamicComment.setToName("按时");

        DynamicCommentModel dynamicComment2 = new DynamicCommentModel();
        dynamicComment2.setId(1);
        dynamicComment2.setName("那就走222");
        dynamicComment2.setTime("9.25 8:23");
        dynamicComment2.setHeadImg("https://cdn.duitang.com/uploads/item/201508/30/20150830105732_nZCLV.jpeg");
        dynamicComment2.setContent("你报警阿斯兰的可见光发2222");
        dynamicComment2.setToId(0);

        List<DynamicCommentModel> dynamicComments = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            if (i % 2 == 0) {
                dynamicComments.add(dynamicComment);
            } else {
                dynamicComments.add(dynamicComment2);
            }
        }
        this.model.setDynamicComments(dynamicComments);
        initViewData();
    }

    @Override
    public void friendPersonalFriendSterFailed(String msg) {
        showShortToast(msg);
    }

    @Override
    public void friendQueryLikesSuccess(EmptyModel models) {
        model.setLike(!model.isLike());
        setNice(model.isLike());
    }

    @Override
    public void friendQueryLikesFailed(String msg) {
        showShortToast(msg);
    }
}
