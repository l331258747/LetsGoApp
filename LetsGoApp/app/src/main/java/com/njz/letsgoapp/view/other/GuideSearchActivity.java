package com.njz.letsgoapp.view.other;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.njz.letsgoapp.R;
import com.njz.letsgoapp.adapter.base.EndlessRecyclerOnScrollListener;
import com.njz.letsgoapp.adapter.base.LoadMoreWrapper;
import com.njz.letsgoapp.adapter.other.GuideSearchAdapter;
import com.njz.letsgoapp.base.BaseActivity;
import com.njz.letsgoapp.bean.MySelfInfo;
import com.njz.letsgoapp.bean.home.GuideListModel;
import com.njz.letsgoapp.bean.home.GuideModel;
import com.njz.letsgoapp.bean.server.ServerDetailModel;
import com.njz.letsgoapp.constant.Constant;
import com.njz.letsgoapp.mvp.home.GuideListContract;
import com.njz.letsgoapp.mvp.home.GuideListPresenter;
import com.njz.letsgoapp.view.home.GuideDetailActivity;
import com.njz.letsgoapp.widget.emptyView.EmptyView;
import com.njz.letsgoapp.widget.flowlayout.FlowLayout;
import com.njz.letsgoapp.widget.flowlayout.TagAdapter;
import com.njz.letsgoapp.widget.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by LGQ
 * Time: 2018/12/25
 * Function:
 */

public class GuideSearchActivity extends BaseActivity implements GuideListContract.View{

    private EditText et_search;
    private RecyclerView recyclerView;
    private ImageView iv_left,iv_clean;
    private LinearLayout ll_history;
    private EmptyView view_empty;
    private TagFlowLayout  flowlayout_history;

    private GuideListPresenter mPresenter;
    private GuideSearchAdapter mAdapter;
    private int page = Constant.DEFAULT_PAGE;
    LoadMoreWrapper loadMoreWrapper;
    int isLoadType = 1;//1下拉刷新，2上拉加载
    boolean isLoad = false;//是否在加载，重复加载问题

    Map<String, String> maps = new HashMap<>();

    @Override
    public int getLayoutId() {
        return R.layout.activity_search;
    }

    @Override
    public void initView() {
        hideTitleLayout();

        ll_history = $(R.id.ll_history);
        view_empty = $(R.id.view_empty);
        flowlayout_history = $(R.id.flowlayout_history);


        iv_left = $(R.id.iv_left);
        iv_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        iv_clean = $(R.id.iv_clean);
        iv_clean.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MySelfInfo.getInstance().setSearchGuide(new ArrayList<String>());
                initFlow();
            }
        });

        et_search = $(R.id.et_search);
        recyclerView = $(R.id.recycler_view);

        initEdit();
        initFlow();

        initRecycler();
    }

    public void initFlow() {
        final LayoutInflater mInflater = LayoutInflater.from(activity);
        final List<String> lists = MySelfInfo.getInstance().getSearchGuide();
        TagAdapter adapter1 = new TagAdapter<String>(lists) {
            @Override
            public View getView(FlowLayout parent, int position, String s) {
                TextView tv = (TextView) mInflater.inflate(R.layout.item_flow_space, flowlayout_history, false);
                tv.setText(s);
                return tv;
            }
        };
        flowlayout_history.setAdapter(adapter1);
        flowlayout_history.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                et_search.setText(lists.get(position));
                et_search.setSelection(et_search.getText().toString().length());
                maps = new HashMap<>();
                maps.put("keyWords",lists.get(position));
                mPresenter.guideSortTop10ByLocation("", Constant.GUIDE_TYPE_SYNTHESIZE,Constant.DEFAULT_LIMIT,page,maps);
                return false;
            }
        });
    }

    private void initEdit() {
        et_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                if(actionId != EditorInfo.IME_ACTION_SEARCH) return true;

                if(TextUtils.isEmpty(v.getText().toString()))
                    return true;

                MySelfInfo.getInstance().addSearchGuide(v.getText().toString());

                maps = new HashMap<>();
                maps.put("keyWords",v.getText().toString());
                mPresenter.guideSortTop10ByLocation("", Constant.GUIDE_TYPE_SYNTHESIZE,Constant.DEFAULT_LIMIT,page,maps);
                return false;
            }
        });

        et_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(TextUtils.isEmpty(s.toString())){
                    recyclerView.setVisibility(View.GONE);
                    ll_history.setVisibility(View.VISIBLE);
                    view_empty.setVisible(false);
                    initFlow();
                }
            }
        });
    }

    //初始化recyclerview
    private void initRecycler() {
        recyclerView = $(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false));
        mAdapter = new GuideSearchAdapter(activity, new ArrayList<GuideModel>());
        loadMoreWrapper = new LoadMoreWrapper(mAdapter);
        recyclerView.setAdapter(loadMoreWrapper);

        mAdapter.setOnItemClickListener(new GuideSearchAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                //TODO 进入详情
                Intent intent = new Intent(context, GuideDetailActivity.class);
                intent.putExtra("GUIDEID",mAdapter.getData(position).getId());
                startActivity(intent);
                finish();
            }
        });

        recyclerView.addOnScrollListener(new EndlessRecyclerOnScrollListener() {
            @Override
            public void onLoadMore() {
                if (isLoad || loadMoreWrapper.getLoadState() == LoadMoreWrapper.LOADING_END) return;
                loadMoreWrapper.setLoadState(loadMoreWrapper.LOADING);
                getMoreData();
            }
        });
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

    private void getData(){
        mPresenter.guideSortTop10ByLocation("", Constant.GUIDE_TYPE_SYNTHESIZE,Constant.DEFAULT_LIMIT,page,maps);
    }

    @Override
    public void initData() {
        mPresenter = new GuideListPresenter(context,this);
    }

    @Override
    public void guideSortTop10ByLocationSuccess(GuideListModel models) {
        List<GuideModel> datas = models.getList();

        if (isLoadType == 1) {
            mAdapter.setDatas(datas);
        } else {
            mAdapter.addDatas(datas);
        }
        isLoad = false;
        if (datas.size() >= Constant.DEFAULT_LIMIT) {
            loadMoreWrapper.setLoadState(loadMoreWrapper.LOADING_COMPLETE);
        } else {
            // 显示加载到底的提示
            loadMoreWrapper.setLoadState(loadMoreWrapper.LOADING_END);
        }

        recyclerView.setVisibility(View.VISIBLE);
        ll_history.setVisibility(View.GONE);

        if(mAdapter.getDatas().size() == 0){
            view_empty.setVisible(true);
            view_empty.setEmptyData(R.mipmap.empty_order,"没有搜索到内容哦~");
        }else{
            view_empty.setVisible(false);
        }
    }

    @Override
    public void guideSortTop10ByLocationFailed(String msg) {
        showShortToast(msg);
    }
}
