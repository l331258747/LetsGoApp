package com.njz.letsgoapp.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.njz.letsgoapp.R;
import com.njz.letsgoapp.bean.home.ServiceInfo;
import com.njz.letsgoapp.bean.home.ServiceInfoGroup;
import com.njz.letsgoapp.bean.home.ServiceItem;
import com.njz.letsgoapp.util.AppUtils;
import com.njz.letsgoapp.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LGQ
 * Time: 2018/8/13
 * Function:
 */

public class PopServiceAdapter extends RecyclerView.Adapter<PopServiceAdapter.BaseViewHolder> {
    Context mContext;

    private static final int SERVICE_TYPE_TITLE = 10;
    private static final int SERVICE_TYPE_FOOT = 11;
    private static final int SERVICE_TYPE_DEFAULT = 12;
    private static final int SERVICE_TYPE_DEFAULT2 = 13;

    public static final String SERVICE_TYPE_GUIDE = "向导陪游服务";
    public static final String SERVICE_TYPE_CUSTOM = "私人定制服务";
    public static final String SERVICE_TYPE_CAR = "包车服务";
    public static final String SERVICE_TYPE_HOTEL = "代订酒店服务";
    public static final String SERVICE_TYPE_TICKET = "代订景点门票服务";

    ServiceInfo serviceInfo;

    private List<ServiceInfoGroup> serviceInfoGroups = new ArrayList<>();

    public PopServiceAdapter(Context mContext, ServiceInfo serviceInfo) {
        this.mContext = mContext;
        this.serviceInfo = serviceInfo;

        setData(serviceInfo);
    }

    private void setData(ServiceInfo serviceInfo) {
        serviceInfoGroups.clear();
        setData2(serviceInfo.getGuideServices(), ServiceInfoGroup.LABEL_TAB_DEFAULT, SERVICE_TYPE_GUIDE);
        setData2(serviceInfo.getCustomServices(), ServiceInfoGroup.LABEL_TAB_DEFAULT, SERVICE_TYPE_CUSTOM);
        setData2(serviceInfo.getCarServices(), ServiceInfoGroup.LABEL_TAB_DEFAULT, SERVICE_TYPE_CAR);
        setData2(serviceInfo.getHotelServices(), ServiceInfoGroup.LABEL_TAB_DEFAULT_2, SERVICE_TYPE_HOTEL);
        setData2(serviceInfo.getTicketServices(), ServiceInfoGroup.LABEL_TAB_DEFAULT_2, SERVICE_TYPE_TICKET);

        notifyDataSetChanged();
    }

    public void setData2(List<ServiceItem> serviceItems, int type, String title) {
        if (serviceItems != null) {
            ServiceInfoGroup serviceInfoGroup = new ServiceInfoGroup();
            serviceInfoGroup.setLabelTab(ServiceInfoGroup.LABEL_TAB_TITLE);
            serviceInfoGroup.setServiceTitle(title);
            serviceInfoGroups.add(serviceInfoGroup);
            if (serviceItems.size() > 0) {
                serviceInfoGroup.setServiceTitleColor(true);
                for (ServiceItem si : serviceItems) {
                    ServiceInfoGroup serviceInfoGroup2 = new ServiceInfoGroup();
                    serviceInfoGroup2.setLabelTab(type);
                    serviceInfoGroup2.setServiceItem(si);
                    serviceInfoGroups.add(serviceInfoGroup2);
                }
                ServiceInfoGroup serviceInfoGroup3 = new ServiceInfoGroup();
                serviceInfoGroup3.setLabelTab(ServiceInfoGroup.LABEL_TAB_FOOT);
                serviceInfoGroup3.setServiceFoot(title);
                serviceInfoGroups.add(serviceInfoGroup3);
            }
        }
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {
            case SERVICE_TYPE_TITLE:
                view = LayoutInflater.from(mContext).inflate(R.layout.item_service_title, parent, false);
                return new TitleHolder(view);
            case SERVICE_TYPE_FOOT:
                view = LayoutInflater.from(mContext).inflate(R.layout.item_service_foot, parent, false);
                return new FootHolder(view);
            case SERVICE_TYPE_DEFAULT2:
                view = LayoutInflater.from(mContext).inflate(R.layout.item_service_defualt2, parent, false);
                return new Default2Holder(view);
            default:
                view = LayoutInflater.from(mContext).inflate(R.layout.item_service_defualt, parent, false);
                return new DefaultHolder(view);
        }
    }


    @Override
    public int getItemViewType(int position) {
        if (serviceInfoGroups.get(position).getLabelTab() == ServiceInfoGroup.LABEL_TAB_TITLE)
            return SERVICE_TYPE_TITLE;
        if (serviceInfoGroups.get(position).getLabelTab() == ServiceInfoGroup.LABEL_TAB_FOOT)
            return SERVICE_TYPE_FOOT;
        if (serviceInfoGroups.get(position).getLabelTab() == ServiceInfoGroup.LABEL_TAB_DEFAULT_2)
            return SERVICE_TYPE_DEFAULT2;
        return super.getItemViewType(position);

    }

    @Override
    public void onBindViewHolder(final BaseViewHolder holder, int position) {
        if (holder == null) return;
        if (holder instanceof DefaultHolder) {
            final int pos = holder.getAdapterPosition();
            final ServiceInfoGroup data = serviceInfoGroups.get(pos);
            if (data == null) return;

            ((DefaultHolder) holder).service_default_content.setText(data.getServiceItem().getContent());
            ((DefaultHolder) holder).service_default_price.setText("￥" + data.getServiceItem().getPrice());
        }

        if (holder instanceof TitleHolder) {
            final int pos = holder.getAdapterPosition();
            final ServiceInfoGroup data = serviceInfoGroups.get(pos);
            if (data == null) return;

            ((TitleHolder) holder).service_title.setText(data.getServiceTitle());
            if(data.isServiceTitleColor()){
                ((TitleHolder) holder).service_title.setBackground(ContextCompat.getDrawable(AppUtils.getContext(), R.drawable.btn_blue_solid_r15));
            }else{
                ((TitleHolder) holder).service_title.setBackground(ContextCompat.getDrawable(AppUtils.getContext(), R.drawable.btn_gray999_solid_r15));
            }

            ((TitleHolder) holder).service_title.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String strTitle = (((TitleHolder) holder).service_title).getText().toString();
                    mOnItemClickListener.onClick(strTitle);
                }
            });

        }
    }

    @Override
    public int getItemCount() {
        return serviceInfoGroups.size();
    }

    //--------------View Holder start----------------
    static class BaseViewHolder extends RecyclerView.ViewHolder {
        BaseViewHolder(View itemView) {
            super(itemView);
        }
    }

    public static class TitleHolder extends BaseViewHolder {
        TextView service_title;

        TitleHolder(View itemView) {
            super(itemView);
            service_title = itemView.findViewById(R.id.service_title);
        }
    }

    public static class DefaultHolder extends BaseViewHolder {

        TextView service_default_content;
        TextView service_default_price;
        TextView service_default_cancel;


        DefaultHolder(View itemView) {
            super(itemView);
            service_default_content = itemView.findViewById(R.id.service_default_content);
            service_default_price = itemView.findViewById(R.id.service_default_price);
            service_default_cancel = itemView.findViewById(R.id.service_default_cancel);
        }
    }

    public static class Default2Holder extends BaseViewHolder {

        Default2Holder(View itemView) {
            super(itemView);
        }
    }

    public static class FootHolder extends BaseViewHolder {
        TextView service_foot_price;
        TextView service_foot_total;

        FootHolder(View itemView) {
            super(itemView);
            service_foot_price = itemView.findViewById(R.id.service_foot_price);
            service_foot_total = itemView.findViewById(R.id.service_foot_total);
        }
    }

    //--------------View Holder end----------------


    //-----start 事件
    OnTitleClickListener mOnItemClickListener;
    public interface OnTitleClickListener {
        void onClick(String titleType);
    }
    public void setOnItemClickListener(OnTitleClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }
    //-----end 事件
}
