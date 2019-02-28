package com.njz.letsgoapp.view.order;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.EaseConstant;
import com.njz.letsgoapp.R;
import com.njz.letsgoapp.adapter.order.OrderDetailAdapter;
import com.njz.letsgoapp.base.BaseActivity;
import com.njz.letsgoapp.bean.EmptyModel;
import com.njz.letsgoapp.bean.MySelfInfo;
import com.njz.letsgoapp.bean.order.OrderDetailChildModel;
import com.njz.letsgoapp.bean.order.OrderDetailModel;
import com.njz.letsgoapp.bean.order.PayModel;
import com.njz.letsgoapp.bean.server.ServerItem;
import com.njz.letsgoapp.constant.Constant;
import com.njz.letsgoapp.dialog.DialogUtil;
import com.njz.letsgoapp.mvp.order.OrderDeleteContract;
import com.njz.letsgoapp.mvp.order.OrderDeletePresenter;
import com.njz.letsgoapp.mvp.order.OrderDetailContract;
import com.njz.letsgoapp.mvp.order.OrderDetailPresenter;
import com.njz.letsgoapp.util.ToastUtil;
import com.njz.letsgoapp.util.rxbus.RxBus2;
import com.njz.letsgoapp.util.rxbus.busEvent.OrderCancelEvent;
import com.njz.letsgoapp.view.home.GuideDetailActivity;
import com.njz.letsgoapp.view.im.ChatActivity;
import com.njz.letsgoapp.view.login.LoginActivity;
import com.njz.letsgoapp.view.pay.PayActivity;
import com.njz.letsgoapp.view.server.CustomPlanActivity;
import com.njz.letsgoapp.widget.FixedItemEditView;
import com.njz.letsgoapp.widget.FixedItemTextView;
import com.njz.letsgoapp.widget.SpecialFixedItemEditView;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by LGQ
 * Time: 2018/9/20
 * Function:
 */

public class OrderDetailActivity extends BaseActivity implements View.OnClickListener, OrderDetailContract.View ,OrderDeleteContract.View{

    public TextView tv_guide_name, tv_order_status;

    public FixedItemTextView fixed_view_city;
    public FixedItemEditView login_view_name, login_view_phone,login_view_num;
    public SpecialFixedItemEditView et_special;

    public RecyclerView recyclerView;
    public TextView tv_order_price,tv_order_coupon;

    public LinearLayout ll_order_no, ll_order_create_time, ll_order_pay_time, ll_order_pay_method, ll_order_guide_time,
            ll_order_refund_apply, ll_order_refund_verify, ll_order_refund_time,ll_order_travel_start,ll_order_travel_end,
            ll_order_plan_confirm,ll_order_plan_up;
    public TextView tv_order_no, tv_order_create_time, tv_order_pay_time, tv_order_pay_method, tv_order_guide_time, tv_order_refund_apply,
            tv_order_refund_verify, tv_order_refund_time,tv_order_travel_start,tv_order_travel_end,
            tv_order_plan_confirm,tv_order_plan_up,btn_consult;

    public TextView btn_cancel_order, btn_call_guide, btn_pay, btn_refund, btn_delete, btn_call_custom, btn_evaluate,btn_evaluate_see,btn_see_plan;

    public OrderDetailPresenter mPresenter;
    public OrderDeletePresenter deletePresenter;

    public int orderId;

    public OrderDetailAdapter mAdapter;

    OrderDetailModel model;

    Disposable disposable;

    FrameLayout cv_refund_reason;
    LinearLayout ll_order_cancel_time;
    TextView tv_refund_reason_title,tv_refund_explain_title,tv_refund_reason,tv_refund_explain,tv_order_cancel_time;

    @Override
    public void getIntentData() {
        super.getIntentData();
        orderId = intent.getIntExtra("ORDER_ID", 0);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_order_detail;
    }

    @Override
    public void initView() {
        showLeftAndTitle("查看订单");

        tv_guide_name = $(R.id.tv_guide_name);
        tv_order_status = $(R.id.tv_order_status);

        fixed_view_city = $(R.id.fixed_view_city);
        login_view_name = $(R.id.login_view_name);
        login_view_phone = $(R.id.login_view_phone);
        et_special = $(R.id.et_special);

        recyclerView = $(R.id.recycler_view);

        tv_order_price = $(R.id.tv_order_price);
        tv_order_coupon = $(R.id.tv_order_coupon);

        cv_refund_reason = $(R.id.cv_refund_reason);
        tv_refund_reason_title = $(R.id.tv_refund_reason_title);
        tv_refund_explain_title = $(R.id.tv_refund_explain_title);
        tv_refund_reason = $(R.id.tv_refund_reason);
        tv_refund_explain = $(R.id.tv_refund_explain);
        ll_order_cancel_time = $(R.id.ll_order_cancel_time);
        tv_order_cancel_time = $(R.id.tv_order_cancel_time);

        ll_order_no = $(R.id.ll_order_no);
        btn_consult = $(R.id.btn_consult);
        ll_order_create_time = $(R.id.ll_order_create_time);
        login_view_num = $(R.id.login_view_num);
        ll_order_pay_time = $(R.id.ll_order_pay_time);
        ll_order_pay_method = $(R.id.ll_order_pay_method);
        ll_order_guide_time = $(R.id.ll_order_guide_time);
        ll_order_refund_apply = $(R.id.ll_order_refund_apply);
        ll_order_refund_verify = $(R.id.ll_order_refund_verify);
        ll_order_refund_time = $(R.id.ll_order_refund_time);
        ll_order_travel_start = $(R.id.ll_order_travel_start);
        ll_order_travel_end = $(R.id.ll_order_travel_end);
        ll_order_plan_confirm = $(R.id.ll_order_plan_confirm);
        ll_order_plan_up = $(R.id.ll_order_plan_up);
        tv_order_no = $(R.id.tv_order_no);
        tv_order_create_time = $(R.id.tv_order_create_time);
        tv_order_pay_time = $(R.id.tv_order_pay_time);
        tv_order_pay_method = $(R.id.tv_order_pay_method);
        tv_order_guide_time = $(R.id.tv_order_guide_time);
        tv_order_refund_apply = $(R.id.tv_order_refund_apply);
        tv_order_refund_verify = $(R.id.tv_order_refund_verify);
        tv_order_refund_time = $(R.id.tv_order_refund_time);
        tv_order_travel_start = $(R.id.tv_order_travel_start);
        tv_order_travel_end = $(R.id.tv_order_travel_end);
        tv_order_plan_confirm = $(R.id.tv_order_plan_confirm);
        tv_order_plan_up = $(R.id.tv_order_plan_up);

        btn_cancel_order = $(R.id.btn_cancel_order);
        btn_call_guide = $(R.id.btn_call_guide);
        btn_pay = $(R.id.btn_pay);
        btn_refund = $(R.id.btn_refund);
        btn_delete = $(R.id.btn_delete);
        btn_call_custom = $(R.id.btn_call_custom);
        btn_evaluate = $(R.id.btn_evaluate);
        btn_evaluate_see = $(R.id.btn_evaluate_see);
        btn_see_plan = $(R.id.btn_see_plan);

        btn_cancel_order.setOnClickListener(this);
        btn_call_guide.setOnClickListener(this);
        btn_pay.setOnClickListener(this);
        btn_refund.setOnClickListener(this);
        btn_delete.setOnClickListener(this);
        btn_call_custom.setOnClickListener(this);
        btn_evaluate.setOnClickListener(this);
        btn_evaluate_see.setOnClickListener(this);
        tv_guide_name.setOnClickListener(this);
        btn_see_plan.setOnClickListener(this);
        btn_consult.setOnClickListener(this);

        btn_cancel_order.setVisibility(View.GONE);
        btn_call_guide.setVisibility(View.GONE);
        btn_pay.setVisibility(View.GONE);
        btn_refund.setVisibility(View.GONE);
        btn_delete.setVisibility(View.GONE);
        btn_call_custom.setVisibility(View.GONE);
        btn_evaluate.setVisibility(View.GONE);
        btn_evaluate_see.setVisibility(View.GONE);
        btn_see_plan.setVisibility(View.GONE);

        login_view_phone.getEtView().setEnabled(false);
        login_view_num.getEtView().setEnabled(false);
        login_view_name.getEtView().setEnabled(false);
        et_special.getEtView().setEnabled(false);

        ll_order_no.setVisibility(View.GONE);
        ll_order_create_time.setVisibility(View.GONE);
        ll_order_pay_time.setVisibility(View.GONE);
        ll_order_pay_method.setVisibility(View.GONE);
        ll_order_guide_time.setVisibility(View.GONE);
        ll_order_refund_apply.setVisibility(View.GONE);
        ll_order_refund_verify.setVisibility(View.GONE);
        ll_order_refund_time.setVisibility(View.GONE);
        ll_order_travel_start.setVisibility(View.GONE);
        ll_order_travel_end.setVisibility(View.GONE);
        ll_order_plan_confirm.setVisibility(View.GONE);
        ll_order_plan_up.setVisibility(View.GONE);
        ll_order_cancel_time.setVisibility(View.GONE);

        initRecycler();
    }

    @Override
    public void initData() {
        mPresenter = new OrderDetailPresenter(context, this);
        mPresenter.orderQueryOrder(orderId);
        deletePresenter = new OrderDeletePresenter(context,this);

        disposable = RxBus2.getInstance().toObservable(OrderCancelEvent.class, new Consumer<OrderCancelEvent>() {
            @Override
            public void accept(OrderCancelEvent orderCancelEvent) throws Exception {
                if(orderCancelEvent.getIsMainly() == 0){
                    mPresenter.orderQueryOrder(orderId);
                }else if(orderCancelEvent.getIsMainly() == 1){
                    finish();
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(disposable != null && disposable.isDisposed()){
            disposable.dispose();
        }
    }

    //初始化recyclerview
    public void initRecycler() {
        recyclerView = $(R.id.recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        mAdapter = new OrderDetailAdapter(activity, new ArrayList<OrderDetailChildModel>());
        recyclerView.setAdapter(mAdapter);
        recyclerView.setNestedScrollingEnabled(false);

        mAdapter.setOnCancelClickListener(new OrderDetailAdapter.OnCancelClickListener() {
            @Override
            public void onClick(int orderId) {
                Intent intent = new Intent(context,OrderCancelActivity.class);
                if(mAdapter.getItemCount() == 1){
                    intent.putExtra("ORDER_ID",model.getId());
                    intent.putExtra("IS_MAINLY",1);
                }else {
                    intent.putExtra("ORDER_ID",orderId);
                    intent.putExtra("IS_MAINLY",0);
                }
                intent.putExtra("name",model.getName());
                intent.putExtra("phone",model.getMobile());
                context.startActivity(intent);
            }
        });

        mAdapter.setOnRefundClickListener(new OrderDetailAdapter.OnRefundClickListener() {
            @Override
            public void onClick(int id, List<Integer> childIds, int status, int index) {
                Intent intent = new Intent(context,OrderRefundActivity.class);
                intent.putExtra("id",id);
                intent.putIntegerArrayListExtra("childIds", (ArrayList<Integer>) childIds);
                intent.putExtra("name",model.getName());
                intent.putExtra("phone",model.getMobile());
                intent.putExtra("guideMobile",model.getGuideMobile());
                intent.putExtra("status",status);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.btn_cancel_order:
                intent = new Intent(context,OrderCancelActivity.class);
                intent.putExtra("ORDER_ID",model.getId());
                intent.putExtra("name",model.getName());
                intent.putExtra("phone",model.getMobile());
                context.startActivity(intent);
                break;
            case R.id.btn_call_guide:
                DialogUtil.getInstance().showGuideMobileDialog(context,model.getGuideMobile());
                break;
            case R.id.tv_guide_name:
                intent = new Intent(context, GuideDetailActivity.class);
                intent.putExtra(GuideDetailActivity.GUIDEID,model.getGuideId());
                context.startActivity(intent);
                break;
            case R.id.btn_see_plan:
                intent = new Intent(context, CustomPlanActivity.class);
                intent.putExtra("ORDER_ID",model.getId());
                intent.putExtra("GUIDE_PHONE",model.getGuideMobile());
                if(model.getPayStatus() == Constant.ORDER_PAY_WAIT
                        && model.getPlanStatus() == Constant.ORDER_PLAN_USER_WAIT){
                    intent.putExtra("SHOW_PAY",true);
                    intent.putExtra("PAY_MODEL",getPayModel(model));
                }
                startActivity(intent);
                break;
            case R.id.btn_pay:
                if(model.isCustom()){
                    intent = new Intent(context,CustomSubmitActivity.class);
                    intent.putExtra("name",model.getName());
                    intent.putExtra("tel",model.getMobile());
                    intent.putExtra("personNum",model.getPersonNum());
                    intent.putExtra("special",model.getSpecialRequire());
                    intent.putExtra("PAY_MODEL",getPayModel(model));
                    intent.putParcelableArrayListExtra("SERVICEMODEL", (ArrayList<ServerItem>) getServerItems(model.getNjzChildOrderVOS().get(0)));
                    startActivity(intent);
                }else{
                    PayActivity.startActivity(context, getPayModel(model));
                }
                break;
            case R.id.btn_refund:
                intent = new Intent(context,OrderRefundActivity.class);
                intent.putExtra("id",model.getId());
                List<Integer> childIds = new ArrayList<Integer>();
                for (int i = 0; i < model.getNjzChildOrderVOS().size(); i++) {
                    childIds.add(model.getNjzChildOrderVOS().get(i).getId());
                }
                intent.putIntegerArrayListExtra("childIds", (ArrayList<Integer>) childIds);
                intent.putExtra("name",model.getName());
                intent.putExtra("phone",model.getMobile());
                intent.putExtra("status",model.getOrderStatus());
                intent.putExtra("guideMobile",model.getGuideMobile());
                context.startActivity(intent);
                break;
            case R.id.btn_delete:
                deletePresenter.orderDeleteOrder(model.getId(),0);
                break;
            case R.id.btn_call_custom:
                DialogUtil.getInstance().showCustomerMobileDialog(context);
                break;
            case R.id.btn_evaluate:
                intent = new Intent(context,OrderEvaluateActivity.class);
                intent.putExtra("ORDER_ID",model.getId());
                intent.putExtra("GUIDE_ID",model.getGuideId());
                intent.putExtra("evaluateType",model.getEvaluateType());
                startActivity(intent);
                break;
            case R.id.btn_evaluate_see:
                intent = new Intent(context,OrderEvaluateSeeActivity.class);
                intent.putExtra("ORDER_ID",model.getId());
                startActivity(intent);
                break;
            case R.id.btn_consult:

                if(!MySelfInfo.getInstance().getImLogin()){
                    showShortToast("用户未注册到im");
                    return;
                }

                if(model == null) return;
                String name = "g_"+ model.getGuideId();
                String myName = EMClient.getInstance().getCurrentUser();
                if (!TextUtils.isEmpty(name)) {
                    if (name.equals(myName)) {
                        showShortToast("不能和自己聊天");
                        return;
                    }
                    Intent chat = new Intent(context, ChatActivity.class);
                    chat.putExtra(EaseConstant.EXTRA_USER_ID, name);  //对方账号
                    chat.putExtra(EaseConstant.EXTRA_CHAT_TYPE, EMMessage.ChatType.Chat); //单聊模式
                    chat.putExtra(EaseConstant.EXTRA_USER_NAME, model.getGuideName());
                    startActivity(chat);

                } else {
                    showShortToast("导游还未注册即时通讯，请使用电话联系TA");
                }
                break;
        }
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

    public PayModel getPayModel(OrderDetailModel model){
        PayModel payModel = new PayModel();
        payModel.setTotalAmount(model.getPayPrice()+"");
        payModel.setSubject(model.getLocation() + model.getGuideName()+"导游为您服务！");
        payModel.setOutTradeNo(model.getOrderNo());
        payModel.setLastPayTime(model.getLastPayTime());
        payModel.setOrderId(model.getId());
        return payModel;
    }

    @Override
    public void orderQueryOrderSuccess(OrderDetailModel str) {
        model = str;
        switch (str.getPayStatus()) {
            case Constant.ORDER_PAY_WAIT:
                if(str.getPlanStatus() == Constant.ORDER_PLAN_GUIDE_REFUND){
                    setCancelView(str);
                    return;
                }
                ll_order_no.setVisibility(View.VISIBLE);
                ll_order_create_time.setVisibility(View.VISIBLE);
                tv_order_no.setText(str.getOrderNo());
                tv_order_create_time.setText(str.getCreateTime());

                if (str.getPlanStatus() == Constant.ORDER_PLAN_GUIDE_WAIT
                        || str.getPlanStatus() == Constant.ORDER_PLAN_PLANING) {//取消订单，联系他
                    btn_call_guide.setVisibility(View.VISIBLE);
                    btn_cancel_order.setVisibility(View.VISIBLE);

                    if(str.getPlanStatus() == Constant.ORDER_PLAN_PLANING){
                        ll_order_plan_confirm.setVisibility(View.VISIBLE);
                        tv_order_plan_confirm.setText(str.getGuideSureTime());
                    }
                } else if (str.getPlanStatus() == Constant.ORDER_PLAN_USER_WAIT) {//取消订单，查看方案，付款
                    btn_cancel_order.setVisibility(View.VISIBLE);
                    btn_see_plan.setVisibility(View.VISIBLE);
                    btn_pay.setVisibility(View.VISIBLE);

                    ll_order_plan_confirm.setVisibility(View.VISIBLE);
                    tv_order_plan_confirm.setText(str.getGuideSureTime());
                    ll_order_plan_up.setVisibility(View.VISIBLE);
                    tv_order_plan_up.setText(str.getPlanDesignTime());

                } else {
                    btn_call_guide.setVisibility(View.VISIBLE);
                    if (str.getPayingStatus() == Constant.ORDER_WAIT_PAYING) {
                        btn_pay.setVisibility(View.GONE);
                        btn_cancel_order.setVisibility(View.GONE);
                    } else {
                        btn_pay.setVisibility(View.VISIBLE);
                        btn_cancel_order.setVisibility(View.VISIBLE);
                    }
                }

                break;
            case Constant.ORDER_PAY_ALREADY:
                ll_order_no.setVisibility(View.VISIBLE);
                ll_order_create_time.setVisibility(View.VISIBLE);
                ll_order_pay_time.setVisibility(View.VISIBLE);
                ll_order_pay_method.setVisibility(View.VISIBLE);
                tv_order_no.setText(str.getOrderNo());
                tv_order_create_time.setText(str.getCreateTime());
                tv_order_pay_time.setText(str.getPayTime());
                tv_order_pay_method.setText(str.getPayType());

                switch (str.getOrderStatus()){
                    case Constant.ORDER_TRAVEL_NO_GO:
                        ll_order_guide_time.setVisibility(View.VISIBLE);
                        tv_order_guide_time.setText(str.getGuideSureTime());
                        break;
                    case Constant.ORDER_TRAVEL_GOING:
                        ll_order_guide_time.setVisibility(View.VISIBLE);
                        tv_order_guide_time.setText(str.getGuideSureTime());
                        ll_order_travel_start.setVisibility(View.VISIBLE);
                        tv_order_travel_start.setText(str.getStartDate());
                        break;
                }

                if (str.getOrderStatus() != Constant.ORDER_TRAVEL_GOING) {
                    btn_call_custom.setVisibility(View.VISIBLE);
                    btn_call_guide.setVisibility(View.VISIBLE);
                    btn_refund.setVisibility(View.VISIBLE);
                } else {
                    btn_call_custom.setVisibility(View.VISIBLE);
                    btn_call_guide.setVisibility(View.VISIBLE);
                }

                if(str.isCustom()){
                    btn_see_plan.setVisibility(View.VISIBLE);
                    btn_call_custom.setVisibility(View.GONE);
                }

                break;
            case Constant.ORDER_PAY_FINISH:
                ll_order_no.setVisibility(View.VISIBLE);
                ll_order_create_time.setVisibility(View.VISIBLE);
                tv_order_no.setText(str.getOrderNo());
                tv_order_create_time.setText(str.getCreateTime());
                ll_order_pay_time.setVisibility(View.VISIBLE);
                tv_order_pay_time.setText(str.getPayTime());
                ll_order_pay_method.setVisibility(View.VISIBLE);
                tv_order_pay_method.setText(str.getPayType());
                ll_order_guide_time.setVisibility(View.VISIBLE);
                tv_order_guide_time.setText(str.getGuideSureTime());
                ll_order_travel_start.setVisibility(View.VISIBLE);
                tv_order_travel_start.setText(str.getStartDate());
                ll_order_travel_end.setVisibility(View.VISIBLE);
                tv_order_travel_end.setText(str.getEndDate());

                switch (str.getReviewStatus()) {
                    case Constant.ORDER_EVALUATE_NO:
                        btn_call_guide.setVisibility(View.VISIBLE);
                        btn_evaluate.setVisibility(View.VISIBLE);
                        break;
                    case Constant.ORDER_EVALUATE_YES:
                        btn_delete.setVisibility(View.VISIBLE);
                        btn_call_guide.setVisibility(View.VISIBLE);
                        btn_evaluate_see.setVisibility(View.VISIBLE);
                        break;
                }

                if(str.isCustom()){
                    btn_see_plan.setVisibility(View.VISIBLE);
                    btn_call_custom.setVisibility(View.GONE);
                }
                break;
            case Constant.ORDER_PAY_CANCEL:
                setCancelView(str);
                break;
        }

        tv_order_price.setText(str.getOrderPriceStr());

        if(str.getCouponPrice() > 0){
            tv_order_coupon.setText("￥"+str.getCouponPrice());
            tv_order_coupon.setTextColor(ContextCompat.getColor(context,R.color.color_theme));
            tv_order_coupon.getPaint().setFakeBoldText(true);
            tv_order_coupon.postInvalidate();
        }else{
            tv_order_coupon.setText("未使用优惠卷");
            tv_order_coupon.setTextColor(ContextCompat.getColor(context,R.color.color_99));
            tv_order_coupon.getPaint().setFakeBoldText(false);
            tv_order_coupon.postInvalidate();
        }

        tv_guide_name.setText(str.getGuideName());
        tv_order_status.setText(str.getPayStatusStr());

        fixed_view_city.setContent(str.getLocation());
        login_view_name.setContent(str.getName());
        login_view_phone.setContent(str.getMobile());
        login_view_num.setContent(str.getPersonNum() + "");
        et_special.setContent(TextUtils.isEmpty(str.getSpecialRequire())?"无":str.getSpecialRequire());

        for (OrderDetailChildModel model: str.getNjzChildOrderVOS()){
            model.setPayingStatus(str.getPayingStatus());
            model.setPlanStatus(str.getPlanStatus());
        }
        mAdapter.setData(str.getNjzChildOrderVOS());

    }

    //refuse:true 拒绝接单：私人定制
    private void setCancelView(OrderDetailModel str) {
        cv_refund_reason.setVisibility(View.VISIBLE);
        tv_refund_reason_title.setText("取消原因");
        tv_refund_explain_title.setText("取消说明");
        tv_refund_reason.setText(str.getCancelReason());
        tv_refund_explain.setText(str.getCancelExplain());

        btn_delete.setVisibility(View.VISIBLE);

        ll_order_no.setVisibility(View.VISIBLE);
        ll_order_create_time.setVisibility(View.VISIBLE);
        tv_order_no.setText(str.getOrderNo());
        tv_order_create_time.setText(str.getCreateTime());
        ll_order_cancel_time.setVisibility(View.VISIBLE);
        tv_order_cancel_time.setText(str.getCancelTime());

        if(str.isCustom()){
            //私人定制,拒绝接单
            if(!TextUtils.isEmpty(str.getGuideSureTime())){
                ll_order_plan_confirm.setVisibility(View.VISIBLE);
                tv_order_plan_confirm.setText(str.getGuideSureTime());
            }
            if(!TextUtils.isEmpty(str.getPlanDesignTime())){
                ll_order_plan_up.setVisibility(View.VISIBLE);
                tv_order_plan_up.setText(str.getPlanDesignTime());
            }
        }
    }

    @Override
    public void orderQueryOrderFailed(String msg) {
        showShortToast(msg);
    }

    @Override
    public void orderDeleteOrderSuccess(EmptyModel str) {
        showShortToast("删除成功");
        finish();
    }

    @Override
    public void orderDeleteOrderFailed(String msg) {
        showShortToast(msg);
    }
}
