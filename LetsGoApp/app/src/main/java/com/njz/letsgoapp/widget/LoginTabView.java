package com.njz.letsgoapp.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.njz.letsgoapp.R;

/**
 * Created by LGQ
 * Time: 2019/1/17
 * Function:
 */

public class LoginTabView extends LinearLayout {

    Context context;
    TextView tv_name;
    View view_line;

    public LoginTabView(Context context) {
        this(context, null);
    }

    public LoginTabView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoginTabView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        View view = LayoutInflater.from(context).inflate(R.layout.view_login_tab, this, true);

        tv_name = view.findViewById(R.id.tv_name);
        view_line = view.findViewById(R.id.view_line);

        TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.login_tab);

        if (attributes != null) {
            String titleText = attributes.getString(R.styleable.login_tab_login_tab_title);
            if (!TextUtils.isEmpty(titleText)) {
                tv_name.setText(titleText);
            }

            boolean showLine = attributes.getBoolean(R.styleable.login_tab_login_tab_line,false);
            if (showLine) {
                view_line.setBackgroundColor(ContextCompat.getColor(context,R.color.color_theme));
                tv_name.setTextColor(ContextCompat.getColor(context,R.color.color_theme));
            }else{
                view_line.setBackgroundColor(ContextCompat.getColor(context,R.color.transparent));
                tv_name.setTextColor(ContextCompat.getColor(context,R.color.color_68));
            }

            attributes.recycle();
        }
    }


    public void setSelect(boolean isSelect){
        if(isSelect){
            tv_name.setTextColor(ContextCompat.getColor(context,R.color.color_theme));
            view_line.setBackgroundColor(ContextCompat.getColor(context,R.color.color_theme));
        }else{
            tv_name.setTextColor(ContextCompat.getColor(context,R.color.color_68));
            view_line.setBackgroundColor(ContextCompat.getColor(context,R.color.transparent));
        }
    }
}
