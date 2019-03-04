package com.njz.letsgoapp.adapter.coupon;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.njz.letsgoapp.R;
import com.njz.letsgoapp.bean.coupon.CouponModel;

import java.util.List;

/**
 * Created by LGQ
 * Time: 2019/2/25
 * Function:
 */

public class CouponReceiveAdapter extends RecyclerView.Adapter<CouponReceiveAdapter.ViewHolder> {

    Context mContext;
    List<CouponModel> datas;

    public CouponReceiveAdapter(Context mContext, List<CouponModel> datas) {
        this.mContext = mContext;
        this.datas = datas;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(mContext).inflate(R.layout.item_coupon_receive, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (holder == null) return;
        final CouponModel data = datas.get(position);
        if (data == null) return;

        holder.tv_price.setText("￥" + data.getTypeMoneyStr());
        holder.tv_title.setText(data.getTitle());
        holder.tv_limit.setText("满" + data.getFillMoney()+"元可用");
        holder.tv_expire.setText("有效期至" + data.getUseEndDateStr());

    }

    public void setData(List<CouponModel> datas) {
        this.datas = datas;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tv_price,tv_title,tv_limit,tv_expire;

        public ViewHolder(View itemView) {
            super(itemView);

            tv_price = itemView.findViewById(R.id.tv_price);
            tv_title = itemView.findViewById(R.id.tv_title);
            tv_limit = itemView.findViewById(R.id.tv_limit);
            tv_expire = itemView.findViewById(R.id.tv_expire);
        }
    }
}
