package com.njz.letsgoapp.view.serverFragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.widget.TextView;

import com.njz.letsgoapp.R;
import com.njz.letsgoapp.base.BaseFragment;

/**
 * Created by LGQ
 * Time: 2018/12/21
 * Function:私人定制 报价明细
 */

public class CustomOffersFragment extends BaseFragment {

    TextView tv_custom_detail;
    String offerDetail;

    public static Fragment newInstance(String offerDetail) {
        CustomOffersFragment fragment = new CustomOffersFragment();
        Bundle bundle = new Bundle();
        bundle.putString("OFFER_DETAIL", offerDetail);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            offerDetail = bundle.getString("OFFER_DETAIL");
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_custom_offers;
    }

    @Override
    public void initView() {
        tv_custom_detail = $(R.id.tv_custom_detail);
    }

    @Override
    public void initData() {
        tv_custom_detail.setText(offerDetail);
    }

}
