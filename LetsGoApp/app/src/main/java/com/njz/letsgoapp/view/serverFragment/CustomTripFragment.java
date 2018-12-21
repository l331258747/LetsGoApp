package com.njz.letsgoapp.view.serverFragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.widget.TextView;

import com.njz.letsgoapp.R;
import com.njz.letsgoapp.base.BaseFragment;
import com.njz.letsgoapp.util.webview.LWebView;

/**
 * Created by LGQ
 * Time: 2018/12/21
 * Function: 私人定制行程介绍
 */

public class CustomTripFragment extends BaseFragment {

    LWebView webView;
    TextView tv_refund_rule_30,tv_refund_rule_50;
    String travelDesign;
    String renegePriceThree;
    String renegePriceFive;

    public static Fragment newInstance(String travelDesign,String renegePriceThree,String renegePriceFive) {
        CustomTripFragment fragment = new CustomTripFragment();
        Bundle bundle = new Bundle();
        bundle.putString("TRAVEL_DESIGN", travelDesign);
        bundle.putString("RENEGE_PRICE_THREE", renegePriceThree);
        bundle.putString("RENEGE_PRICE_FIVE", renegePriceFive);
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
    }

    @Override
    public void initData() {
        initInfo();
    }

    public void initInfo(){
        if (!TextUtils.isEmpty(renegePriceThree))
            tv_refund_rule_30.setText(String.format(getResources().getString(R.string.refund_rule_30), renegePriceThree.replace(",", "-")));
        if (!TextUtils.isEmpty(renegePriceFive))
            tv_refund_rule_50.setText(String.format(getResources().getString(R.string.refund_rule_50), renegePriceFive.replace(",", "-")));

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
}
