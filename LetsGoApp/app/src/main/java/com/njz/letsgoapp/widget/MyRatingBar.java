package com.njz.letsgoapp.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.njz.letsgoapp.R;
import com.njz.letsgoapp.util.AppUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/12/17.
 */

public class MyRatingBar extends LinearLayout implements View.OnClickListener {

    ImageView ivStar1, ivStar2, ivStar3, ivStar4, ivStar5;
    List<ImageView> ivStars;

    public MyRatingBar(Context context) {
        this(context, null);
    }

    public MyRatingBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyRatingBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        View view = LayoutInflater.from(context).inflate(R.layout.view_my_ratingbar, this, true);

        ivStar1 = findViewById(R.id.iv_star1);
        ivStar2 = findViewById(R.id.iv_star2);
        ivStar3 = findViewById(R.id.iv_star3);
        ivStar4 = findViewById(R.id.iv_star4);
        ivStar5 = findViewById(R.id.iv_star5);
        ivStars = new ArrayList<>();
        ivStars.add(ivStar1);
        ivStars.add(ivStar2);
        ivStars.add(ivStar3);
        ivStars.add(ivStar4);
        ivStars.add(ivStar5);

        //TODO 设置大小，和间隔
        TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.my_ratingbar);
        if (attributes != null) {
            int margin = (int) attributes.getDimension(R.styleable.my_ratingbar_my_ratingbar_margin, AppUtils.dip2px(context, 3f));

            int width = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
            int height = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
            ivStar1.measure(width, height);

            int size = (int) attributes.getDimension(R.styleable.my_ratingbar_my_ratingbar_size, ivStar1.getMeasuredHeight());

            LayoutParams lp = (LayoutParams) ivStar1.getLayoutParams();
            lp.height = size;
            lp.width = size;
            ivStar1.setLayoutParams(lp);

            LayoutParams lp2 = (LayoutParams) ivStar1.getLayoutParams();
            lp2.leftMargin = margin;
            lp2.height = size;
            lp2.width = size;

            ivStar2.setLayoutParams(lp2);
            ivStar3.setLayoutParams(lp2);
            ivStar4.setLayoutParams(lp2);
            ivStar5.setLayoutParams(lp2);

            boolean isCLick = attributes.getBoolean(R.styleable.my_ratingbar_my_ratingbar_click, false);
            if (isCLick) {
                ivStar1.setOnClickListener(this);
                ivStar2.setOnClickListener(this);
                ivStar3.setOnClickListener(this);
                ivStar4.setOnClickListener(this);
                ivStar5.setOnClickListener(this);
            }

            attributes.recycle();
        }
    }

    public void setRating(int rating) {
        if(rating < 0) rating = 0;
        if (rating > 5) rating = 5;
        starNum = rating;
        for (int i = 0; i < rating; i++) {
            ivStars.get(i).setSelected(true);
        }
        for (int i = rating; i < ivStars.size(); i++) {
            ivStars.get(i).setSelected(false);
        }

    }

    private int starNum = 0;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_star1:
                starNum = 1;
                setRating(1);
                break;
            case R.id.iv_star2:
                starNum = 2;
                setRating(2);
                break;
            case R.id.iv_star3:
                starNum = 3;
                setRating(3);
                break;
            case R.id.iv_star4:
                starNum = 4;
                setRating(4);
                break;
            case R.id.iv_star5:
                starNum = 5;
                setRating(5);
                break;
        }
    }

    public int getRating() {
        return starNum;
    }
}
