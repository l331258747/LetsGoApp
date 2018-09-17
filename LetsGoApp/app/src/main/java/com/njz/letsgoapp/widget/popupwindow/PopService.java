package com.njz.letsgoapp.widget.popupwindow;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.njz.letsgoapp.R;
import com.njz.letsgoapp.adapter.home.PopServiceAdapter;
import com.njz.letsgoapp.bean.home.GuideDetailModel;
import com.njz.letsgoapp.bean.home.GuideServiceModel;
import com.njz.letsgoapp.bean.home.ServiceItem;
import com.njz.letsgoapp.constant.Constant;
import com.njz.letsgoapp.util.ToastUtil;
import com.njz.letsgoapp.util.glide.GlideUtil;
import com.njz.letsgoapp.util.log.LogUtil;
import com.njz.letsgoapp.util.rxbus.RxBus2;
import com.njz.letsgoapp.util.rxbus.busEvent.ServicePriceEvent;
import com.njz.letsgoapp.view.order.OrderSubmitActivity;
import com.njz.letsgoapp.view.home.ServiceDetailActivity;
import com.njz.letsgoapp.view.home.ServiceListActivity;
import com.njz.letsgoapp.widget.GuideLabelView;
import com.njz.letsgoapp.widget.MyRatingBar;
import com.njz.letsgoapp.widget.ServiceTagView;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by LGQ
 * Time: 2018/8/9
 * Function:
 */

public class PopService extends BackgroundDarkPopupWindow implements View.OnClickListener {

    private View contentView;
    private Activity mContext;
    private RecyclerView recyclerView;
    private PopServiceAdapter mAdapter;
    private Context context;

    private List<GuideServiceModel> ServiceModels;
    private int guideId;
    private GuideDetailModel guideDetailModel;
    private Disposable servicePriceDisposable;
    private Disposable serviceItemDisposable;

    public PopService(final Activity context, View parentView, GuideDetailModel guideDetailModel) {
        super(parentView, WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
        mContext = context;
        this.guideDetailModel = guideDetailModel;
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        contentView = inflater.inflate(R.layout.popup_service, null);
        recyclerView = contentView.findViewById(R.id.recycler_view);

        this.context = context;

        this.setContentView(contentView);
        setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
        setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);

        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0000000000);
        this.setBackgroundDrawable(dw);

        contentView.setFocusable(true);
        contentView.setFocusableInTouchMode(true);
        this.setOutsideTouchable(true);

        this.setFocusable(true);

        initSubmit();
        initInfo();

    }

    public void initData(int guideId, List<GuideServiceModel> guideServiceModels) {
        this.ServiceModels = guideServiceModels;
        this.guideId = guideId;
        for (GuideServiceModel model : ServiceModels) {
            model.setServiceItems(new ArrayList<ServiceItem>());
        }

        serviceItemDisposable = RxBus2.getInstance().toObservable(ServiceItem.class, new Consumer<ServiceItem>() {
            @Override
            public void accept(ServiceItem serviceItem) throws Exception {
                for (GuideServiceModel model : ServiceModels) {
                    if (TextUtils.equals(model.getServeType(), serviceItem.getServiceType())) {
                        model.addServiceItem(serviceItem);
                        mAdapter.setData(ServiceModels);
                    }
                }
            }
        });
    }


    private void initSubmit() {
        final TextView tv_submit = contentView.findViewById(R.id.tv_submit);
        tv_submit.setOnClickListener(this);

        servicePriceDisposable = RxBus2.getInstance().toObservable(ServicePriceEvent.class, new Consumer<ServicePriceEvent>() {
            @Override
            public void accept(ServicePriceEvent servicePriceEvent) throws Exception {
                float price = 0;
                for (GuideServiceModel model : ServiceModels) {
                    for (ServiceItem item : model.getServiceItems()){
                        price  = item.getPrice() * item.getNumber() * item.getTimeDay() + price;
                    }
                }
                tv_submit.setText("立即预定（￥" + price +"）");
            }
        });
    }


    ImageView iv_head;
    MyRatingBar my_rating_bar;
    TextView tv_service_num;
    GuideLabelView guideLabel;
    ServiceTagView stv_tag;
    TextView tv_name;

    private void initInfo() {
        iv_head = contentView.findViewById(R.id.iv_head);
        my_rating_bar = contentView.findViewById(R.id.my_rating_bar);
        tv_service_num = contentView.findViewById(R.id.tv_service_num);
        guideLabel = contentView.findViewById(R.id.guide_label);
        stv_tag = contentView.findViewById(R.id.stv_tag);
        tv_name = contentView.findViewById(R.id.tv_name);

        GlideUtil.LoadCircleImage(mContext, guideDetailModel.getImage(), iv_head);
        tv_name.setText(guideDetailModel.getGuideName());
        my_rating_bar.setRating((int) guideDetailModel.getGuideScore());
        stv_tag.setServiceTag(guideDetailModel.getLanguage());
        tv_service_num.setText(guideDetailModel.getServiceCounts());
        guideLabel.setTabel(guideDetailModel.getSign());
    }

    public void showPopupWindow(View parent) {

        if (!this.isShowing()) {
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
            recyclerView.setLayoutManager(linearLayoutManager);
            mAdapter = new PopServiceAdapter(mContext, ServiceModels);
            recyclerView.setAdapter(mAdapter);

            mAdapter.setOnItemClickListener(new PopServiceAdapter.OnTitleClickListener() {
                @Override
                public void onTitleClick(String titleType, int id) {
                    Intent intent;
                    if (TextUtils.equals(titleType, Constant.SERVICE_TYPE_GUIDE)
                            || TextUtils.equals(titleType, Constant.SERVICE_TYPE_CAR)) {

                        if(serviceNoJoin(titleType)) return;

                        intent = new Intent(mContext, ServiceDetailActivity.class);
                        intent.putExtra(ServiceDetailActivity.TITLE, titleType);
                        intent.putExtra(ServiceDetailActivity.SERVICEID, id);
                        intent.putParcelableArrayListExtra(ServiceDetailActivity.SERVICEITEMS, (ArrayList<ServiceItem>) getServiceItems(titleType));
                        mContext.startActivity(intent);
                        return;
                    }
                    if (TextUtils.equals(titleType, Constant.SERVICE_TYPE_CUSTOM)
                            || TextUtils.equals(titleType, Constant.SERVICE_TYPE_HOTEL)
                            || TextUtils.equals(titleType, Constant.SERVICE_TYPE_TICKET)) {

                        if(serviceNoJoin(titleType)) return;

                        intent = new Intent(mContext, ServiceListActivity.class);
                        intent.putExtra(ServiceListActivity.TITLE, titleType);
                        intent.putExtra(ServiceListActivity.SERVICE_TYPE, titleType);
                        intent.putExtra(ServiceListActivity.GUIDE_ID, guideId);
                        intent.putParcelableArrayListExtra(ServiceListActivity.SERVICEITEMS, (ArrayList<ServiceItem>) getServiceItems(titleType));

                        mContext.startActivity(intent);
                        return;
                    }
                }

                @Override
                public void onCancelClick(String titleType, int id) {
                    for (GuideServiceModel model : ServiceModels) {
                        if (TextUtils.equals(model.getServeType(), titleType)) {
                            for (ServiceItem item : model.getServiceItems()) {
                                if (item.getId() == id) {
                                    model.getServiceItems().remove(item);
                                    break;
                                }
                            }
                        }
                    }
                    mAdapter.setData(ServiceModels);
                }
            });

            //NestedScroll 嵌套 recyclerView 触摸到RecyclerView的时候滑动还有些粘连的感觉
            recyclerView.setNestedScrollingEnabled(false);

            setDarkStyle(-1);
            setDarkColor(Color.parseColor("#a0000000"));
            resetDarkPosition();
            darkAbove(parent);
            showAtLocation(parent, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
        }
    }

    public void dismissPopupWindow() {
        if (this.isShowing())
            this.dismiss();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_submit:
                mContext.startActivity(new Intent(mContext, OrderSubmitActivity.class));
                dismissPopupWindow();
                break;
        }
    }

    public List<ServiceItem> getServiceItems(String serviceType){
        List<ServiceItem> serviceItems = new ArrayList<>();
        for (GuideServiceModel model : ServiceModels) {
            if(TextUtils.equals(model.getServeType(),serviceType)){
                serviceItems = model.getServiceItems();
            }
        }
        return serviceItems;
    }


    //判断服务项是否可进入
    public boolean serviceNoJoin(String serviceType){
        switch (serviceType){
            case Constant.SERVICE_TYPE_CUSTOM:
                for (GuideServiceModel model : ServiceModels){
                    if(!TextUtils.equals(model.getServeType(),Constant.SERVICE_TYPE_CUSTOM) && model.getServiceItems().size() > 0){
                        ToastUtil.showLongToast(mContext,"若要选择此服务，请先取消" + model.getServeType());
                        return true;
                    }
                }
                return false;
            case Constant.SERVICE_TYPE_GUIDE:
                for (GuideServiceModel model : ServiceModels){
                    if(TextUtils.equals(model.getServeType(),Constant.SERVICE_TYPE_CUSTOM) && model.getServiceItems().size() > 0){
                        ToastUtil.showLongToast(mContext,"若要选择此服务，请先取消" + model.getServeType());
                        return true;
                    }
                    if(TextUtils.equals(model.getServeType(),Constant.SERVICE_TYPE_CAR) && model.getServiceItems().size() > 0){
                        ToastUtil.showLongToast(mContext,"若要选择此服务，请先取消" + model.getServeType());
                        return true;
                    }
                }
                return false;
            case Constant.SERVICE_TYPE_CAR:
                for (GuideServiceModel model : ServiceModels){
                    if(TextUtils.equals(model.getServeType(),Constant.SERVICE_TYPE_CUSTOM) && model.getServiceItems().size() > 0){
                        ToastUtil.showLongToast(mContext,"若要选择此服务，请先取消" + model.getServeType());
                        return true;
                    }
                    if(TextUtils.equals(model.getServeType(),Constant.SERVICE_TYPE_GUIDE) && model.getServiceItems().size() > 0){
                        ToastUtil.showLongToast(mContext,"若要选择此服务，请先取消" + model.getServeType());
                        return true;
                    }
                }
                return false;
            case Constant.SERVICE_TYPE_HOTEL:
                for (GuideServiceModel model : ServiceModels){
                    if(TextUtils.equals(model.getServeType(),Constant.SERVICE_TYPE_CUSTOM) && model.getServiceItems().size() > 0){
                        ToastUtil.showLongToast(mContext,"若要选择此服务，请先取消" + model.getServeType());
                        return true;
                    }
                }
                return false;
            case Constant.SERVICE_TYPE_TICKET:
                for (GuideServiceModel model : ServiceModels){
                    if(TextUtils.equals(model.getServeType(),Constant.SERVICE_TYPE_CUSTOM) && model.getServiceItems().size() > 0){
                        ToastUtil.showLongToast(mContext,"若要选择此服务，请先取消" + model.getServeType());
                        return true;
                    }
                }
                return false;
        }
        return false;
    }

    public void onDestroty(){
        servicePriceDisposable.dispose();
        serviceItemDisposable.dispose();
    }
}
