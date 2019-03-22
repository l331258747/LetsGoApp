package com.njz.letsgoapp.view.order;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.text.TextUtils;
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
import com.njz.letsgoapp.base.ActivityCollect;
import com.njz.letsgoapp.base.BaseActivity;
import com.njz.letsgoapp.bean.EmptyModel;
import com.njz.letsgoapp.bean.order.OrderRefundChildModel;
import com.njz.letsgoapp.bean.order.OrderRefundModel;
import com.njz.letsgoapp.bean.other.ConfigChildModel;
import com.njz.letsgoapp.bean.other.ConfigModel;
import com.njz.letsgoapp.constant.Constant;
import com.njz.letsgoapp.dialog.DialogUtil;
import com.njz.letsgoapp.mvp.order.OrderRefundContract;
import com.njz.letsgoapp.mvp.order.OrderRefundPresenter;
import com.njz.letsgoapp.mvp.other.ConfigContract;
import com.njz.letsgoapp.mvp.other.ConfigPresenter;
import com.njz.letsgoapp.util.AppUtils;
import com.njz.letsgoapp.view.homeFragment.HomeActivity;
import com.njz.letsgoapp.widget.FixedItemEditViewNoLine;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LGQ
 * Time: 2018/9/26
 * Function:
 */

public class OrderRefundActivity extends BaseActivity implements View.OnClickListener,OrderRefundContract.View,ConfigContract.View{

    private RecyclerView recyclerView;
    private TextView tv_tips, tv_reason, tv_submit;
    private LinearLayout ll_reason, ll_call_custom, ll_call_guide;
    private EditText et_special;
    private FixedItemEditViewNoLine view_name, view_phone;
    private TextView tv_minus_price,tv_coupon_price,tv_last_price;

    private List<String> reasons;

    private OrderRefundAdapter mAdapter;

    private OrderRefundPresenter mPresenter;
    private ConfigPresenter configPresenter;

    int id;
    List<Integer> childIds;
    String phone;
    String name;
    int status;

    String guideMobile;

    @Override
    public void getIntentData() {
        super.getIntentData();
        id = intent.getIntExtra("id",0);
        childIds = intent.getIntegerArrayListExtra("childIds");
        if(childIds == null)
            childIds = new ArrayList<>();
        phone = intent.getStringExtra("phone");
        name = intent.getStringExtra("name");
        guideMobile = intent.getStringExtra("guideMobile");
        status = intent.getIntExtra("status",0);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_order_refund;
    }

    @Override
    public void initView() {
        showLeftAndTitle("申请退款");

        tv_coupon_price = $(R.id.tv_coupon_price);

        tv_tips = $(R.id.tv_tips);
        tv_reason = $(R.id.tv_reason);
        ll_reason = $(R.id.ll_reason);
        ll_call_custom = $(R.id.ll_call_custom);
        ll_call_guide = $(R.id.ll_call_guide);
        et_special = $(R.id.et_special);
        view_name = $(R.id.view_name);
        view_phone = $(R.id.view_phone);
        tv_minus_price = $(R.id.tv_minus_price);
        tv_last_price = $(R.id.tv_last_price);
        tv_submit = $(R.id.tv_submit);
        view_phone.setEtInputType(InputType.TYPE_CLASS_NUMBER);

        ll_reason.setOnClickListener(this);
        ll_call_custom.setOnClickListener(this);
        ll_call_guide.setOnClickListener(this);

        tv_submit.setOnClickListener(this);

        initRecycler();

    }

    @Override
    public void initData() {
        reasons = new ArrayList<>();

        view_name.setEtContent(name);
        view_name.getEtView().setTextColor(ContextCompat.getColor(context,R.color.color_99));
        view_phone.setEtContent(phone);
        view_phone.getEtView().setTextColor(ContextCompat.getColor(context,R.color.color_99));

        mPresenter = new OrderRefundPresenter(context,this);
        configPresenter = new ConfigPresenter(context,this);

        if(status == Constant.ORDER_TRAVEL_WAIT){
            tv_tips.setVisibility(View.GONE);
        }else{
            //TODO 退款分析
            tv_tips.setVisibility(View.VISIBLE);
        }

        mPresenter.orderRefundRefundAnalysis(id,childIds);

        List<String> values = new ArrayList<>();
        values.add(Constant.CONFIG_TKYY);
        configPresenter.guideGetGuideMacros(values);

    }

    //初始化recyclerview
    private void initRecycler() {
        recyclerView = $(R.id.recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        mAdapter = new OrderRefundAdapter(activity,new ArrayList<OrderRefundChildModel>());
        recyclerView.setAdapter(mAdapter);

        recyclerView.setNestedScrollingEnabled(false);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_reason:
                if(reasons.size() == 0) return;
                AppUtils.HideKeyboard(ll_reason);
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
                DialogUtil.getInstance().showCustomerMobileDialog(context);
                break;
            case R.id.ll_call_guide:
                DialogUtil.getInstance().showGuideMobileDialog(context,guideMobile);
                break;
            case R.id.tv_submit:
                if(TextUtils.isEmpty(tv_reason.getText().toString())){
                    showShortToast("请选择退款原因");
                    return;
                }

                DialogUtil.getInstance().getDefaultDialog(context, "是否确认退款?", new DialogUtil.DialogCallBack() {
                    @Override
                    public void exectEvent(DialogInterface alterDialog) {
                        mPresenter.orderRefundAliRefund(id,childIds,tv_reason.getText().toString(),et_special.getText().toString());
                    }
                }).show();
                break;

        }
    }

    @Override
    public void orderRefundAliRefundSuccess(EmptyModel str) {
        showShortToast("操作成功");
        HomeActivity activity = (HomeActivity) ActivityCollect.getAppCollect().findActivity(HomeActivity.class);
        activity.setTabIndex(2);
        activity.getOrderFragment().setIndex(3);
        finish();
    }

    @Override
    public void orderRefundAliRefundFailed(String msg) {
        showShortToast(msg);
    }

    @Override
    public void orderRefundRefundAnalysisSuccess(OrderRefundModel str) {
        mAdapter.setData(str.getNjzChildOrderToRefundVOS());
        mAdapter.setOrderId(str.getOrderId());

        tv_minus_price.setText("-￥" + str.getDefaultMoney());
        tv_coupon_price.setText("-￥" + str.getCouponPrice());
        tv_last_price.setText("￥" + str.getRefundMoney());
    }

    @Override
    public void orderRefundRefundAnalysisFailed(String msg) {
        showShortToast(msg);
    }

    @Override
    public void guideGetGuideMacrosSuccess(List<ConfigModel> models) {
        if(models == null || models.size() == 0) return;
        for (ConfigChildModel childModel : models.get(0).getList()){
            reasons.add(childModel.getName());
        }
    }

    @Override
    public void guideGetGuideMacrosFailed(String msg) {
        showShortToast(msg);
    }
}
