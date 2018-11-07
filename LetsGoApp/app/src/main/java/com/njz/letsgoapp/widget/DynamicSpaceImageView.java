package com.njz.letsgoapp.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.njz.letsgoapp.R;
import com.njz.letsgoapp.adapter.home.SpaceDynamicAdapter;
import com.njz.letsgoapp.util.glide.GlideUtil;

import java.util.List;

/**
 * Created by LGQ
 * Time: 2018/8/24
 * Function:
 */

public class DynamicSpaceImageView extends LinearLayout {

    LinearLayout ll_left, ll_right;
    ImageView iv_left1, iv_left2, iv_right1, iv_right2;

    Context context;

    public DynamicSpaceImageView(Context context) {
        this(context, null);
    }

    public DynamicSpaceImageView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DynamicSpaceImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        View view = LayoutInflater.from(context).inflate(R.layout.view_dynamic_space_image, this, true);

        this.context = context;

        ll_left = findViewById(R.id.ll_left);
        ll_right = findViewById(R.id.ll_right);

        iv_left1 = findViewById(R.id.iv_left1);
        iv_left2 = findViewById(R.id.iv_left2);
        iv_right1 = findViewById(R.id.iv_right1);
        iv_right2 = findViewById(R.id.iv_right2);
    }

    private void setVisibility(){
        ll_left.setVisibility(GONE);
        ll_right.setVisibility(GONE);

        iv_left1.setVisibility(GONE);
        iv_left2.setVisibility(GONE);
        iv_right1.setVisibility(GONE);
        iv_right2.setVisibility(GONE);
    }


    public void setImages(List<String> datas) {
        setVisibility();
        if (datas == null || datas.size() == 0) {
//            setVisibility(GONE);
            return;
        }

        setOnclick();

        switch (datas.size()) {
            case 1:
                ll_left.setVisibility(VISIBLE);
                iv_left1.setVisibility(VISIBLE);
                GlideUtil.LoadDefaultImage(context, datas.get(0), iv_left1,R.mipmap.dynamic_default);
                break;
            case 2:
                ll_left.setVisibility(VISIBLE);
                ll_right.setVisibility(VISIBLE);

                iv_left1.setVisibility(VISIBLE);
                GlideUtil.LoadDefaultImage(context, datas.get(0), iv_left1,R.mipmap.dynamic_default);

                iv_right1.setVisibility(VISIBLE);
                GlideUtil.LoadDefaultImage(context, datas.get(1), iv_right1,R.mipmap.dynamic_default);
                break;
            case 3:
                ll_left.setVisibility(VISIBLE);
                ll_right.setVisibility(VISIBLE);

                iv_left1.setVisibility(VISIBLE);
                GlideUtil.LoadDefaultImage(context, datas.get(0), iv_left1,R.mipmap.dynamic_default);

                iv_right1.setVisibility(VISIBLE);
                GlideUtil.LoadDefaultImage(context, datas.get(1), iv_right1,R.mipmap.dynamic_default);
                iv_right2.setVisibility(VISIBLE);
                GlideUtil.LoadDefaultImage(context, datas.get(2), iv_right2,R.mipmap.dynamic_default);
                break;
            default :
                ll_left.setVisibility(VISIBLE);
                ll_right.setVisibility(VISIBLE);

                iv_left1.setVisibility(VISIBLE);
                GlideUtil.LoadDefaultImage(context, datas.get(0), iv_left1,R.mipmap.dynamic_default);
                iv_left2.setVisibility(VISIBLE);
                GlideUtil.LoadDefaultImage(context, datas.get(2), iv_left2,R.mipmap.dynamic_default);

                iv_right1.setVisibility(VISIBLE);
                GlideUtil.LoadDefaultImage(context, datas.get(1), iv_right1,R.mipmap.dynamic_default);
                iv_right2.setVisibility(VISIBLE);
                GlideUtil.LoadDefaultImage(context, datas.get(3), iv_right2,R.mipmap.dynamic_default);
                break;
        }
    }

    private void setOnclick(){
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemClickListener.onClick();
            }
        });
    }

    OnItemClickListener mOnItemClickListener;

    public interface OnItemClickListener {
        void onClick();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }
}
