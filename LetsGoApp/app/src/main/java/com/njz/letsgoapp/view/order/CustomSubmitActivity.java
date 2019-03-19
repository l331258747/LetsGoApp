package com.njz.letsgoapp.view.order;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.njz.letsgoapp.R;
import com.njz.letsgoapp.adapter.server.OrderSubmitAdapter;
import com.njz.letsgoapp.base.BaseActivity;
import com.njz.letsgoapp.bean.coupon.OrderCouponModel;
import com.njz.letsgoapp.bean.order.OrderDetailChildModel;
import com.njz.letsgoapp.bean.order.OrderDetailModel;
import com.njz.letsgoapp.bean.order.PayModel;
import com.njz.letsgoapp.bean.server.ServerItem;
import com.njz.letsgoapp.mvp.coupon.OrderCouponContract;
import com.njz.letsgoapp.mvp.coupon.OrderCouponPresenter;
import com.njz.letsgoapp.mvp.order.OrderDetailContract;
import com.njz.letsgoapp.mvp.order.OrderDetailPresenter;
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

public class CustomSubmitActivity extends BaseActivity implements View.OnClickListener,OrderDetailContract.View,OrderCouponContract.View {

    FixedItemEditView login_view_name,login_view_phone,login_view_num;
    SpecialFixedItemEditView et_special;
    RecyclerView recyclerView;

    TextView tv_coupon,tv_total_price,tv_contract,tv_submit;
    RelativeLayout rl_coupon;

    OrderCouponPresenter couponPresenter;
    OrderDetailPresenter detailPresenter;
    Disposable orderCouponDisposable;

    int couponId = -1;
    float couponPrice = 0;
    float payPrice = 0;
    float totalPrice;

    int orderId;
    PayModel payModel;

    @Override
    public void getIntentData() {
        super.getIntentData();
        orderId = intent.getIntExtra("order_id",0);
        payModel = intent.getParcelableExtra("PAY_MODEL");
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
        rl_coupon = $(R.id.rl_coupon);

        login_view_name.getEtView().setEnabled(false);
        login_view_phone.getEtView().setEnabled(false);
        login_view_num.getEtView().setEnabled(false);
        et_special.getEtView().setEnabled(false);

        tv_contract.setOnClickListener(this);
        tv_submit.setOnClickListener(this);
        rl_coupon.setOnClickListener(this);

    }

    @Override
    public void initData() {
        detailPresenter = new OrderDetailPresenter(context,this);
        detailPresenter.orderQueryOrder(orderId);

        couponPresenter = new OrderCouponPresenter(context,this);

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
        payPrice = DecimalUtil.subtract(totalPrice, couponPrice);
        tv_total_price.setText("￥" + payPrice);
    }

    public void getTotalPrice(OrderDetailChildModel model) {
        totalPrice = model.getOrderPrice();
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
            case R.id.rl_coupon:
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
                if(payModel == null) return;
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

    @Override
    public void orderQueryOrderSuccess(OrderDetailModel str) {
        initDetail(str);
    }

    private void initDetail(OrderDetailModel str) {
        login_view_name.setContent(str.getName());
        login_view_phone.setContent(str.getMobile());
        login_view_num.setContent(str.getPersonNum());
        et_special.setContent(TextUtils.isEmpty(str.getSpecialRequire())?"无":str.getSpecialRequire());

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        OrderSubmitAdapter mAdapter = new OrderSubmitAdapter(context, getServerItems(str.getNjzChildOrderVOS().get(0)));
        recyclerView.setAdapter(mAdapter);
        recyclerView.setNestedScrollingEnabled(false);

        getTotalPrice(str.getNjzChildOrderVOS().get(0));

        StringUtils.setHtml(tv_contract, getResources().getString(R.string.guide_service_contract));

        couponPresenter.userCouponChooseCoupon(totalPrice);
    }

    public List<ServerItem> getServerItems(OrderDetailChildModel model){
        List<ServerItem> items = new ArrayList<>();
        ServerItem item = new ServerItem();
        item.setServeNum(model.getServeNum());
        item.setSelectTimeValueList(model.getTravelDate());
        item.setNjzGuideServeId(model.getId());
        item.setTitile(model.getTitle());
        item.setImg(model.getTitleImg());
        item.setPrice(model.getOrderPrice());
        item.setServiceTypeName(model.getServerName());
        item.setServerType(model.getServeType());
        item.setLocation(model.getLocation());
        items.add(item);
        return items;
    }

    @Override
    public void orderQueryOrderFailed(String msg) {
        showShortToast(msg);
    }
}
