package com.njz.letsgoapp.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.njz.letsgoapp.R;
import com.njz.letsgoapp.util.glide.GlideUtil;
import com.njz.letsgoapp.view.coupon.CouponReceiveActivity;

/**
 * Created by LGQ
 * Time: 2019/2/25
 * Function:
 */

public class ActivityDialog extends Dialog implements View.OnClickListener {

    Context context;

    ImageView iv_img;
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

        iv_img.setOnClickListener(this);
        layout_parent.setOnClickListener(this);
        tv_close.setOnClickListener(this);

        GlideUtil.LoadImageFitCenter(context,imgurl,iv_img);
    }

    public void setData(String imgurl,int eventId){
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
                intent.putExtra("eventId",eventId);
                context.startActivity(intent);
                dismiss();
                break;
        }
    }
}
