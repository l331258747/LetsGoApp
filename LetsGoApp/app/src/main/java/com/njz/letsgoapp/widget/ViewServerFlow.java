package com.njz.letsgoapp.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.njz.letsgoapp.R;
import com.njz.letsgoapp.bean.mine.LabelItemModel;
import com.njz.letsgoapp.bean.server.PlayChileMedel;
import com.njz.letsgoapp.bean.server.PriceCalendarChildModel;
import com.njz.letsgoapp.widget.flowlayout.FlowLayout;
import com.njz.letsgoapp.widget.flowlayout.TagAdapter;
import com.njz.letsgoapp.widget.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by LGQ
 * Time: 2018/12/6
 * Function:
 */

public class ViewServerFlow extends LinearLayout {

    Context context;

    TextView tv_title, tv_title2,tv_date_more;
    TagFlowLayout tagFlowLayout;
    boolean isSelectedOne;
    int maxSelectCount;

    public ViewServerFlow(Context context) {
        this(context, null);
    }

    public ViewServerFlow(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ViewServerFlow(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        this.context = context;

        View view = LayoutInflater.from(context).inflate(R.layout.popup_server_flow, this, true);
        tv_title = findViewById(R.id.tv_title);
        tv_title2 = findViewById(R.id.tv_title2);
        tagFlowLayout = findViewById(R.id.tagFlowLayout);
        tv_date_more = findViewById(R.id.tv_date_more);

    }

    public void setMaxSelect(int size){
        maxSelectCount = size;
        tagFlowLayout.setMaxSelectCount(size);
    }

    public int getMaxSelect(){
        return maxSelectCount;
    }

    public void setSelectedOne(boolean isSelectedOne){
        this.isSelectedOne = isSelectedOne;
    }

    public void setMore(View.OnClickListener click){
        tv_date_more.setVisibility(VISIBLE);
        tv_date_more.setOnClickListener(click);
    }

    public void initFlow(String title, String title2, List<PlayChileMedel> mVals,final OnTagLinsenerClick onTagLinsenerClick) {
        tv_title.setText(title);
        if (TextUtils.isEmpty(title2)) {
            tv_title2.setVisibility(GONE);
        } else {
            tv_title2.setVisibility(VISIBLE);
            tv_title2.setText(title2);
        }
        final LayoutInflater mInflater = LayoutInflater.from(context);
        TagAdapter adapter1 = new TagAdapter<PlayChileMedel>(mVals) {
            @Override
            public View getView(FlowLayout parent, int position, PlayChileMedel s) {
                TextView tv = (TextView) mInflater.inflate(R.layout.item_flow_server, tagFlowLayout, false);
                tv.setText(s.getGuideServeFormatName());

                if(position == 0 && isSelectedOne){
                    onSelected(0,tv);
                }

                return tv;
            }
            @Override
            public void onSelected(int position, View view) {
                super.onSelected(position, view);

                view.setBackground(ContextCompat.getDrawable(context, R.drawable.btn_ffe6d5_solid_r5));
                ((TextView) view).setTextColor(ContextCompat.getColor(context, R.color.color_theme));
                onTagLinsenerClick.onTagLinsenerClick(position);

            }

            @Override
            public void unSelected(int position, View view) {
                super.unSelected(position, view);
                view.setBackground(ContextCompat.getDrawable(context, R.drawable.btn_ee_solid_r5));
                ((TextView) view).setTextColor(ContextCompat.getColor(context, R.color.color_88));
            }
        };
        tagFlowLayout.setAdapter(adapter1);
    }

    public interface OnTagLinsenerClick{
        void onTagLinsenerClick(int position);
    }


    public void initFlow2(String title, String title2, final List<PriceCalendarChildModel> mVals) {
        tv_title.setText(title);
        if (TextUtils.isEmpty(title2)) {
            tv_title2.setVisibility(GONE);
        } else {
            tv_title2.setVisibility(VISIBLE);
            tv_title2.setText(title2);
        }
        final LayoutInflater mInflater = LayoutInflater.from(context);
        TagAdapter adapter1 = new TagAdapter<PriceCalendarChildModel>(mVals) {

            @Override
            public View getView(FlowLayout parent, int position, PriceCalendarChildModel s) {
                LinearLayout ll = (LinearLayout) mInflater.inflate(R.layout.item_flow_server2, tagFlowLayout, false);
                ((TextView) ll.findViewById(R.id.tv_txt1)).setText(s.getTimeMD());
                ((TextView) ll.findViewById(R.id.tv_txt2)).setText("￥" + s.getAddPrice());

                return ll;
            }

            @Override
            public void onSelected(int position, View view) {
                super.onSelected(position, view);

                view.setBackground(ContextCompat.getDrawable(context, R.drawable.btn_ffe6d5_solid_r5));
                ((TextView) view.findViewById(R.id.tv_txt1)).setTextColor(ContextCompat.getColor(context, R.color.color_theme));

                mVals.get(position).setSelect(true);
            }

            @Override
            public void unSelected(int position, View view) {
                super.unSelected(position, view);
                view.setBackground(ContextCompat.getDrawable(context, R.drawable.btn_ee_solid_r5));
                ((TextView) view.findViewById(R.id.tv_txt1)).setTextColor(ContextCompat.getColor(context, R.color.color_88));

                mVals.get(position).setSelect(false);
            }
        };
        tagFlowLayout.setAdapter(adapter1);
    }

    public void setAdapter(final List<PriceCalendarChildModel> mVals){
        final LayoutInflater mInflater = LayoutInflater.from(context);
        final TagAdapter adapter1 = new TagAdapter<PriceCalendarChildModel>(mVals) {

            @Override
            public View getView(FlowLayout parent, int position, PriceCalendarChildModel s) {
                LinearLayout ll = (LinearLayout) mInflater.inflate(R.layout.item_flow_server2, tagFlowLayout, false);
                ((TextView) ll.findViewById(R.id.tv_txt1)).setText(s.getTimeMD());
                ((TextView) ll.findViewById(R.id.tv_txt2)).setText("￥" + s.getAddPrice());
                return ll;
            }

            @Override
            public void onSelected(int position, View view) {
                super.onSelected(position, view);

                view.setBackground(ContextCompat.getDrawable(context, R.drawable.btn_ffe6d5_solid_r5));
                ((TextView) view.findViewById(R.id.tv_txt1)).setTextColor(ContextCompat.getColor(context, R.color.color_theme));

                mVals.get(position).setSelect(true);
            }

            @Override
            public void unSelected(int position, View view) {
                super.unSelected(position, view);
                view.setBackground(ContextCompat.getDrawable(context, R.drawable.btn_ee_solid_r5));
                ((TextView) view.findViewById(R.id.tv_txt1)).setTextColor(ContextCompat.getColor(context, R.color.color_88));

                mVals.get(position).setSelect(false);
            }
        };
        tagFlowLayout.setAdapter(adapter1);
        adapter1.setSelectedList(getSelectIndex(mVals));
    }

    public Set<Integer> getSelectIndex(List<PriceCalendarChildModel> sVals) {
        Set<Integer> ints = new HashSet<>();
        for (int i = 0; i < sVals.size(); i++) {
            if (sVals.get(i).isSelect() == true) {
                ints.add(i);
            }
        }
        return ints;
    }


}
