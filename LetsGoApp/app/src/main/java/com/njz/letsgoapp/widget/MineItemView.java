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
 * Time: 2018/8/9
 * Function:
 */

public class MineItemView extends LinearLayout {

    TextView tv_title;
    TextView tv_content;
    ImageView iv_next;

    public MineItemView(Context context) {
        this(context, null);
    }

    public MineItemView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MineItemView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        View view = LayoutInflater.from(context).inflate(R.layout.view_mine_item, this, true);

        tv_title = findViewById(R.id.tv_title);
        tv_content = findViewById(R.id.tv_content);
        iv_next = findViewById(R.id.iv_next);


        TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.home_my_item);
        if (attributes != null) {
            String titleText = attributes.getString(R.styleable.home_my_item_title);
            if (!TextUtils.isEmpty(titleText)) {
                tv_title.setText(titleText);
            }

            String contentText = attributes.getString(R.styleable.home_my_item_content);
            if (!TextUtils.isEmpty(contentText)) {
                tv_content.setText(contentText);
            }

            String hint = attributes.getString(R.styleable.home_my_item_hint);
            if (!TextUtils.isEmpty(hint)) {
                tv_content.setHint(hint);
            }

            int leftDrawable = attributes.getResourceId(R.styleable.home_my_item_left_drawable, -1);
            if (leftDrawable != -1) {
                iv_next.setImageDrawable(context.getResources().getDrawable(leftDrawable));
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
