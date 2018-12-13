package com.njz.letsgoapp.view.serverFragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;

import com.njz.letsgoapp.R;
import com.njz.letsgoapp.adapter.base.LoadMoreWrapper;
import com.njz.letsgoapp.adapter.home.ServerListAdapter1;
import com.njz.letsgoapp.adapter.home.ServerListAdapter2;
import com.njz.letsgoapp.base.BaseFragment;
import com.njz.letsgoapp.bean.MySelfInfo;
import com.njz.letsgoapp.bean.home.BannerModel;
import com.njz.letsgoapp.bean.home.GuideDetailModel;
import com.njz.letsgoapp.bean.home.GuideServiceModel;
import com.njz.letsgoapp.bean.server.PlayModel;
import com.njz.letsgoapp.bean.server.ServerDetailMedel;
import com.njz.letsgoapp.constant.Constant;
import com.njz.letsgoapp.mvp.server.ServerListContract;
import com.njz.letsgoapp.mvp.server.ServerListPresenter;
import com.njz.letsgoapp.util.log.LogUtil;
import com.njz.letsgoapp.view.server.CustomActivity;
import com.njz.letsgoapp.view.server.ServiceDetailActivity;
import com.njz.letsgoapp.widget.popupwindow.PopServer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LGQ
 * Time: 2018/12/4
 * Function:
 */

public class ServerListFragment extends BaseFragment implements ServerListContract.View{
    GuideDetailModel model;
    RecyclerView recycler_view1,recycler_view2;
    private ServerListAdapter1 mAdapter1;
    private ServerListAdapter2 mAdapter2;

    ServerListPresenter serverListPresenter;

    private LoadMoreWrapper loadMoreWrapper;
    private int page;
    private int isLoadType = 1;//1下拉刷新，2上拉加载
    private boolean isLoad = false;//是否在加载，重复加载问题
    PopServer popServer;

    private int value;

    public static Fragment newInstance(GuideDetailModel guideDetailModel) {
        ServerListFragment fragment = new ServerListFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("GuideDetailModel", guideDetailModel);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            model = bundle.getParcelable("GuideDetailModel");
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_server_list;
    }

    @Override
    public void initView() {
        initRecycler1();
        initRecycler2();
    }

    @Override
    public void initData() {
        serverListPresenter = new ServerListPresenter(context,this);
        initServerList();
    }

    //初始化recyclerview
    private void initRecycler1() {
        recycler_view1 = $(R.id.recycler_view1);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false);
        recycler_view1.setLayoutManager(linearLayoutManager);
        mAdapter1 = new ServerListAdapter1(activity, new ArrayList<GuideServiceModel>());
        recycler_view1.setAdapter(mAdapter1);
//        recycler_view1.setNestedScrollingEnabled(false);

        mAdapter1.setOnItemClickListener(new ServerListAdapter1.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                value = mAdapter1.getData(position).getServeType();
                getRefreshData();
            }
        });
    }
    private void initRecycler2() {
        recycler_view2 = $(R.id.recycler_view2);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false);
        recycler_view2.setLayoutManager(linearLayoutManager);
        mAdapter2 = new ServerListAdapter2(activity, new ArrayList<ServerDetailMedel>());
        loadMoreWrapper = new LoadMoreWrapper(mAdapter2);
        recycler_view2.setAdapter(loadMoreWrapper);
//        recycler_view2.setNestedScrollingEnabled(false);

        mAdapter2.setOnItemClickListener(new ServerListAdapter2.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                Intent intent = new Intent(context, ServiceDetailActivity.class);
                intent.putExtra(ServiceDetailActivity.SERVICEID,mAdapter2.getData(position).getId());
                LogUtil.e(mAdapter2.getData(position).getId()+"-------");
                startActivity(intent);
            }

            @Override
            public void onCancelClick(int position) {
                //TODO 把本地数据清除,刷新价格
            }

            @Override
            public void onBookClick(int position) {
                //TODO 其他的定制根据类型弹出不一样的pop，pop选择后修改本地数据并修改列表数据刷新
                popServer = new PopServer(activity, recycler_view2,mAdapter2.getData(position));
                popServer.showPopupWindow(recycler_view2);

            }

            @Override
            public void onCustemClick(int position) {
                //TODO 私人定制 跳转私人定制页面
                Intent intent = new Intent(context, CustomActivity.class);
                intent.putExtra("LOCATION", mAdapter2.getData(position).getAddress());
                startActivity(intent);
            }
        });
    }

    private void getRefreshData() {
        isLoad = true;
        page = Constant.DEFAULT_PAGE;
        isLoadType = 1;
        getData();
    }

    private void initServerList(){
        List<GuideServiceModel> datas2 = model.getTravelGuideServiceInfoVOs();
        mAdapter1.setDatas(datas2);
        if(datas2 !=null && datas2.size() !=0){
            value = datas2.get(0).getServeType();
        }
    }

    public void getData(){
        if(value != 0)
            serverListPresenter.serveGuideServeOrderList(value,Constant.DEFAULT_LIMIT,page, MySelfInfo.getInstance().getDefaultCity(),0,model.getId(),0);
    }

    @Override
    public void serveGuideServeOrderListSuccess(List<ServerDetailMedel> datas) {
        if (isLoadType == 1) {
            mAdapter2.setDatas(datas);
        } else {
            mAdapter2.addDatas(datas);
        }
        isLoad = false;
        if (datas.size() >= Constant.DEFAULT_LIMIT) {
            loadMoreWrapper.setLoadState(loadMoreWrapper.LOADING_COMPLETE);
        } else {
            // 显示加载到底的提示
            loadMoreWrapper.setLoadState(loadMoreWrapper.LOADING_END);
        }
    }

    @Override
    public void serveGuideServeOrderListFailed(String msg) {
        showShortToast(msg);
        isLoad = false;
        loadMoreWrapper.setLoadState(loadMoreWrapper.LOADING_COMPLETE);
    }
}
