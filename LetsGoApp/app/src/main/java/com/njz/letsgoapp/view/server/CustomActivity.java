package com.njz.letsgoapp.view.server;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.njz.letsgoapp.R;
import com.njz.letsgoapp.base.ActivityCollect;
import com.njz.letsgoapp.base.BaseActivity;
import com.njz.letsgoapp.bean.MySelfInfo;
import com.njz.letsgoapp.bean.order.PayModel;
import com.njz.letsgoapp.bean.server.ServerItem;
import com.njz.letsgoapp.bean.server.SubmitOrderChildModel;
import com.njz.letsgoapp.bean.server.SubmitOrderChilderItemModel;
import com.njz.letsgoapp.bean.server.SubmitOrderModel;
import com.njz.letsgoapp.dialog.DialogUtil;
import com.njz.letsgoapp.mvp.server.CreateOrderContract;
import com.njz.letsgoapp.mvp.server.CreateOrderPresenter;
import com.njz.letsgoapp.util.DateUtil;
import com.njz.letsgoapp.util.LoginUtil;
import com.njz.letsgoapp.util.rxbus.RxBus2;
import com.njz.letsgoapp.util.rxbus.busEvent.CalendarEvent;
import com.njz.letsgoapp.view.calendar.RangeCalendarActivity;
import com.njz.letsgoapp.view.homeFragment.HomeActivity;
import com.njz.letsgoapp.view.pay.PayActivity;
import com.njz.letsgoapp.view.pay.PaySuccessActivity;
import com.njz.letsgoapp.widget.NumberView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by LGQ
 * Time: 2018/12/6
 * Function:
 */

public class CustomActivity extends BaseActivity implements View.OnClickListener, CreateOrderContract.View {

    EditText et_price, et_name, et_phone, et_special;
    TextView tv_time, tv_count, tv_submit, tv_location, tv_time_start, tv_time_count, tv_time_end;
    ImageView iv_name_cancel, iv_phone_cancel, iv_special_cancel;

    LinearLayout ll_time;

    String location;
    int guideId;
    int serverId;

    String startTime;
    String endTime;
    int children;
    int adult = 1;

    Disposable calendarDisposable;

    CreateOrderPresenter mPresenter;

    @Override
    public void getIntentData() {
        super.getIntentData();
        location = intent.getStringExtra("LOCATION");
        guideId = intent.getIntExtra("GUIDE_ID", 0);
        serverId = intent.getIntExtra("SERVER_ID",0);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_custom;
    }

    @Override
    public void initView() {

        showLeftAndTitle("私人定制");

        ll_time = $(R.id.ll_time);
        tv_time_start = $(R.id.tv_time_start);
        tv_time_count = $(R.id.tv_time_count);
        tv_time_end = $(R.id.tv_time_end);
        tv_location = $(R.id.tv_location);
        et_price = $(R.id.et_price);
        et_name = $(R.id.et_name);
        et_phone = $(R.id.et_phone);
        et_special = $(R.id.et_special);
        tv_time = $(R.id.tv_time);
        tv_count = $(R.id.tv_count);
        tv_submit = $(R.id.tv_submit);
        iv_name_cancel = $(R.id.iv_name_cancel);
        iv_phone_cancel = $(R.id.iv_phone_cancel);
        iv_special_cancel = $(R.id.iv_special_cancel);

        tv_time.setOnClickListener(this);
        tv_count.setOnClickListener(this);
        ll_time.setOnClickListener(this);
        tv_submit.setOnClickListener(this);
        iv_name_cancel.setOnClickListener(this);
        iv_phone_cancel.setOnClickListener(this);
        iv_special_cancel.setOnClickListener(this);

        tv_location.setText(location);

    }

    @Override
    public void initData() {
        mPresenter = new CreateOrderPresenter(context, this);
        tv_count.setText(adult + "成人" + children + "儿童");
        et_name.setText(MySelfInfo.getInstance().getUserName());
        et_phone.setText(MySelfInfo.getInstance().getUserMoble());
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.tv_time:
            case R.id.ll_time:
                intent = new Intent(context, RangeCalendarActivity.class);
                intent.putExtra("startTime", startTime);
                intent.putExtra("endTime", endTime);
                startActivity(intent);

                calendarDisposable = RxBus2.getInstance().toObservable(CalendarEvent.class, new Consumer<CalendarEvent>() {
                    @Override
                    public void accept(CalendarEvent calendarEvent) throws Exception {
                        if (!TextUtils.isEmpty(calendarEvent.getStartTime())) {
                            ll_time.setVisibility(View.VISIBLE);
                            tv_time.setVisibility(View.GONE);
                            tv_time_start.setText(startTime = calendarEvent.getStartTime());
                            tv_time_end.setText(endTime = calendarEvent.getEndTime());
                            tv_time_count.setText(calendarEvent.getDays());

                        }
                        calendarDisposable.dispose();
                    }
                });

                break;
            case R.id.tv_count:
                final AlertDialog.Builder builder = new AlertDialog.Builder(context);
                View view = LayoutInflater.from(context).inflate(R.layout.dialog_custom_count, null);
                builder.setView(view);
                final AlertDialog dialog = builder.show();
                final NumberView numberView_adult = view.findViewById(R.id.numberView_adult);
                numberView_adult.setMinNum(1);
                numberView_adult.setNum(adult);
                final NumberView numberView_child = view.findViewById(R.id.numberView_child);
                view.findViewById(R.id.tv_submit).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        tv_count.setText(numberView_adult.getNum() + "成人" + numberView_child.getNum() + "儿童");
                        adult = numberView_adult.getNum();
                        children = numberView_child.getNum();
                    }
                });

                break;
            case R.id.tv_submit:
                //TODO 提交
                if (TextUtils.isEmpty(startTime)) {
                    showShortToast("请选择行程时间");
                    return;
                }
                if (!LoginUtil.verifyName(et_name.getText().toString()))
                    return;
                if (!LoginUtil.verifyPhone(et_phone.getText().toString()))
                    return;

                mPresenter.orderCreateOrder(getOrderData());
                break;
            case R.id.iv_name_cancel:
                et_name.setText("");
                break;
            case R.id.iv_phone_cancel:
                et_phone.setText("");
                break;
            case R.id.iv_special_cancel:
                et_special.setText("");
                break;

        }
    }

    public String getTimes(){
        String times="";
        List<String> dates = new ArrayList<>();
        int count = DateUtil.getGapCount(startTime,endTime);
        for (int i=0;i<count;i++){
            Calendar ca = Calendar.getInstance();
            ca.setTime(DateUtil.str2Date(startTime));
            ca.add(Calendar.DAY_OF_MONTH,i);
            dates.add(DateUtil.dateToStr(ca.getTime(),"yyyy-MM-dd"));
        }

        StringBuffer sb = new StringBuffer("");
        for (String str : dates){
            sb.append(str + ",");
        }
        times = sb.toString();
        times = times.endsWith(",")?times.substring(0,times.length()):times;
        return times;
    }

    public List<SubmitOrderChilderItemModel> getData() {
        List<SubmitOrderChilderItemModel> childerItems = new ArrayList<>();
        SubmitOrderChilderItemModel childerItem = new SubmitOrderChilderItemModel();
        childerItem.setServeNum(1);
        childerItem.setNjzGuideServeId(serverId);
        childerItem.setNjzGuideServeFormatId("");
        childerItem.setSelectTimeValueList(getTimes());
        childerItems.add(childerItem);
        return childerItems;
    }

    public SubmitOrderModel getOrderData() {
        SubmitOrderModel submitOrderModel = new SubmitOrderModel();
        submitOrderModel.setGuideId(guideId);
        submitOrderModel.setLocation(tv_location.getText().toString());
        submitOrderModel.setMobile(et_phone.getText().toString());
        submitOrderModel.setName(et_name.getText().toString());
        submitOrderModel.setAdult(adult);
        submitOrderModel.setChildren(children);
        submitOrderModel.setPersonNum(adult + children);
        String bugget = et_price.getText().toString();
        submitOrderModel.setBugGet(TextUtils.isEmpty(bugget)?0:Integer.valueOf(bugget));
        submitOrderModel.setSpecialRequire(et_special.getText().toString());
        SubmitOrderChildModel submitOrderChildModel = new SubmitOrderChildModel();
        submitOrderChildModel.setNjzGuideServeToOrderServeDtos(getData());
        submitOrderModel.setNjzGuideServeToOrderDto(submitOrderChildModel);
        return submitOrderModel;
    }

    @Override
    public void orderCreateOrderSuccess(PayModel model) {
        ActivityCollect.getAppCollect().finishAllNotHome();
        HomeActivity activity2 = (HomeActivity) ActivityCollect.getAppCollect().findActivity(HomeActivity.class);
        activity2.setTabIndex(2);
        activity2.getOrderFragment().setIndex(0);
    }

    @Override
    public void orderCreateOrderFailed(String msg) {
        showShortToast(msg);
    }
}
