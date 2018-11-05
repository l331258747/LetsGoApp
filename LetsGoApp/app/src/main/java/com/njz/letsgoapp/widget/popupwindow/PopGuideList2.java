package com.njz.letsgoapp.widget.popupwindow;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.njz.letsgoapp.R;
import com.njz.letsgoapp.bean.other.ConfigChildModel;
import com.njz.letsgoapp.bean.other.ConfigModel;
import com.njz.letsgoapp.constant.Constant;
import com.njz.letsgoapp.util.AppUtils;
import com.njz.letsgoapp.util.rxbus.RxBus2;
import com.njz.letsgoapp.util.rxbus.busEvent.CalendarEvent;
import com.njz.letsgoapp.view.calendar.CalendarActivity;
import com.njz.letsgoapp.widget.flowlayout.FlowLayout;
import com.njz.letsgoapp.widget.flowlayout.TagAdapter;
import com.njz.letsgoapp.widget.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by LGQ
 * Time: 2018/8/9
 * Function:
 */

public class PopGuideList2 extends BackgroundDarkPopupWindow implements View.OnClickListener {
    TextView btn_submit, btn_reset;
    LinearLayout llParent;


    private View contentView;
    private Context context;

    Disposable calDisposable;

    TextView tv_time_start, tv_time_end,tv_time_unrestricted;

    List<ConfigModel> configModels;
    List<TagFlowLayout> tagFlowLayouts = new ArrayList<>();

    public PopGuideList2(final Context context, View parentView) {
        super(parentView, WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);

        this.context = context;

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        contentView = inflater.inflate(R.layout.popup_guide_list2, null);

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
        tv_time_start = contentView.findViewById(R.id.tv_time_start);
        tv_time_end = contentView.findViewById(R.id.tv_time_end);
        tv_time_unrestricted = contentView.findViewById(R.id.tv_time_unrestricted);

        btn_submit = contentView.findViewById(R.id.btn_submit);
        btn_reset = contentView.findViewById(R.id.btn_reset);
        llParent = contentView.findViewById(R.id.ll_parent);

        tv_time_start.setOnClickListener(this);
        tv_time_end.setOnClickListener(this);
        tv_time_unrestricted.setOnClickListener(this);
        btn_submit.setOnClickListener(this);
        btn_reset.setOnClickListener(this);
    }


    private void initDataView() {
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.setMargins(0, 10, 0, 0);

        LinearLayout.LayoutParams lp2 = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        for (int i = 0; i < configModels.size(); i++) {
            TextView tvTitle = new TextView(context);
            tvTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
            tvTitle.setLayoutParams(lp);
            ConfigModel labelModel = configModels.get(i);
            tvTitle.setText(labelModel.getName());
            llParent.addView(tvTitle);

            if (labelModel.getList().size() > 0) {
                TagFlowLayout tagFlowLayout = new TagFlowLayout(context);
                tagFlowLayout.setLayoutParams(lp2);
                tagFlowLayout.setPadding(10, 10, 10, 10);
                if(TextUtils.equals(labelModel.getValue() ,Constant.CONFIG_FWLX) || TextUtils.equals(labelModel.getValue() ,Constant.CONFIG_YYLX)){
                    tagFlowLayout.setMaxSelectCount(-1);
                }else{
                    tagFlowLayout.setMaxSelectCount(-2);
                }
                llParent.addView(tagFlowLayout);

                initFlow(tagFlowLayout,labelModel.getList());
                tagFlowLayout.setTag(labelModel.getValue());
                tagFlowLayouts.add(tagFlowLayout);

            }
        }
    }

    public void initFlow(final TagFlowLayout tagFlowLayout, final List<ConfigChildModel> mVals) {
        final LayoutInflater mInflater = LayoutInflater.from(context);

        TagAdapter adapter1 = new TagAdapter<ConfigChildModel>(mVals) {
            @Override
            public View getView(FlowLayout parent, int position, ConfigChildModel s) {
                TextView tv = (TextView) mInflater.inflate(R.layout.item_flow_guide, tagFlowLayout, false);
                tv.setText(s.getName());
                return tv;
            }
        };
        tagFlowLayout.setAdapter(adapter1);
    }

    private Map<String,String> result = new HashMap<>();
    //确定
    public void submit() {
        for (int i =0;i<tagFlowLayouts.size();i++){
            StringBuffer sb = new StringBuffer("");
            if(TextUtils.equals((String)tagFlowLayouts.get(i).getTag(),Constant.CONFIG_YYLX)){
                for (int index : tagFlowLayouts.get(i).getSelectedList()) {
                    sb.append(configModels.get(i).getList().get(index).getId()+",");
                }
            }else{
                for (int index : tagFlowLayouts.get(i).getSelectedList()) {
                    sb.append(configModels.get(i).getList().get(index).getValue()+",");
                }
            }
            String sbString = sb.toString();
            sbString = sbString.endsWith(",")?sbString.substring(0,sbString.length()-1):sbString;
            if(TextUtils.equals((String)tagFlowLayouts.get(i).getTag(),Constant.CONFIG_XB)){
                result.put("gender", sbString);
            }else if(TextUtils.equals((String)tagFlowLayouts.get(i).getTag(),Constant.CONFIG_DYNL)){
                result.put("ages", sbString);
            }else  if(TextUtils.equals((String)tagFlowLayouts.get(i).getTag(),Constant.CONFIG_CYNX)){
                result.put("workYears", sbString);
            }else if(TextUtils.equals((String)tagFlowLayouts.get(i).getTag(),Constant.CONFIG_FWLX)){
                result.put("serviceTypes", sbString);
            }else if(TextUtils.equals((String)tagFlowLayouts.get(i).getTag(),Constant.CONFIG_YYLX)){
                result.put("language", sbString);
            }
        }
        result.put("startTime", tv_time_start.isSelected() ? tv_time_start.getText().toString() : "");
        result.put("startTime", tv_time_end.isSelected() ? tv_time_end.getText().toString() : "");
        submitLisener.onSubmit(result);
        dismissPopupWindow();
    }

    //重置
    private void reset() {
        for (int i=0;i<tagFlowLayouts.size();i++){
            tagFlowLayouts.get(i).onChanged();
        }
        setResetAllView(tv_time_start,tv_time_end,tv_time_unrestricted);
        tv_time_start.setText("开始时间");
        tv_time_end.setText("结束时间");
        submitLisener.onReset();
        dismissPopupWindow();
    }

    //取消选中状态
    private void setResetAllView(TextView... tvs) {
        for (TextView tv : tvs) {
            tv.setBackgroundResource(R.drawable.btn_gray_hollow_r3);
            tv.setTextColor(ContextCompat.getColor(AppUtils.getContext(), R.color.color_99));
            tv.setSelected(false);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_time_start:
                setTime();
                break;
            case R.id.tv_time_end:
                setTime();
                break;
            case R.id.tv_time_unrestricted:
                tv_time_start.setBackgroundResource(R.drawable.btn_gray_hollow_r3);
                tv_time_start.setTextColor(ContextCompat.getColor(AppUtils.getContext(), R.color.color_99));
                tv_time_start.setSelected(false);
                tv_time_end.setBackgroundResource(R.drawable.btn_gray_hollow_r3);
                tv_time_end.setTextColor(ContextCompat.getColor(AppUtils.getContext(), R.color.color_99));
                tv_time_end.setSelected(false);
                tv_time_start.setText("开始时间");
                tv_time_end.setText("结束时间");

                tv_time_unrestricted.setBackgroundResource(R.drawable.btn_theme_hollow_r3);
                tv_time_unrestricted.setTextColor(ContextCompat.getColor(AppUtils.getContext(), R.color.color_theme));
                tv_time_unrestricted.setSelected(true);
                break;
            case R.id.btn_submit:
                submit();
                break;
            case R.id.btn_reset:
                reset();
                break;
        }
    }

    public void showPopupWindow(View parent) {
        if(configModels ==null || configModels.size()==0) return;
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

    public void setTime(String startTime,String endTime){
        tv_time_start.setText(startTime);
        tv_time_end.setText(endTime);

        tv_time_start.setBackgroundResource(R.drawable.btn_theme_hollow_r3);
        tv_time_start.setTextColor(ContextCompat.getColor(AppUtils.getContext(), R.color.color_theme));
        tv_time_start.setSelected(true);
        tv_time_end.setBackgroundResource(R.drawable.btn_theme_hollow_r3);
        tv_time_end.setTextColor(ContextCompat.getColor(AppUtils.getContext(), R.color.color_theme));
        tv_time_end.setSelected(true);
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

                tv_time_start.setBackgroundResource(R.drawable.btn_theme_hollow_r3);
                tv_time_start.setTextColor(ContextCompat.getColor(AppUtils.getContext(), R.color.color_theme));
                tv_time_start.setSelected(true);
                tv_time_end.setBackgroundResource(R.drawable.btn_theme_hollow_r3);
                tv_time_end.setTextColor(ContextCompat.getColor(AppUtils.getContext(), R.color.color_theme));
                tv_time_end.setSelected(true);

                tv_time_unrestricted.setBackgroundResource(R.drawable.btn_gray_hollow_r3);
                tv_time_unrestricted.setTextColor(ContextCompat.getColor(AppUtils.getContext(), R.color.color_99));
                tv_time_unrestricted.setSelected(false);
            }
        });
    }

    SubmitLisener submitLisener;
    public interface SubmitLisener {
        void onSubmit(Map<String,String> result);

        void onReset();
    }
    public void setSubmitLisener(SubmitLisener submitLisener) {
        this.submitLisener = submitLisener;
    }

    public void setConfigData(List<ConfigModel> configModels){
        this.configModels = configModels;
        initDataView();
    }

}
