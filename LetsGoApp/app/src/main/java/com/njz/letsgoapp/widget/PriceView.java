package com.njz.letsgoapp.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.njz.letsgoapp.R;

import java.text.DecimalFormat;

/**
 * Created by LGQ
 * Time: 2018/8/6
 * Function:
 */

public class PriceView extends LinearLayout {
    TextView tvPrice;

    public PriceView(Context context) {
        this(context, null);
    }

    public PriceView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }


    public PriceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        View view = LayoutInflater.from(context).inflate(R.layout.view_my_price, this, true);

        tvPrice = view.findViewById(R.id.tv_price);

    }


    public void setPrice(double price) {
        if (price < 0) price = 0;
        String strPrice = String.valueOf(price);
//        if (isDouble(strPrice)) {
//            tvPrice.setText(doubleToString(strPrice));
//        } else {
//            tvPrice.setText(strPrice);
//        }
        tvPrice.setText(strPrice);
    }


    /**
     * 判断字符串是否是浮点数
     */
    private static boolean isDouble(String value) {
        try {
            Double.parseDouble(value);
            if (value.contains("."))
                return true;
            return false;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private static String doubleToString(String value) {
        DecimalFormat df = new DecimalFormat("#.0");
        return df.format(value);
    }

}
