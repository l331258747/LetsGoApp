package com.njz.letsgoapp.view.home;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.njz.letsgoapp.R;
import com.njz.letsgoapp.adapter.base.EndlessRecyclerOnScrollListener;
import com.njz.letsgoapp.adapter.base.LoadMoreWrapper;
import com.njz.letsgoapp.adapter.home.EvaluateAdapter;
import com.njz.letsgoapp.base.BaseActivity;
import com.njz.letsgoapp.bean.BasePageModel;
import com.njz.letsgoapp.bean.home.EvaluateModel2;
import com.njz.letsgoapp.constant.Constant;
import com.njz.letsgoapp.mvp.home.GuideEvaluateListContract;
import com.njz.letsgoapp.mvp.home.GuideEvaluateListPresenter;
import com.njz.letsgoapp.widget.flowlayout.FlowLayout;
import com.njz.letsgoapp.widget.flowlayout.TagAdapter;
import com.njz.letsgoapp.widget.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LGQ
 * Time: 2018/8/16
 * Function: 导游评价列表
 */

public class EvaluateListActivity extends BaseActivity implements GuideEvaluateListContract.View {

    public static final String GUIDEID = "GUIDE_ID";
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;

    private TagFlowLayout mFlowLayout;
    private TagAdapter<String> mFlowAdapter;
    private String[] mVals = new String[]
            {"全部", "向导陪游", "私人定制", "车导服务", "代订酒店", "景点门票"};

    private EvaluateAdapter mAdapter;

    private GuideEvaluateListPresenter mPresenter;
    private int guideId;

    private LoadMoreWrapper loadMoreWrapper;
    private int page;
    private int isLoadType = 1;//1下拉刷新，2上拉加载
    private boolean isLoad = false;//是否在加载，重复加载问题

    private String value = "";

    @Override
    public void getIntentData() {
        super.getIntentData();
        guideId = intent.getIntExtra(GUIDEID, 0);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_comment_detail;
    }

    @Override
    public void initView() {
        showLeftAndTitle("全部评价");

        mFlowLayout = $(R.id.id_flowlayout);

        initRecycler();
        initSwipeLayout();
    }

    @Override
    public void initData() {
        initFlow();
        mPresenter = new GuideEvaluateListPresenter(context, this);
        getRefreshData();
    }

    //初始化recyclerview
    private void initRecycler() {
        recyclerView = $(R.id.recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        mAdapter = new EvaluateAdapter(activity, new ArrayList<EvaluateModel2>());
        loadMoreWrapper = new LoadMoreWrapper(mAdapter);
        recyclerView.setAdapter(loadMoreWrapper);
        ((SimpleItemAnimator) recyclerView.getItemAnimator()).setSupportsChangeAnimations(false);//itemChanged 闪烁问题

        recyclerView.addOnScrollListener(new EndlessRecyclerOnScrollListener() {
            @Override
            public void onLoadMore() {
                if (isLoad || loadMoreWrapper.getLoadState() == LoadMoreWrapper.LOADING_END) return;
                loadMoreWrapper.setLoadState(loadMoreWrapper.LOADING);
                getMoreData();
            }
        });

        mAdapter.setOnItemClickListener(new EvaluateAdapter.OnItemClickListener() {
            @Override
            public void onReplyClick(int... positions) {
                for (int position : positions) {
                    loadMoreWrapper.notifyItemChanged(position);
                }
            }
        });

    }

    //初始化SwipeLayout
    private void initSwipeLayout() {
        swipeRefreshLayout = $(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setColorSchemeColors(getResColor(R.color.color_theme));
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (isLoad) return;
                getRefreshData();
            }
        });
    }

    public void initFlow() {
        final LayoutInflater mInflater = LayoutInflater.from(activity);
        mFlowLayout.setAdapter(mFlowAdapter = new TagAdapter<String>(mVals) {
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
                value = position == 0 ? ""
                        : position == 1 ? Constant.SERVER_TYPE_GUIDE
                        : position == 2 ? Constant.SERVER_TYPE_CUSTOM
                        : position == 3 ? Constant.SERVER_TYPE_CAR
                        : position == 4 ? Constant.SERVER_TYPE_HOTEL
                        : position == 5 ? Constant.SERVER_TYPE_TICKET
                        : "";
                getRefreshData();
                return true;
            }
        });

        mFlowAdapter.setSelectedList(0);
    }

    private void getRefreshData() {
        swipeRefreshLayout.setRefreshing(true);
        isLoad = true;
        page = Constant.DEFAULT_PAGE;
        isLoadType = 1;
        mPresenter.orderReviewsFindGuideReviews(guideId, value, Constant.DEFAULT_LIMIT, page);
    }

    private void getMoreData() {
        isLoad = true;
        page = page + 1;
        isLoadType = 2;
        mPresenter.orderReviewsFindGuideReviews(guideId, value, Constant.DEFAULT_LIMIT, page);
    }


    @Override
    public void orderReviewsFindGuideReviewsSuccess(BasePageModel<EvaluateModel2> model) {
        List<EvaluateModel2> datas = model.getList();


        if (isLoadType == 1) {
            mAdapter.setData(datas);
        } else {
            mAdapter.addData(datas);
        }
        isLoad = false;
        if (datas.size() >= Constant.DEFAULT_LIMIT) {
            loadMoreWrapper.setLoadState(loadMoreWrapper.LOADING_COMPLETE);
        } else {
            // 显示加载到底的提示
            loadMoreWrapper.setLoadState(loadMoreWrapper.LOADING_END);
        }
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void orderReviewsFindGuideReviewsFailed(String msg) {
        showShortToast(msg);

        isLoad = false;
        swipeRefreshLayout.setRefreshing(false);
        loadMoreWrapper.setLoadState(loadMoreWrapper.LOADING_COMPLETE);
    }
}
