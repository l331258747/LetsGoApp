package com.njz.letsgoapp.view.other;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.widget.EditText;
import android.widget.TextView;

import com.njz.letsgoapp.R;
import com.njz.letsgoapp.adapter.other.SearchAdapter;
import com.njz.letsgoapp.base.BaseActivity;
import com.njz.letsgoapp.bean.other.SearchCityModel;
import com.njz.letsgoapp.mvp.other.CitySearchContract;
import com.njz.letsgoapp.mvp.other.CitySearchPresenter;
import com.njz.letsgoapp.util.rxbus.RxBus2;
import com.njz.letsgoapp.util.rxbus.busEvent.CityPickEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LGQ
 * Time: 2018/10/12
 * Function:
 */

public class SearchActivity extends BaseActivity implements CitySearchContract.View{

    EditText et_search;
    private RecyclerView recyclerView;
    private SearchAdapter mAdapter;

    CitySearchPresenter mPresenter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_search;
    }

    @Override
    public void initView() {
        hideTitleLayout();

        et_search = $(R.id.et_search);
        recyclerView = $(R.id.recycler_view);

        et_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                if(TextUtils.isEmpty(v.getText().toString()))
                    return true;
                mPresenter.regionFuzzyBySpell(v.getText().toString());
                showShortToast("搜索");

                return false;
            }
        });

        initRecycler();

    }


    //初始化recyclerview
    private void initRecycler() {
        recyclerView = $(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false));
        mAdapter = new SearchAdapter(activity, new ArrayList<SearchCityModel>());
        recyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new SearchAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                RxBus2.getInstance().post(new CityPickEvent(mAdapter.getItem(position).getName()));
                finish();
            }
        });

    }

    @Override
    public void initData() {
        mPresenter = new CitySearchPresenter(context,this);
    }

    @Override
    public void regionFuzzyBySpellSuccess(List<SearchCityModel> models) {
        mAdapter.setData(models);
    }

    @Override
    public void regionFuzzyBySpellFailed(String msg) {
        showShortToast(msg);
    }
}
