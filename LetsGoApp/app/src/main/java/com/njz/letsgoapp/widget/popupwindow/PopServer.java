package com.njz.letsgoapp.widget.popupwindow;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.njz.letsgoapp.R;
import com.njz.letsgoapp.bean.server.PlayChileMedel;
import com.njz.letsgoapp.bean.server.PriceCalendarChildModel;
import com.njz.letsgoapp.bean.server.PriceCalendarModel;
import com.njz.letsgoapp.bean.server.ServerDetailModel;
import com.njz.letsgoapp.bean.server.ServerItem;
import com.njz.letsgoapp.constant.Constant;
import com.njz.letsgoapp.mvp.server.PriceDateContract;
import com.njz.letsgoapp.mvp.server.PriceDatePresenter;
import com.njz.letsgoapp.util.DateUtil;
import com.njz.letsgoapp.util.DecimalUtil;
import com.njz.letsgoapp.util.ToastUtil;
import com.njz.letsgoapp.util.glide.GlideUtil;
import com.njz.letsgoapp.util.rxbus.RxBus2;
import com.njz.letsgoapp.util.rxbus.busEvent.PriceCalendarEvent;
import com.njz.letsgoapp.view.calendar.PriceCalendarActivity;
import com.njz.letsgoapp.widget.GuideScoreView2;
import com.njz.letsgoapp.widget.NumberEtView;
import com.njz.letsgoapp.widget.NumberView;
import com.njz.letsgoapp.widget.PriceView;
import com.njz.letsgoapp.widget.ViewServerFlow;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by LGQ
 * Time: 2018/12/6
 * Function:
 */

public class PopServer extends BackgroundDarkPopupWindow implements View.OnClickListener, PriceDateContract.View {

    private View contentView;
    private Activity context;

    ImageView iv_img;
    TextView tv_title, tv_pop_close, tv_price_total, tv_submit,tv_count_title;
    GuideScoreView2 guideScoreView2;
    PriceView priceView;
    LinearLayout flow_parent;
    RelativeLayout rl_count;
    NumberEtView numberView;


    ServerDetailModel serverDetailModel;
    ServerItem serverItem;

    String titleLanguage;
    String titleCar;
    String titleTc;
    String title;

    float priceTotal;
    float priceLanguage;
    float priceCar;
    float priceTc;

    String formatIds;
    int formatIdTc;
    int formatIdCar;
    int formatIdLanguage;

    PriceDatePresenter datePresenter;

    List<PriceCalendarChildModel> priceCalendarChildModels;

    ViewServerFlow pirceVsf;
    Disposable priceDisposable;

    int serverNum;


    public PopServer(final Activity context, View parentView, ServerDetailModel serverDetailModel,ServerItem serverItem) {
        super(parentView, WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        contentView = inflater.inflate(R.layout.popup_server, null);
        this.context = context;
        this.serverDetailModel = serverDetailModel;
        this.serverItem = serverItem;

        initView();

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

    }

    //默认3天的未选中的时间。
    public void setPriceData() {
        priceCalendarChildModels = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            PriceCalendarChildModel priceCalendarChildModel = new PriceCalendarChildModel();
            priceCalendarChildModel.setSelect(false);
            priceCalendarChildModel.setAddPrice(serverDetailModel.getServePrice());
            Date date = DateUtil.getDate(i);
            Calendar ca = Calendar.getInstance();
            ca.setTime(date);
            priceCalendarChildModel.setYearInt(ca.get(Calendar.YEAR));
            priceCalendarChildModel.setMonthInt(ca.get(Calendar.MONTH) + 1);
            priceCalendarChildModel.setDateInt(ca.get(Calendar.DAY_OF_MONTH));
            priceCalendarChildModels.add(priceCalendarChildModel);
        }
    }

    //已选后，时间
    private void setSelectedPriceData() {
        priceCalendarChildModels.clear();
        if (pirceVsf.getMaxSelect() == 1) {
            if (serverItem.getSelectTimeValueList2().size() > 0) {
                //如果是maxSelect只能选择一天，需要展示第一天被选中，展示后面2天不被选中的ui
                PriceCalendarChildModel priceCalendarChildModel = new PriceCalendarChildModel();
                Calendar ca = Calendar.getInstance();
                ca.setTime(DateUtil.str2Date(serverItem.getSelectTimeValueList2().get(0)));
                priceCalendarChildModel.setYearInt(ca.get(Calendar.YEAR));
                priceCalendarChildModel.setMonthInt(ca.get(Calendar.MONTH) + 1);
                priceCalendarChildModel.setDateInt(ca.get(Calendar.DAY_OF_MONTH));
                priceCalendarChildModel.setSelect(true);
                priceCalendarChildModels.add(priceCalendarChildModel);
                for (int i = 1; i < 3; i++) {
                    PriceCalendarChildModel priceCalendarChildModel2 = new PriceCalendarChildModel();
                    Date date = priceCalendarChildModel.getDate();

                    Calendar ca2 = Calendar.getInstance();
                    ca2.setTime(date);
                    ca2.add(Calendar.DATE, i); //向后走一天

                    priceCalendarChildModel2.setYearInt(ca2.get(Calendar.YEAR));
                    priceCalendarChildModel2.setMonthInt(ca2.get(Calendar.MONTH) + 1);
                    priceCalendarChildModel2.setDateInt(ca2.get(Calendar.DAY_OF_MONTH));
                    priceCalendarChildModel2.setSelect(false);
                    priceCalendarChildModels.add(priceCalendarChildModel2);
                }
            }

        } else {
            for (int i = 0; i < serverItem.getSelectTimeValueList2().size(); i++) {
                PriceCalendarChildModel priceCalendarChildModel = new PriceCalendarChildModel();
                Calendar ca = Calendar.getInstance();
                ca.setTime(DateUtil.str2Date(serverItem.getSelectTimeValueList2().get(i)));
                priceCalendarChildModel.setYearInt(ca.get(Calendar.YEAR));
                priceCalendarChildModel.setMonthInt(ca.get(Calendar.MONTH) + 1);
                priceCalendarChildModel.setDateInt(ca.get(Calendar.DAY_OF_MONTH));
                priceCalendarChildModel.setSelect(true);
                priceCalendarChildModels.add(priceCalendarChildModel);
            }
        }
    }

    public void initView() {
        rl_count = contentView.findViewById(R.id.rl_count);
        numberView = contentView.findViewById(R.id.numberView);
        flow_parent = contentView.findViewById(R.id.flow_parent);
        iv_img = contentView.findViewById(R.id.iv_img);
        tv_title = contentView.findViewById(R.id.tv_title);
        tv_pop_close = contentView.findViewById(R.id.tv_pop_close);
        tv_price_total = contentView.findViewById(R.id.tv_price_total);
        tv_submit = contentView.findViewById(R.id.tv_submit);
        guideScoreView2 = contentView.findViewById(R.id.guideScoreView2);
        priceView = contentView.findViewById(R.id.priceView);
        tv_count_title = contentView.findViewById(R.id.tv_count_title);

        GlideUtil.LoadImage(context, serverDetailModel.getTitleImg2(), iv_img);
        tv_title.setText(serverDetailModel.getTitle());
        title = serverDetailModel.getTitle();
        guideScoreView2.setGuideScore2(serverDetailModel.getSellCount(), serverDetailModel.getScore(), serverDetailModel.getReviewCount());
        priceView.setPrice(serverDetailModel.getServePrice());
        tv_price_total.setText("￥" + serverDetailModel.getServePrice());
        tv_count_title.setText(serverDetailModel.getCountTitle());

        tv_pop_close.setOnClickListener(this);
        tv_submit.setOnClickListener(this);

        initNum();
        initData();
    }

    private void initNum(){
        serverNum = 1;
        numberView.setNum(1);
        numberView.setMinNum(1);

        if(serverItem !=null){
            serverNum = serverItem.getServeNum();
            numberView.setNum(serverNum);
        }

        numberView.setCallback(new NumberEtView.OnItemClickListener() {
            @Override
            public void onClick(int num) {
                serverNum = num;
                tv_price_total.setText("￥" + (DecimalUtil.multiply(priceTotal,serverNum)));
            }
        });
        if(serverDetailModel.getServeType() == Constant.SERVER_TYPE_GUIDE_ID){
            rl_count.setVisibility(View.GONE);
        }

        numberView.getEtNum().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(TextUtils.isEmpty(s.toString())){
                    serverNum = 0;
                    tv_price_total.setText("￥" + (DecimalUtil.multiply(priceTotal,serverNum)));
                }else{
                    serverNum = Integer.valueOf(s.toString());
                    tv_price_total.setText("￥" + (DecimalUtil.multiply(priceTotal,serverNum)));
                }
            }
        });
    }

    private void initData() {
        datePresenter = new PriceDatePresenter(context, this);
        initPriceFlowDate();
        setPriceData();
        if(serverItem != null){
            setSelectedPriceData();
        }
        initFlowDate(serverDetailModel.getNjzGuideServeFormatEntitys());
    }

    private void initPriceFlowDate() {
        pirceVsf = new ViewServerFlow(context);
        if ((serverDetailModel.getServeType() == Constant.SERVER_TYPE_CAR_ID)
                || (serverDetailModel.getServeType() == Constant.SERVER_TYPE_TICKET_ID)
                || (serverDetailModel.getServeType() == Constant.SERVER_TYPE_FEATURE_ID)) {
            pirceVsf.setMaxSelect(1);
        } else {
            pirceVsf.setMaxSelect(100);
        }
    }

    public synchronized void setChange() {
        tv_title.setText(serverDetailModel.getTitle() +
                (TextUtils.isEmpty(titleTc) ? "" : ("+" + titleTc)) +
                (TextUtils.isEmpty(titleCar) ? "" : ("+" + titleCar)) +
                (TextUtils.isEmpty(titleLanguage) ? "" : ("+" + titleLanguage))
        );

        formatIds = (formatIdTc != 0 ? formatIdTc + "," : "")
                + (formatIdCar != 0 ? formatIdCar + "," : "")
                + (formatIdLanguage != 0 ? formatIdLanguage + "," : "");
        formatIds = formatIds.endsWith(",") ? formatIds.substring(0, formatIds.length() - 1) : formatIds;

        priceView.setPrice(DecimalUtil.add(DecimalUtil.add(priceLanguage , priceCar) , priceTc));

        if(TextUtils.isEmpty(formatIds)){
            ToastUtil.showShortToast(context,"套餐不能为空~");
            return;
        }
        datePresenter.serveGetPrice(formatIds, getTravelDates(), serverDetailModel.getId());
    }

    public void setPriceChange(){
        priceTotal = 0;
        int dateCount = 0;
        for (int i = 0; i < priceCalendarChildModels.size(); i++) {
            if(priceCalendarChildModels.get(i).isSelect()){
                priceTotal = DecimalUtil.add(priceTotal , priceCalendarChildModels.get(i).getAddPrice());
                dateCount = dateCount + 1;
            }
        }
        tv_price_total.setText("￥" + (DecimalUtil.multiply(priceTotal,serverNum)));
        if(serverDetailModel.getServeType() == Constant.SERVER_TYPE_HOTEL_ID
                || serverDetailModel.getServeType() == Constant.SERVER_TYPE_GUIDE_ID)
            pirceVsf.setPriceTitle2("（共"+ dateCount+"天）");
    }

    public String getTravelDates() {
        String travelDates;
        StringBuffer travelDatesb = new StringBuffer();
        for (int i = 0; i < priceCalendarChildModels.size(); i++) {
            travelDatesb.append(priceCalendarChildModels.get(i).getTime("") + ",");
        }
        travelDates = travelDatesb.toString();
        travelDates = travelDates.endsWith(",") ? travelDates.substring(0, travelDates.length() - 1) : travelDates;
        return travelDates;
    }

    public String getSubmitTravelDates() {
        String travelDates;
        StringBuffer travelDatesb = new StringBuffer();
        for (int i = 0; i < priceCalendarChildModels.size(); i++) {
            if(priceCalendarChildModels.get(i).isSelect()){
                travelDatesb.append(priceCalendarChildModels.get(i).getTime("-") + ",");
            }
        }
        travelDates = travelDatesb.toString();
        travelDates = travelDates.endsWith(",") ? travelDates.substring(0, travelDates.length() - 1) : travelDates;
        return travelDates;
    }

    private void initFlowDate(List<PlayChileMedel> mVals) {
        final List<PlayChileMedel> mValsCx = new ArrayList<>();
        final List<PlayChileMedel> mValsYy = new ArrayList<>();
        final List<PlayChileMedel> mValsTc = new ArrayList<>();

        for (int i = 0; i < mVals.size(); i++) {
            if (mVals.get(i).getNjzGuideServeFormatDic().endsWith("cx")) {
                mValsCx.add(mVals.get(i));
                continue;
            }
            if (mVals.get(i).getNjzGuideServeFormatDic().endsWith("yy")) {
                mValsYy.add(mVals.get(i));
                continue;
            }
            if (mVals.get(i).getNjzGuideServeFormatDic().endsWith("tc")) {
                mValsTc.add(mVals.get(i));
                continue;
            }
        }

        if (mValsTc.size() > 0) {
            ViewServerFlow vsf = new ViewServerFlow(context);
            flow_parent.addView(vsf);
            vsf.setSelectedIndex(0);

            if(serverItem != null){
                int index = getTypeIndex(mValsTc);
                if(index != -1)
                    vsf.setSelectedIndex(index);
            }

            vsf.initFlow("选择套餐", null, mValsTc, new ViewServerFlow.OnTagLinsenerClick() {
                @Override
                public void onTagLinsenerClick(int position,boolean isSelect) {
                    titleTc = mValsTc.get(position).getGuideServeFormatName();
                    priceTc = mValsTc.get(position).getServeDefaultPrice();
                    formatIdTc = mValsTc.get(position).getId();
                    setChange();
                }
            });
        }

        if (mValsCx.size() > 0) {
            ViewServerFlow vsf = new ViewServerFlow(context);
            flow_parent.addView(vsf);

            String title2 = "";
            if(serverDetailModel.getServeType() == Constant.SERVER_TYPE_CAR_ID){
                vsf.setSelectedIndex(0);
            }else{
                title2 = "（自己开车可不选）";
                vsf.setMaxSelect(-2);
            }

            if(serverItem != null){
                int index = getTypeIndex(mValsCx);
                if(index != -1)
                    vsf.setSelectedIndex(index);
            }

            vsf.initFlow("选择车型", title2, mValsCx, new ViewServerFlow.OnTagLinsenerClick() {
                @Override
                public void onTagLinsenerClick(int position,boolean isSelect) {
                    if(isSelect){
                        titleCar = mValsCx.get(position).getGuideServeFormatName();
                        priceCar = mValsCx.get(position).getServeDefaultPrice();
                        formatIdCar = mValsCx.get(position).getId();
                    }else{
                        titleCar = "";
                        priceCar = 0f;
                        formatIdCar = 0;
                    }
                    setChange();
                }
            });
        }

        if (mValsYy.size() > 0) {
            ViewServerFlow vsf = new ViewServerFlow(context);
            flow_parent.addView(vsf);

            if(serverDetailModel.getServeType() == Constant.SERVER_TYPE_FEATURE_ID){
                vsf.setMaxSelect(-2);
            }else{
                vsf.setSelectedIndex(0);
            }

            if(serverItem != null){
                int index = getTypeIndex(mValsYy);
                if(index != -1)
                    vsf.setSelectedIndex(index);
            }

            vsf.initFlow("选择语言", null, mValsYy, new ViewServerFlow.OnTagLinsenerClick() {
                @Override
                public void onTagLinsenerClick(int position,boolean isSelect) {
                    if(isSelect){
                        titleLanguage = mValsYy.get(position).getGuideServeFormatName();
                        priceLanguage = mValsYy.get(position).getServeDefaultPrice();
                        formatIdLanguage = mValsYy.get(position).getId();
                    }else{
                        titleLanguage = "";
                        priceLanguage = 0f;
                        formatIdLanguage = 0;
                    }
                    setChange();
                }
            });
        }

        if (priceCalendarChildModels.size() > 0) {
            flow_parent.addView(pirceVsf);

            pirceVsf.initFlow2(serverDetailModel.getDateTitle(), null, priceCalendarChildModels);
            pirceVsf.setMore(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, PriceCalendarActivity.class);
                    intent.putParcelableArrayListExtra("priceCalendarChildModels", (ArrayList<PriceCalendarChildModel>) priceCalendarChildModels);
                    intent.putExtra("formatIds", formatIds);
                    intent.putExtra("serveId", serverDetailModel.getId());
                    intent.putExtra("maxSelect", pirceVsf.getMaxSelect());
                    context.startActivity(intent);

                    priceDisposable = RxBus2.getInstance().toObservable(PriceCalendarEvent.class, new Consumer<PriceCalendarEvent>() {
                        @Override
                        public void accept(PriceCalendarEvent priceCalendarEvent) throws Exception {
                            if (priceCalendarEvent.getPriceCalendarChildModels() == null) {
                                priceDisposable.dispose();
                                return;
                            }
                            if (priceCalendarEvent.getPriceCalendarChildModels().size() == 0) {
                                setPriceData();
                                datePresenter.serveGetPrice(formatIds, getTravelDates(), serverDetailModel.getId());
                                priceDisposable.dispose();
                                return;
                            }
                            priceCalendarChildModels = priceCalendarEvent.getPriceCalendarChildModels();
                            datePresenter.serveGetPrice(formatIds, getTravelDates(), serverDetailModel.getId());
                            priceDisposable.dispose();
                        }
                    });
                }
            });
        }
    }

    public int getTypeIndex(List<PlayChileMedel> mVals){
        for (int i =0; i < serverItem.getNjzGuideServeFormatId2().size();i++){
            for (int j =0;j<mVals.size();j++){
                if(serverItem.getNjzGuideServeFormatId2().get(i).equals(mVals.get(j).getId()+"")){
                    return j;
                }
            }
        }
        return -1;
    }

    public void showPopupWindow(View parent) {
        if (serverDetailModel == null || serverDetailModel.getNjzGuideServeFormatEntitys().size() == 0)
            return;
        if (!this.isShowing()) {
            setDarkStyle(-1);
            setDarkColor(Color.parseColor("#a0000000"));
            resetDarkPosition();
            darkFillScreen();
            showAtLocation(parent, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
        }
    }

    SubmitClick onSubmitClick;
    public interface SubmitClick{
        void onClick(ServerItem serverItem);
    }
    public void setSubmit(String submitTxt, SubmitClick onSubmitClick) {
        if (!TextUtils.isEmpty(submitTxt))
            tv_submit.setText(submitTxt);
        this.onSubmitClick = onSubmitClick;

    }

    public void dismissPopupWindow() {
        if (this.isShowing())
            this.dismiss();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_pop_close:
                dismissPopupWindow();
                break;
            case R.id.tv_submit:
                if(onSubmitClick !=null){
                    if(TextUtils.isEmpty(formatIds)){
                        ToastUtil.showShortToast(context,"请选择规格");
                        return ;
                    }
                    if(TextUtils.isEmpty(getSubmitTravelDates())){
                        ToastUtil.showShortToast(context,"请选择时间");
                        return;
                    }

                    if(serverNum == 0){
                        ToastUtil.showShortToast(context,"请输入" + serverDetailModel.getCountTitle());
                        return;
                    }

                    ServerItem data = new ServerItem();
                    data.setImg(serverDetailModel.getTitleImg());
                    data.setTitile(tv_title.getText().toString());
                    data.setPrice(priceTotal);
                    data.setLocation(serverDetailModel.getAddress());
                    data.setServiceTypeName(serverDetailModel.getServeTypeName());
                    data.setServeNum(serverNum);
                    data.setSelectTimeValueList(getSubmitTravelDates());
                    data.setNjzGuideServeId(serverDetailModel.getId());
                    data.setNjzGuideServeFormatId(formatIds);
                    data.setServerType(serverDetailModel.getServeType());
                    onSubmitClick.onClick(data);
                }
                dismissPopupWindow();
                break;
        }
    }

    @Override
    public void serveGetPriceSuccess(PriceCalendarModel model) {
        for (int i = 0; i < model.getNjzGuideServeFormatOnlyPriceVOList().size(); i++) {
            for (int j = 0; j < priceCalendarChildModels.size(); j++) {
                if (TextUtils.equals(model.getNjzGuideServeFormatOnlyPriceVOList().get(i).getTime(), priceCalendarChildModels.get(j).getTime())) {
                    priceCalendarChildModels.get(j).setAddPrice(model.getNjzGuideServeFormatOnlyPriceVOList().get(i).getAddPrice());
                    break;
                }
            }
        }
        for (int i = 0; i < model.getNoTimes().size(); i++) {
            for (int j = 0; j < priceCalendarChildModels.size(); j++) {
                if (TextUtils.equals(model.getNoTimes().get(i), priceCalendarChildModels.get(j).getTime())) {
                    priceCalendarChildModels.get(j).setEnable(true);
                    priceCalendarChildModels.get(j).setSelect(false);
                    break;
                }
            }
        }


        Collections.sort(priceCalendarChildModels); // 按年龄排序
        pirceVsf.setAdapter(priceCalendarChildModels, new ViewServerFlow.OnTagLinsenerClick() {
            @Override
            public void onTagLinsenerClick(int position,boolean isSelect) {
                setPriceChange();
            }
        });
        setPriceChange();
    }

    @Override
    public void serveGetPriceFailed(String msg) {
        ToastUtil.showShortToast(context, msg);
    }
}
