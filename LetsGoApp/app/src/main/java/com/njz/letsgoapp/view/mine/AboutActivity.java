package com.njz.letsgoapp.view.mine;

import android.content.Intent;
import android.view.View;

import com.njz.letsgoapp.R;
import com.njz.letsgoapp.base.BaseActivity;
import com.njz.letsgoapp.view.home.GuideContractActivity;
import com.njz.letsgoapp.widget.MineItemView;

/**
 * Created by LGQ
 * Time: 2018/8/9
 * Function:
 */

public class AboutActivity extends BaseActivity {

    MineItemView about_agreement;

    @Override
    public int getLayoutId() {
        return R.layout.activity_about;
    }

    @Override
    public void initView() {
        showLeftAndTitle("关于");

        about_agreement = $(R.id.about_agreement);
        about_agreement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, GuideContractActivity.class);
                intent.putExtra("CONTRACT_TYPE",1);
                startActivity(intent);
            }
        });
    }

    @Override
    public void initData() {

    }
}
