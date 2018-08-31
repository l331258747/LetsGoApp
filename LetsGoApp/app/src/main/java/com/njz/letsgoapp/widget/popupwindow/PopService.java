package com.njz.letsgoapp.widget.popupwindow;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.njz.letsgoapp.R;
import com.njz.letsgoapp.adapter.home.PopServiceAdapter;
import com.njz.letsgoapp.bean.home.GuideServiceModel;
import com.njz.letsgoapp.util.ToastUtil;
import com.njz.letsgoapp.util.glide.GlideUtil;
import com.njz.letsgoapp.view.order.OrderSubmitActivity;
import com.njz.letsgoapp.view.home.ServiceDetailActivity;
import com.njz.letsgoapp.view.home.ServiceListActivity;
import com.njz.letsgoapp.widget.GuideLabelView;
import com.njz.letsgoapp.widget.MyRatingBar;
import com.njz.letsgoapp.widget.ServiceTagView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LGQ
 * Time: 2018/8/9
 * Function:
 */

public class PopService extends BackgroundDarkPopupWindow implements View.OnClickListener {

    private View contentView;
    private Activity mContext;
    private RecyclerView recyclerView;
    private PopServiceAdapter mAdapter;
    private Context context;

    private List<GuideServiceModel> guideServiceModels;

    public PopService(final Activity context, View parentView) {
        super(parentView, WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
        mContext = context;
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        contentView = inflater.inflate(R.layout.popup_service, null);
        recyclerView = contentView.findViewById(R.id.recycler_view);

        this.context = context;

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

        initSubmit();

    }

    public void setDate(List<GuideServiceModel> guideServiceModels) {
        this.guideServiceModels = guideServiceModels;

    }

    private void initSubmit() {
        TextView tv_submit = contentView.findViewById(R.id.tv_submit);
        tv_submit.setOnClickListener(this);
    }


//    ImageView iv_head;
//    MyRatingBar my_rating_bar;
//    TextView tv_service_num;
//    GuideLabelView guideLabel;
//    ServiceTagView stv_tag;
//    private void initInfo() {
//        iv_head = contentView.findViewById(R.id.iv_head);
//        my_rating_bar = contentView.findViewById(R.id.my_rating_bar);
//        tv_service_num = contentView.findViewById(R.id.tv_service_num);
//        guideLabel = contentView.findViewById(R.id.guide_label);
//        stv_tag = contentView.findViewById(R.id.stv_tag);
//
//        String photo = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1532339453709&di=c506e751bd24c08cb2221d51ac3300c7&imgtype=0&src=http%3A%2F%2Fimg.80tian.com%2Fblog%2F201403%2F20140323170732_1145.jpg";
//        GlideUtil.LoadCircleImage(mContext, photo, iv_head);
//
//        my_rating_bar.setRating(4);
//        List<String> services = new ArrayList<>();
//        services.add("4年");
//        services.add("英语");
//        services.add("中文");
//        services.add("泰语");
//        services.add("葡萄牙语");
//        stv_tag.setServiceTag(services);
//        List<String> tabels = new ArrayList<>();
//        tabels.add("幽默达人");
//        tabels.add("风趣性感");
//        tabels.add("旅游玩家高手");
//        guideLabel.setTabel(tabels);
//        tv_service_num.setText("服务" + 6000 + "次");
//    }

    public void showPopupWindow(View parent) {

        if (!this.isShowing()) {
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
            recyclerView.setLayoutManager(linearLayoutManager);
            mAdapter = new PopServiceAdapter(mContext, guideServiceModels);
            recyclerView.setAdapter(mAdapter);

            mAdapter.setOnItemClickListener(new PopServiceAdapter.OnTitleClickListener() {
                @Override
                public void onClick(String titleType) {
                    Intent intent;
                    if (TextUtils.equals(titleType, PopServiceAdapter.SERVICE_TYPE_GUIDE)
                            || TextUtils.equals(titleType, PopServiceAdapter.SERVICE_TYPE_CAR)) {
                        intent = new Intent(mContext, ServiceDetailActivity.class);
                        intent.putExtra("ServiceDetailActivity_title", titleType);
                        mContext.startActivity(intent);
                        return;
                    }
                    if (TextUtils.equals(titleType, PopServiceAdapter.SERVICE_TYPE_CUSTOM)
                            || TextUtils.equals(titleType, PopServiceAdapter.SERVICE_TYPE_HOTEL)
                            || TextUtils.equals(titleType, PopServiceAdapter.SERVICE_TYPE_TICKET)){
                        intent = new Intent(mContext, ServiceListActivity.class);
                        intent.putExtra("ServiceDetailActivity_title", titleType);
                        mContext.startActivity(intent);
                        return;
                    }
                }
            });

            //NestedScroll 嵌套 recyclerView 触摸到RecyclerView的时候滑动还有些粘连的感觉
            recyclerView.setNestedScrollingEnabled(false);

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
            case R.id.tv_submit:
                mContext.startActivity(new Intent(mContext, OrderSubmitActivity.class));
                dismissPopupWindow();
                break;

        }
    }
}
