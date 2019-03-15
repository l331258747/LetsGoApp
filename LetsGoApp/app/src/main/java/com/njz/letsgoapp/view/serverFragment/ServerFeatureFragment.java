package com.njz.letsgoapp.view.serverFragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.njz.letsgoapp.R;
import com.njz.letsgoapp.base.BaseFragment;
import com.njz.letsgoapp.bean.server.ServerDetailModel;
import com.njz.letsgoapp.constant.Constant;
import com.njz.letsgoapp.util.webview.LWebView;
import com.njz.letsgoapp.widget.emptyView.EmptyView3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by LGQ
 * Time: 2018/12/3
 * Function:
 */

public class ServerFeatureFragment extends BaseFragment {

    LWebView webView;
    NestedScrollView scrollView;
    TextView price_introduce_content, tv_refund_rule_30, tv_refund_rule_50;
    EmptyView3 view_empty;
    LinearLayout ll_refund_rule;

    ServerDetailModel model;

    public static Fragment newInstance(ServerDetailModel serverDetailModel) {
        ServerFeatureFragment fragment = new ServerFeatureFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("serviceDetailModel", serverDetailModel);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            model = bundle.getParcelable("serviceDetailModel");
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_service_feature;
    }

    @Override
    public void initView() {
        webView = $(R.id.webview);
        scrollView = $(R.id.scrollView);
        view_empty = $(R.id.view_empty);
        price_introduce_content = $(R.id.price_introduce_content);
        tv_refund_rule_30 = $(R.id.tv_refund_rule_30);
        tv_refund_rule_50 = $(R.id.tv_refund_rule_50);
        ll_refund_rule = $(R.id.ll_refund_rule);
    }

    @Override
    public void initData() {

        if(model.getServeType() == Constant.SERVER_TYPE_CUSTOM_ID){
            ll_refund_rule.setVisibility(View.GONE);
        }else{
            if (!TextUtils.isEmpty(model.getRenegePriceThree())) {
                List<String> lists = getValue(model.getRenegePriceThree(),"0.3");
                tv_refund_rule_30.setText(String.format(getResources().getString(R.string.refund_rule_30),
                        lists.get(0) + "-" + lists.get(1), getProportion(lists.get(2))));
            }
            if (!TextUtils.isEmpty(model.getRenegePriceFive())) {
                List<String> lists = getValue(model.getRenegePriceFive(),"0.5");
                tv_refund_rule_50.setText(String.format(getResources().getString(R.string.refund_rule_50),
                        lists.get(0) + "-" + lists.get(1), getProportion(lists.get(2))));
            }
        }

        if (!TextUtils.isEmpty(model.getCostExplain()))
            price_introduce_content.setText(model.getCostExplain());
        if (!TextUtils.isEmpty(model.getServeFeature())) {
            webView.loadDataWithBaseURL(null, model.getServeFeature(), "text/html", "utf-8", null);
            view_empty.setVisible(false);
        } else {
            view_empty.setVisible(true);
            view_empty.setEmptyData(R.mipmap.empty_guide_story, "他很害羞哦，什么都没留下~");
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
//         webview 需要加载空界面来释放资源
        webView.loadUrl("about:blank");
        webView.clearCache(false);
        webView.destroy();
    }

    public String getProportion(String str){
        int value = (int) (Float.valueOf(str) * 100);
        return value + "";
    }

    public List<String> getValue(String str,String def) {
        String[] strs = str.split(",");
        List<String> lists = new ArrayList<>(Arrays.asList(strs));
        if (lists.size() != 3) {
            lists.add(def);
        }else{
            if(Float.valueOf(lists.get(2)) <= 0){
                lists.set(2,def);
            }
        }
        return lists;
    }
}
