package com.njz.letsgoapp.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.njz.letsgoapp.R;
import com.njz.letsgoapp.view.coupon.CouponReceiveActivity;

/**
 * Created by LGQ
 * Time: 2019/2/25
 * Function:
 */

public class ActivityDialog extends Dialog implements View.OnClickListener {

    Context context;

    ImageView iv_img;
    View view_line;
    TextView tv_close;
    RelativeLayout layout_parent;
    String imgurl;
    int eventId;

    public ActivityDialog(Context context) {
        super(context);
        this.context = context;
    }

    public ActivityDialog(Context context, int theme) {
        super(context, theme);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.dialog_activity, null);
        this.setContentView(layout);

        getWindow().setBackgroundDrawable(new ColorDrawable(0x00000000));
        getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);

        iv_img = layout.findViewById(R.id.iv_img);
        layout_parent = layout.findViewById(R.id.layout_parent);
        tv_close = layout.findViewById(R.id.tv_close);
        view_line = layout.findViewById(R.id.view_line);

        tv_close.setVisibility(View.GONE);
        view_line.setVisibility(View.GONE);

        iv_img.setOnClickListener(this);
        layout_parent.setOnClickListener(this);
        tv_close.setOnClickListener(this);

        if (TextUtils.isEmpty(imgurl)) imgurl = "";
        Glide.with(context).load(imgurl).fitCenter()
                .diskCacheStrategy(DiskCacheStrategy.SOURCE).into(new SimpleTarget<GlideDrawable>() {
            @Override
            public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                //加载完成后的处理
                iv_img.setImageDrawable(resource);

                tv_close.setVisibility(View.VISIBLE);
                view_line.setVisibility(View.VISIBLE);

            }
        });

    }

    public void setData(String imgurl, int eventId) {
        this.imgurl = imgurl;
        this.eventId = eventId;
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.layout_parent:
            case R.id.tv_close:
                dismiss();
                break;
            case R.id.iv_img:
                intent = new Intent(context, CouponReceiveActivity.class);
                intent.putExtra("eventId", eventId);
                context.startActivity(intent);
                dismiss();
                break;
        }
    }
}
