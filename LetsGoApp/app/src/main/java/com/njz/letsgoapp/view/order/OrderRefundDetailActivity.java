package com.njz.letsgoapp.view.order;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextPaint;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.EaseConstant;
import com.njz.letsgoapp.R;
import com.njz.letsgoapp.adapter.order.OrderRefundDetailAdapter;
import com.njz.letsgoapp.bean.MySelfInfo;
import com.njz.letsgoapp.bean.order.OrderRefundDetailChildModel;
import com.njz.letsgoapp.bean.order.OrderRefundDetailModel;
import com.njz.letsgoapp.constant.Constant;
import com.njz.letsgoapp.dialog.DialogUtil;
import com.njz.letsgoapp.mvp.order.OrderDeletePresenter;
import com.njz.letsgoapp.mvp.order.OrderRefundDetailContract;
import com.njz.letsgoapp.mvp.order.OrderRefundDetailPresenter;
import com.njz.letsgoapp.view.home.GuideDetailActivity;
import com.njz.letsgoapp.view.im.ChatActivity;
import com.njz.letsgoapp.view.login.LoginActivity;

import java.util.ArrayList;

/**
 * Created by LGQ
 * Time: 2018/9/29
 * Function:
 */

public class OrderRefundDetailActivity extends OrderDetailActivity implements OrderRefundDetailContract.View{

    OrderRefundDetailPresenter refundPresenter;
    OrderRefundDetailAdapter refundAdapter;

    OrderRefundDetailModel refundModel;

    RelativeLayout rl_refund_penalty,rl_refund_price,rl_order_price,rl_refund_used_price,rl_order_coupon;
    TextView tv_refund_penalty,tv_refund_price,tv_refund_used_price;
    FrameLayout cv_refund_reason;
    TextView tv_refund_reason,tv_refund_explain;

    @Override
    public void initData() {
        refundPresenter = new OrderRefundDetailPresenter(context,this);
        refundPresenter.orderRefundQueryOrderRefundDetails(orderId);

        deletePresenter = new OrderDeletePresenter(context,this);
    }

    @Override
    public void initView() {
        super.initView();
        rl_refund_penalty = $(R.id.rl_refund_penalty);
        rl_refund_price = $(R.id.rl_refund_price);
        rl_order_price = $(R.id.rl_order_price);
        rl_refund_used_price = $(R.id.rl_refund_used_price);
        tv_refund_used_price = $(R.id.tv_refund_used_price);
        tv_refund_penalty = $(R.id.tv_refund_penalty);
        tv_refund_price = $(R.id.tv_refund_price);
        cv_refund_reason = $(R.id.cv_refund_reason);
        tv_refund_reason = $(R.id.tv_refund_reason);
        tv_refund_explain = $(R.id.tv_refund_explain);
        rl_order_coupon = $(R.id.rl_order_coupon);

        cv_refund_reason.setVisibility(View.VISIBLE);

    }

    //初始化recyclerview
    public void initRecycler() {
        recyclerView = $(R.id.recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        refundAdapter = new OrderRefundDetailAdapter(activity, new ArrayList<OrderRefundDetailChildModel>());
        recyclerView.setAdapter(refundAdapter);
        recyclerView.setNestedScrollingEnabled(false);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_call_guide:
                DialogUtil.getInstance().showGuideMobileDialog(context,refundModel.getGuideMobile(),refundModel.getId(),0,refundModel.getGuideId());
                break;
            case R.id.btn_delete:
                deletePresenter.orderDeleteOrder(refundModel.getId(),1);
                break;
            case R.id.btn_call_custom:
                DialogUtil.getInstance().showCustomerMobileDialog(context);
                break;
            case R.id.tv_guide_name:
                intent = new Intent(context, GuideDetailActivity.class);
                intent.putExtra(GuideDetailActivity.GUIDEID,refundModel.getGuideId());
                startActivity(intent);
                break;
            case R.id.btn_consult:
                if(!MySelfInfo.getInstance().getImLogin()){
                    showShortToast("用户未注册到im");
                    return;
                }
                if(refundModel == null) return;
                String name = "g_"+ refundModel.getGuideId();
                String myName = EMClient.getInstance().getCurrentUser();
                if (!TextUtils.isEmpty(name)) {
                    if (name.equals(myName)) {
                        showShortToast("不能和自己聊天");
                        return;
                    }
                    Intent chat = new Intent(context, ChatActivity.class);
                    chat.putExtra(EaseConstant.EXTRA_USER_ID, name);  //对方账号
                    chat.putExtra(EaseConstant.EXTRA_CHAT_TYPE, EMMessage.ChatType.Chat); //单聊模式
                    chat.putExtra(EaseConstant.EXTRA_USER_NAME, refundModel.getGuideName());
                    startActivity(chat);

                } else {
                    showShortToast("导游还未注册即时通讯，请使用电话联系TA");
                }
                break;
        }

    }

    @Override
    public void orderRefundQueryOrderRefundDetailsSuccess(OrderRefundDetailModel str) {
        refundModel = str;

        ll_order_no.setVisibility(View.VISIBLE);//单号
        tv_order_no.setText(str.getOrderNo());

        switch (str.getRefundStatus()){
            case Constant.ORDER_REFUND_PROCESS:
                if(!TextUtils.isEmpty(str.getGuideCheckTime())){
                    ll_order_refund_verify.setVisibility(View.VISIBLE);//导游退款审核时间
                    tv_order_refund_verify.setText(str.getGuideCheckTime());
                }
                ll_order_refund_apply.setVisibility(View.VISIBLE);//申请退款时间
                tv_order_refund_apply.setText(str.getApplyTime());
                break;
            case Constant.ORDER_REFUND_FINISH:
                if(!TextUtils.isEmpty(str.getGuideCheckTime())){
                    ll_order_refund_verify.setVisibility(View.VISIBLE);//导游退款审核时间
                    tv_order_refund_verify.setText(str.getGuideCheckTime());
                }
                ll_order_refund_time.setVisibility(View.VISIBLE);//退款时间
                tv_order_refund_time.setText(str.getRefundTime());
                ll_order_refund_apply.setVisibility(View.VISIBLE);//申请退款时间
                tv_order_refund_apply.setText(str.getApplyTime());
                break;
            case Constant.ORDER_REFUND_CANCEL:
            case Constant.ORDER_REFUND_PLAN_REFUSE:
                setCancelView(str);
                break;
        }

        switch (str.getRefundStatus()){
            case Constant.ORDER_REFUND_WAIT:
            case Constant.ORDER_REFUND_PROCESS:
                btn_call_custom.setVisibility(View.VISIBLE);
                btn_call_guide.setVisibility(View.VISIBLE);
                break;
            case Constant.ORDER_REFUND_FINISH:
                btn_delete.setVisibility(View.VISIBLE);
                break;
        }

        tv_guide_name.setText(str.getGuideName());
        tv_order_status.setText(str.getPayStatusStr());

        fixed_view_city.setContent(str.getLocation());
        login_view_name.setContent(str.getName());
        login_view_phone.setContent(str.getMobile());
        login_view_num.setContent(str.getPersonNum() + "");
        et_special.setContent(TextUtils.isEmpty(str.getSpecialRequire())?"无":str.getSpecialRequire());

        tv_order_total.setText(str.getOrderPrice());

        if(str.getRefundStatus() == Constant.ORDER_REFUND_CANCEL || str.getRefundStatus() == Constant.ORDER_REFUND_PLAN_REFUSE){
            rl_order_price.setVisibility(View.VISIBLE);
            rl_refund_used_price.setVisibility(View.GONE);
            rl_refund_price.setVisibility(View.GONE);
            rl_refund_penalty.setVisibility(View.GONE);

            tv_order_price.setText(str.getOrderPrice());

            rl_order_coupon.setVisibility(View.GONE);
        }else{
            rl_order_price.setVisibility(View.GONE);
            rl_refund_price.setVisibility(View.VISIBLE);
            rl_refund_penalty.setVisibility(View.VISIBLE);

            tv_refund_penalty.setText("-￥" + str.getDefaultMoney());
            tv_refund_price.setText("￥" + str.getRefundMoney());

            //优惠卷
            rl_order_coupon.setVisibility(View.VISIBLE);
            if(str.getCouponPrice() > 0){
                tv_order_coupon.setText("-￥"+str.getCouponPrice());
                tv_order_coupon.setTextColor(ContextCompat.getColor(context,R.color.color_theme));
                tv_order_coupon.getPaint().setFakeBoldText(true);
                tv_order_coupon.postInvalidate();
            }else{
                tv_order_coupon.setText("未使用优惠卷");
                tv_order_coupon.setTextColor(ContextCompat.getColor(context,R.color.color_99));
                tv_order_coupon.getPaint().setFakeBoldText(false);
                tv_order_coupon.postInvalidate();
            }
        }

        //设置子单状态 判断是否在行程中。
        boolean isTravelGoing = false;
        for(int i = 0;i<str.getNjzRefundDetailsChildVOS().size();i++){

            //设置子单状态
            str.getNjzRefundDetailsChildVOS().get(i).setRefundStatus(str.getRefundStatus());//处理子单，显示退款ui，还是显示取消ui
            str.getNjzRefundDetailsChildVOS().get(i).setPlanStatus(str.getPlanStatus());//处理价待确定

            //判断是否在行程中
            if(str.getNjzRefundDetailsChildVOS().get(i).getChildOrderStatus() == Constant.ORDER_TRAVEL_GOING){
                isTravelGoing = true;
            }
        }
        if(isTravelGoing){//已消费金额
            rl_refund_used_price.setVisibility(View.VISIBLE);
            tv_refund_used_price.setText("-￥"+str.getUseMoney());
        }else{
            rl_refund_used_price.setVisibility(View.GONE);
        }

        tv_refund_reason.setText(str.getRefundReason());
        tv_refund_explain.setText(str.getRefundContent());

        refundAdapter.setData(str.getNjzRefundDetailsChildVOS());
    }

    @Override
    public void orderRefundQueryOrderRefundDetailsFailed(String msg) {
        showShortToast(msg);
    }


    //refuse:true 拒绝接单：私人定制
    public void setCancelView(OrderRefundDetailModel str) {
        tv_refund_reason_title.setText("取消原因");
        tv_refund_explain_title.setText("取消说明");

        btn_delete.setVisibility(View.VISIBLE);

        ll_order_create_time.setVisibility(View.VISIBLE);//创建时间
        tv_order_create_time.setText(str.getCreateTime());
        ll_order_cancel_time.setVisibility(View.VISIBLE);//取消时间
        tv_order_cancel_time.setText(str.getRefundTime());

        if(str.isCustom()){
            //私人定制,拒绝接单
            if(!TextUtils.isEmpty(str.getGuideSureTime())){
                ll_order_plan_confirm.setVisibility(View.VISIBLE);//导游私人定制确认时间
                tv_order_plan_confirm.setText(str.getGuideSureTime());
            }
            if(!TextUtils.isEmpty(str.getPlanDesignTime())){
                ll_order_plan_up.setVisibility(View.VISIBLE);//导游私人定制方案上传时间
                tv_order_plan_up.setText(str.getPlanDesignTime());
            }
        }
    }
}
