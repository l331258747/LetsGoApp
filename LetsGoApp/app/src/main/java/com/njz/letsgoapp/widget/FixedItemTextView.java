package com.njz.letsgoapp.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.njz.letsgoapp.R;

/**
 * Created by LGQ
 * Time: 2018/8/10
 * Function:
 */

public class FixedItemTextView extends LinearLayout {

    TextView tv_name;
    TextView login_item_content;
    ImageView iv_next;


    public FixedItemTextView(Context context) {
        this(context, null);
    }

    public FixedItemTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FixedItemTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        View view = LayoutInflater.from(context).inflate(R.layout.view_fixed_item_text, this, true);

        tv_name = findViewById(R.id.login_item_name);
        login_item_content = findViewById(R.id.login_item_content);
        iv_next = findViewById(R.id.login_item_iv);

        TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.login_item);
        if (attributes != null) {
            String titleText = attributes.getString(R.styleable.login_item_login_item_title);
            if (!TextUtils.isEmpty(titleText)) {
                tv_name.setText(titleText);
            }

            int nameViewWidth = attributes.getDimensionPixelSize(R.styleable.login_item_login_item_title_width, 0);
            if (nameViewWidth != 0) {
                tv_name.setWidth(nameViewWidth);
            }

            int contentSize = attributes.getInteger(R.styleable.login_item_login_item_content_size, 0);
            if (contentSize != 0) {
                login_item_content.setTextSize(TypedValue.COMPLEX_UNIT_SP,contentSize);
            }


            int leftDrawable = attributes.getResourceId(R.styleable.login_item_login_item_right_drawable, -1);
            if (leftDrawable != -1) {
                iv_next.setImageDrawable(context.getResources().getDrawable(leftDrawable));
            }
            attributes.recycle();
        }
    }

    public void setContent(String str) {
        login_item_content.setText(str);
    }


}
