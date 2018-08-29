package com.njz.letsgoapp.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.njz.letsgoapp.R;

/**
 * Created by LGQ
 * Time: 2018/8/10
 * Function:
 */

public class LoginItemView2 extends LinearLayout {

    EditText et_input;
    ImageView iv_next;
    TextView login_item_tv;

    public LoginItemView2(Context context) {
        this(context, null);
    }

    public LoginItemView2(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoginItemView2(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        View view = LayoutInflater.from(context).inflate(R.layout.view_login_item2, this, true);

        et_input = findViewById(R.id.login_item_et);
        iv_next = findViewById(R.id.login_item_iv);
        login_item_tv = findViewById(R.id.login_item_tv);

        TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.login_item);
        if (attributes != null) {

            int etViewlength = attributes.getInteger(R.styleable.login_item_login_item_max_length,20);
            if(etViewlength != 0){
                et_input.setFilters(new InputFilter[]{new InputFilter.LengthFilter(etViewlength)});
            }

            String hint = attributes.getString(R.styleable.login_item_login_item_hint);
            if (!TextUtils.isEmpty(hint)) {
                et_input.setHint(hint);
            }

            int leftDrawable = attributes.getResourceId(R.styleable.login_item_login_item_right_drawable, -1);
            if (leftDrawable != -1) {
                iv_next.setImageDrawable(context.getResources().getDrawable(leftDrawable));
            }


            String rightText = attributes.getString(R.styleable.login_item_login_item_right_text);
            if (!TextUtils.isEmpty(hint)) {
                login_item_tv.setVisibility(VISIBLE);
                login_item_tv.setText(rightText);
            }

            attributes.recycle();
        }
    }

    public void setEtInputType(int type){
        et_input.setInputType(type);
    }

    public String getEtContent(){
        return et_input.getText().toString();
    }

    public EditText getEtView(){
        return et_input;
    }

    public TextView getRightText(){
        return login_item_tv;
    }

    public void setOnClickLisener(OnClickListener listener){
        login_item_tv.setOnClickListener(listener);
    }

}
