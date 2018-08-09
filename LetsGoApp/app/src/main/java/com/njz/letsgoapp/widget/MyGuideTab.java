package com.njz.letsgoapp.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.njz.letsgoapp.R;
import com.njz.letsgoapp.util.AppUtils;

/**
 * Created by LGQ
 * Time: 2018/8/9
 * Function:
 */

public class MyGuideTab extends LinearLayout implements View.OnClickListener {

    TextView tv_synthesize, tv_price, tv_comment, tv_screen, tv_sell;
    public static final int MYGUIDETAB_SYNTHESIZE = 0;
    public static final int MYGUIDETAB_PRICE = 3;
    public static final int MYGUIDETAB_COMMENT = 1;
    public static final int MYGUIDETAB_SCREEN = 4;
    public static final int MYGUIDETAB_SELL = 2;


    public MyGuideTab(Context context) {
        this(context, null);
    }

    public MyGuideTab(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyGuideTab(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        View view = LayoutInflater.from(context).inflate(R.layout.my_tab_view, this, true);


        tv_synthesize = view.findViewById(R.id.tv_synthesize);
        tv_price = view.findViewById(R.id.tv_price);
        tv_comment = view.findViewById(R.id.tv_comment);
        tv_screen = view.findViewById(R.id.tv_screen);
        tv_sell = view.findViewById(R.id.tv_sell);

        tv_synthesize.setOnClickListener(this);
        tv_price.setOnClickListener(this);
        tv_comment.setOnClickListener(this);
        tv_screen.setOnClickListener(this);
        tv_sell.setOnClickListener(this);

        setTextColor(tv_synthesize);

    }

    @Override
    public void onClick(View v) {
        int index = -1;
        switch (v.getId()){
            case R.id.tv_synthesize:
                setTextColor(tv_synthesize);
                index = MYGUIDETAB_SYNTHESIZE;
                break;
            case R.id.tv_price:
                setTextColor(tv_price);
                index = MYGUIDETAB_PRICE;
                break;
            case R.id.tv_comment:
                setTextColor(tv_comment);
                index = MYGUIDETAB_COMMENT;
                break;
            case R.id.tv_screen:
                setTextColor(tv_screen);
                index = MYGUIDETAB_SCREEN;
                break;
            case R.id.tv_sell:
                setTextColor(tv_sell);
                index = MYGUIDETAB_SELL;
                break;
        }
        if(onItemClickListener!=null){
            onItemClickListener.onClick(index);
        }
    }


    OnItemClickListener onItemClickListener;

    public void setCallback(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }

    private void setTextColor(TextView tv){
        tv_synthesize.setTextColor(ContextCompat.getColor(AppUtils.getContext(),R.color.white));
        tv_price.setTextColor(ContextCompat.getColor(AppUtils.getContext(),R.color.white));
        tv_comment.setTextColor(ContextCompat.getColor(AppUtils.getContext(),R.color.white));
        tv_screen.setTextColor(ContextCompat.getColor(AppUtils.getContext(),R.color.white));
        tv_sell.setTextColor(ContextCompat.getColor(AppUtils.getContext(),R.color.white));
        tv.setTextColor(ContextCompat.getColor(AppUtils.getContext(),R.color.colorAccent));
    }



    public interface OnItemClickListener {
        void onClick(int position);
    }
}
