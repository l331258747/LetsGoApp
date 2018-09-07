package com.njz.letsgoapp.view.home;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.njz.letsgoapp.R;
import com.njz.letsgoapp.adapter.home.CommentAdapter;
import com.njz.letsgoapp.base.BaseActivity;
import com.njz.letsgoapp.bean.home.CommentData;
import com.njz.letsgoapp.util.log.LogUtil;
import com.njz.letsgoapp.widget.flowlayout.FlowLayout;
import com.njz.letsgoapp.widget.flowlayout.TagAdapter;
import com.njz.letsgoapp.widget.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LGQ
 * Time: 2018/8/16
 * Function:
 */

public class CommentDetailActivity extends BaseActivity {

    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;

    TagFlowLayout mFlowLayout;
    private String[] mVals = new String[]
            {"全部(100)", "向导陪游(100)", "私人定制(100)", "车导服务(100)", "代订酒店(100)", "景点门票(100)"};

    CommentAdapter mAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_comment_detail;
    }

    @Override
    public void initView() {
        showLeftAndTitle("全部评论");

        mFlowLayout = $(R.id.id_flowlayout);

        initRecycler();
        initSwipeLayout();


    }

    @Override
    public void initData() {
        initFlow();
    }

    //初始化recyclerview
    private void initRecycler() {
        recyclerView = $(R.id.recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        mAdapter = new CommentAdapter(activity, getData());
        recyclerView.setAdapter(mAdapter);

    }

    //初始化SwipeLayout
    private void initSwipeLayout() {
        swipeRefreshLayout = $(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setColorSchemeColors(getResColor(R.color.color_theme));
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mAdapter.setData(getData());
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    public void initFlow(){
        final LayoutInflater mInflater = LayoutInflater.from(activity);
        mFlowLayout.setAdapter(new TagAdapter<String>(mVals) {
            @Override
            public View getView(FlowLayout parent, int position, String s) {
                TextView tv = (TextView) mInflater.inflate(R.layout.item_flow_comment, mFlowLayout, false);
                tv.setText(s);
                return tv;
            }
        });

        mFlowLayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                Toast.makeText(activity, mVals[position], Toast.LENGTH_SHORT).show();
                LogUtil.e("onTagClick:" + mVals[position]);
                return true;
            }
        });
    }

    public List<CommentData> getData(){
        List<CommentData> commentDatas = new ArrayList<>();
        CommentData commentData = new CommentData();

        List<String> banners = new ArrayList<>();
        String bannerImg = "http://s9.rr.itc.cn/r/wapChange/20164_30_21/a2tklm523975660855.jpg";
        banners.add(bannerImg);
        banners.add(bannerImg);
        banners.add(bannerImg);
        banners.add(bannerImg);
        banners.add(bannerImg);
        String photo = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1532339453709&di=c506e751bd24c08cb2221d51ac3300c7&imgtype=0&src=http%3A%2F%2Fimg.80tian.com%2Fblog%2F201403%2F20140323170732_1145.jpg";

        commentData.setBanners(banners);
        commentData.setContent("那就走导游那就走导游那就走导游那就走导游那就走导游那就走导游那就走导游真好");
        commentData.setImg_head(photo);
        commentData.setName("那就走");
        commentData.setOrderInfo("天门山一日游\n包车旅行\n导游陪游景点翻游\n天门山假日酒店");
        commentData.setReply("回复回复回复回复回复回复回复回复回复回复回复回复回复回复回复回复");
        commentData.setTime("2018-07-08");
        commentData.setReplyTime("2018-09-14");
        commentData.setScore(4);

        commentDatas.add(commentData);
        commentDatas.add(commentData);
        commentDatas.add(commentData);
        commentDatas.add(commentData);
        commentDatas.add(commentData);
        commentDatas.add(commentData);
        commentDatas.add(commentData);
        commentDatas.add(commentData);
        commentDatas.add(commentData);

        return commentDatas;
    }
}
