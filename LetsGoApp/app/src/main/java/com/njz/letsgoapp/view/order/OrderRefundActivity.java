package com.njz.letsgoapp.view.order;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.njz.letsgoapp.R;
import com.njz.letsgoapp.adapter.order.OrderRefundAdapter;
import com.njz.letsgoapp.base.BaseActivity;
import com.njz.letsgoapp.dialog.DialogUtil;
import com.njz.letsgoapp.widget.FixedItemEditViewNoLine;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LGQ
 * Time: 2018/9/26
 * Function:
 */

public class OrderRefundActivity extends BaseActivity implements View.OnClickListener {

    private RecyclerView recyclerView;
    private TextView tv_tips, tv_reason, tv_submit, tv_submit2, tv_minus_price, tv_last_price;
    private LinearLayout ll_reason, ll_call_custom, ll_call_guide;
    private EditText et_special;
    private FixedItemEditViewNoLine view_name, view_phone;
    private RelativeLayout rl_price;

    private List<String> reasons;

    private OrderRefundAdapter mAdapter;


    @Override
    public int getLayoutId() {
        return R.layout.activity_order_refund;
    }

    @Override
    public void initView() {
        tv_tips = $(R.id.tv_tips);
        tv_reason = $(R.id.tv_reason);
        ll_reason = $(R.id.ll_reason);
        ll_call_custom = $(R.id.ll_call_custom);
        ll_call_guide = $(R.id.ll_call_guide);
        et_special = $(R.id.et_special);
        view_name = $(R.id.view_name);
        view_phone = $(R.id.view_phone);
        tv_submit2 = $(R.id.tv_submit2);
        tv_minus_price = $(R.id.tv_minus_price);
        tv_last_price = $(R.id.tv_last_price);
        rl_price = $(R.id.rl_price);
        tv_submit = $(R.id.tv_submit);
        view_phone.setEtInputType(InputType.TYPE_CLASS_NUMBER);

        ll_reason.setOnClickListener(this);
        ll_call_custom.setOnClickListener(this);
        ll_call_guide.setOnClickListener(this);

    }

    @Override
    public void initData() {
        reasons = new ArrayList<>();
        reasons.add("原因1");
        reasons.add("原因2");
        reasons.add("原因3");
        reasons.add("原因4");

    }

    //初始化recyclerview
    private void initRecycler() {
        recyclerView = $(R.id.recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        mAdapter = new OrderRefundAdapter(activity);
        recyclerView.setAdapter(mAdapter);

        recyclerView.setNestedScrollingEnabled(false);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_reason:
                //条件选择器
                OptionsPickerView pvOptions = new OptionsPickerBuilder(context,
                        new OnOptionsSelectListener() {
                            @Override
                            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                                tv_reason.setText(reasons.get(options1));
                            }
                        })
                        .build();
                pvOptions.setPicker(reasons, null, null);
                pvOptions.show();
                break;
            case R.id.ll_call_custom:
                DialogUtil.getInstance().getDefaultDialog(context, "提示", "13211111111", "呼叫", new DialogUtil.DialogCallBack() {
                    @Override
                    public void exectEvent(DialogInterface alterDialog) {
                        Intent dialIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + "13211111111"));
                        context.startActivity(dialIntent);
                        alterDialog.dismiss();
                    }
                }).show();
                break;
            case R.id.ll_call_guide:
                DialogUtil.getInstance().getDefaultDialog(context, "提示", "13211111111", "呼叫", new DialogUtil.DialogCallBack() {
                    @Override
                    public void exectEvent(DialogInterface alterDialog) {
                        Intent dialIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + "13211111111"));
                        context.startActivity(dialIntent);
                        alterDialog.dismiss();
                    }
                }).show();
                break;
        }
    }
}
