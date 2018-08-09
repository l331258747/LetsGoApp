package com.njz.letsgoapp.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.widget.TextView;

import com.njz.letsgoapp.R;
import com.njz.letsgoapp.util.AppUtils;

import java.util.List;

/**
 * Created by LGQ
 * Time: 2018/8/9
 * Function:
 */

public class ServiceTagView extends TextView {

    public ServiceTagView(Context context) {
        super(context);
    }

    public ServiceTagView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ServiceTagView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        setTextSize(AppUtils.px2sp(11));
//        setTextColor(ContextCompat.getColor(AppUtils.getContext(), R.color.white));
//        setCompoundDrawables();


    }

    public void setServiceTag(List<String> serviceTags){
        StringBuffer serviceTagStr= new StringBuffer();
        for (int i = 0;i<serviceTags.size();i++){
            if(i == 0){
                serviceTagStr.append(serviceTags.get(i));
                continue;
            }
            serviceTagStr.append(" | ");
            serviceTagStr.append(serviceTags.get(i));
        }
        setText(serviceTagStr.toString());

    }



}
