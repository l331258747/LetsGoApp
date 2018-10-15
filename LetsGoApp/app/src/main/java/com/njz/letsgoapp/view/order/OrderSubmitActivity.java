package com.njz.letsgoapp.view.order;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.njz.letsgoapp.R;
import com.njz.letsgoapp.adapter.order.OrderSubmitAdapter;
import com.njz.letsgoapp.base.BaseActivity;
import com.njz.letsgoapp.bean.MySelfInfo;
import com.njz.letsgoapp.bean.home.GuideServiceModel;
import com.njz.letsgoapp.bean.home.ServiceItem;
import com.njz.letsgoapp.bean.order.PayModel;
import com.njz.letsgoapp.bean.send.SendChildOrderModel;
import com.njz.letsgoapp.bean.send.SendOrderModel;
import com.njz.letsgoapp.constant.Constant;
import com.njz.letsgoapp.mvp.order.OrderCreateContract;
import com.njz.letsgoapp.mvp.order.OrderCreatePresenter;
import com.njz.letsgoapp.util.LoginUtil;
import com.njz.letsgoapp.util.StringUtils;
import com.njz.letsgoapp.view.home.GuideContractActivity;
import com.njz.letsgoapp.view.pay.PayActivity;
import com.njz.letsgoapp.widget.FixedItemTextView;
import com.njz.letsgoapp.widget.FixedItemEditView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LGQ
 * Time: 2018/8/16
 * Function: 确认订单
 */

public class OrderSubmitActivity extends BaseActivity implements View.OnClickListener,OrderCreateContract.View{

    public static final String SERVICEMODEL = "SERVICEMODEL";
    public static final String GUIDE_ID = "GUIDE_ID";
    public static final String LOCATION = "LOCATION";

    FixedItemEditView login_view_name, login_view_phone;
    FixedItemTextView fixed_view_city;
    RecyclerView recyclerView;
    TextView tv_contract, tv_submit;
    EditText et_special;

    OrderCreatePresenter mPresenter;

    List<GuideServiceModel> serviceModels;
    int guideId;
    float totalPrice;

    @Override
    public void getIntentData() {
        super.getIntentData();
        serviceModels = intent.getParcelableArrayListExtra(SERVICEMODEL);
        guideId = intent.getIntExtra(GUIDE_ID, 0);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_order_submit;
    }

    @Override
    public void initView() {

        showLeftAndTitle("确认订单");

        login_view_name = $(R.id.login_view_name);
        login_view_name.setEtInputType(InputType.TYPE_CLASS_TEXT);
        login_view_phone = $(R.id.login_view_phone);
        login_view_phone.setEtInputType(InputType.TYPE_CLASS_NUMBER);
        fixed_view_city = $(R.id.fixed_view_city);
        recyclerView = $(R.id.recycler_view);
        tv_contract = $(R.id.tv_contract);
        tv_submit = $(R.id.tv_submit);
        et_special = $(R.id.et_special);

        StringUtils.setHtml(tv_contract, getResources().getString(R.string.guide_service_contract));

        tv_contract.setOnClickListener(this);
        tv_submit.setOnClickListener(this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        OrderSubmitAdapter mAdapter = new OrderSubmitAdapter(context, getData());
        recyclerView.setAdapter(mAdapter);

        recyclerView.setNestedScrollingEnabled(false);

        et_special.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // scrollview 与 edittext 滑动冲突
                v.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });
    }


    @Override
    public void initData() {
        fixed_view_city.setContent(MySelfInfo.getInstance().getDefaultCity());
        getTotalPrice();

        mPresenter = new OrderCreatePresenter(context,this);
    }


    public List<ServiceItem> getData() {
        List<ServiceItem> serviceItems = new ArrayList<>();
        for (GuideServiceModel model : serviceModels) {
            serviceItems.addAll(model.getServiceItems());
        }
        return serviceItems;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_contract:
                startActivity(new Intent(context, GuideContractActivity.class));
                break;
            case R.id.tv_submit:
                if(!LoginUtil.verifyName(login_view_name.getEtContent()))
                    return;
                if(!LoginUtil.verifyPhone(login_view_phone.getEtContent()))
                    return;
                mPresenter.orderCreateOrder(getOrderData());
                break;
        }
    }

    public void getTotalPrice() {
        totalPrice = 0;
        for (GuideServiceModel model : serviceModels) {
            for (ServiceItem item : model.getServiceItems()) {
                if (TextUtils.equals(item.getValue(),Constant.SERVICE_TYPE_SHORT_GUIDE)
                        || TextUtils.equals(item.getValue() , Constant.SERVICE_TYPE_SHORT_CAR)) {
                    totalPrice = item.getPrice() * item.getTimeDay() + totalPrice;
                } else {
                    totalPrice = item.getPrice() * item.getNumber() * item.getTimeDay() + totalPrice;
                }
            }
        }
        tv_submit.setText("立即预定（￥" + totalPrice + "）");
    }

    public SendOrderModel getOrderData() {
        List<ServiceItem> sis = getData();
        SendOrderModel sendOrderModel = new SendOrderModel();
        sendOrderModel.setMobile(login_view_phone.getEtContent());
        sendOrderModel.setName(login_view_name.getEtContent());
        sendOrderModel.setGuideId(guideId);
        sendOrderModel.setLocation(fixed_view_city.getContent());
        sendOrderModel.setSpecialRequire(et_special.getText().toString());
        sendOrderModel.setEarlyOrderPrice(totalPrice);

        List<SendChildOrderModel> sendChildOrders = new ArrayList<>();
        for (ServiceItem si : sis) {
            SendChildOrderModel sendChildOrder = new SendChildOrderModel();
            sendChildOrder.setServeId(si.getId());
            sendChildOrder.setPrice(si.getPrice());
            sendChildOrder.setDayNum(si.getTimeDay());

            if (TextUtils.equals(si.getValue() , Constant.SERVICE_TYPE_SHORT_GUIDE)
                    || TextUtils.equals(si.getValue() , Constant.SERVICE_TYPE_SHORT_CAR)
                    || TextUtils.equals(si.getValue() , Constant.SERVICE_TYPE_SHORT_CUSTOM)) {
                sendChildOrder.setPersonNum(si.getNumber());
            } else if(TextUtils.equals(si.getValue() , Constant.SERVICE_TYPE_SHORT_HOTEL)){
                sendChildOrder.setRoomNum(si.getNumber());
            } else if(TextUtils.equals(si.getValue() , Constant.SERVICE_TYPE_SHORT_TICKET)){
                sendChildOrder.setTicketNum(si.getNumber());
            }

            if(TextUtils.equals(si.getValue() , Constant.SERVICE_TYPE_SHORT_CUSTOM)
                    || TextUtils.equals(si.getValue() ,Constant.SERVICE_TYPE_SHORT_TICKET)){
                sendChildOrder.setTravelDate(si.getOneTime());
            }else{
                sendChildOrder.setTravelDate(si.getDaysStr2());
            }

            sendChildOrders.add(sendChildOrder);
        }
        sendOrderModel.setChildOrders(sendChildOrders);

        return sendOrderModel;
    }

    @Override
    public void orderCreateOrderSuccess(PayModel str) {
        finish();
        PayActivity.startActivity(activity, str);//TODO 订单上传成功，返回单号
    }

    @Override
    public void orderCreateOrderFailed(String msg) {
        showShortToast(msg);
    }
}
