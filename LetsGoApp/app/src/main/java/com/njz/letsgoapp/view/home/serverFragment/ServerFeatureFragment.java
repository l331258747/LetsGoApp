package com.njz.letsgoapp.view.home.serverFragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.text.TextUtils;
import android.widget.TextView;

import com.njz.letsgoapp.R;
import com.njz.letsgoapp.base.BaseFragment;
import com.njz.letsgoapp.bean.home.ServiceDetailModel;
import com.njz.letsgoapp.util.webview.LWebView;
import com.njz.letsgoapp.widget.emptyView.EmptyView3;

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

    ServiceDetailModel model;

    public static Fragment newInstance(ServiceDetailModel serviceDetailModel) {
        ServerFeatureFragment fragment = new ServerFeatureFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("serviceDetailModel", serviceDetailModel);
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
    }

    @Override
    public void initData() {
        if (!TextUtils.isEmpty(model.getServeFeature()))
            webView.loadDataWithBaseURL(null, model.getServeFeature(), "text/html", "utf-8", null);

        if (!TextUtils.isEmpty(model.getRenegePriceThree()))
            price_introduce_content.setText(model.getCostExplain());
        if (!TextUtils.isEmpty(model.getRenegePriceThree()))
            tv_refund_rule_30.setText(String.format(getResources().getString(R.string.refund_rule_30), model.getRenegePriceThree().replace(",", "-")));
        if (!TextUtils.isEmpty(model.getRenegePriceFive()))
            tv_refund_rule_50.setText(String.format(getResources().getString(R.string.refund_rule_50), model.getRenegePriceFive().replace(",", "-")));

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
}
