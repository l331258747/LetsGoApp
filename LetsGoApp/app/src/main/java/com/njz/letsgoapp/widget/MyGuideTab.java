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

    TextView tv_synthesize, tv_count, tv_score, tv_comment, tv_screen;
    View line_synthesize, line_count, line_score, line_comment;
    public static final int MYGUIDETAB_SYNTHESIZE = 1;
    public static final int MYGUIDETAB_COUNT = 2;
    public static final int MYGUIDETAB_SCORE = 3;
    public static final int MYGUIDETAB_COMMENT = 4;
    public static final int MYGUIDETAB_SCREEN = 5;

    Context context;

    public MyGuideTab(Context context) {
        this(context, null);
    }

    public MyGuideTab(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyGuideTab(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        this.context = context;

        View view = LayoutInflater.from(context).inflate(R.layout.my_tab_view, this, true);


        tv_synthesize = view.findViewById(R.id.tv_synthesize);
        tv_count = view.findViewById(R.id.tv_count);
        tv_score = view.findViewById(R.id.tv_score);
        tv_screen = view.findViewById(R.id.tv_screen);
        tv_comment = view.findViewById(R.id.tv_comment);

        //line_synthesize, line_count, line_score, line_comment,;
        line_synthesize = view.findViewById(R.id.line_synthesize);
        line_count = view.findViewById(R.id.line_count);
        line_score = view.findViewById(R.id.line_score);
        line_comment = view.findViewById(R.id.line_comment);

        tv_synthesize.setOnClickListener(this);
        tv_count.setOnClickListener(this);
        tv_score.setOnClickListener(this);
        tv_screen.setOnClickListener(this);
        tv_comment.setOnClickListener(this);

        setTextColor(tv_synthesize,line_synthesize);

    }

    @Override
    public void onClick(View v) {
        int index = -1;
        switch (v.getId()){
            case R.id.tv_synthesize:
                setTextColor(tv_synthesize,line_synthesize);
                index = MYGUIDETAB_SYNTHESIZE;
                break;
            case R.id.tv_comment:
                setTextColor(tv_comment,line_comment);
                index = MYGUIDETAB_COMMENT;
                break;
            case R.id.tv_count:
                setTextColor(tv_count,line_count);
                index = MYGUIDETAB_COUNT;
                break;
            case R.id.tv_score:
                setTextColor(tv_score,line_score);
                index = MYGUIDETAB_SCORE;
                break;
            case R.id.tv_screen:
                index = MYGUIDETAB_SCREEN;
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

    private void setTextColor(TextView tv,View line){
        tv_synthesize.setTextColor(ContextCompat.getColor(AppUtils.getContext(),R.color.color_text));
        tv_count.setTextColor(ContextCompat.getColor(AppUtils.getContext(),R.color.color_text));
        tv_score.setTextColor(ContextCompat.getColor(AppUtils.getContext(),R.color.color_text));
        tv_comment.setTextColor(ContextCompat.getColor(AppUtils.getContext(),R.color.color_text));
        tv.setTextColor(ContextCompat.getColor(AppUtils.getContext(),R.color.color_theme));

        line_synthesize.setVisibility(GONE);
        line_count.setVisibility(GONE);
        line_score.setVisibility(GONE);
        line_comment.setVisibility(GONE);
        line.setVisibility(VISIBLE);
    }



    public interface OnItemClickListener {
        void onClick(int position);
    }

    public void setScreen(boolean isSelect){
        tv_screen.setTextColor(isSelect?ContextCompat.getColor(AppUtils.getContext(),R.color.color_theme):ContextCompat.getColor(AppUtils.getContext(),R.color.color_text));
    }
}
