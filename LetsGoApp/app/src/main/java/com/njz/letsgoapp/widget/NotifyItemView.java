package com.njz.letsgoapp.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.njz.letsgoapp.R;

/**
 * Created by LGQ
 * Time: 2018/10/11
 * Function:
 */

public class NotifyItemView extends LinearLayout{

    ImageView iv_img;
    TextView tv_title,tv_content,tv_time,tv_num;
    View view_line;


    public NotifyItemView(Context context) {
        this(context,null);
    }

    public NotifyItemView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public NotifyItemView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        View view = LayoutInflater.from(context).inflate(R.layout.view_notify_item, this, true);

        iv_img = findViewById(R.id.iv_img);
        tv_title = findViewById(R.id.tv_title);
        tv_content = findViewById(R.id.tv_content);
        tv_time = findViewById(R.id.tv_time);
        tv_num = findViewById(R.id.tv_num);
        view_line = findViewById(R.id.view_line);

        TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.notify_item);
        if (attributes != null) {
            String titleText = attributes.getString(R.styleable.notify_item_notify_item_title);
            if (!TextUtils.isEmpty(titleText)) {
                tv_title.setText(titleText);
            }

            int leftDrawable = attributes.getResourceId(R.styleable.notify_item_notify_item_img, -1);
            if (leftDrawable != -1) {
                iv_img.setImageDrawable(context.getResources().getDrawable(leftDrawable));
            }

            boolean showLine = attributes.getBoolean(R.styleable.notify_item_notify_item_line,false);
            if (showLine) {
                view_line.setVisibility(VISIBLE);
            }else{
                view_line.setVisibility(GONE);
            }

            attributes.recycle();
        }
    }

    public void setContent(String str){
        tv_content.setText(str);
    }

    public void setTime(String time){
        tv_time.setText(time);
    }

    public void setNum(int num){
        tv_num.setText("" + num);
    }
}
