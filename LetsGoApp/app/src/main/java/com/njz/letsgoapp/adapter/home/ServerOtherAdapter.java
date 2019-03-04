package com.njz.letsgoapp.adapter.home;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.njz.letsgoapp.R;
import com.njz.letsgoapp.bean.server.ServerDetailModel;
import com.njz.letsgoapp.util.glide.GlideUtil;
import com.njz.letsgoapp.widget.PriceView;

import java.util.List;

/**
 * Created by LGQ
 * Time: 2018/12/4
 * Function:
 */

public class ServerOtherAdapter extends RecyclerView.Adapter<ServerOtherAdapter.ViewHolder> {

    Context mContext;
    List<ServerDetailModel> datas;

    public ServerOtherAdapter(Context mContext, List<ServerDetailModel> datas) {
        this.mContext = mContext;
        this.datas = datas;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(mContext).inflate(R.layout.item_server_other, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        if (holder == null) return;
        final int pos = holder.getAdapterPosition();
        final ServerDetailModel data = datas.get(pos);
        if (data == null) return;

        GlideUtil.LoadTopRoundImage(mContext, data.getTitleImg(), holder.iv_img,5);

        holder.tv_server.setText(data.getServeTypeName() + " | " + data.getAddress());
        holder.tv_title.setText(data.getTitle());
        holder.tv_count.setText("已售"+data.getSellCount());
        holder.preview.setPrice(data.getServePrice());

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

    public void setData(List<ServerDetailModel> datas){
        this.datas = datas;
    }

    public List<ServerDetailModel> getDatas(){
        return this.datas;
    }

    public void addData(List<ServerDetailModel> datas){
        this.datas.addAll(datas);
    }

    public ServerDetailModel getData(int position){
       return this.datas.get(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView iv_img;
        TextView tv_server,tv_title,tv_count;
        PriceView preview;
        LinearLayout ll_parent;

        public ViewHolder(View itemView) {
            super(itemView);
            iv_img = itemView.findViewById(R.id.iv_img);
            tv_server = itemView.findViewById(R.id.tv_server);
            tv_title = itemView.findViewById(R.id.tv_title);
            tv_count = itemView.findViewById(R.id.tv_count);
            preview = itemView.findViewById(R.id.preview);
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
