package com.njz.letsgoapp.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.InputFilter;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
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

public class SpecialFixedItemEditView extends LinearLayout {

    TextView tv_name;
    EditText et_input;
    ImageView iv_next;


    public SpecialFixedItemEditView(Context context) {
        this(context, null);
    }

    public SpecialFixedItemEditView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SpecialFixedItemEditView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        View view = LayoutInflater.from(context).inflate(R.layout.view_special_fixed_item_edit, this, true);

        tv_name = findViewById(R.id.login_item_name);
        et_input = findViewById(R.id.login_item_et);
        iv_next = findViewById(R.id.login_item_iv);

        TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.login_item);
        if (attributes != null) {
            String titleText = attributes.getString(R.styleable.login_item_login_item_title);
            if (!TextUtils.isEmpty(titleText)) {
                tv_name.setText(titleText);
            }

            int titleColor = attributes.getResourceId(R.styleable.login_item_login_item_title_color,0);
            if (titleColor != 0) {
                tv_name.setTextColor(ContextCompat.getColor(context,titleColor));
            }

            int nameViewWidth = attributes.getDimensionPixelSize(R.styleable.login_item_login_item_title_width,0);
            if(nameViewWidth != 0){
                tv_name.setWidth(nameViewWidth);
            }

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

    public void setContent(String str){
        et_input.setText(str);
    }

    public void setIvRight(OnClickListener listener){
        iv_next.setOnClickListener(listener);
    }

}
