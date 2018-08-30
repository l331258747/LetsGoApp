package com.njz.letsgoapp.adapter.order;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.njz.letsgoapp.R;
import com.njz.letsgoapp.bean.other.ProvinceModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LGQ
 * Time: 2018/8/30
 * Function:
 */

public class CityPickProvinceAdapter extends RecyclerView.Adapter<CityPickProvinceAdapter.ViewHolder> {


    List<ProvinceModel> datas;
    Context context;
    private List<Boolean> isClicks;//控件是否被点击,默认为false，如果被点击，改变值，控件根据值改变自身颜色

    public CityPickProvinceAdapter(Context context,List<ProvinceModel> datas) {
        this.datas = datas;
        this.context = context;
        isClicks = new ArrayList<>();
        for(int i = 0;i<datas.size();i++){
            isClicks.add(false);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_city_pick_province, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        if (holder == null) return;

        holder.tv_name.setText(datas.get(position).getProName());

        if(isClicks.get(position)){
            holder.tv_name.setTextColor(ContextCompat.getColor(context,R.color.color_theme));
        }else{
            holder.tv_name.setTextColor(ContextCompat.getColor(context,R.color.color_text));
        }

        if(mOnItemClickListener != null){
            holder.tv_name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    for(int i = 0; i <isClicks.size();i++){
                        isClicks.set(i,false);
                    }
                    isClicks.set(position,true);
                    notifyDataSetChanged();

                    mOnItemClickListener.onClick(position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tv_name;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_name = itemView.findViewById(R.id.tv_name);
        }

    }

    public void setDatas(List<ProvinceModel> datas){
        this.datas = datas;

        isClicks = new ArrayList<>();
        for(int i = 0;i<datas.size();i++){
            if(i == 0){
                isClicks.add(true);
                mOnItemClickListener.onClick(i);
                continue;
            }
            isClicks.add(false);
        }

        notifyDataSetChanged();
    }

    OnItemClickListener mOnItemClickListener;
    public interface OnItemClickListener {
        void onClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }
}
