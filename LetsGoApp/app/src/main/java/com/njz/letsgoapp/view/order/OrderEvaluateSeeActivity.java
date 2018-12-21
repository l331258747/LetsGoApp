package com.njz.letsgoapp.view.order;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.njz.letsgoapp.R;
import com.njz.letsgoapp.adapter.order.SimpleImageAdapter;
import com.njz.letsgoapp.base.BaseActivity;
import com.njz.letsgoapp.bean.home.EvaluateModel2;
import com.njz.letsgoapp.mvp.order.OrderEvaluateSeeContract;
import com.njz.letsgoapp.mvp.order.OrderEvaluateSeePresenter;
import com.njz.letsgoapp.view.other.BigImageActivity;
import com.njz.letsgoapp.widget.EvaluateView;

/**
 * Created by LGQ
 * Time: 2018/11/8
 * Function:
 */

public class OrderEvaluateSeeActivity extends BaseActivity implements OrderEvaluateSeeContract.View {

    TextView tv_time,reply_content;
    EvaluateView ev_attitude,ev_quality,ev_scheduling,ev_car_condition;
    TextView tv_special;
    LinearLayout ll_img,ll_reply,ll_content;
    RecyclerView mRecyclerView;

    OrderEvaluateSeePresenter mPresenter;

    int orderId;

    @Override
    public void getIntentData() {
        super.getIntentData();
        orderId = intent.getIntExtra("ORDER_ID", 0);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_order_evaluate_see;
    }

    @Override
    public void initView() {

        showLeftAndTitle("评价详情");

        tv_time = $(R.id.tv_time);
        reply_content = $(R.id.reply_content);
        ev_attitude = $(R.id.ev_attitude);
        ev_quality = $(R.id.ev_quality);
        ev_scheduling = $(R.id.ev_scheduling);
        ev_car_condition = $(R.id.ev_car_condition);
        tv_special = $(R.id.tv_special);
        ll_img = $(R.id.ll_img);
        ll_content = $(R.id.ll_content);
        ll_reply = $(R.id.ll_reply);
        mRecyclerView = $(R.id.recycler_view);

        ev_attitude.getRatingBar().setClick(false);
        ev_quality.getRatingBar().setClick(false);
        ev_scheduling.getRatingBar().setClick(false);
        ev_car_condition.getRatingBar().setClick(false);
    }

    @Override
    public void initData() {
        mPresenter = new OrderEvaluateSeePresenter(context,this);

        mPresenter.orderReviewsQueryOrderReview(orderId);
    }

    @Override
    public void orderReviewsQueryOrderReviewSuccess(final EvaluateModel2 str) {
        if(str == null) return;
        tv_time.setText(str.getUserDate());
        if(str.getServiceAttitude() < 1){
            ev_attitude.setVisibility(View.GONE);
        }else{
            ev_attitude.setContent((int)str.getServiceAttitude());
            ev_attitude.getRatingBar().setRating((int)str.getServiceAttitude());
        }
        if(str.getServiceQuality() < 1){
            ev_quality.setVisibility(View.GONE);
        }else{
            ev_quality.setContent((int)str.getServiceQuality());
            ev_quality.getRatingBar().setRating((int)str.getServiceQuality());
        }
        if(str.getCarCondition() < 1){
            ev_car_condition.setVisibility(View.GONE);
        }else{
            ev_car_condition.setContent((int)str.getCarCondition());
            ev_car_condition.getRatingBar().setRating((int)str.getCarCondition());
        }
        if(str.getTravelArrange() < 1){
            ev_scheduling.setVisibility(View.GONE);
        }else{
            ev_scheduling.setContent((int)str.getTravelArrange());
            ev_scheduling.getRatingBar().setRating((int)str.getTravelArrange());
        }

        if(str.isCustom()){
            ev_quality.setTitle("方案设计");
            ev_scheduling.setTitle("行程体验");
        }

        if(TextUtils.isEmpty(str.getUserContent())){
            tv_special.setVisibility(View.GONE);
        }else{
            tv_special.setText(str.getUserContent());
        }

        if(str.getImageUrls() == null || str.getImageUrls().size() == 0){
            ll_img.setVisibility(View.GONE);
        }else{
            mRecyclerView.setNestedScrollingEnabled(false);//滑动取消
            mRecyclerView.setLayoutManager(new GridLayoutManager(mRecyclerView.getContext(), 4));
            SimpleImageAdapter enterAdapter = new SimpleImageAdapter(context, str.getImageUrls());
            enterAdapter.setOnItemClickListener(new SimpleImageAdapter.OnItemClickListener() {
                @Override
                public void onClick(int position) {
                    BigImageActivity.startActivity(activity,position,str.getImageUrls());
                }
            });
            mRecyclerView.setAdapter(enterAdapter);
        }

        if(tv_special.getVisibility() == View.GONE && ll_img.getVisibility() == View.GONE){
            ll_content.setVisibility(View.GONE);
        }

        if(TextUtils.isEmpty(str.getGuideContent())){
            ll_reply.setVisibility(View.GONE);
        }else{
            reply_content.setText(str.getGuideContent());
        }
    }

    @Override
    public void orderReviewsQueryOrderReviewFailed(String msg) {
        showShortToast(msg);
    }
}
