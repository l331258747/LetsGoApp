package com.njz.letsgoapp.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.njz.letsgoapp.R;
import com.njz.letsgoapp.util.glide.GlideUtil;

import java.util.List;

/**
 * Created by LGQ
 * Time: 2018/9/6
 * Function:
 */

public class DynamicNiceImageView extends LinearLayout {

    ImageView iv_img1,iv_img2,iv_img3;
    Context context;

    public DynamicNiceImageView(Context context) {
        this(context,null);
    }

    public DynamicNiceImageView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public DynamicNiceImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;

        View view = LayoutInflater.from(context).inflate(R.layout.view_dynamic_nice_image, this, true);

        iv_img1 = findViewById(R.id.iv_img1);
        iv_img2 = findViewById(R.id.iv_img2);
        iv_img3 = findViewById(R.id.iv_img3);

    }

    private void setVisibility(){
        iv_img1.setVisibility(GONE);
        iv_img2.setVisibility(GONE);
        iv_img3.setVisibility(GONE);
    }


    public void setImages(List<String> datas) {
        setVisibility();
        if (datas == null || datas.size() == 0) {
            return;
        }

        switch (datas.size()) {
            case 1:
                iv_img1.setVisibility(VISIBLE);
                GlideUtil.LoadCircleImage(context, datas.get(0), iv_img1);
                break;
            case 2:
                iv_img1.setVisibility(VISIBLE);
                GlideUtil.LoadCircleImage(context, datas.get(0), iv_img1);
                iv_img2.setVisibility(VISIBLE);
                GlideUtil.LoadCircleImage(context, datas.get(1), iv_img2);
                break;
            default:
                iv_img1.setVisibility(VISIBLE);
                GlideUtil.LoadCircleImage(context, datas.get(0), iv_img1);
                iv_img2.setVisibility(VISIBLE);
                GlideUtil.LoadCircleImage(context, datas.get(1), iv_img2);
                iv_img3.setVisibility(VISIBLE);
                GlideUtil.LoadCircleImage(context, datas.get(2), iv_img3);
                break;
        }
    }
}
