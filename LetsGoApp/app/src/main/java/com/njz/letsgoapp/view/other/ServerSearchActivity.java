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
import com.njz.letsgoapp.adapter.other.ServerSearchAdapter;
import com.njz.letsgoapp.base.BaseActivity;
import com.njz.letsgoapp.bean.MySelfInfo;
import com.njz.letsgoapp.bean.server.ServerDetailModel;
import com.njz.letsgoapp.constant.Constant;
import com.njz.letsgoapp.mvp.server.ServerListScreenContract;
import com.njz.letsgoapp.mvp.server.ServerListScreenPresenter;
import com.njz.letsgoapp.view.server.ServiceDetailActivity2;
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

public class ServerSearchActivity extends BaseActivity implements ServerListScreenContract.View{

    private EditText et_search;
    private RecyclerView recyclerView;
    private ImageView iv_left,iv_clean;
    private LinearLayout ll_history;
    private EmptyView view_empty;
    private TagFlowLayout flowlayout_history;

    int page = Constant.DEFAULT_PAGE;
    ServerListScreenPresenter mPresenter;
    ServerSearchAdapter mAdapter;

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
                MySelfInfo.getInstance().setSearchServer(new ArrayList<String>());
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
        final List<String> lists = MySelfInfo.getInstance().getSearchServer();
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
                Map<String, String> maps = new HashMap<>();
                maps.put("keyWords",lists.get(position));
                mPresenter.serveGuideServeFilterList(0, Constant.DEFAULT_LIMIT,page,"",0,0,0,Constant.GUIDE_TYPE_SYNTHESIZE,maps);
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

                MySelfInfo.getInstance().addSearchServer(v.getText().toString());

                Map<String, String> maps = new HashMap<>();
                maps.put("keyWords",v.getText().toString());
                mPresenter.serveGuideServeFilterList(0, Constant.DEFAULT_LIMIT,page,"",0,0,0,Constant.GUIDE_TYPE_SYNTHESIZE,maps);
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
        mAdapter = new ServerSearchAdapter(activity, new ArrayList<ServerDetailModel>());
        recyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new ServerSearchAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                //TODO 进入详情
                Intent intent = new Intent(context, ServiceDetailActivity2.class);
                intent.putExtra("SERVICEID",mAdapter.getData(position).getId());
                startActivity(intent);
                finish();
            }
        });

    }

    @Override
    public void initData() {
        mPresenter = new ServerListScreenPresenter(context,this);
    }

    @Override
    public void serveGuideServeFilterListSuccess(List<ServerDetailModel> str) {
        mAdapter.setDatas(str);

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
    public void serveGuideServeFilterListFailed(String msg) {
        showShortToast(msg);
    }
}
