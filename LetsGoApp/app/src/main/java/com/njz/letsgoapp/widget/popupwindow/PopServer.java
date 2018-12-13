package com.njz.letsgoapp.widget.popupwindow;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.njz.letsgoapp.R;
import com.njz.letsgoapp.bean.server.PlayChileMedel;
import com.njz.letsgoapp.bean.server.PriceCalendarChildModel;
import com.njz.letsgoapp.bean.server.PriceCalendarModel;
import com.njz.letsgoapp.bean.server.ServerDetailMedel;
import com.njz.letsgoapp.bean.server.ServerItem;
import com.njz.letsgoapp.constant.Constant;
import com.njz.letsgoapp.mvp.server.PriceDateContract;
import com.njz.letsgoapp.mvp.server.PriceDatePresenter;
import com.njz.letsgoapp.util.DateUtil;
import com.njz.letsgoapp.util.ToastUtil;
import com.njz.letsgoapp.util.glide.GlideUtil;
import com.njz.letsgoapp.util.rxbus.RxBus2;
import com.njz.letsgoapp.util.rxbus.busEvent.PriceCalendarEvent;
import com.njz.letsgoapp.view.calendar.PriceCalendarActivity;
import com.njz.letsgoapp.widget.GuideScoreView2;
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
    TextView tv_title, tv_pop_close, tv_price_total, tv_submit;
    GuideScoreView2 guideScoreView2;
    PriceView priceView;
    LinearLayout flow_parent;

    ServerDetailMedel serverDetailMedel;

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


    public PopServer(final Activity context, View parentView, ServerDetailMedel serverDetailMedel) {
        super(parentView, WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        contentView = inflater.inflate(R.layout.popup_server, null);
        this.context = context;
        this.serverDetailMedel = serverDetailMedel;

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

    public void setPriceData() {
        priceCalendarChildModels = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            PriceCalendarChildModel priceCalendarChildModel = new PriceCalendarChildModel();
            priceCalendarChildModel.setSelect(false);
            priceCalendarChildModel.setAddPrice(serverDetailMedel.getServePrice());
            Date date = DateUtil.getDate(i);
            Calendar ca = Calendar.getInstance();
            ca.setTime(date);
            priceCalendarChildModel.setYearInt(ca.get(Calendar.YEAR));
            priceCalendarChildModel.setMonthInt(ca.get(Calendar.MONTH) + 1);
            priceCalendarChildModel.setDateInt(ca.get(Calendar.DAY_OF_MONTH));
            priceCalendarChildModels.add(priceCalendarChildModel);
        }

    }

    public void initView() {
        flow_parent = contentView.findViewById(R.id.flow_parent);
        iv_img = contentView.findViewById(R.id.iv_img);
        tv_title = contentView.findViewById(R.id.tv_title);
        tv_pop_close = contentView.findViewById(R.id.tv_pop_close);
        tv_price_total = contentView.findViewById(R.id.tv_price_total);
        tv_submit = contentView.findViewById(R.id.tv_submit);
        guideScoreView2 = contentView.findViewById(R.id.guideScoreView2);
        priceView = contentView.findViewById(R.id.priceView);

        GlideUtil.LoadImage(context, serverDetailMedel.getTitleImg(), iv_img);
        tv_title.setText(serverDetailMedel.getTitle());
        title = serverDetailMedel.getTitle();
        guideScoreView2.setGuideScore(serverDetailMedel.getSellCount(), serverDetailMedel.getScore(), serverDetailMedel.getReviewCount());
        priceView.setPrice(serverDetailMedel.getServePrice());
        tv_price_total.setText("￥" + serverDetailMedel.getServePrice());

        tv_pop_close.setOnClickListener(this);
        tv_submit.setOnClickListener(this);

        initData();
    }

    private void initData() {
        datePresenter = new PriceDatePresenter(context, this);
        setPriceData();
        initFlowDate(serverDetailMedel.getNjzGuideServeFormatEntitys());
    }

    public void setChange() {

        tv_title.setText(serverDetailMedel.getTitle() +
                (TextUtils.isEmpty(titleTc) ? "" : ("+" + titleTc)) +
                (TextUtils.isEmpty(titleCar) ? "" : ("+" + titleCar)) +
                (TextUtils.isEmpty(titleLanguage) ? "" : ("+" + titleLanguage))
        );

        priceTotal = priceLanguage + priceCar + priceTc;
        tv_price_total.setText("￥" + (priceTotal));
        priceView.setPrice(priceTotal);

        formatIds = (formatIdTc != 0 ? formatIdTc + "," : "")
                + (formatIdCar != 0 ? formatIdCar + "," : "")
                + (formatIdLanguage != 0 ? formatIdLanguage + "," : "");
        formatIds = formatIds.endsWith(",") ? formatIds.substring(0, formatIds.length() - 1) : formatIds;

        datePresenter.serveGetPrice(formatIds, getTravelDates(), serverDetailMedel.getServeType());
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
            vsf.setSelectedOne(true);
            vsf.initFlow("选择套餐", null, mValsTc, new ViewServerFlow.OnTagLinsenerClick() {
                @Override
                public void onTagLinsenerClick(int position) {
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
            vsf.initFlow("选择车型", "（自己开车可不选）", mValsCx, new ViewServerFlow.OnTagLinsenerClick() {
                @Override
                public void onTagLinsenerClick(int position) {
                    titleCar = mValsCx.get(position).getGuideServeFormatName();
                    priceCar = mValsCx.get(position).getServeDefaultPrice();
                    formatIdCar = mValsCx.get(position).getId();
                    setChange();
                }
            });
        }

        if (mValsYy.size() > 0) {
            ViewServerFlow vsf = new ViewServerFlow(context);
            flow_parent.addView(vsf);
            vsf.setSelectedOne(true);
            vsf.initFlow("选择语言", null, mValsYy, new ViewServerFlow.OnTagLinsenerClick() {
                @Override
                public void onTagLinsenerClick(int position) {
                    titleLanguage = mValsYy.get(position).getGuideServeFormatName();
                    priceLanguage = mValsYy.get(position).getServeDefaultPrice();
                    formatIdLanguage = mValsYy.get(position).getId();
                    setChange();
                }
            });
        }

        if (priceCalendarChildModels.size() > 0) {
            pirceVsf = new ViewServerFlow(context);
            if ((serverDetailMedel.getServeType() == Constant.SERVER_TYPE_CAR_ID)
                    || (serverDetailMedel.getServeType() == Constant.SERVER_TYPE_TICKET_ID)
                    || (serverDetailMedel.getServeType() == Constant.SERVER_TYPE_FEATURE_ID)) {
                pirceVsf.setMaxSelect(1);
            } else {
                pirceVsf.setMaxSelect(100);
            }

            flow_parent.addView(pirceVsf);
            pirceVsf.initFlow2("选择行程时间", null, priceCalendarChildModels);
            pirceVsf.setMore(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, PriceCalendarActivity.class);
                    intent.putParcelableArrayListExtra("priceCalendarChildModels", (ArrayList<PriceCalendarChildModel>) priceCalendarChildModels);
                    intent.putExtra("formatIds", formatIds);
                    intent.putExtra("serveId", serverDetailMedel.getId());
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
                                datePresenter.serveGetPrice(formatIds, getTravelDates(), serverDetailMedel.getServeType());
                                priceDisposable.dispose();
                                return;
                            }
                            priceCalendarChildModels = priceCalendarEvent.getPriceCalendarChildModels();
                            datePresenter.serveGetPrice(formatIds, getTravelDates(), serverDetailMedel.getServeType());
                            priceDisposable.dispose();
                        }
                    });
                }
            });
        }
    }

    public void showPopupWindow(View parent) {
        if (serverDetailMedel == null || serverDetailMedel.getNjzGuideServeFormatEntitys().size() == 0)
            return;
        if (!this.isShowing()) {
            setDarkStyle(-1);
            setDarkColor(Color.parseColor("#a0000000"));
            resetDarkPosition();
            darkAbove(parent);
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
                dismissPopupWindow();
                if(onSubmitClick !=null){
                    ServerItem data = new ServerItem();
                    data.setImg(serverDetailMedel.getTitleImg());
                    data.setTitile(tv_title.getText().toString());
                    data.setPrice(priceTotal);
                    data.setServiceTypeName(serverDetailMedel.getServeTypeName());
                    data.setServeNum(1);
                    data.setSelectTimeValueList(getTravelDates());
                    data.setNjzGuideServeId(serverDetailMedel.getServeType());
                    data.setNjzGuideServeFormatId(formatIds);
                    onSubmitClick.onClick(data);
                }
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
        Collections.sort(priceCalendarChildModels); // 按年龄排序
        pirceVsf.setAdapter(priceCalendarChildModels);
    }

    @Override
    public void serveGetPriceFailed(String msg) {
        ToastUtil.showShortToast(context, msg);
    }
}
