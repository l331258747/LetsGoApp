package com.njz.letsgoapp.adapter.other;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.njz.letsgoapp.R;
import com.njz.letsgoapp.bean.other.SearchCityModel;

import java.util.List;

/**
 * Created by LGQ
 * Time: 2018/10/12
 * Function:
 */

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder>{
    Context mContext;
    List<SearchCityModel> datas;

    public SearchAdapter(Context mContext, List<SearchCityModel> datas) {
        this.mContext = mContext;
        this.datas = datas;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(mContext).inflate(R.layout.item_search, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        if (holder == null) return;
        final SearchCityModel data = datas.get(position);
        if (data == null) return;

//        holder.iv_img.setImageDrawable();
        holder.tv_content.setText(data.getParentName() + "-" + data.getName());

        holder.ll_parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mOnItemClickListener != null){
                    mOnItemClickListener.onClick(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public void setData(List<SearchCityModel> datas) {
        this.datas = datas;
        notifyDataSetChanged();
    }

    public void addData(List<SearchCityModel> datas){
        this.datas.addAll(datas);
        notifyDataSetChanged();
    }

    public SearchCityModel getItem(int position){
        return this.datas.get(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView iv_img;
        TextView tv_content;
        LinearLayout ll_parent;

        public ViewHolder(View itemView) {
            super(itemView);

            iv_img = itemView.findViewById(R.id.iv_img);
            tv_content = itemView.findViewById(R.id.tv_content);
            ll_parent = itemView.findViewById(R.id.ll_parent);
        }
    }

    OnItemClickListener mOnItemClickListener;
    public interface OnItemClickListener {
        void onClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }
}
