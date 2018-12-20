package com.njz.letsgoapp.view.serverFragment;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.njz.letsgoapp.R;
import com.njz.letsgoapp.adapter.base.EndlessRecyclerOnScrollListener;
import com.njz.letsgoapp.adapter.base.LoadMoreWrapper;
import com.njz.letsgoapp.adapter.home.EvaluateAdapter;
import com.njz.letsgoapp.base.BaseFragment;
import com.njz.letsgoapp.bean.BasePageModel;
import com.njz.letsgoapp.bean.home.EvaluateModel2;
import com.njz.letsgoapp.bean.home.EvaluateServicesModel;
import com.njz.letsgoapp.constant.Constant;
import com.njz.letsgoapp.mvp.home.GuideEvaluateListContract;
import com.njz.letsgoapp.mvp.home.GuideEvaluateListPresenter;
import com.njz.letsgoapp.widget.emptyView.EmptyView2;
import com.njz.letsgoapp.widget.flowlayout.FlowLayout;
import com.njz.letsgoapp.widget.flowlayout.TagAdapter;
import com.njz.letsgoapp.widget.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LGQ
 * Time: 2018/12/4
 * Function:
 */

public class ServerEvaluateFragment extends BaseFragment implements GuideEvaluateListContract.View{

    public static final String GUIDEID = "GUIDE_ID";
    private RecyclerView recyclerView;
    private EmptyView2 view_empty;

    private TagFlowLayout mFlowLayout;
    private TagAdapter<String> mFlowAdapter;
    private String[] mVals = new String[]
            {"全部", "好评", "中评", "差评"};

    private EvaluateAdapter mAdapter;

    private GuideEvaluateListPresenter mPresenter;
    private int guideId;

    private LoadMoreWrapper loadMoreWrapper;
    private int page;
    private int isLoadType = 1;//1下拉刷新，2上拉加载
    private boolean isLoad = false;//是否在加载，重复加载问题

    private String value = "";

    public static Fragment newInstance(int guideId) {
        ServerEvaluateFragment fragment = new ServerEvaluateFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("GUIDE_ID", guideId);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            guideId = bundle.getInt("GUIDE_ID");
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_service_evaluate;
    }

    @Override
    public void initView() {
        view_empty = $(R.id.view_empty);
        mFlowLayout = $(R.id.id_flowlayout);

        initRecycler();
    }

    @Override
    public void initData() {
        mPresenter = new GuideEvaluateListPresenter(context, this);

        initFlow();
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
        recyclerView.setNestedScrollingEnabled(false);

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

    public void initFlow() {
        final LayoutInflater mInflater = LayoutInflater.from(activity);
        mFlowLayout.setAdapter(mFlowAdapter = new TagAdapter<String>(mVals) {
            @Override
            public View getView(FlowLayout parent, int position, String s) {
                TextView tv = (TextView) mInflater.inflate(R.layout.item_flow_evaluate, mFlowLayout, false);
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
        isLoad = true;
        page = Constant.DEFAULT_PAGE;
        isLoadType = 1;
        getData();
    }

    private void getMoreData() {
        isLoad = true;
        page = page + 1;
        isLoadType = 2;
        getData();
    }

    @Override
    public void orderReviewsFindGuideReviewsSuccess(BasePageModel<EvaluateModel2> models) {
        List<EvaluateModel2> datas = models.getList();

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

        if(mAdapter.getDatas().size() == 0){
            view_empty.setVisible(true);
            view_empty.setEmptyData(R.mipmap.empty_comment_meto,"空空如也~");
        }else{
            view_empty.setVisible(false);
        }
    }

    @Override
    public void orderReviewsFindGuideReviewsFailed(String msg) {
        showShortToast(msg);
        isLoad = false;
        loadMoreWrapper.setLoadState(loadMoreWrapper.LOADING_COMPLETE);
    }

    public void getData() {
        mPresenter.orderReviewsFindGuideReviews(guideId, value, Constant.DEFAULT_LIMIT, page);
    }
}
