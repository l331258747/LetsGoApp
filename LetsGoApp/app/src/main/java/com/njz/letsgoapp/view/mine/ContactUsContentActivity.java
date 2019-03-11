package com.njz.letsgoapp.view.mine;

import com.njz.letsgoapp.R;
import com.njz.letsgoapp.base.BaseActivity;

/**
 * Created by LGQ
 * Time: 2019/3/11
 * Function:
 */

public class ContactUsContentActivity extends BaseActivity {

    public static final int CODE_WX = 0;
    public static final int CODE_WXXCX = 1;
    public static final int CODE_GUIDE = 2;

    int type = 0;


    @Override
    public int getLayoutId() {
        type = getIntent().getIntExtra("ContactUs_Type", 0);

        switch (type) {
            case CODE_WX:
                return R.layout.layout_contact_us_wx;
            case CODE_WXXCX:
                return R.layout.layout_contact_us_wxxcx;
            case CODE_GUIDE:
                return R.layout.layout_contact_us_guide;
        }
        return R.layout.layout_contact_us_wx;
    }

    @Override
    public void initView() {
        switch (type) {
            case CODE_WX:
                showLeftAndTitle("微信公众号");
            case CODE_WXXCX:
                showLeftAndTitle("游客小程序");
            case CODE_GUIDE:
                showLeftAndTitle("向导入驻");
        }
    }

    @Override
    public void initData() {

    }
}
