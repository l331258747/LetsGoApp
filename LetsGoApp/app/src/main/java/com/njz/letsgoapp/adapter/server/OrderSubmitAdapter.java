package com.njz.letsgoapp.adapter.server;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.njz.letsgoapp.R;
import com.njz.letsgoapp.bean.home.ServiceItem;
import com.njz.letsgoapp.bean.server.ServerItem;
import com.njz.letsgoapp.constant.Constant;
import com.njz.letsgoapp.util.DecimalUtil;
import com.njz.letsgoapp.util.glide.GlideUtil;

import java.util.List;

/**
 * Created by LGQ
 * Time: 2018/8/16
 * Function:
 */

public class OrderSubmitAdapter extends RecyclerView.Adapter<OrderSubmitAdapter.ViewHolder> {

    private Context context;
    private List<ServerItem> serverItems;

    public OrderSubmitAdapter(Context context, List<ServerItem> data) {
        this.context = context;
        this.serverItems = data;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(context).inflate(R.layout.item_order_submit2, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (holder == null) return;
        if (holder instanceof ViewHolder) {
            final int pos = holder.getAdapterPosition();
            final ServerItem data = serverItems.get(pos);
            if (data == null) return;

            GlideUtil.LoadRoundImage(context, data.getImg(), holder.iv_img,5);
            holder.tv_title.setText(data.getTitile());
            holder.tv_serverName.setText(data.getServiceTypeName());
            holder.tv_price_total.setText("￥" + (data.getPrice() * data.getServeNum()));

            holder.ll_count.setVisibility(View.VISIBLE);
            if(data.getServerType() == Constant.SERVER_TYPE_GUIDE_ID){
                holder.ll_count.setVisibility(View.GONE);
            }
            holder.tv_count_content.setText(data.getCountContent());

            holder.tv_time_title.setText(data.getTimeTitle());
            holder.tv_time_content.setText(data.getSelectTimeValueList());
        }
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        return serverItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView iv_img;
        TextView tv_title, tv_serverName,  tv_price_total;
        LinearLayout ll_count;
        TextView tv_count_title, tv_count_content, tv_time_title, tv_time_content;

        public ViewHolder(View itemView) {
            super(itemView);
            iv_img = itemView.findViewById(R.id.iv_img);
            tv_title = itemView.findViewById(R.id.tv_title);
            tv_serverName = itemView.findViewById(R.id.tv_serverName);
            tv_price_total = itemView.findViewById(R.id.tv_price_total);

            tv_time_content = itemView.findViewById(R.id.tv_time_content);
            tv_time_title = itemView.findViewById(R.id.tv_time_title);

            ll_count = itemView.findViewById(R.id.ll_count);
            tv_count_content = itemView.findViewById(R.id.tv_count_content);
            tv_count_title = itemView.findViewById(R.id.tv_count_title);

        }
    }
}
