package com.njz.letsgoapp.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.njz.letsgoapp.R;

import java.util.List;

/**
 * Created by LGQ
 * Time: 2018/8/15
 * Function:
 */

public class GuideAuthenticationView extends LinearLayout {

    public static final int AUTHENT_IDENTITY = 0;
    public static final int AUTHENT_GUIDE = 1;
    public static final int AUTHENT_CAR = 2;


    ImageView ivIdentity, ivGuide, ivCar;

    public GuideAuthenticationView(Context context) {
        this(context, null);
    }

    public GuideAuthenticationView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GuideAuthenticationView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        View view = LayoutInflater.from(context).inflate(R.layout.view_guide_authentication, this, true);

        ivIdentity = findViewById(R.id.iv_identity);
        ivGuide = findViewById(R.id.iv_guide);
        ivCar = findViewById(R.id.iv_car);

    }

    public void setAuthentication(List<Integer> authentications) {
        ivIdentity.setVisibility(GONE);
        ivGuide.setVisibility(GONE);
        ivCar.setVisibility(GONE);
        for (Integer authentication : authentications) {
            if (authentication == AUTHENT_IDENTITY) {
                ivIdentity.setVisibility(VISIBLE);
            }else if (authentication == AUTHENT_GUIDE) {
                ivGuide.setVisibility(VISIBLE);
            }else if (authentication == AUTHENT_CAR) {
                ivCar.setVisibility(VISIBLE);
            }
        }
    }

}
