package com.njz.letsgoapp.widget.popupwindow;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.njz.letsgoapp.util.rxbus.RxBus2;
import com.njz.letsgoapp.util.rxbus.busEvent.ServicePriceEvent;
import com.njz.letsgoapp.view.home.ServiceDetailActivity;
import com.njz.letsgoapp.view.home.ServiceListActivity;
import com.njz.letsgoapp.view.order.OrderSubmitActivity;
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
                    if (model.getServeType() == serviceItem.getServeType()) {
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
                        if(item.getServeType() == Constant.SERVICE_TYPE_GUIDE
                                || item.getServeType() == Constant.SERVICE_TYPE_CAR){
                            price  = item.getPrice() * item.getTimeDay() + price;
                        }else{
                            price  = item.getPrice() * item.getNumber() * item.getTimeDay() + price;
                        }
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
                public void onTitleClick(String serviceTypeName ,int serviceTypeId, int id) {
                    Intent intent;
                    if (serviceTypeId == Constant.SERVICE_TYPE_GUIDE
                            || serviceTypeId == Constant.SERVICE_TYPE_CAR) {

                        if(serviceNoJoin(serviceTypeId)) return;

                        intent = new Intent(mContext, ServiceDetailActivity.class);
                        intent.putExtra(ServiceDetailActivity.TITLE, serviceTypeName);
                        intent.putExtra(ServiceDetailActivity.SERVICEID, id);
                        intent.putParcelableArrayListExtra(ServiceDetailActivity.SERVICEITEMS, (ArrayList<ServiceItem>) getServiceItems(serviceTypeId));
                        mContext.startActivity(intent);
                        return;
                    }
                    if (serviceTypeId == Constant.SERVICE_TYPE_CUSTOM
                            || serviceTypeId == Constant.SERVICE_TYPE_HOTEL
                            || serviceTypeId == Constant.SERVICE_TYPE_TICKET) {

                        if(serviceNoJoin(serviceTypeId)) return;

                        intent = new Intent(mContext, ServiceListActivity.class);
                        intent.putExtra(ServiceListActivity.TITLE, serviceTypeName);
                        intent.putExtra(ServiceListActivity.SERVICE_TYPE, serviceTypeId);
                        intent.putExtra(ServiceListActivity.GUIDE_ID, guideId);
                        intent.putParcelableArrayListExtra(ServiceListActivity.SERVICEITEMS, (ArrayList<ServiceItem>) getServiceItems(serviceTypeId));

                        mContext.startActivity(intent);
                        return;
                    }
                }

                @Override
                public void onCancelClick(int serviceTypeId, int id) {
                    for (GuideServiceModel model : ServiceModels) {
                        if (model.getServeType() ==  serviceTypeId) {
                            for (ServiceItem item : model.getServiceItems()) {
                                if (item.getId() == id) {
                                    model.getServiceItems().remove(item);
                                    RxBus2.getInstance().post(new ServicePriceEvent());
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
                boolean isServiceItem = false;

                for (GuideServiceModel model : ServiceModels){
                    for (ServiceItem item : model.getServiceItems()){
                        isServiceItem = true;
                        if(item.getNumber() < 1 || item.getTimeDay() < 1){
                            ToastUtil.showLongToast(mContext,"请完善 " + item.getServiceType() + " 信息");
                            return;
                        }
                    }
                }
                if(!isServiceItem){
                    ToastUtil.showLongToast(mContext,"请选择服务项");
                    return;
                }

                Intent intent = new Intent(mContext, OrderSubmitActivity.class);
                intent.putParcelableArrayListExtra(OrderSubmitActivity.SERVICEMODEL, (ArrayList<GuideServiceModel>) ServiceModels);
                intent.putExtra(OrderSubmitActivity.GUIDE_ID, guideId);
                intent.putExtra(OrderSubmitActivity.LOCATION, Constant.DEFAULT_CITY);
                mContext.startActivity(intent);
                dismissPopupWindow();
                break;
        }
    }

    public List<ServiceItem> getServiceItems(int serviceTypeId){
        List<ServiceItem> serviceItems = new ArrayList<>();
        for (GuideServiceModel model : ServiceModels) {
            if(model.getServeType() == serviceTypeId){
                serviceItems = model.getServiceItems();
            }
        }
        return serviceItems;
    }


    //判断服务项是否可进入
    public boolean serviceNoJoin(int serviceTypeId){
        switch (serviceTypeId){
            case Constant.SERVICE_TYPE_CUSTOM:
                for (GuideServiceModel model : ServiceModels){
                    if(model.getServeType() != Constant.SERVICE_TYPE_CUSTOM && model.getServiceItems().size() > 0){
                        ToastUtil.showLongToast(mContext,"若要选择此服务，请先取消" + model.getServeType());
                        return true;
                    }
                }
                return false;
            case Constant.SERVICE_TYPE_GUIDE:
                for (GuideServiceModel model : ServiceModels){
                    if(model.getServeType() == Constant.SERVICE_TYPE_CUSTOM && model.getServiceItems().size() > 0){
                        ToastUtil.showLongToast(mContext,"若要选择此服务，请先取消" + model.getServeType());
                        return true;
                    }
                    if(model.getServeType() == Constant.SERVICE_TYPE_CAR && model.getServiceItems().size() > 0){
                        ToastUtil.showLongToast(mContext,"若要选择此服务，请先取消" + model.getServeType());
                        return true;
                    }
                }
                return false;
            case Constant.SERVICE_TYPE_CAR:
                for (GuideServiceModel model : ServiceModels){
                    if(model.getServeType() == Constant.SERVICE_TYPE_CUSTOM && model.getServiceItems().size() > 0){
                        ToastUtil.showLongToast(mContext,"若要选择此服务，请先取消" + model.getServeType());
                        return true;
                    }
                    if(model.getServeType() == Constant.SERVICE_TYPE_GUIDE && model.getServiceItems().size() > 0){
                        ToastUtil.showLongToast(mContext,"若要选择此服务，请先取消" + model.getServeType());
                        return true;
                    }
                }
                return false;
            case Constant.SERVICE_TYPE_HOTEL:
                for (GuideServiceModel model : ServiceModels){
                    if(model.getServeType() == Constant.SERVICE_TYPE_CUSTOM && model.getServiceItems().size() > 0){
                        ToastUtil.showLongToast(mContext,"若要选择此服务，请先取消" + model.getServeType());
                        return true;
                    }
                }
                return false;
            case Constant.SERVICE_TYPE_TICKET:
                for (GuideServiceModel model : ServiceModels){
                    if(model.getServeType() == Constant.SERVICE_TYPE_CUSTOM && model.getServiceItems().size() > 0){
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
