package com.njz.letsgoapp.view.mine;

import android.content.Intent;
import android.view.View;

import com.njz.letsgoapp.R;
import com.njz.letsgoapp.base.BaseActivity;
import com.njz.letsgoapp.constant.Constant;
import com.njz.letsgoapp.dialog.DialogUtil;
import com.njz.letsgoapp.view.other.WebViewActivity;
import com.njz.letsgoapp.widget.MineItemView2;

/**
 * Created by LGQ
 * Time: 2019/3/11
 * Function:
 */

public class ContactUsActivity extends BaseActivity implements View.OnClickListener {

    MineItemView2 mine_customer, mine_wxgzh,mine_wxxcx,mine_network,mine_guide;

    @Override
    public int getLayoutId() {
        return R.layout.activity_contact_us;
    }

    @Override
    public void initView() {
        showLeftAndTitle("联系我们");

        mine_customer = $(R.id.mine_customer);
        mine_wxgzh = $(R.id.mine_wxgzh);
        mine_wxxcx = $(R.id.mine_wxxcx);
        mine_network = $(R.id.mine_network);
        mine_guide = $(R.id.mine_guide);

        mine_customer.setOnClickListener(this);
        mine_wxgzh.setOnClickListener(this);
        mine_wxxcx.setOnClickListener(this);
        mine_network.setOnClickListener(this);
        mine_guide.setOnClickListener(this);
    }

    @Override
    public void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.mine_customer:
                DialogUtil.getInstance().showCustomerMobileDialog(context);
                break;
            case R.id.mine_wxgzh:
                startContent(ContactUsContentActivity.CODE_WXGZH);
                break;
            case R.id.mine_wxxcx:
                startContent(ContactUsContentActivity.CODE_WXXCX);
                break;
            case R.id.mine_guide:
                startContent(ContactUsContentActivity.CODE_GUIDE);
                break;
            case R.id.mine_network:
//                Intent intent = new Intent(context, WebViewActivity.class);
//                intent.putExtra(Constant.EXTRA_URL,"www.njzou.com");
//                startActivity(intent);
                break;
        }
    }

    public void startContent(int type){
        Intent intent = new Intent(context,ContactUsContentActivity.class);
        intent.putExtra("ContactUs_Type", type);
        startActivity(intent);
    }
}
