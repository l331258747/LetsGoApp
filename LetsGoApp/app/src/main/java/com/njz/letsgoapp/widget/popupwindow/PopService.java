package com.njz.letsgoapp.widget.popupwindow;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.njz.letsgoapp.R;
import com.njz.letsgoapp.adapter.PopServiceAdapter;
import com.njz.letsgoapp.bean.home.ServiceInfo;
import com.njz.letsgoapp.bean.home.ServiceItem;
import com.njz.letsgoapp.util.ToastUtil;
import com.njz.letsgoapp.util.glide.GlideUtil;
import com.njz.letsgoapp.util.rxbus.RxBus2;
import com.njz.letsgoapp.util.rxbus.busEvent.CalendarEvent;
import com.njz.letsgoapp.view.calendar.CalendarActivity;
import com.njz.letsgoapp.view.order.OrderSubmitActivity;
import com.njz.letsgoapp.view.home.ServiceDetailActivity;
import com.njz.letsgoapp.view.home.ServiceListActivity;
import com.njz.letsgoapp.widget.GuideLabelView;
import com.njz.letsgoapp.widget.MyRatingBar;
import com.njz.letsgoapp.widget.NumberView;
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
    private ServiceInfo serviceInfo;
    private PopServiceAdapter mAdapter;

    public PopService(final Activity context, View parentView) {
        super(parentView, WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
        mContext = context;
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        contentView = inflater.inflate(R.layout.popup_service, null);
        recyclerView = contentView.findViewById(R.id.recycler_view);

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

        initInfo();
        initTrip();
        initSubmit();

        serviceInfo = initData();

        initSpecial();

    }

    private void initSubmit() {
        TextView tv_submit = contentView.findViewById(R.id.tv_submit);
        tv_submit.setOnClickListener(this);
    }


    TextView tv_city,tv_start_time,tv_end_time,tv_day;
    NumberView number_view;

    private void initTrip() {
        tv_city = contentView.findViewById(R.id.tv_city);
        tv_start_time = contentView.findViewById(R.id.tv_start_time);
        tv_end_time = contentView.findViewById(R.id.tv_end_time);
        tv_day = contentView.findViewById(R.id.tv_day);
        number_view = contentView.findViewById(R.id.number_view);

        tv_start_time.setOnClickListener(this);
        tv_end_time.setOnClickListener(this);

        number_view.setNum(1);

    }

    ImageView iv_head;
    MyRatingBar my_rating_bar;
    TextView tv_service_num;
    GuideLabelView guideLabel;
    ServiceTagView stv_tag;

    private void initInfo() {
        iv_head = contentView.findViewById(R.id.iv_head);
        my_rating_bar = contentView.findViewById(R.id.my_rating_bar);
        tv_service_num = contentView.findViewById(R.id.tv_service_num);
        guideLabel = contentView.findViewById(R.id.guide_label);
        stv_tag = contentView.findViewById(R.id.stv_tag);

        String photo = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1532339453709&di=c506e751bd24c08cb2221d51ac3300c7&imgtype=0&src=http%3A%2F%2Fimg.80tian.com%2Fblog%2F201403%2F20140323170732_1145.jpg";
        GlideUtil.LoadCircleImage(mContext, photo, iv_head);

        my_rating_bar.setRating(4);
        List<String> services = new ArrayList<>();
        services.add("4年");
        services.add("英语");
        services.add("中文");
        services.add("泰语");
        services.add("葡萄牙语");
        stv_tag.setServiceTag(services);
        List<String> tabels = new ArrayList<>();
        tabels.add("幽默达人");
        tabels.add("风趣性感");
        tabels.add("旅游玩家高手");
        guideLabel.setTabel(tabels);
        tv_service_num.setText("服务" + 6000 + "次");
    }

    private void initSpecial() {
        TextView tv_special = contentView.findViewById(R.id.tv_special);
        final NestedScrollView scrollView = contentView.findViewById(R.id.scrollView);
        final EditText et_special = contentView.findViewById(R.id.et_special);

    }

    public ServiceInfo initData() {
        ServiceInfo serviceInfo = new ServiceInfo();
        List<ServiceItem> serviceItems = new ArrayList<>();
        List<ServiceItem> serviceItems2 = new ArrayList<>();
        ServiceItem serviceItem = new ServiceItem();
        serviceItem.setContent("咖喱块手机噶隆盛科技噶历史课按理说大家告诉");
        serviceItem.setPrice(360);
        serviceItems.add(serviceItem);
        serviceItems.add(serviceItem);
        serviceInfo.setGuideServices(serviceItems2);
        serviceInfo.setCustomServices(serviceItems);
        serviceInfo.setCarServices(serviceItems);
        serviceInfo.setHotelServices(serviceItems);
        return serviceInfo;
    }

    public void showPopupWindow(View parent) {
        if (!this.isShowing()) {


            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
            recyclerView.setLayoutManager(linearLayoutManager);
            mAdapter = new PopServiceAdapter(mContext, serviceInfo);
            recyclerView.setAdapter(mAdapter);

            mAdapter.setOnItemClickListener(new PopServiceAdapter.OnTitleClickListener() {
                @Override
                public void onClick(String titleType) {
                    Intent intent;
                    if(TextUtils.equals(titleType,PopServiceAdapter.SERVICE_TYPE_GUIDE)){

                        ToastUtil.showShortToast(mContext,PopServiceAdapter.SERVICE_TYPE_GUIDE);
                        intent = new Intent(mContext, ServiceDetailActivity.class);
                        intent.putExtra("ServiceDetailActivity_title",titleType);
                        mContext.startActivity(intent);
                        return;

                    }else if(TextUtils.equals(titleType,PopServiceAdapter.SERVICE_TYPE_CUSTOM)){
                        ToastUtil.showShortToast(mContext, PopServiceAdapter.SERVICE_TYPE_CUSTOM);
                    }else if(TextUtils.equals(titleType,PopServiceAdapter.SERVICE_TYPE_CAR)){
                        ToastUtil.showShortToast(mContext,PopServiceAdapter.SERVICE_TYPE_CAR);
                    }else if(TextUtils.equals(titleType,PopServiceAdapter.SERVICE_TYPE_HOTEL)){
                        ToastUtil.showShortToast(mContext,PopServiceAdapter.SERVICE_TYPE_HOTEL);
                    }else if(TextUtils.equals(titleType,PopServiceAdapter.SERVICE_TYPE_TICKET)){
                        ToastUtil.showShortToast(mContext,PopServiceAdapter.SERVICE_TYPE_TICKET);
                    }
                    intent = new Intent(mContext, ServiceListActivity.class);
                    intent.putExtra("ServiceDetailActivity_title",titleType);
                    mContext.startActivity(intent);
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
        switch (v.getId()){
            case R.id.tv_start_time:
                calendarPick();
                break;
            case R.id.tv_end_time:
                calendarPick();
                break;
            case R.id.tv_submit:
                mContext.startActivity(new Intent(mContext, OrderSubmitActivity.class));
                dismissPopupWindow();
                break;

        }
    }


    Disposable calDisposable;


    private void calendarPick() {
        Intent intent = new Intent(mContext, CalendarActivity.class);
        intent.putExtra("CalendarTag", 1);
        mContext.startActivity(intent);

        calDisposable = RxBus2.getInstance().toObservable(CalendarEvent.class, new Consumer<CalendarEvent>() {
            @Override
            public void accept(CalendarEvent calendarEvent) throws Exception {
                tv_start_time.setText(calendarEvent.getStartTime());
                tv_end_time.setText(calendarEvent.getEndTime());
                tv_day.setText(calendarEvent.getDays());
                calDisposable.dispose();
            }
        });
    }
}
