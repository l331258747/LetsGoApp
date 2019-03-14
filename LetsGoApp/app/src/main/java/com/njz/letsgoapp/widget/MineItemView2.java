package com.njz.letsgoapp.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
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
 * Time: 2018/8/9
 * Function:
 */

public class MineItemView2 extends LinearLayout {

    TextView tv_title;
    TextView tv_content;
    ImageView iv_right_img,iv_left_img;

    public MineItemView2(Context context) {
        this(context, null);
    }

    public MineItemView2(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MineItemView2(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        View view = LayoutInflater.from(context).inflate(R.layout.view_mine_item2, this, true);

        tv_title = findViewById(R.id.tv_title);
        tv_content = findViewById(R.id.tv_content);
        iv_right_img = findViewById(R.id.iv_right_img);
        iv_left_img = findViewById(R.id.iv_left_img);


        TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.home_my_item2);
        if (attributes != null) {
            String titleText = attributes.getString(R.styleable.home_my_item2_home_my_item2_title);
            if (!TextUtils.isEmpty(titleText)) {
                tv_title.setText(titleText);
            }

            String contentText = attributes.getString(R.styleable.home_my_item2_home_my_item2_content);
            if (!TextUtils.isEmpty(contentText)) {
                tv_content.setText(contentText);
            }

            int rightDrawable = attributes.getResourceId(R.styleable.home_my_item2_home_my_item2_right_drawable, -1);
            if (rightDrawable != -1) {
                iv_right_img.setImageDrawable(ContextCompat.getDrawable(context,rightDrawable));
            }else{
                iv_right_img.setVisibility(GONE);
            }

            int leftDrawable = attributes.getResourceId(R.styleable.home_my_item2_home_my_item2_left_drawable, -1);
            if (leftDrawable != -1) {
                iv_left_img.setImageDrawable(ContextCompat.getDrawable(context,leftDrawable));
            }
            attributes.recycle();
        }
    }

    public void setContent(String content){
        tv_content.setText(content);
    }

    public String getContent(){
        return tv_content.getText().toString();
    }


}
