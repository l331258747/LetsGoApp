package com.njz.letsgoapp.adapter.home;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amap.api.maps2d.model.Text;
import com.njz.letsgoapp.R;
import com.njz.letsgoapp.bean.home.GuideServiceModel;
import com.njz.letsgoapp.bean.home.ServiceInfoGroup;
import com.njz.letsgoapp.bean.home.ServiceItem;
import com.njz.letsgoapp.constant.Constant;
import com.njz.letsgoapp.util.rxbus.RxBus2;
import com.njz.letsgoapp.util.rxbus.busEvent.CalendarEvent;
import com.njz.letsgoapp.util.rxbus.busEvent.ServicePriceEvent;
import com.njz.letsgoapp.view.calendar.CalendarActivity;
import com.njz.letsgoapp.widget.NumberView;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by LGQ
 * Time: 2018/8/13
 * Function:
 */

public class PopServiceAdapter extends RecyclerView.Adapter<PopServiceAdapter.BaseViewHolder> {
    Context mContext;

    private static final int SERVICE_TYPE_TITLE = 10;
    private static final int SERVICE_TYPE_DEFAULT = 12;

    List<GuideServiceModel> serviceInfo;
    Disposable calDisposable;
    int calendarType;

    private List<ServiceInfoGroup> serviceInfoGroups = new ArrayList<>();

    public PopServiceAdapter(Context mContext, List<GuideServiceModel> serviceInfo) {
        this.mContext = mContext;
        this.serviceInfo = serviceInfo;

        setData(serviceInfo);
    }

    public void setData(List<GuideServiceModel> serviceInfo) {
        this.serviceInfo = serviceInfo;
        serviceInfoGroups.clear();
        for (GuideServiceModel item : serviceInfo) {
            setData2(item);
        }
        notifyDataSetChanged();
    }

    public void setData2(GuideServiceModel item) {
        ServiceInfoGroup serviceInfoGroup = new ServiceInfoGroup();
        serviceInfoGroup.setLabelTab(ServiceInfoGroup.LABEL_TAB_TITLE);
        serviceInfoGroup.setServiceTitle(item.getServiceType());
        serviceInfoGroup.setId(item.getId());
        serviceInfoGroup.setServiceTypeId(item.getServeType());
        serviceInfoGroup.setServiceTypeShort(item.getValue());
        serviceInfoGroups.add(serviceInfoGroup);
        if (item.getServiceItems() != null && item.getServiceItems().size() > 0) {
            serviceInfoGroup.setServiceTitleColor(true);
            for (ServiceItem si : item.getServiceItems()) {
                ServiceInfoGroup serviceInfoGroup2 = new ServiceInfoGroup();
                serviceInfoGroup2.setLabelTab(ServiceInfoGroup.LABEL_TAB_DEFAULT);
                serviceInfoGroup2.setServiceItem(si);
                serviceInfoGroups.add(serviceInfoGroup2);
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
            default:
                view = LayoutInflater.from(mContext).inflate(R.layout.item_service_defualt_new, parent, false);
                return new DefaultHolder(view);
        }
    }


    @Override
    public int getItemViewType(int position) {
        if (serviceInfoGroups.get(position).getLabelTab() == ServiceInfoGroup.LABEL_TAB_TITLE)
            return SERVICE_TYPE_TITLE;
        return super.getItemViewType(position);

    }

    @Override
    public void onBindViewHolder(final BaseViewHolder holder, int position) {
        if (holder == null) return;
        holder.setIsRecyclable(false);//禁止复用

        if (holder instanceof DefaultHolder) {
            final int pos = holder.getAdapterPosition();
            final ServiceInfoGroup data = serviceInfoGroups.get(pos);
            if (data == null) return;

            ((DefaultHolder) holder).tv_content_title.setText(data.getServiceItem().getTitile());
            ((DefaultHolder) holder).btn_content_cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onCancelClick(data.getServiceItem().getServeType(),data.getServiceItem().getId());
                }
            });

            //--------start 设置时间，数量------
            //私人定制和代订门票显示日期。
            //代订酒店，向导陪游，车导服务显示天数
            String initOneDay = null;
            List<String> initDays = null;
            if(TextUtils.equals(data.getServiceItem().getValue(),Constant.SERVICE_TYPE_SHORT_CUSTOM)
                    || TextUtils.equals(data.getServiceItem().getValue(),Constant.SERVICE_TYPE_SHORT_TICKET)){
                initOneDay = data.getServiceItem().getOneTime();
                ((DefaultHolder) holder).tv_time_content.setText(TextUtils.isEmpty(data.getServiceItem().getOneTime()) ?"0":data.getServiceItem().getOneTime());
            }else{
                initDays = data.getServiceItem().getDays();
                if(data.getServiceItem().getDays() == null || data.getServiceItem().getDays().size() == 0){
                    ((DefaultHolder) holder).tv_time_content.setText("0");
                }else{
                    ((DefaultHolder) holder).tv_time_content.setText(data.getServiceItem().getDays().size() + "天");
                }
            }
            ((DefaultHolder) holder).nv_count_content.setNum(data.getServiceItem().getNumber());
            setItemContent(data,((DefaultHolder) holder).tv_time_content,data.getServiceItem().getNumber()
                    ,((DefaultHolder) holder).tv_price_count,((DefaultHolder) holder).tv_price_total,initDays,initOneDay);
            //--------end 设置时间，数量------


            //日期选择事件
            ((DefaultHolder) holder).ll_time.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //私人定制和代订门票 进入 单选 日期
                    //代订酒店，向导陪游，车导服务 进入 多选 日期
                    Intent intent = new Intent(mContext, CalendarActivity.class);
                    if(TextUtils.equals(data.getServiceItem().getValue() , Constant.SERVICE_TYPE_SHORT_CUSTOM)
                            || TextUtils.equals(data.getServiceItem().getValue() , Constant.SERVICE_TYPE_SHORT_TICKET)){
                        calendarType = 3;
                        intent.putExtra(CalendarActivity.CALENDAR_ONE_DAY,data.getServiceItem().getOneTime());
                    }else{
                        calendarType = 2;
                        intent.putStringArrayListExtra(CalendarActivity.CALENDAR_DAYS, (ArrayList<String>) data.getServiceItem().getDays());
                    }
                    intent.putExtra(CalendarActivity.CALENDAR_ID,calendarType);

                    //TODO 传入数据

                    mContext.startActivity(intent);
                    calDisposable = RxBus2.getInstance().toObservable(CalendarEvent.class, new Consumer<CalendarEvent>() {
                        @Override
                        public void accept(CalendarEvent calendarEvent) throws Exception {
                            calDisposable.dispose();

                            String calendarOneDay = null;
                            List<String> calendarDays = null;

                            if(calendarType == 3){
                                if(TextUtils.isEmpty(calendarEvent.getOneTime())){
                                    return;
                                }
                                calendarOneDay = calendarEvent.getOneTime();
                                ((DefaultHolder) holder).tv_time_content.setText(calendarEvent.getOneTime());
                            }else{
                                if(calendarEvent.getMarkerDays().size() < 1){
                                    return;
                                }
                                calendarDays = calendarEvent.getMarkerDays();
                                ((DefaultHolder) holder).tv_time_content.setText(calendarEvent.getMarkerDays().size() + "天");
                            }

                            setItemContent(data,((DefaultHolder) holder).tv_time_content,((DefaultHolder) holder).nv_count_content.getNum()
                                    ,((DefaultHolder) holder).tv_price_count,((DefaultHolder) holder).tv_price_total,calendarDays,calendarOneDay);

                        }
                    });
                }
            });

            //加减数量事件
            ((DefaultHolder) holder).nv_count_content.setCallback(new NumberView.OnItemClickListener() {
                @Override
                public void onClick(int num) {
                    //私人定制和代订门票显示日期。
                    //代订酒店，向导陪游，车导服务显示天数
                    String initOneDay = null;
                    List<String> initDays = null;
                    if(TextUtils.equals(data.getServiceItem().getValue() , Constant.SERVICE_TYPE_SHORT_CUSTOM)
                            || TextUtils.equals(data.getServiceItem().getValue() , Constant.SERVICE_TYPE_SHORT_TICKET)){
                        initOneDay = data.getServiceItem().getOneTime();
                    }else{
                        initDays = data.getServiceItem().getDays();
                    }

                    setItemContent(data,((DefaultHolder) holder).tv_time_content,num
                            ,((DefaultHolder) holder).tv_price_count,((DefaultHolder) holder).tv_price_total,initDays,initOneDay);
                }
            });

            setItemTitle(data,((DefaultHolder) holder).tv_time_title,((DefaultHolder) holder).tv_count_title);
        }

        if (holder instanceof TitleHolder) {
            final int pos = holder.getAdapterPosition();
            final ServiceInfoGroup data = serviceInfoGroups.get(pos);
            if (data == null) return;

            ((TitleHolder) holder).service_title.setText(data.getServiceTitle());
            if (data.isServiceTitleColor()) {
                ((TitleHolder) holder).service_title.setBackground(ContextCompat.getDrawable(mContext, R.drawable.btn_theme_solid_r40));
                ((TitleHolder) holder).service_title.setTextColor(ContextCompat.getColor(mContext,R.color.white));
            } else {
                ((TitleHolder) holder).service_title.setBackground(ContextCompat.getDrawable(mContext, R.drawable.btn_ee_solid_r40));
                ((TitleHolder) holder).service_title.setTextColor(ContextCompat.getColor(mContext,R.color.color_text));
            }

            ((TitleHolder) holder).service_title.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onTitleClick(data.getServiceTitle(),data.getServiceTypeShort(),data.getServiceTypeId(),data.getId());
                }
            });

        }
    }


    @Override
    public int getItemCount() {
        return serviceInfoGroups.size();
    }


    //设置time，count标题文字
    public void setItemTitle(ServiceInfoGroup data, TextView timeTitle, TextView countTitle){
        switch (data.getServiceItem().getValue()){
            case Constant.SERVICE_TYPE_SHORT_CUSTOM:
                timeTitle.setText("出发日期");
                countTitle.setText("出行人数");
                break;
            case Constant.SERVICE_TYPE_SHORT_CAR:
            case Constant.SERVICE_TYPE_SHORT_GUIDE:
                timeTitle.setText("出行日期");
                countTitle.setText("出行人数");
                break;
            case Constant.SERVICE_TYPE_SHORT_HOTEL:
                timeTitle.setText("入住时间");
                countTitle.setText("预订间数");
                break;
            case Constant.SERVICE_TYPE_SHORT_TICKET:
                timeTitle.setText("日期");
                countTitle.setText("数量");
                break;
        }
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

        TextView tv_content_title, tv_time_title, tv_time_content, tv_count_title, tv_price_total, tv_price_count;
        TextView btn_content_cancel;
        LinearLayout ll_time;
        NumberView nv_count_content;


        DefaultHolder(View itemView) {
            super(itemView);
            tv_content_title = itemView.findViewById(R.id.tv_content_title);
            tv_time_title = itemView.findViewById(R.id.tv_time_title);
            tv_time_content = itemView.findViewById(R.id.tv_time_content);
            tv_count_title = itemView.findViewById(R.id.tv_count_title);
            tv_price_total = itemView.findViewById(R.id.tv_price_total);
            tv_price_count = itemView.findViewById(R.id.tv_price_count);
            btn_content_cancel = itemView.findViewById(R.id.btn_content_cancel);
            ll_time = itemView.findViewById(R.id.ll_time);
            nv_count_content = itemView.findViewById(R.id.nv_count_content);
        }
    }

    //--------------View Holder end----------------


    //-----start 事件
    OnTitleClickListener mOnItemClickListener;

    public interface OnTitleClickListener {
        void onTitleClick(String serviceTypeName ,String serviceTypeShort, int serviceTypeId, int id);
        void onCancelClick(int serviceTypeId, int id);
    }

    public void setOnItemClickListener(OnTitleClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }
    //-----end 事件


    public void setItemContent(ServiceInfoGroup data, TextView timeContent,int num,TextView priceCount,TextView priceTotal,List<String> days,String oneDay){
        String timeDay = timeContent.getText().toString();
        int timeDay2 = 0;
        switch (data.getServiceItem().getValue()){
            case Constant.SERVICE_TYPE_SHORT_CUSTOM:
                if(TextUtils.equals(timeDay,"0")){
                    timeDay2 = 0;
                    priceCount.setText(timeDay2 + " x " + num + "人 x " + "￥" + data.getServiceItem().getPrice());
                }
                else{
                    timeDay2 = 1;
                    priceCount.setText(num + "人 x " + "￥" + data.getServiceItem().getPrice());
                }
                priceTotal.setText("￥" + (timeDay2 * num * data.getServiceItem().getPrice()));
                break;
            case Constant.SERVICE_TYPE_SHORT_CAR:
            case Constant.SERVICE_TYPE_SHORT_GUIDE:
                if(TextUtils.equals(timeDay,"0")){
                    timeDay2 = 0;
                }else{
                    timeDay = timeDay.substring(0, timeDay.length() - 1);
                    timeDay2 = Integer.valueOf(timeDay);
                }
                priceCount.setText(timeDay2 + "天 x " + "￥" + data.getServiceItem().getPrice());
                priceTotal.setText("￥" + (timeDay2 * data.getServiceItem().getPrice()));
                break;
            case Constant.SERVICE_TYPE_SHORT_HOTEL:
                if(TextUtils.equals(timeDay,"0")){
                    timeDay2 = 0;
                }else{
                    timeDay = timeDay.substring(0, timeDay.length() - 1);
                    timeDay2 = Integer.valueOf(timeDay);
                }
                priceCount.setText(timeDay2 + "天 x " +num + "间 x " + "￥" + data.getServiceItem().getPrice());
                priceTotal.setText("￥" + (timeDay2 * num * data.getServiceItem().getPrice()));
                break;
            case Constant.SERVICE_TYPE_SHORT_TICKET:
                if(TextUtils.equals(timeDay,"0")){
                    timeDay2 = 0;
                    priceCount.setText(timeDay2 + " x " + num + "张 x " + "￥" + data.getServiceItem().getPrice());
                } else{
                    timeDay2 = 1;
                    priceCount.setText(num + "张 x " + "￥" + data.getServiceItem().getPrice());
                }
                priceTotal.setText("￥" + (timeDay2 * num * data.getServiceItem().getPrice()));
                break;
        }
        data.getServiceItem().setNumber(num);
        if(days!=null){
            data.getServiceItem().setDays(days);
        }
        if(oneDay != null){
            data.getServiceItem().setOneTime(oneDay);
        }
        data.getServiceItem().setTimeDay(timeDay2);
        RxBus2.getInstance().post(new ServicePriceEvent());
    }
}
