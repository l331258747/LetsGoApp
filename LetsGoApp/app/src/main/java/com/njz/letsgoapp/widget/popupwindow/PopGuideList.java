package com.njz.letsgoapp.widget.popupwindow;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v4.util.ArrayMap;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.njz.letsgoapp.R;
import com.njz.letsgoapp.constant.Constant;
import com.njz.letsgoapp.util.AppUtils;
import com.njz.letsgoapp.util.rxbus.RxBus2;
import com.njz.letsgoapp.util.rxbus.busEvent.CalendarEvent;
import com.njz.letsgoapp.view.calendar.CalendarActivity;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

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
    TextView tv_time_start, tv_time_end,tv_time_unrestricted;
//    EditText et_price_low, et_price_high;

    private View contentView;
    private Context context;

    private Map<String,String> result = new HashMap<>();
    Disposable calDisposable;

    public PopGuideList(final Context context, View parentView) {
        super(parentView, WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);

        this.context = context;

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

    public void setTime(String startTime,String endTime){
        tv_time_start.setText(startTime);
        tv_time_end.setText(endTime);

        tv_time_start.setBackgroundResource(R.drawable.btn_ffe6d5_solid_r2_p5);
        tv_time_start.setTextColor(ContextCompat.getColor(AppUtils.getContext(), R.color.color_theme));
        tv_time_start.setSelected(true);
        tv_time_end.setBackgroundResource(R.drawable.btn_ffe6d5_solid_r2_p5);
        tv_time_end.setTextColor(ContextCompat.getColor(AppUtils.getContext(), R.color.color_theme));
        tv_time_end.setSelected(true);
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
//        et_price_low = contentView.findViewById(R.id.et_price_low);
//        et_price_high = contentView.findViewById(R.id.et_price_high);
        //tv_time_start, tv_time_end
        tv_time_start = contentView.findViewById(R.id.tv_time_start);
        tv_time_end = contentView.findViewById(R.id.tv_time_end);
        tv_time_unrestricted = contentView.findViewById(R.id.tv_time_unrestricted);

        setClickLisener(btn_submit, btn_reset);
        setClickLisener(tv_type_private, tv_type_scenic, tv_type_hotel, tv_type_car, tv_type_guide);
        setClickLisener(tv_year_5, tv_year_3, tv_year_1);
        setClickLisener(tv_age_50, tv_age_41, tv_age_31, tv_age_26, tv_age_18, tv_age_unrestricted);
        setClickLisener(tv_sex_lady, tv_sex_man, tv_sex_unrestricted);
        setClickLisener(tv_time_start, tv_time_end,tv_time_unrestricted);
    }

    private void setClickLisener(View... views) {
        for (View view : views) {
            view.setOnClickListener(this);
        }
    }

    //取消选中状态
    private void setResetAllView(TextView... tvs) {
        for (TextView tv : tvs) {
            tv.setBackgroundResource(R.drawable.btn_99_hollow_r2_p5);
            tv.setTextColor(ContextCompat.getColor(AppUtils.getContext(), R.color.color_text));
            tv.setSelected(false);
        }
    }

    //被选中
    private void setViewSelect(TextView tv) {
        if (tv.isSelected()) {
            tv.setBackgroundResource(R.drawable.btn_99_hollow_r2_p5);
            tv.setTextColor(ContextCompat.getColor(AppUtils.getContext(), R.color.color_text));
            tv.setSelected(false);
        } else {
            tv.setBackgroundResource(R.drawable.btn_ffe6d5_solid_r2_p5);
            tv.setTextColor(ContextCompat.getColor(AppUtils.getContext(), R.color.color_theme));
            tv.setSelected(true);
        }
    }

    //确定
    public void submit() {
        result.put("gender", tv_sex_lady.isSelected() ? "0"
                : tv_sex_man.isSelected() ? "1"
                : "");
        result.put("ages", tv_age_18.isSelected() ? "18,25"
                : tv_age_26.isSelected() ? "26,30"
                : tv_age_31.isSelected() ? "31,40"
                : tv_age_41.isSelected() ? "41,50"
                : tv_age_50.isSelected() ? "51,100"
                : "");
        result.put("workYears", tv_year_1.isSelected() ? "1,3"
                : tv_year_3.isSelected() ? "3,5"
                : tv_year_5.isSelected() ? "6,100"
                : "");
        result.put("startTime", tv_time_start.isSelected() ? tv_time_start.getText().toString()
                : "");
        result.put("startTime", tv_time_end.isSelected() ? tv_time_end.getText().toString()
                : "");
        result.put("serviceTypes",getServices());

        submitLisener.onSubmit(result);
        dismissPopupWindow();
    }

    private String getServices(){
        StringBuffer services = new StringBuffer("");
        //tv_type_private,tv_type_scenic,tv_type_hotel,tv_type_car,tv_type_guide
        if(tv_type_private.isSelected())
            services.append(Constant.SERVICE_TYPE_SHORT_CUSTOM + ",");
        if(tv_type_scenic.isSelected())
            services.append(Constant.SERVICE_TYPE_SHORT_TICKET + ",");
        if(tv_type_hotel.isSelected())
            services.append(Constant.SERVICE_TYPE_SHORT_HOTEL + ",");
        if(tv_type_car.isSelected())
            services.append(Constant.SERVICE_TYPE_SHORT_CAR + ",");
        if(tv_type_guide.isSelected())
            services.append(Constant.SERVICE_TYPE_SHORT_GUIDE + ",");
        if(services.toString().length() > 0)
            return services.toString().substring(0,services.toString().length() - 1);
        return services.toString();
    }

    //重置
    private void reset() {
//        et_price_low.setText("");
//        et_price_high.setText("");
        setResetAllView(tv_type_private, tv_type_scenic, tv_type_hotel, tv_type_car, tv_type_guide, tv_year_5, tv_year_3, tv_year_1
                , tv_age_50, tv_age_41, tv_age_31, tv_age_26, tv_age_18, tv_age_unrestricted, tv_sex_lady, tv_sex_man,
                tv_sex_unrestricted,tv_time_start,tv_time_end,tv_time_unrestricted);
        tv_time_start.setText("开始时间");
        tv_time_end.setText("结束时间");
        submitLisener.onReset();
        dismissPopupWindow();
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
            case R.id.tv_time_start:
                setTime();
                break;
            case R.id.tv_time_end:
                setTime();
                break;
            case R.id.tv_time_unrestricted:
                tv_time_start.setBackgroundResource(R.drawable.btn_99_hollow_r2_p5);
                tv_time_start.setTextColor(ContextCompat.getColor(AppUtils.getContext(), R.color.color_text));
                tv_time_start.setSelected(false);
                tv_time_end.setBackgroundResource(R.drawable.btn_99_hollow_r2_p5);
                tv_time_end.setTextColor(ContextCompat.getColor(AppUtils.getContext(), R.color.color_text));
                tv_time_end.setSelected(false);
                tv_time_start.setText("开始时间");
                tv_time_end.setText("结束时间");

                tv_time_unrestricted.setBackgroundResource(R.drawable.btn_ffe6d5_solid_r2_p5);
                tv_time_unrestricted.setTextColor(ContextCompat.getColor(AppUtils.getContext(), R.color.color_theme));
                tv_time_unrestricted.setSelected(true);
                break;
        }
    }

    private void setTime() {

        context.startActivity(new Intent(context, CalendarActivity.class));
        calDisposable = RxBus2.getInstance().toObservable(CalendarEvent.class, new Consumer<CalendarEvent>() {
            @Override
            public void accept(CalendarEvent calendarEvent) throws Exception {
                calDisposable.dispose();
                if(TextUtils.isEmpty(calendarEvent.getStartTime())){
                    return;
                }

                tv_time_start.setText(calendarEvent.getStartTime());
                tv_time_end.setText(calendarEvent.getEndTime());

                tv_time_start.setBackgroundResource(R.drawable.btn_ffe6d5_solid_r2_p5);
                tv_time_start.setTextColor(ContextCompat.getColor(AppUtils.getContext(), R.color.color_theme));
                tv_time_start.setSelected(true);
                tv_time_end.setBackgroundResource(R.drawable.btn_ffe6d5_solid_r2_p5);
                tv_time_end.setTextColor(ContextCompat.getColor(AppUtils.getContext(), R.color.color_theme));
                tv_time_end.setSelected(true);

                tv_time_unrestricted.setBackgroundResource(R.drawable.btn_99_hollow_r2_p5);
                tv_time_unrestricted.setTextColor(ContextCompat.getColor(AppUtils.getContext(), R.color.color_text));
                tv_time_unrestricted.setSelected(false);
            }
        });
    }

    public interface SubmitLisener {
        void onSubmit(Map<String,String> result);

        void onReset();
    }


}
