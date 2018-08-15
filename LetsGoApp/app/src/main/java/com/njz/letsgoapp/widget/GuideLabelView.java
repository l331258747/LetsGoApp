package com.njz.letsgoapp.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.njz.letsgoapp.R;

import java.util.List;

/**
 * Created by LGQ
 * Time: 2018/8/15
 * Function:
 */

public class GuideLabelView extends LinearLayout {

    TextView tv_tabel_1,tv_tabel_2,tv_tabel_3;

    public GuideLabelView(Context context) {
        this(context, null);
    }

    public GuideLabelView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GuideLabelView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        View view = LayoutInflater.from(context).inflate(R.layout.view_guide_label, this, true);

        tv_tabel_1 = findViewById(R.id.tv_tabel_1);
        tv_tabel_2 = findViewById(R.id.tv_tabel_2);
        tv_tabel_3 = findViewById(R.id.tv_tabel_3);

    }

    public void setTabel(List<String> tabels){
        tv_tabel_1.setVisibility(GONE);
        tv_tabel_2.setVisibility(GONE);
        tv_tabel_3.setVisibility(GONE);
        for (int i =0;i<tabels.size();i++){
            if( i == 0){
                tv_tabel_1.setVisibility(VISIBLE);
                tv_tabel_1.setText(tabels.get(i));
            }else if(i == 1){
                tv_tabel_2.setVisibility(VISIBLE);
                tv_tabel_2.setText(tabels.get(i));
            }else if(i == 2){
                tv_tabel_3.setVisibility(VISIBLE);
                tv_tabel_3.setText(tabels.get(i));
            }
        }
    }

}
