package com.njz.letsgoapp.widget.popupwindow;

import android.app.Activity;
import android.content.Context;
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
import com.njz.letsgoapp.bean.server.ServerDetailMedel;
import com.njz.letsgoapp.util.DateUtil;
import com.njz.letsgoapp.util.glide.GlideUtil;
import com.njz.letsgoapp.widget.GuideScoreView2;
import com.njz.letsgoapp.widget.PriceView;
import com.njz.letsgoapp.widget.ViewServerFlow;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LGQ
 * Time: 2018/12/6
 * Function:
 */

public class PopServer extends BackgroundDarkPopupWindow implements View.OnClickListener {

    private View contentView;
    private Activity context;

    ImageView iv_img;
    TextView tv_title, tv_pop_close, tv_price_total, tv_submit;
    GuideScoreView2 guideScoreView2;
    PriceView priceView;
    LinearLayout flow_parent;

    ServerDetailMedel serverDetailMedel;

    String titleLanguage;
    String titleDate;
    String titleCar;
    String titleTc;
    String title;

    float priceLanguage;
    float priceCar;
    float priceTc;


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
        tv_price_total.setText("￥"+serverDetailMedel.getServePrice());

        initFlowDate(serverDetailMedel.getNjzGuideServeFormatEntitys());

        tv_pop_close.setOnClickListener(this);
        tv_submit.setOnClickListener(this);

    }

    public void setChange() {
        tv_title.setText(serverDetailMedel.getTitle() +
                (TextUtils.isEmpty(titleTc) ? "" : ("+" + titleTc)) +
                (TextUtils.isEmpty(titleCar) ? "" : ("+" + titleCar)) +
                (TextUtils.isEmpty(titleLanguage) ? "" : ("+" + titleLanguage)) +
                (TextUtils.isEmpty(titleDate) ? "" : ("+" + titleDate))
        );
        tv_price_total.setText("￥"+(priceLanguage + priceCar + priceTc));
        priceView.setPrice(priceLanguage + priceCar + priceTc);
    }


    private void initFlowDate(List<PlayChileMedel> mVals) {
        final List<PlayChileMedel> mValsCx = new ArrayList<>();
        final List<PlayChileMedel> mValsYy = new ArrayList<>();
        final List<PlayChileMedel> mValsRq = new ArrayList<>();
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
            if (mVals.get(i).getNjzGuideServeFormatDic().endsWith("rq")) {
                mValsRq.add(mVals.get(i));
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
            vsf.initFlow("选择套餐", null, mValsTc, new ViewServerFlow.OnTagLinsenerClick() {
                @Override
                public void onTagLinsenerClick(int position) {
                    titleTc = mValsTc.get(position).getGuideServeFormatName();
                    priceTc = mValsTc.get(position).getServeDefaultPrice();
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
                    setChange();
                }
            });
        }

        if (mValsYy.size() > 0) {
            ViewServerFlow vsf = new ViewServerFlow(context);
            flow_parent.addView(vsf);
            vsf.initFlow("选择语言", null, mValsYy, new ViewServerFlow.OnTagLinsenerClick() {
                @Override
                public void onTagLinsenerClick(int position) {
                    titleLanguage = mValsYy.get(position).getGuideServeFormatName();
                    priceLanguage = mValsYy.get(position).getServeDefaultPrice();
                    setChange();
                }
            });
        }

        if (mValsRq.size() == 0) {
            for (int i = 0; i < 3; i++) {
                PlayChileMedel item = new PlayChileMedel();
                item.setGuideServeFormatName(DateUtil.dateToStrMD(DateUtil.getDate(i)));
                item.setServeDefaultPrice(serverDetailMedel.getServePrice());
                mValsRq.add(item);
            }

            ViewServerFlow vsf = new ViewServerFlow(context);
            flow_parent.addView(vsf);
            vsf.initFlow2("选择行程时间", null, mValsRq);
            vsf.setMore(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //TODO 选择日期
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
                break;
        }
    }
}
