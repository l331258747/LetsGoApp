package com.njz.letsgoapp.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.njz.letsgoapp.R;
import com.njz.letsgoapp.util.AppUtils;

/**
 * Created by LGQ
 * Time: 2018/8/16
 * Function:
 */

public class ReportSelectView extends LinearLayout implements View.OnClickListener {

    LinearLayout ll_sexy,ll_rumor,ll_sensitive,ll_copy,ll_advertisement,ll_harass;
    ImageView iv_sexy,iv_rumor,iv_sensitive,iv_copy,iv_advertisement,iv_harass;

    public ReportSelectView(Context context) {
        this(context, null);
    }

    public ReportSelectView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ReportSelectView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        View view = LayoutInflater.from(context).inflate(R.layout.view_report_select, this, true);

        ll_sexy = findViewById(R.id.ll_sexy);
        ll_rumor = findViewById(R.id.ll_rumor);
        ll_sensitive = findViewById(R.id.ll_sensitive);
        ll_copy = findViewById(R.id.ll_copy);
        ll_advertisement = findViewById(R.id.ll_advertisement);
        ll_harass = findViewById(R.id.ll_harass);

        iv_sexy = findViewById(R.id.iv_sexy);
        iv_rumor = findViewById(R.id.iv_rumor);
        iv_sensitive = findViewById(R.id.iv_sensitive);
        iv_copy = findViewById(R.id.iv_copy);
        iv_advertisement = findViewById(R.id.iv_advertisement);
        iv_harass = findViewById(R.id.iv_harass);

        ll_sexy.setOnClickListener(this);
        ll_rumor.setOnClickListener(this);
        ll_sensitive.setOnClickListener(this);
        ll_copy.setOnClickListener(this);
        ll_advertisement.setOnClickListener(this);
        ll_harass.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        cleanAll();
        switch (v.getId()){
            case R.id.ll_sexy:
                iv_sexy.setImageDrawable(ContextCompat.getDrawable(AppUtils.getContext(),R.mipmap.ic_select));
                break;
            case R.id.ll_rumor:
                iv_rumor.setImageDrawable(ContextCompat.getDrawable(AppUtils.getContext(),R.mipmap.ic_select));
                break;
            case R.id.ll_sensitive:
                iv_sensitive.setImageDrawable(ContextCompat.getDrawable(AppUtils.getContext(),R.mipmap.ic_select));
                break;
            case R.id.ll_copy:
                iv_copy.setImageDrawable(ContextCompat.getDrawable(AppUtils.getContext(),R.mipmap.ic_select));
                break;
            case R.id.ll_advertisement:
                iv_advertisement.setImageDrawable(ContextCompat.getDrawable(AppUtils.getContext(),R.mipmap.ic_select));
                break;
            case R.id.ll_harass:
                iv_harass.setImageDrawable(ContextCompat.getDrawable(AppUtils.getContext(),R.mipmap.ic_select));
                break;

        }
    }

    private void cleanAll(){
        iv_sexy.setImageDrawable(ContextCompat.getDrawable(AppUtils.getContext(),R.mipmap.ic_unselect));
        iv_rumor.setImageDrawable(ContextCompat.getDrawable(AppUtils.getContext(),R.mipmap.ic_unselect));
        iv_sensitive.setImageDrawable(ContextCompat.getDrawable(AppUtils.getContext(),R.mipmap.ic_unselect));
        iv_copy.setImageDrawable(ContextCompat.getDrawable(AppUtils.getContext(),R.mipmap.ic_unselect));
        iv_advertisement.setImageDrawable(ContextCompat.getDrawable(AppUtils.getContext(),R.mipmap.ic_unselect));
        iv_harass.setImageDrawable(ContextCompat.getDrawable(AppUtils.getContext(),R.mipmap.ic_unselect));
    }
}
