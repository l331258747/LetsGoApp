package com.njz.letsgoapp.view.serverFragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.widget.TextView;

import com.njz.letsgoapp.R;
import com.njz.letsgoapp.base.BaseFragment;
import com.njz.letsgoapp.constant.Constant;
import com.njz.letsgoapp.util.webview.LWebView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by LGQ
 * Time: 2018/12/21
 * Function: 私人定制行程介绍
 */

public class CustomTripFragment extends BaseFragment {

    LWebView webView;
    TextView tv_refund_rule_30,tv_refund_rule_50,tv_refund_100;
    String travelDesign;
    String renegePriceThree;
    String renegePriceFive;
    int serverType;

    public static Fragment newInstance(String travelDesign,String renegePriceThree,String renegePriceFive,int serverType) {
        CustomTripFragment fragment = new CustomTripFragment();
        Bundle bundle = new Bundle();
        bundle.putString("TRAVEL_DESIGN", travelDesign);
        bundle.putString("RENEGE_PRICE_THREE", renegePriceThree);
        bundle.putString("RENEGE_PRICE_FIVE", renegePriceFive);
        bundle.putInt("serverType", serverType);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            travelDesign = bundle.getString("TRAVEL_DESIGN");
            renegePriceThree = bundle.getString("RENEGE_PRICE_THREE");
            renegePriceFive = bundle.getString("RENEGE_PRICE_FIVE");
            serverType = bundle.getInt("serverType");
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_custom_trip;
    }

    @Override
    public void initView() {
        webView = $(R.id.webview);
        tv_refund_rule_30 = $(R.id.tv_refund_rule_30);
        tv_refund_rule_50 = $(R.id.tv_refund_rule_50);
        tv_refund_100 = $(R.id.tv_refund_100);
    }

    @Override
    public void initData() {
        initInfo();
    }

    public void initInfo(){
        if (!TextUtils.isEmpty(renegePriceThree)) {
            List<String> lists = getValue(renegePriceThree,"0.3");
            tv_refund_rule_30.setText(String.format(getResources().getString(R.string.refund_rule_30),
                    lists.get(0) + "-" + lists.get(1), getProportion(lists.get(2))));
        }
        if (!TextUtils.isEmpty(renegePriceFive)) {
            List<String> lists = getValue(renegePriceFive,"0.5");
            tv_refund_rule_50.setText(String.format(getResources().getString(R.string.refund_rule_50),
                    lists.get(0) + "-" + lists.get(1), getProportion(lists.get(2))));
        }

        if(serverType == Constant.SERVER_TYPE_CUSTOM_ID){
            tv_refund_100.setText("私人定制订单不支持行程中退款；");
        }

        if (!TextUtils.isEmpty(travelDesign)) {
            webView.loadDataWithBaseURL(null, travelDesign, "text/html", "utf-8", null);
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
