package com.njz.letsgoapp.view.serverFragment;

import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.njz.letsgoapp.R;
import com.njz.letsgoapp.adapter.base.EndlessRecyclerOnScrollListener;
import com.njz.letsgoapp.adapter.base.LoadMoreWrapper;
import com.njz.letsgoapp.adapter.home.EvaluateAdapter;
import com.njz.letsgoapp.base.BaseFragment;
import com.njz.letsgoapp.bean.home.EvaluateModel2;
import com.njz.letsgoapp.bean.home.EvaluateServicesModel;
import com.njz.letsgoapp.constant.Constant;
import com.njz.letsgoapp.mvp.home.GuideEvaluateListPresenter;
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

public class ServerEvaluateFragment extends BaseFragment {

    public static final String GUIDEID = "GUIDE_ID";
    private RecyclerView recyclerView;

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


    public static Fragment newInstance() {
        ServerEvaluateFragment fragment = new ServerEvaluateFragment();
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_service_evaluate;
    }

    @Override
    public void initView() {
        mFlowLayout = $(R.id.id_flowlayout);

        initRecycler();
    }

    @Override
    public void initData() {
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

    public void getData(){
        List<EvaluateModel2> datas = new ArrayList<>();
        EvaluateModel2 item = new EvaluateModel2();
        item.setNickname("那就走");
        item.setGuideService(4.5f);
        item.setBuyService(4.5f);
        item.setCarCondition(4.5f);
        item.setTravelArrange(4.5f);
        item.setScore(4.6f);
        item.setUserDate("2018-09-09");
        item.setUserContent("那就走那就走那就走那就走那就走");
        item.setImgUrl("");
        item.setLevel("长沙");
        item.setGuideContent("哪家时代峻峰");
        item.setGuideDate("2019-01-01");
        item.setImageUrls(Constant.TEST_IMG_URL);
        List<EvaluateServicesModel> ss = new ArrayList<>();
        EvaluateServicesModel sd = new EvaluateServicesModel(1,"来得及");
        ss.add(sd);
        ss.add(sd);
        ss.add(sd);
        item.setServices(ss);
        datas.add(item);
        datas.add(item);
        datas.add(item);

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
    }
}
