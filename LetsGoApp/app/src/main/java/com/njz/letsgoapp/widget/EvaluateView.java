package com.njz.letsgoapp.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.njz.letsgoapp.R;
import com.njz.letsgoapp.util.AppUtils;

/**
 * Created by LGQ
 * Time: 2018/9/11
 * Function:
 */

public class EvaluateView extends LinearLayout {

    TextView tv_title, tv_content;
    MyRatingBar my_rating_bar;

    public EvaluateView(Context context) {
        this(context, null);
    }

    public EvaluateView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public EvaluateView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        View view = LayoutInflater.from(context).inflate(R.layout.view_evaluate, this, true);

        my_rating_bar = findViewById(R.id.my_rating_bar);
        tv_title = findViewById(R.id.tv_title);
        tv_content = findViewById(R.id.tv_content);


        TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.order_evaluate);
        if (attributes != null) {
            String evaluateTitle = attributes.getString(R.styleable.order_evaluate_order_evaluate_title);
            if(!TextUtils.isEmpty(evaluateTitle)){
                tv_title.setText(evaluateTitle);
            }
        }
        attributes.recycle();


        my_rating_bar.setRating(5);
        setContent(5);

        my_rating_bar.setOnRatingLisenterClick(new MyRatingBar.OnRatingLisenterClick() {
            @Override
            public void onRating(int rating) {
                setContent(rating);
            }
        });
    }

    private void setContent(int rating) {
        switch (rating){
            case 1:
                tv_content.setText("非常差");
                break;
            case 2:
                tv_content.setText("差");
                break;
            case 3:
                tv_content.setText("一般");
                break;
            case 4:
                tv_content.setText("好");
                break;
            case 5:
                tv_content.setText("非常好");
                break;
        }
    }

    public int getRating(){
        return my_rating_bar.getRating();
    }

}
