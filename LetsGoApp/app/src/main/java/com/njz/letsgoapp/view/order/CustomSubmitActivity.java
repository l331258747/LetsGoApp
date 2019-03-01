package com.njz.letsgoapp.view.order;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.njz.letsgoapp.R;
import com.njz.letsgoapp.adapter.server.OrderSubmitAdapter;
import com.njz.letsgoapp.base.BaseActivity;
import com.njz.letsgoapp.bean.coupon.OrderCouponModel;
import com.njz.letsgoapp.bean.order.PayModel;
import com.njz.letsgoapp.bean.server.ServerItem;
import com.njz.letsgoapp.mvp.coupon.OrderCouponContract;
import com.njz.letsgoapp.mvp.coupon.OrderCouponPresenter;
import com.njz.letsgoapp.util.DecimalUtil;
import com.njz.letsgoapp.util.StringUtils;
import com.njz.letsgoapp.util.rxbus.RxBus2;
import com.njz.letsgoapp.util.rxbus.busEvent.OrderCouponEvent;
import com.njz.letsgoapp.view.coupon.OrderCouponActivity;
import com.njz.letsgoapp.view.home.GuideContractActivity;
import com.njz.letsgoapp.view.pay.PayActivity;
import com.njz.letsgoapp.widget.FixedItemEditView;
import com.njz.letsgoapp.widget.SpecialFixedItemEditView;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by LGQ
 * Time: 2019/2/28
 * Function:
 */

public class CustomSubmitActivity extends BaseActivity implements View.OnClickListener,OrderCouponContract.View {

    FixedItemEditView login_view_name,login_view_phone,login_view_num;
    SpecialFixedItemEditView et_special;
    RecyclerView recyclerView;

    TextView tv_coupon,tv_total_price,tv_contract,tv_submit;

    OrderCouponPresenter couponPresenter;
    Disposable orderCouponDisposable;

    int couponId = -1;
    float couponPrice = 0;
    float payPrice = 0;
    float totalPrice;

    List<ServerItem> serverItems;
    PayModel payModel;
    String name;
    String tel;
    String personNum;
    String special;

    @Override
    public void getIntentData() {
        super.getIntentData();
        serverItems = intent.getParcelableArrayListExtra("SERVICEMODEL");
        payModel = intent.getParcelableExtra("PAY_MODEL");
        name = intent.getStringExtra("name");
        tel = intent.getStringExtra("tel");
        personNum = intent.getStringExtra("personNum");
        special = intent.getStringExtra("special");
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_custom_submit;
    }

    @Override
    public void initView() {
        showLeftAndTitle("确认订单");

        login_view_name = $(R.id.login_view_name);
        login_view_phone = $(R.id.login_view_phone);
        login_view_num = $(R.id.login_view_num);
        et_special = $(R.id.et_special);
        recyclerView = $(R.id.recycler_view);
        tv_coupon = $(R.id.tv_coupon);
        tv_total_price = $(R.id.tv_total_price);
        tv_contract = $(R.id.tv_contract);
        tv_submit = $(R.id.tv_submit);

        login_view_name.getEtView().setEnabled(false);
        login_view_phone.getEtView().setEnabled(false);
        login_view_num.getEtView().setEnabled(false);
        et_special.getEtView().setEnabled(false);

        tv_contract.setOnClickListener(this);
        tv_submit.setOnClickListener(this);
        tv_coupon.setOnClickListener(this);

    }

    @Override
    public void initData() {

        login_view_name.setContent(name);
        login_view_phone.setContent(tel);
        login_view_num.setContent(personNum);
        et_special.setContent(TextUtils.isEmpty(special)?"无":special);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        OrderSubmitAdapter mAdapter = new OrderSubmitAdapter(context, serverItems);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setNestedScrollingEnabled(false);

        getTotalPrice();

        StringUtils.setHtml(tv_contract, getResources().getString(R.string.guide_service_contract));

        couponPresenter = new OrderCouponPresenter(context,this);
        couponPresenter.userCouponChooseCoupon(totalPrice);

        orderCouponDisposable = RxBus2.getInstance().toObservable(OrderCouponEvent.class, new Consumer<OrderCouponEvent>() {
            @Override
            public void accept(OrderCouponEvent orderCouponEvent) throws Exception {
                if(orderCouponEvent.getId() == -1){
                    couponPresenter.userCouponChooseCoupon(totalPrice);
                    couponPrice = 0;
                    couponId = -1;
                }else{
                    tv_coupon.setTextColor(ContextCompat.getColor(context,R.color.color_theme));
                    tv_coupon.setText("-￥"+orderCouponEvent.getPrice());
                    couponPrice = orderCouponEvent.getPrice();
                    couponId = orderCouponEvent.getId();
                }
                getPayPrice(couponPrice);
            }
        });
    }

    public void getPayPrice(float couponPrice){
        payPrice = totalPrice - couponPrice;
        tv_total_price.setText("￥" + payPrice);
    }

    public void getTotalPrice() {
        totalPrice = 0;
        for (ServerItem model : serverItems) {
            float price = DecimalUtil.multiply(model.getPrice() , model.getServeNum());
            totalPrice = DecimalUtil.add(totalPrice , price);
        }
        getPayPrice(0);
    }

    //TODO paymodel里面传入
    public List<Integer> getCouponIds(){
        List<Integer> couponIds = new ArrayList<>();
        if(couponId != -1){
            couponIds.add(couponId);
        }
        return couponIds;
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.tv_coupon:
                intent = new Intent(context, OrderCouponActivity.class);
                intent.putExtra("couponId",couponId);
                intent.putExtra("totalOrderPrice",totalPrice);
                startActivity(intent);
                break;
            case R.id.tv_contract:
                intent = new Intent(context, GuideContractActivity.class);
                intent.putExtra("CONTRACT_TYPE",0);
                startActivity(intent);
                break;
            case R.id.tv_submit:
                payModel.setTotalAmount(payPrice+"");
                PayActivity.startActivity(context, payModel,getCouponIds());
                break;
        }
    }

    @Override
    public void userCouponChooseCouponSuccess(OrderCouponModel model) {
        if(model.getCount() == 0){
            tv_coupon.setTextColor(ContextCompat.getColor(context,R.color.color_99));
            tv_coupon.setText("暂无可用");
        }else{
            tv_coupon.setTextColor(ContextCompat.getColor(context,R.color.color_theme));
            tv_coupon.setText(model.getCount()+"张可用");
        }
    }

    @Override
    public void userCouponChooseCouponFailed(String msg) {
        showShortToast(msg);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (orderCouponDisposable != null && !orderCouponDisposable.isDisposed())
            orderCouponDisposable.dispose();
    }
}
