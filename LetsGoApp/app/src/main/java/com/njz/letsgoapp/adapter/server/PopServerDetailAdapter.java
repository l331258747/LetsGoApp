package com.njz.letsgoapp.adapter.server;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.njz.letsgoapp.R;
import com.njz.letsgoapp.bean.server.ServerItem;
import com.njz.letsgoapp.constant.Constant;
import com.njz.letsgoapp.util.DecimalUtil;
import com.njz.letsgoapp.widget.NumberView;

import java.util.List;

/**
 * Created by LGQ
 * Time: 2018/12/14
 * Function:
 */

public class PopServerDetailAdapter extends RecyclerView.Adapter<PopServerDetailAdapter.ViewHolder> {

    private Context context;
    private List<ServerItem> serverItems;

    public PopServerDetailAdapter(Context context, List<ServerItem> serverItems) {
        this.context = context;
        this.serverItems = serverItems;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(context).inflate(R.layout.item_pop_server_detail, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        if (holder == null) return;
        if (holder instanceof ViewHolder) {
            final int pos = holder.getAdapterPosition();
            final ServerItem data = serverItems.get(pos);
            if (data == null) return;

            holder.tv_title.setText(data.getTitile());
            holder.tv_price.setText("￥" + DecimalUtil.multiply(data.getPrice(), data.getServeNum()));

            if(data.getServerType() == Constant.SERVER_TYPE_GUIDE_ID){
                holder.tv_cancel.setVisibility(View.VISIBLE);
                holder.numberView.setVisibility(View.GONE);
            }else{
                holder.tv_cancel.setVisibility(View.GONE);
                holder.numberView.setVisibility(View.VISIBLE);
                holder.numberView.setNum(data.getServeNum());
            }

            holder.tv_cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mOnItemClickListener != null){
                        mOnItemClickListener.onCancelClick(pos);
                    }
                }
            });

            holder.numberView.setCallback(new NumberView.OnItemClickListener() {
                @Override
                public void onClick(int num) {
                    if(mOnItemClickListener != null){
                        mOnItemClickListener.onNumClick(pos,num);
                    }
                    holder.tv_price.setText("￥" + DecimalUtil.multiply(data.getPrice(), num));
                }
            });

        }
    }

    @Override
    public int getItemCount() {
        return serverItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_title,tv_price,tv_cancel;
        NumberView numberView;


        public ViewHolder(View itemView) {
            super(itemView);
            numberView = itemView.findViewById(R.id.numberView);
            tv_title = itemView.findViewById(R.id.tv_title);
            tv_price = itemView.findViewById(R.id.tv_price);
            tv_cancel = itemView.findViewById(R.id.tv_cancel);
        }
    }

    OnItemClickListener mOnItemClickListener;

    public interface OnItemClickListener {
        void onCancelClick(int position);
        void onNumClick(int position,int num);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }
}
