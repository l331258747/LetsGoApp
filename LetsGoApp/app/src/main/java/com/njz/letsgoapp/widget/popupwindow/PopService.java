package com.njz.letsgoapp.widget.popupwindow;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.njz.letsgoapp.R;
import com.njz.letsgoapp.adapter.PopServiceAdapter;
import com.njz.letsgoapp.bean.home.ServiceInfo;
import com.njz.letsgoapp.bean.home.ServiceItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LGQ
 * Time: 2018/8/9
 * Function:
 */

public class PopService extends BackgroundDarkPopupWindow implements View.OnClickListener {

    private View contentView;
    private Context mContext;
    private RecyclerView recyclerView;
    private ServiceInfo serviceInfo;
    private PopServiceAdapter mAdapter;

    public PopService(final Context context, View parentView) {
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

        serviceInfo = initData();

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
        serviceInfo.setGuideServices(serviceItems);
        serviceInfo.setCustomServices(serviceItems2);
        serviceInfo.setCarServices(serviceItems);
        serviceInfo.setHotelServices(serviceItems2);
        return serviceInfo;
    }

    public void showPopupWindow(View parent) {
        if (!this.isShowing()) {


            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
            recyclerView.setLayoutManager(linearLayoutManager);
            mAdapter = new PopServiceAdapter(mContext, serviceInfo);
            recyclerView.setAdapter(mAdapter);

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

    }
}
