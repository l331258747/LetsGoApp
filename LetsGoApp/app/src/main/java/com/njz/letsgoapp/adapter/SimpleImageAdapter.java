package com.njz.letsgoapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.njz.letsgoapp.R;
import com.njz.letsgoapp.util.glide.GlideUtil;

import java.util.List;

/**
 * Created by LGQ
 * Time: 2018/8/14
 * Function:
 */

public class SimpleImageAdapter extends RecyclerView.Adapter<SimpleImageAdapter.ViewHolder> {

    private Context context;
    private List<String> data;

    public SimpleImageAdapter(Context context, List<String> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_simple_image,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        //ContextCompat.getDrawable(AppUtils.getContext(), R.drawable.btn_blue_solid_r15)
        GlideUtil.LoadImage(context,data.get(position), holder.img);
    }

    @Override
    public int getItemCount() {
        if(data == null){
            return 0;
        }
        if(data.size() > 4){
            return 4;
        }
        return data.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView img;

        public ViewHolder(View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.simple_image);

        }

    }
}
