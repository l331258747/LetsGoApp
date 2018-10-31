package com.njz.letsgoapp.widget.popupwindow;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.njz.letsgoapp.R;
import com.njz.letsgoapp.bean.MySelfInfo;
import com.njz.letsgoapp.bean.home.ConfigModel;
import com.njz.letsgoapp.bean.mine.LabelItemModel;
import com.njz.letsgoapp.bean.mine.LabelModel;
import com.njz.letsgoapp.constant.Constant;
import com.njz.letsgoapp.util.AppUtils;
import com.njz.letsgoapp.util.log.LogUtil;
import com.njz.letsgoapp.util.rxbus.RxBus2;
import com.njz.letsgoapp.util.rxbus.busEvent.CalendarEvent;
import com.njz.letsgoapp.view.calendar.CalendarActivity;
import com.njz.letsgoapp.widget.flowlayout.FlowLayout;
import com.njz.letsgoapp.widget.flowlayout.TagAdapter;
import com.njz.letsgoapp.widget.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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

        initDataView();

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


        configModels = initFalseData();

        for (int i = 0; i < configModels.size(); i++) {
            TextView tvTitle = new TextView(context);
            tvTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
            tvTitle.setLayoutParams(lp);
            ConfigModel labelModel = configModels.get(i);
            tvTitle.setText(labelModel.getName());
            llParent.addView(tvTitle);

            if (labelModel.getChildModels().size() > 0) {
                TagFlowLayout tagFlowLayout = new TagFlowLayout(context);
                tagFlowLayout.setLayoutParams(lp2);
                tagFlowLayout.setPadding(10, 10, 10, 10);
                if(TextUtils.equals(labelModel.getValue() ,"fwlx") || TextUtils.equals(labelModel.getValue() ,"fwyy")){
                    tagFlowLayout.setMaxSelectCount(-1);
                }else{
                    tagFlowLayout.setMaxSelectCount(-2);
                }
                llParent.addView(tagFlowLayout);

                initFlow(tagFlowLayout,labelModel.getChildModels());
                tagFlowLayout.setTag(labelModel.getValue());
                tagFlowLayouts.add(tagFlowLayout);

            }
        }
    }

    public void initFlow(final TagFlowLayout tagFlowLayout, final List<ConfigModel.ConfigChildModel> mVals) {
        final LayoutInflater mInflater = LayoutInflater.from(context);

        TagAdapter adapter1 = new TagAdapter<ConfigModel.ConfigChildModel>(mVals) {
            @Override
            public View getView(FlowLayout parent, int position, ConfigModel.ConfigChildModel s) {
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
            for (int index : tagFlowLayouts.get(i).getSelectedList()) {
                sb.append(configModels.get(i).getChildModels().get(index).getValue()+",");
            }
            String sbString = sb.toString();
            sbString = sbString.endsWith(",")?sbString.substring(0,sbString.length()-1):sbString;
            if(TextUtils.equals((String)tagFlowLayouts.get(i).getTag(),"dyxb")){
                result.put("gender", sbString);
            }
            if(TextUtils.equals((String)tagFlowLayouts.get(i).getTag(),"dynl")){
                result.put("ages", sbString);
            }
            if(TextUtils.equals((String)tagFlowLayouts.get(i).getTag(),"fwnx")){
                result.put("workYears", sbString);
            }
            if(TextUtils.equals((String)tagFlowLayouts.get(i).getTag(),"fwlx")){
                result.put("serviceTypes", sbString);
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
            tv.setBackgroundResource(R.drawable.btn_99_hollow_r2_p5);
            tv.setTextColor(ContextCompat.getColor(AppUtils.getContext(), R.color.color_text));
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

    private List<ConfigModel> initFalseData(){
        List<ConfigModel> configModels = new ArrayList<>();
        ConfigModel configModel = new ConfigModel();
        configModel.setName("导游性别");
        configModel.setValue("dyxb");
        List<ConfigModel.ConfigChildModel> configChildModels = new ArrayList<>();
        ConfigModel.ConfigChildModel configChildModel = new ConfigModel.ConfigChildModel();
        configChildModel.setName("男");
        configChildModel.setValue("1");
        ConfigModel.ConfigChildModel configChildModel2 = new ConfigModel.ConfigChildModel();
        configChildModel2.setName("女");
        configChildModel2.setValue("2");
        configChildModels.add(configChildModel);
        configChildModels.add(configChildModel2);
        configModel.setChildModels(configChildModels);
        configModels.add(configModel);

        ConfigModel configModel2 = new ConfigModel();
        configModel2.setName("导游年龄");
        configModel2.setValue("dynl");
        List<ConfigModel.ConfigChildModel> configChildModels2 = new ArrayList<>();
        ConfigModel.ConfigChildModel configChildModel21 = new ConfigModel.ConfigChildModel();
        configChildModel21.setName("18-25岁");
        configChildModel21.setValue("18,25");
        ConfigModel.ConfigChildModel configChildModel22 = new ConfigModel.ConfigChildModel();
        configChildModel22.setName("26-30岁");
        configChildModel22.setValue("26,30");
        ConfigModel.ConfigChildModel configChildModel23 = new ConfigModel.ConfigChildModel();
        configChildModel23.setName("31-40岁");
        configChildModel23.setValue("31,40");
        ConfigModel.ConfigChildModel configChildModel24 = new ConfigModel.ConfigChildModel();
        configChildModel24.setName("41-50岁");
        configChildModel24.setValue("41,50");
        ConfigModel.ConfigChildModel configChildModel25 = new ConfigModel.ConfigChildModel();
        configChildModel25.setName("50岁以上");
        configChildModel25.setValue("50,100");
        configChildModels2.add(configChildModel21);
        configChildModels2.add(configChildModel22);
        configChildModels2.add(configChildModel23);
        configChildModels2.add(configChildModel24);
        configChildModels2.add(configChildModel25);
        configModel2.setChildModels(configChildModels2);
        configModels.add(configModel2);


        ConfigModel configModel3 = new ConfigModel();
        configModel3.setName("服务年限");
        configModel3.setValue("fwnx");
        List<ConfigModel.ConfigChildModel> configChildModels3 = new ArrayList<>();
        ConfigModel.ConfigChildModel configChildModel31 = new ConfigModel.ConfigChildModel();
        configChildModel31.setName("1-3年");
        configChildModel31.setValue("1,3");
        ConfigModel.ConfigChildModel configChildModel32 = new ConfigModel.ConfigChildModel();
        configChildModel32.setName("3-5年");
        configChildModel32.setValue("3,5");
        ConfigModel.ConfigChildModel configChildModel33 = new ConfigModel.ConfigChildModel();
        configChildModel33.setName("5年以上");
        configChildModel33.setValue("5,100");
        configChildModels3.add(configChildModel31);
        configChildModels3.add(configChildModel32);
        configChildModels3.add(configChildModel33);
        configModel3.setChildModels(configChildModels3);
        configModels.add(configModel3);

        ConfigModel configModel4 = new ConfigModel();
        configModel4.setName("服务类型");
        configModel4.setValue("fwlx");
        List<ConfigModel.ConfigChildModel> configChildModels4 = new ArrayList<>();
        ConfigModel.ConfigChildModel configChildModel41 = new ConfigModel.ConfigChildModel();
        configChildModel41.setName("向导陪游");
        configChildModel41.setValue(Constant.SERVICE_TYPE_SHORT_GUIDE);
        ConfigModel.ConfigChildModel configChildModel42 = new ConfigModel.ConfigChildModel();
        configChildModel42.setName("车导服务");
        configChildModel42.setValue(Constant.SERVICE_TYPE_SHORT_CAR);
        ConfigModel.ConfigChildModel configChildModel43 = new ConfigModel.ConfigChildModel();
        configChildModel43.setName("代订酒店");
        configChildModel43.setValue(Constant.SERVICE_TYPE_SHORT_HOTEL);
        ConfigModel.ConfigChildModel configChildModel44 = new ConfigModel.ConfigChildModel();
        configChildModel44.setName("代订门票");
        configChildModel44.setValue(Constant.SERVICE_TYPE_SHORT_TICKET);
        ConfigModel.ConfigChildModel configChildModel45 = new ConfigModel.ConfigChildModel();
        configChildModel45.setName("私人定制");
        configChildModel45.setValue(Constant.SERVICE_TYPE_SHORT_CUSTOM);
        configChildModels4.add(configChildModel41);
        configChildModels4.add(configChildModel42);
        configChildModels4.add(configChildModel43);
        configChildModels4.add(configChildModel44);
        configChildModels4.add(configChildModel45);
        configModel4.setChildModels(configChildModels4);
        configModels.add(configModel4);

        ConfigModel configModel5 = new ConfigModel();
        configModel5.setName("服务语言");
        configModel5.setValue("fwyy");
        List<ConfigModel.ConfigChildModel> configChildModels5 = new ArrayList<>();
        ConfigModel.ConfigChildModel configChildModel51 = new ConfigModel.ConfigChildModel();
        configChildModel51.setName("中文");
        configChildModel51.setValue("zw");
        ConfigModel.ConfigChildModel configChildModel52 = new ConfigModel.ConfigChildModel();
        configChildModel52.setName("英语");
        configChildModel52.setValue("yy");
        ConfigModel.ConfigChildModel configChildModel53 = new ConfigModel.ConfigChildModel();
        configChildModel53.setName("法语");
        configChildModel53.setValue("fy");
        configChildModels5.add(configChildModel51);
        configChildModels5.add(configChildModel52);
        configChildModels5.add(configChildModel53);
        configModel5.setChildModels(configChildModels5);
        configModels.add(configModel5);

        return configModels;
    }
}
