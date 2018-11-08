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
import com.njz.letsgoapp.widget.EvaluateView;

/**
 * Created by LGQ
 * Time: 2018/11/8
 * Function:
 */

public class OrderEvaluateSeeActivity extends BaseActivity implements OrderEvaluateSeeContract.View {

    TextView tv_time,reply_content;
    EvaluateView ev_guide,ev_car,ev_substituting,ev_trip;
    TextView tv_special;
    LinearLayout ll_img,ll_reply;
    RecyclerView mRecyclerView;

    OrderEvaluateSeePresenter mPresenter;

    int orderId;

    @Override
    public void getIntentData() {
        super.getIntentData();
        orderId = intent.getIntExtra("ORDER_ID", 0);
        orderId = 123;
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
        ev_guide = $(R.id.ev_guide);
        ev_car = $(R.id.ev_car);
        ev_substituting = $(R.id.ev_substituting);
        ev_trip = $(R.id.ev_trip);
        tv_special = $(R.id.tv_special);
        ll_img = $(R.id.ll_img);
        ll_reply = $(R.id.ll_reply);
        mRecyclerView = $(R.id.recycler_view);

        ev_guide.getRatingBar().setClick(false);
        ev_car.getRatingBar().setClick(false);
        ev_substituting.getRatingBar().setClick(false);
        ev_trip.getRatingBar().setClick(false);
    }

    @Override
    public void initData() {
        mPresenter = new OrderEvaluateSeePresenter(context,this);

        mPresenter.orderReviewsQueryOrderReview(orderId);
    }

    @Override
    public void orderReviewsQueryOrderReviewSuccess(EvaluateModel2 str) {
        if(str == null) return;
        tv_time.setText(str.getUserDate());
        if(str.getGuideService() < 0){
            ev_guide.setVisibility(View.GONE);
        }else{
            ev_guide.setContent((int)str.getGuideService());
            ev_guide.getRatingBar().setRating((int)str.getGuideService());
        }
        if(str.getCarCondition() < 0){
            ev_car.setVisibility(View.GONE);
        }else{
            ev_car.setContent((int)str.getCarCondition());
            ev_car.getRatingBar().setRating((int)str.getCarCondition());
        }
        if(str.getBuyService() < 0){
            ev_substituting.setVisibility(View.GONE);
        }else{
            ev_substituting.setContent((int)str.getBuyService());
            ev_substituting.getRatingBar().setRating((int)str.getBuyService());
        }
        if(str.getTravelArrange() < 0){
            ev_trip.setVisibility(View.GONE);
        }else{
            ev_trip.setContent((int)str.getTravelArrange());
            ev_trip.getRatingBar().setRating((int)str.getTravelArrange());
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
            mRecyclerView.setAdapter(enterAdapter);
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
