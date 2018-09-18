package com.dali.custompicker.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dali.custompicker.CalendarData;
import com.dali.custompicker.R;
import com.dali.custompicker.bean.DayTimeEntity;
import com.dali.custompicker.holder.DayTimeViewHolder;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by xqx on 2017/1/17.
 */
public class DayTimeAdapter2 extends RecyclerView.Adapter<DayTimeViewHolder>{

    private ArrayList<DayTimeEntity> days;
    private Context context;

    public DayTimeAdapter2(ArrayList<DayTimeEntity> days, Context context) {
        this.days = days;
        this.context = context;

    }

    @Override
    public DayTimeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        DayTimeViewHolder ret = null;
        // 不需要检查是否复用，因为只要进入此方法，必然没有复用
        // 因为RecyclerView 通过Holder检查复用
        View v = LayoutInflater.from(context).inflate(R.layout.item_recycler_selectday, parent, false);
        ret = new DayTimeViewHolder(v);

        return ret;
    }

    @Override
    public void onBindViewHolder(final DayTimeViewHolder holder, final int position) {
        final DayTimeEntity dayTimeEntity = days.get(position);
        //显示日期
        if (dayTimeEntity.getDay()!=0) {
            if (dayTimeEntity.getStatus() == 100){
                holder.select_txt_day.setText(dayTimeEntity.getDay() + "");
                holder.select_ly_day.setEnabled(false);
                holder.select_txt_day.setTextColor(Color.parseColor("#FFFFFF"));
            }else if (dayTimeEntity.getStatus() == 101){
                holder.select_txt_day.setText("今天");
                holder.select_ly_day.setEnabled(true);
            }else {
                holder.select_txt_day.setText(dayTimeEntity.getDay() + "");
                holder.select_ly_day.setEnabled(true);
            }
        }else{
            holder.select_ly_day.setEnabled(false);
        }
        holder.select_ly_day.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dayTimeEntity.setSelect(!dayTimeEntity.isSelect());
                    if(dayTimeEntity.isSelect()){
                        CalendarData.markerDays.add(dayTimeEntity);
                    holder.select_ly_day.setBackgroundResource(R.drawable.bg_time_startstop);
                }else{
                    CalendarData.markerDays.remove(dayTimeEntity);
                    holder.select_ly_day.setBackgroundResource(R.color.white);
                }
            }
        });

        if (dayTimeEntity.getStatus() == 100){
            holder.select_ly_day.setBackgroundResource(R.drawable.bg_time_gray);
        }else {
            holder.select_ly_day.setBackgroundResource(R.color.white);

            for (DayTimeEntity dayTimeEntity1 : CalendarData.markerDays){

                Log.e("calendar","dayTimeEntity1:" + dayTimeEntity1.getYear());
                Log.e("calendar","dayTimeEntity1:" + dayTimeEntity1.getMonth());
                Log.e("calendar","dayTimeEntity1:" + dayTimeEntity1.getDay());

                Log.e("calendar","dayTimeEntity:" + dayTimeEntity.getYear());
                Log.e("calendar","dayTimeEntity:" + dayTimeEntity.getMonth());
                Log.e("calendar","dayTimeEntity:" + dayTimeEntity.getDay());


                if(dayTimeEntity1.getYear() == dayTimeEntity.getYear() && dayTimeEntity1.getMonth() == dayTimeEntity.getMonth() && dayTimeEntity1.getDay() == dayTimeEntity.getDay()){
                    holder.select_ly_day.setBackgroundResource(R.drawable.bg_time_startstop);
                }
            }
        }

    }

    @Override
    public int getItemCount() {
        int ret = 0;
        if (days!=null){
            ret = days.size();
        }
        return ret;
    }
}
