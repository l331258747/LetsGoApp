package com.njz.letsgoapp.view.server;

import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.njz.letsgoapp.R;
import com.njz.letsgoapp.base.BaseActivity;
import com.njz.letsgoapp.dialog.DialogUtil;
import com.njz.letsgoapp.widget.NumberView;

/**
 * Created by LGQ
 * Time: 2018/12/6
 * Function:
 */

public class CustomActivity extends BaseActivity implements View.OnClickListener {

    EditText et_price, et_name, et_phone, et_special;
    TextView tv_time, tv_count, tv_submit, tv_location;
    ImageView iv_name_cancel, iv_phone_cancel, iv_special_cancel;

    String location;

    @Override
    public void getIntentData() {
        super.getIntentData();
        location = intent.getStringExtra("LOCATION");
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_custom;
    }

    @Override
    public void initView() {

        showLeftAndTitle("私人定制");

        tv_location = $(R.id.tv_location);
        et_price = $(R.id.et_price);
        et_name = $(R.id.et_name);
        et_phone = $(R.id.et_phone);
        et_special = $(R.id.et_special);
        tv_time = $(R.id.tv_time);
        tv_count = $(R.id.tv_count);
        tv_submit = $(R.id.tv_submit);
        iv_name_cancel = $(R.id.iv_name_cancel);
        iv_phone_cancel = $(R.id.iv_phone_cancel);
        iv_special_cancel = $(R.id.iv_special_cancel);

        tv_time.setOnClickListener(this);
        tv_count.setOnClickListener(this);
        tv_submit.setOnClickListener(this);
        iv_name_cancel.setOnClickListener(this);
        iv_phone_cancel.setOnClickListener(this);
        iv_special_cancel.setOnClickListener(this);

        tv_location.setText(location);
    }

    @Override
    public void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_time:
                //TODO 日期选择
                break;
            case R.id.tv_count:
                final AlertDialog.Builder builder = new AlertDialog.Builder(context);
                View view = LayoutInflater.from(context).inflate(R.layout.dialog_custom_count, null);
                builder.setView(view);
                final AlertDialog dialog = builder.show();
                final NumberView numberView_adult = view.findViewById(R.id.numberView_adult);
                final NumberView numberView_child = view.findViewById(R.id.numberView_child);
                view.findViewById(R.id.tv_submit).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        tv_count.setText(numberView_adult.getNum()+"成人"+numberView_child.getNum()+"儿童");
                    }
                });

                break;
            case R.id.tv_submit:
                //TODO 提交
                break;
            case R.id.iv_name_cancel:
                et_name.setText("");
                break;
            case R.id.iv_phone_cancel:
                et_phone.setText("");
                break;
            case R.id.iv_special_cancel:
                et_special.setText("");
                break;

        }
    }
}
