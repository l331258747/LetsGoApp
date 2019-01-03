package com.njz.letsgoapp.widget;

import android.content.Context;
import android.support.annotation.Nullable;
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
 * Time: 2018/8/15
 * Function:
 */

public class NumberEtView extends LinearLayout implements View.OnClickListener {

    EditText et_num;
    ImageView tv_minus,tv_plus;
    int num;
    int minNum = -1;

    public NumberEtView(Context context) {
        this(context, null);
    }

    public NumberEtView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NumberEtView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        View view = LayoutInflater.from(context).inflate(R.layout.view_number_et, this, true);

        tv_minus = findViewById(R.id.tv_minus);
        et_num = findViewById(R.id.et_num);
        tv_plus = findViewById(R.id.tv_plus);

        tv_minus.setOnClickListener(this);
        tv_plus.setOnClickListener(this);

        setNum(0);

    }

    public void setNum(int num) {
        this.num = num;
        et_num.setText(num + "");
        et_num.setSelection(et_num.getText().toString().length());
        if(onItemClickListener == null)
            return;
        onItemClickListener.onClick(num);
    }

    public int getNum(){
        if(TextUtils.isEmpty(et_num.getText().toString())){
            return 0;
        }
        return Integer.valueOf(et_num.getText().toString());
    }

    public void setMinNum(int minNum){
        this.minNum = minNum;
    }


    @Override
    public void onClick(View v) {
        num = getNum();
        switch (v.getId()) {
            case R.id.tv_minus:
                if(minNum > 0){
                    if (num < minNum + 1)
                        return;
                    num--;
                    setNum(num);
                }else {
                    if (num < 1)
                        return;
                    num--;
                    setNum(num);
                }

                break;
            case R.id.tv_plus:
                if (num > 1000)
                    return;
                num++;
                setNum(num);
                break;
        }
    }

    OnItemClickListener onItemClickListener;

    public void setCallback(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onClick(int num);
    }
}
