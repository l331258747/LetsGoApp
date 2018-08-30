package com.njz.letsgoapp.adapter.order;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.njz.letsgoapp.R;
import com.njz.letsgoapp.bean.other.CityModel;
import com.njz.letsgoapp.util.glide.GlideUtil;

import java.util.List;

/**
 * Created by LGQ
 * Time: 2018/8/30
 * Function:
 */

public class CityPickCityAdapter extends RecyclerView.Adapter<CityPickCityAdapter.ViewHolder> {

    List<CityModel> datas;
    Context context;

    public CityPickCityAdapter(Context context,List<CityModel> datas) {
        this.datas = datas;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_city_pick_city, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        if (holder == null) return;

        GlideUtil.LoadImage(context,datas.get(position).getImgUrl(),holder.iv_img);
        holder.iv_name.setText(datas.get(position).getName());

        if(mOnItemClickListener != null){
            holder.iv_img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
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

        ImageView iv_img;
        TextView iv_name;

        public ViewHolder(View itemView) {
            super(itemView);

            iv_img = itemView.findViewById(R.id.iv_img);
            iv_name = itemView.findViewById(R.id.iv_name);
        }
    }

    public void setDatas(List<CityModel> datas){
        this.datas = datas;
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
