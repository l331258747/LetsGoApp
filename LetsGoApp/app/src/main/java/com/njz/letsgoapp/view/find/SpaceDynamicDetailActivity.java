package com.njz.letsgoapp.view.find;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.njz.letsgoapp.R;
import com.njz.letsgoapp.adapter.find.DynamicCommentAdapter;
import com.njz.letsgoapp.base.BaseActivity;
import com.njz.letsgoapp.bean.EmptyModel;
import com.njz.letsgoapp.bean.MySelfInfo;
import com.njz.letsgoapp.bean.find.DynamicCommentModel;
import com.njz.letsgoapp.bean.home.DynamicModel;
import com.njz.letsgoapp.constant.URLConstant;
import com.njz.letsgoapp.dialog.DialogUtil;
import com.njz.letsgoapp.dialog.ShareDialog;
import com.njz.letsgoapp.mvp.find.DynamicDeleteContract;
import com.njz.letsgoapp.mvp.find.DynamicDeletePresenter;
import com.njz.letsgoapp.mvp.find.DynamicDetailContract;
import com.njz.letsgoapp.mvp.find.DynamicDetailPresenter;
import com.njz.letsgoapp.mvp.find.DynamicNiceContract;
import com.njz.letsgoapp.mvp.find.DynamicNicePresenter;
import com.njz.letsgoapp.mvp.find.FollowContract;
import com.njz.letsgoapp.mvp.find.FollowPresenter;
import com.njz.letsgoapp.util.AppUtils;
import com.njz.letsgoapp.util.ToastUtil;
import com.njz.letsgoapp.util.glide.GlideUtil;
import com.njz.letsgoapp.view.mine.FansListActivity;
import com.njz.letsgoapp.view.mine.SpaceActivity;
import com.njz.letsgoapp.view.other.BigImageActivity;
import com.njz.letsgoapp.widget.DynamicImageView2;
import com.njz.letsgoapp.widget.DynamicNiceImageView;
import com.njz.letsgoapp.widget.emptyView.EmptyView2;
import com.njz.letsgoapp.widget.popupwindow.PopComment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LGQ
 * Time: 2018/9/6
 * Function:
 */

public class SpaceDynamicDetailActivity extends BaseActivity implements DynamicDetailContract.View, DynamicNiceContract.View,
        DynamicDeleteContract.View, View.OnClickListener,FollowContract.View {

    public static final String FRIENDSTERID ="FRIENDSTERID";

    private ImageView iv_head;
    private TextView  tvTime, tvContent, tvNice, tvComment, btnNiceContent, tvLocation,tvDelete,tvFollow;
    private DynamicImageView2 dynamicImageView;
    private DynamicNiceImageView dynamicNiceImgView;
    private RelativeLayout rlNice;
    private RecyclerView recyclerView;

    private LinearLayout btnNice, btnComment;

    private PopComment popComment;

    private DynamicDetailPresenter mPresenter;
    private DynamicNicePresenter nicePresenter;
    private DynamicDeletePresenter deletePresenter;
    private FollowPresenter followPresenter;

    private int friendSterId;
    private DynamicCommentAdapter mAdapter;

    private DynamicModel model;
    private List<DynamicCommentModel> comments;

    private int toId;

    public EmptyView2 view_empty;
    private ImageView iv_back,iv_share;
    private RelativeLayout rl_title_parent;
    private TextView tv_title_title;
    private NestedScrollView scrollView;
    private View view_title_line;

    private int deletePosition;

    @Override
    public void getIntentData() {
        super.getIntentData();
        friendSterId = intent.getIntExtra(FRIENDSTERID, 0);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_space_dynamic_detail;
    }

    @Override
    public void initView() {

        hideTitleLayout();

        view_empty = $(R.id.view_empty);

        view_title_line = $(R.id.view_title_line);
        rl_title_parent = $(R.id.rl_title_parent);
        tv_title_title = $(R.id.tv_title_title);
        scrollView = $(R.id.scrollView);
        iv_back = $(R.id.iv_back);
        iv_share = $(R.id.iv_share);
        iv_head = $(R.id.iv_head);
        tvTime = $(R.id.tv_time);
        tvFollow = $(R.id.tv_follow);
        tvContent = $(R.id.tv_content);
        tvNice = $(R.id.tv_nice);
        tvComment = $(R.id.tv_comment);
        tvDelete = $(R.id.tv_delete);
        dynamicImageView = $(R.id.dynamic_image_view);
        dynamicNiceImgView = $(R.id.dynamic_nice_img_view);
        rlNice = $(R.id.rl_nice);
        recyclerView = $(R.id.recycler_view);
        btnNice = $(R.id.btn_nice);
        btnComment = $(R.id.btn_comment);
        tvLocation = $(R.id.tv_location);
        btnNiceContent = $(R.id.btn_nice_content);
        recyclerView.setNestedScrollingEnabled(false);
        iv_back.setOnClickListener(this);
        iv_share.setOnClickListener(this);
        initRecycler();

        popComment = new PopComment(context, btnComment);
        popComment.setSendClickLisener(new PopComment.OnSendClickLisener() {
            @Override
            public void send(String content) {
                //id,toId,content,dynamicId
//                MySelfInfo.getInstance().getUserId();//id
//                model.getFriendSterId();//dynamicId
//                toId;//toId
                mPresenter.friendDiscuss(model.getFriendSterId(),MySelfInfo.getInstance().getUserId(),content,toId);

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

        dynamicImageView.setOnItemClickListener(new DynamicImageView2.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                BigImageActivity.startActivity((Activity) context, position, model.getImgUrls());
            }
        });

        rlNice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentFans = new Intent(context, FansListActivity.class);
                intentFans.putExtra(FansListActivity.TITLE, "点赞的人");
                intentFans.putExtra(FansListActivity.TYPE, 2);
                intentFans.putExtra(FansListActivity.USER_ID, model.getFriendSterId());
                startActivity(intentFans);
            }
        });

        btnNice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nicePresenter.friendQueryLikes(model.isLike(), model.getFriendSterId());
            }
        });

        tvDelete.setVisibility(View.GONE);
        tvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogUtil.getInstance().getDefaultDialog(context, "您是否确认删除动态?", new DialogUtil.DialogCallBack() {
                    @Override
                    public void exectEvent(DialogInterface alterDialog) {
                        deletePresenter.friendDeleteFriendSter(model.getFriendSterId());
                    }
                }).show();
            }
        });
        tvFollow.setVisibility(View.GONE);
        tvFollow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                followPresenter.userFocusOff(model.isFocus(),model.getUserId());
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
            public void onClick(final int position) {
                if (comments.get(position).getDiscussUserId() == MySelfInfo.getInstance().getUserId()){
                    DialogUtil.getInstance().getDefaultDialog(context, "是否删除评论?", new DialogUtil.DialogCallBack() {
                        @Override
                        public void exectEvent(DialogInterface alterDialog) {
                            deletePosition = position;
                            mPresenter.friendDeleteDiscuss(comments.get(position).getId());
                        }
                    }).show();
                    return;
                }

                popComment.setEtHint("回复 " + model.getDynamicComments().get(position).getDiscussUserName());
                popComment.showPop(btnComment);
                toId = comments.get(position).getDiscussUserId();
            }
        });
    }

    public void initViewData() {
        GlideUtil.LoadCircleImage(context, model.getImgUrl(), iv_head);
        tvTime.setText(model.getStartTimeTwo());
        if(TextUtils.isEmpty(model.getContent())){
            tvContent.setVisibility(View.GONE);
        }else{
            tvContent.setVisibility(View.VISIBLE);
            tvContent.setText(model.getContent());
        }
        tvNice.setText(model.getLikeCount() + "人点赞");
        tvComment.setText(model.getReplyCount() + "条评论");
        if(model.getImgUrls() == null || model.getImgUrls().size() == 0){
            dynamicImageView.setVisibility(View.GONE);
        }else{
            dynamicImageView.setVisibility(View.VISIBLE);
            dynamicImageView.setImages(model.getImgUrls());
        }
        dynamicNiceImgView.setImages(model.getLikeList());
        setNice(model.isLike());
        tvLocation.setText(model.getLocation());

        mAdapter.setData(model.getDynamicComments());

        if(MySelfInfo.getInstance().getUserId() == model.getUserId()){
            tvDelete.setVisibility(View.VISIBLE);
            tvFollow.setVisibility(View.GONE);
        }else{
            tvDelete.setVisibility(View.GONE);
            tvFollow.setVisibility(View.VISIBLE);
            setFollow(model.isFocus());
        }

    }

    public void setNice(boolean isNice) {
        if (isNice) {
            btnNiceContent.setText("已赞");
            btnNiceContent.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(context,R.mipmap.ic_nice),null,null,null);
        } else {
            btnNiceContent.setText("点赞");
            btnNiceContent.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(context,R.mipmap.ic_nice_un),null,null,null);
        }
    }

    @Override
    public void initData() {
        mPresenter = new DynamicDetailPresenter(context, this);
        nicePresenter = new DynamicNicePresenter(context, this);
        deletePresenter = new DynamicDeletePresenter(context,this);
        followPresenter = new FollowPresenter(context,this);

        mPresenter.friendPersonalFriendSter(friendSterId);

        final int mDisplayHeight = AppUtils.dip2px(140 - 42);
        scrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (scrollY > mDisplayHeight) {
                    rl_title_parent.setBackgroundResource(R.color.white);
                    tv_title_title.setTextColor(ContextCompat.getColor(context,R.color.color_text));
                    iv_back.setImageDrawable(ContextCompat.getDrawable(context,R.mipmap.icon_back));
                    iv_share.setImageDrawable(ContextCompat.getDrawable(context,R.mipmap.icon_share));
                    view_title_line.setVisibility(View.VISIBLE);
                } else {
                    rl_title_parent.setBackgroundResource(R.color.transparent);
                    tv_title_title.setTextColor(ContextCompat.getColor(context,R.color.white));
                    iv_back.setImageDrawable(ContextCompat.getDrawable(context,R.mipmap.icon_back_white));
                    iv_share.setImageDrawable(ContextCompat.getDrawable(context,R.mipmap.icon_share_white));
                    view_title_line.setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    public void friendPersonalFriendSterSuccess(DynamicModel model) {
        this.model = model;
        this.comments = model.getDynamicComments();
        initViewData();

        view_empty.setEmptyData(R.mipmap.empty_dynamic_detail,"还没有人留言，还不快来抢沙发~");
        if(comments.size() == 0){
            view_empty.setVisible(true);
        }else{
            view_empty.setVisible(false);
        }

    }

    @Override
    public void friendPersonalFriendSterFailed(String msg) {
        showShortToast(msg);
    }

    @Override
    public void friendDiscussSuccess(DynamicCommentModel comment) {
        comments.add(comment);
        mAdapter.setData(comments);
        model.setReplyCount(model.getReplyCount() + 1);
        tvComment.setText(model.getReplyCount() + "条评论");
        recyclerView.scrollToPosition(comments.size()-1);
        AppUtils.HideKeyboard(tvComment);

        view_empty.setVisible(false);
    }

    @Override
    public void friendDiscussFailed(String msg) {
        showShortToast(msg);
        AppUtils.HideKeyboard(tvComment);
    }

    @Override
    public void friendDeleteDiscussSuccess(EmptyModel models) {
        comments.remove(deletePosition);
        mAdapter.setData(comments);
        model.setReplyCount(model.getReplyCount() - 1);
        tvComment.setText(model.getReplyCount() + "条评论");
        showShortToast("删除成功");

        if(comments.size() == 0){
            view_empty.setVisible(true);
        }
    }

    @Override
    public void friendDeleteDiscussFailed(String msg) {
        showShortToast(msg);
    }

    @Override
    public void friendQueryLikesSuccess(EmptyModel models) {
        model.setLike(!model.isLike());
        if(model.isLike()){
            model.setLikeCount(model.getLikeCount() + 1);
        }else{
            model.setLikeCount(model.getLikeCount() - 1);
        }
        tvNice.setText(model.getLikeCount() + "人点赞");
        setNice(model.isLike());
    }

    @Override
    public void friendQueryLikesFailed(String msg) {
        showShortToast(msg);
    }


    @Override
    public void friendDeleteFriendSterSuccess(EmptyModel models) {
        finish();
        showShortToast("删除成功");
    }

    @Override
    public void friendDeleteFriendSterFailed(String msg) {
        showShortToast(msg);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.iv_share:
                if(model == null) return;
                ShareDialog dialog = new ShareDialog(activity,
                        model.getNickname() + "旅游动态",
                        TextUtils.isEmpty(model.getContent())?"TA发表了一份动态，快去查看吧！":model.getContent(),
                        model.getImgUrl(),
                        URLConstant.SHARE_DYNAMIC+"?friendSterId="+model.getFriendSterId());
                dialog.setReportData(model.getFriendSterId(), ShareDialog.REPORT_DYNAMIC);
                dialog.setType(ShareDialog.TYPE_ALL);
                dialog.show();
                break;
        }

    }

    public void setFollow(boolean isFollow){
        if(isFollow){
            tvFollow.setText(getResources().getString(R.string.follow_in));
            tvFollow.setTextColor(ContextCompat.getColor(context,R.color.white));
            tvFollow.setBackground(ContextCompat.getDrawable(context,R.drawable.btn_theme_solid_r5));
        }else{
            tvFollow.setText(getResources().getString(R.string.follow_un));
            tvFollow.setTextColor(ContextCompat.getColor(context,R.color.color_theme));
            tvFollow.setBackground(ContextCompat.getDrawable(context,R.drawable.btn_theme_hollow_r5));
        }
    }

    @Override
    public void userFocusOffSuccess(EmptyModel models) {
        model.setFocus(!model.isFocus());
        setFollow(model.isFocus());
    }

    @Override
    public void userFocusOffFailed(String msg) {
        showShortToast(msg);
    }
}
