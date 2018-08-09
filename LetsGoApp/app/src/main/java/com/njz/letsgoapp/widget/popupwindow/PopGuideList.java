package com.njz.letsgoapp.widget.popupwindow;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.njz.letsgoapp.R;
import com.njz.letsgoapp.util.AppUtils;

/**
 * Created by LGQ
 * Time: 2018/8/9
 * Function:
 */

public class PopGuideList extends BackgroundDarkPopupWindow implements View.OnClickListener {
    TextView btn_submit, btn_reset;
    TextView tv_type_private, tv_type_scenic, tv_type_hotel, tv_type_car, tv_type_guide;
    TextView tv_year_5, tv_year_3, tv_year_1;
    TextView tv_age_50, tv_age_41, tv_age_31, tv_age_26, tv_age_18, tv_age_unrestricted;
    TextView tv_sex_lady, tv_sex_man, tv_sex_unrestricted;
    EditText et_price_low, et_price_high;

    private View contentView;

    public PopGuideList(final Context context, View parentView) {
        super(parentView, WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        contentView = inflater.inflate(R.layout.popup_guide_list, null);

        this.setContentView(contentView);
        setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
        setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);

        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0000000000);
        this.setBackgroundDrawable(dw);

        contentView.setFocusable(true);
        contentView.setFocusableInTouchMode(true);
        this.setOutsideTouchable(true);

        this.setFocusable(true);

        initView();

    }

    private void initView() {
        btn_submit = contentView.findViewById(R.id.btn_submit);
        btn_reset = contentView.findViewById(R.id.btn_reset);
        //tv_type_private,tv_type_scenic,tv_type_hotel,tv_type_car,tv_type_guide
        tv_type_private = contentView.findViewById(R.id.tv_type_private);
        tv_type_scenic = contentView.findViewById(R.id.tv_type_scenic);
        tv_type_hotel = contentView.findViewById(R.id.tv_type_hotel);
        tv_type_car = contentView.findViewById(R.id.tv_type_car);
        tv_type_guide = contentView.findViewById(R.id.tv_type_guide);
        //tv_year_5,tv_year_3,tv_year_1;
        tv_year_5 = contentView.findViewById(R.id.tv_year_5);
        tv_year_3 = contentView.findViewById(R.id.tv_year_3);
        tv_year_1 = contentView.findViewById(R.id.tv_year_1);
        //tv_age_50,tv_age_41,tv_age_31,tv_age_26,tv_age_18,tv_age_unrestricted;
        tv_age_50 = contentView.findViewById(R.id.tv_age_50);
        tv_age_41 = contentView.findViewById(R.id.tv_age_41);
        tv_age_31 = contentView.findViewById(R.id.tv_age_31);
        tv_age_26 = contentView.findViewById(R.id.tv_age_26);
        tv_age_18 = contentView.findViewById(R.id.tv_age_18);
        tv_age_unrestricted = contentView.findViewById(R.id.tv_age_unrestricted);
        //tv_sex_lady,tv_sex_man,tv_sex_unrestricted;
        tv_sex_lady = contentView.findViewById(R.id.tv_sex_lady);
        tv_sex_man = contentView.findViewById(R.id.tv_sex_man);
        tv_sex_unrestricted = contentView.findViewById(R.id.tv_sex_unrestricted);
        //et_price_low,et_price_hight;
        et_price_low = contentView.findViewById(R.id.et_price_low);
        et_price_high = contentView.findViewById(R.id.et_price_high);

        setClickLisener(btn_submit, btn_reset);
        setClickLisener(tv_type_private, tv_type_scenic, tv_type_hotel, tv_type_car, tv_type_guide);
        setClickLisener(tv_year_5, tv_year_3, tv_year_1);
        setClickLisener(tv_age_50, tv_age_41, tv_age_31, tv_age_26, tv_age_18, tv_age_unrestricted);
        setClickLisener(tv_sex_lady, tv_sex_man, tv_sex_unrestricted);
    }

    private void setClickLisener(View... views) {
        for (View view : views) {
            view.setOnClickListener(this);
        }
    }

    //取消选中状态
    private void setResetAllView(TextView... tvs) {
        for (TextView tv : tvs) {
            tv.setBackgroundResource(R.drawable.btn_black_hollow);
            tv.setTextColor(ContextCompat.getColor(AppUtils.getContext(), R.color.black));
            tv.setSelected(false);
        }
    }

    //被选中
    private void setViewSelect(TextView tv) {
        if (tv.isSelected()) {
            tv.setBackgroundResource(R.drawable.btn_black_hollow);
            tv.setTextColor(ContextCompat.getColor(AppUtils.getContext(), R.color.black));
            tv.setSelected(false);
        } else {
            tv.setBackgroundResource(R.drawable.btn_green_solid);
            tv.setTextColor(ContextCompat.getColor(AppUtils.getContext(), R.color.white));
            tv.setSelected(true);
        }
    }

    //确定
    public void submit() {
        submitLisener.onSubmit();
        dismissPopupWindow();
    }

    //重置
    private void reset() {
        et_price_low.setText("");
        et_price_high.setText("");
        setResetAllView(tv_type_private, tv_type_scenic, tv_type_hotel, tv_type_car, tv_type_guide, tv_year_5, tv_year_3, tv_year_1
                , tv_age_50, tv_age_41, tv_age_31, tv_age_26, tv_age_18, tv_age_unrestricted, tv_sex_lady, tv_sex_man, tv_sex_unrestricted);
    }


    public void showPopupWindow(View parent) {
        if (!this.isShowing()) {
            setDarkStyle(-1);
            setDarkColor(Color.parseColor("#a0000000"));
            resetDarkPosition();
            darkAbove(parent);
            showAsDropDown(parent, 0, 0);
        }
    }

    public void dismissPopupWindow() {
        if (this.isShowing())
            this.dismiss();
    }

    SubmitLisener submitLisener;

    public void setSubmitLisener(SubmitLisener submitLisener) {
        this.submitLisener = submitLisener;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_reset:
                reset();
                break;
            case R.id.btn_submit:
                submit();
                break;
            //tv_sex_lady,tv_sex_man,tv_sex_unrestricted;
            case R.id.tv_sex_lady:
                setViewSelect(tv_sex_lady);
                setResetAllView(tv_sex_man, tv_sex_unrestricted);
                break;
            case R.id.tv_sex_man:
                setViewSelect(tv_sex_man);
                setResetAllView(tv_sex_lady, tv_sex_unrestricted);
                break;
            case R.id.tv_sex_unrestricted:
                setViewSelect(tv_sex_unrestricted);
                setResetAllView(tv_sex_lady, tv_sex_man);
                break;
            //tv_year_5,tv_year_3,tv_year_1;
            case R.id.tv_year_1:
                setViewSelect(tv_year_1);
                setResetAllView(tv_year_3, tv_year_5);
                break;
            case R.id.tv_year_3:
                setViewSelect(tv_year_3);
                setResetAllView(tv_year_1, tv_year_5);
                break;
            case R.id.tv_year_5:
                setViewSelect(tv_year_5);
                setResetAllView(tv_year_1, tv_year_3);
                break;
            //tv_type_private,tv_type_scenic,tv_type_hotel,tv_type_car,tv_type_guide
            case R.id.tv_type_private:
                setViewSelect(tv_type_private);
                break;
            case R.id.tv_type_scenic:
                setViewSelect(tv_type_scenic);
                break;
            case R.id.tv_type_hotel:
                setViewSelect(tv_type_hotel);
                break;
            case R.id.tv_type_car:
                setViewSelect(tv_type_car);
                break;
            case R.id.tv_type_guide:
                setViewSelect(tv_type_guide);
                break;
            //tv_age_50,tv_age_41,tv_age_31,tv_age_26,tv_age_18,tv_age_unrestricted;
            case R.id.tv_age_50:
                setViewSelect(tv_age_50);
                setResetAllView(tv_age_41, tv_age_31, tv_age_26, tv_age_18, tv_age_unrestricted);
                break;
            case R.id.tv_age_41:
                setViewSelect(tv_age_41);
                setResetAllView(tv_age_50, tv_age_31, tv_age_26, tv_age_18, tv_age_unrestricted);
                break;
            case R.id.tv_age_31:
                setViewSelect(tv_age_31);
                setResetAllView(tv_age_41, tv_age_50, tv_age_26, tv_age_18, tv_age_unrestricted);
                break;
            case R.id.tv_age_26:
                setViewSelect(tv_age_26);
                setResetAllView(tv_age_41, tv_age_31, tv_age_50, tv_age_18, tv_age_unrestricted);
                break;
            case R.id.tv_age_18:
                setViewSelect(tv_age_18);
                setResetAllView(tv_age_41, tv_age_31, tv_age_26, tv_age_50, tv_age_unrestricted);
                break;
            case R.id.tv_age_unrestricted:
                setViewSelect(tv_age_unrestricted);
                setResetAllView(tv_age_41, tv_age_31, tv_age_26, tv_age_18, tv_age_50);
                break;
        }
    }

    public interface SubmitLisener {
        void onSubmit();
    }

}
