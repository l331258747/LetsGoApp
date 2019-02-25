package com.njz.letsgoapp.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.njz.letsgoapp.R;
import com.njz.letsgoapp.view.other.CouponReceiveActivity;

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

        iv_img.setOnClickListener(this);
        layout_parent.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.layout_parent:
                dismiss();
                break;
            case R.id.iv_img:
                intent = new Intent(context, CouponReceiveActivity.class);
                context.startActivity(intent);
                dismiss();
                break;
        }
    }
}
