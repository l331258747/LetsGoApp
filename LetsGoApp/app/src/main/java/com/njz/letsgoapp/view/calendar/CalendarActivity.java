package com.njz.letsgoapp.view.calendar;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dali.custompicker.CalendarData;
import com.dali.custompicker.adapter.MonthTimeAdapter;
import com.dali.custompicker.bean.DayTimeEntity;
import com.dali.custompicker.bean.MonthTimeEntity;
import com.dali.custompicker.bean.UpdataCalendar;
import com.njz.letsgoapp.util.DateUtil;
import com.njz.letsgoapp.util.ToastUtil;
import com.njz.letsgoapp.util.log.LogUtil;
import com.njz.letsgoapp.util.rxbus.RxBus2;
import com.njz.letsgoapp.util.rxbus.busEvent.CalendarEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class CalendarActivity extends Activity {

    public static final String CALENDAR_ID = "CALENDAR_ID";
    public static final String CALENDAR_ONE_DAY = "CALENDAR_ONE_DAY";
    public static final String CALENDAR_DAYS = "CALENDAR_DAYS";

    private TextView tvRight;
    private TextView ivLeft;

    private TextView startTime;          //开始时间
    private TextView stopTime;           //结束时间
    private TextView plan_time_txt_month;
    private RecyclerView reycycler;
    private MonthTimeAdapter adapter;
    private ArrayList<MonthTimeEntity> datas;

    private String startTimeStr = "";
    private String endTimeStr = "";
    private String oneTimeStr = "";

    private int mSuspensionHeight;
    private int mCurrentPosition = 0;

    private int intentTag;
    private String oneDay;
    private List<String> days;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.dali.custompicker.R.layout.activity_picker);

        initIntent();
        initView();
        initData();

        EventBus.getDefault().register(this);
    }

    private void initIntent(){
        intentTag = getIntent().getIntExtra(CALENDAR_ID,1);
        oneDay = getIntent().getStringExtra(CALENDAR_ONE_DAY);
        days = getIntent().getStringArrayListExtra(CALENDAR_DAYS);
    }

    private void initData() {
        CalendarData.startDay = new DayTimeEntity(0,0,0,0);
        CalendarData.stopDay = new DayTimeEntity(-1,-1,-1,-1);
        CalendarData.oneDay = new DayTimeEntity(-1,-1,-1,-1);
        CalendarData.markerDays = new ArrayList<>();

        //用来保留选择的日期
//        if(!TextUtils.isEmpty(oneDay)){
//            Calendar cd = DateUtil.getSelectedDate(oneDay);
//            cd.add(Calendar.MONTH,1);
//            CalendarData.oneDay = new DayTimeEntity(cd.get(Calendar.DAY_OF_MONTH),cd.get(Calendar.MONTH),cd.get(Calendar.YEAR),0);
//        }
//
//        if(days != null && days.size() > 0){
//            for (String str: days){
//                Calendar cd = DateUtil.getSelectedDate(str);
//                cd.add(Calendar.MONTH,1);
//                DayTimeEntity day = new DayTimeEntity(cd.get(Calendar.DAY_OF_MONTH),cd.get(Calendar.MONTH),cd.get(Calendar.YEAR),0);
//                CalendarData.markerDays.add(day);
//            }
//        }

        datas = new ArrayList<>();

        Calendar c = Calendar.getInstance();

        CalendarData.today = c.get(Calendar.DAY_OF_MONTH);

        c.add(Calendar.MONTH,1);
        int nextYear = c.get(Calendar.YEAR);
        int nextMonth = c.get(Calendar.MONTH);

        for (int i = 0; i < 5; i++) {
            datas.add(new MonthTimeEntity(nextYear,nextMonth,nextYear + "--"+ nextMonth));
            if (nextMonth == 12){
                nextMonth = 0;
                nextYear = nextYear + 1;
            }
            nextMonth = nextMonth + 1;
        }

        adapter = new MonthTimeAdapter(datas, CalendarActivity.this,intentTag);
        reycycler.setAdapter(adapter);

    }

    private void initView() {
        ivLeft = findViewById(com.dali.custompicker.R.id.left_iv);
        ivLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        tvRight = (TextView) findViewById(com.dali.custompicker.R.id.tv_right);
        tvRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(intentTag == 1){
                    if(CalendarData.stopDay.getDay() < 1 || CalendarData.startDay.getDay() < 1){
                        ToastUtil.showShortToast(CalendarActivity.this,"请完善开始结束时间");
                        return;
                    }else{
                        startTimeStr = CalendarData.startDay.getYear()+"-"+CalendarData.startDay.getMonth()+"-"+CalendarData.startDay.getDay();
                        endTimeStr = CalendarData.stopDay.getYear()+"-"+CalendarData.stopDay.getMonth()+"-"+CalendarData.stopDay.getDay();
                        RxBus2.getInstance().post(new CalendarEvent(CalendarData.startDay.getYear() +"-"+CalendarData.startDay.getMonth()+"-"+CalendarData.startDay.getDay()
                                ,CalendarData.startDay.getYear() +"-"+CalendarData.stopDay.getMonth() + "-" + CalendarData.stopDay.getDay()
                                ,getGapCount(startTimeStr,endTimeStr) + "天"));
                    }
                }else if(intentTag == 2){
                    if(CalendarData.markerDays.size() < 1){
                        ToastUtil.showShortToast(CalendarActivity.this,"请选择时间");
                        return;
                    }else{
                        RxBus2.getInstance().post(new CalendarEvent(getMarkerDaysStr(CalendarData.markerDays)));
                    }
                }else if(intentTag == 3){
                    if(CalendarData.oneDay.getDay() < 1){
                        ToastUtil.showShortToast(CalendarActivity.this,"请选择时间");
                        return;
                    }else{
                        oneTimeStr = CalendarData.oneDay.getYear()+"-"+CalendarData.oneDay.getMonth()+"-"+CalendarData.oneDay.getDay();
                        RxBus2.getInstance().post(new CalendarEvent(oneTimeStr));
                    }
                }
                finish();
            }
        });

        startTime = (TextView) findViewById(com.dali.custompicker.R.id.plan_time_txt_start);
        stopTime = (TextView) findViewById(com.dali.custompicker.R.id.plan_time_txt_stop);
        plan_time_txt_month = (TextView) findViewById(com.dali.custompicker.R.id.plan_time_txt_month);
        reycycler = (RecyclerView) findViewById(com.dali.custompicker.R.id.plan_time_calender);
        final LinearLayoutManager layoutManager =
                new LinearLayoutManager(this,   // 上下文
                        LinearLayout.VERTICAL,  //垂直布局,
                        false);

        reycycler.setLayoutManager(layoutManager);

        reycycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                mSuspensionHeight = plan_time_txt_month.getHeight();
            }

            @Override public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                View view = layoutManager.findViewByPosition(mCurrentPosition + 1);
                if (view != null) {
                    if (view.getTop() <= mSuspensionHeight) {
                        plan_time_txt_month.setY(-(mSuspensionHeight - view.getTop()));
                    } else {
                        plan_time_txt_month.setY(0);
                    }
                }

                if (mCurrentPosition != layoutManager.findFirstVisibleItemPosition()) {
                    mCurrentPosition = layoutManager.findFirstVisibleItemPosition();
                    plan_time_txt_month.setY(0);
                    plan_time_txt_month.setText(datas.get(mCurrentPosition).getSticky());
                }
            }
        });
    }

    public List<String> getMarkerDaysStr(List<DayTimeEntity> dayTimeEntities) {
        List<String> days = new ArrayList<>();
        if(dayTimeEntities == null || dayTimeEntities.size() == 0)
            return days;
        for (DayTimeEntity dayTimeEntity : dayTimeEntities){
            days.add(dayTimeEntity.getYear() + "-" + dayTimeEntity.getMonth() + "-" + dayTimeEntity.getDay());
        }
        return days;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(UpdataCalendar event) {
        adapter.notifyDataSetChanged();
        if(intentTag == 3) return;
//        startTime.setText("入住:" + CalendarData.startDay.getMonth()+"月"+CalendarData.startDay.getDay()+"日"+"\n");
//        if (CalendarData.stopDay.getDay() == -1) {
//            stopTime.setText("结束"+"\n"+"时间");
//        }else{
//            stopTime.setText("离店:" + CalendarData.stopDay.getMonth() + "月" + CalendarData.stopDay.getDay() + "日" + "\n");
//        }
    }

    @Override
    protected void onDestroy() {
        if(intentTag == 1){
            if(TextUtils.isEmpty(startTimeStr)){
                RxBus2.getInstance().post(new CalendarEvent("","",""));
            }
        }else if(intentTag == 2){
            RxBus2.getInstance().post(new CalendarEvent(getMarkerDaysStr(CalendarData.markerDays)));
        }else if(intentTag == 3){
            if(TextUtils.isEmpty(oneTimeStr)){
                RxBus2.getInstance().post(new CalendarEvent(""));
            }
        }

        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    public void getMarkerDays(){
        for (DayTimeEntity dayTimeEntity : CalendarData.markerDays){
            Log.e("---------","tear:"+ dayTimeEntity.getYear() + ",month:" + dayTimeEntity.getMonth() + ",day:" + dayTimeEntity.getDay());
        }
    }

    public static int getGapCount(String startStr, String endStr) {


        Date startDate = null;
        Date endDate = null;
        try {
            startDate = stringToDate(startStr);
            endDate =  stringToDate(endStr);
        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }


        Calendar fromCalendar = Calendar.getInstance();
        fromCalendar.setTime(startDate);
        fromCalendar.set(Calendar.HOUR_OF_DAY, 0);
        fromCalendar.set(Calendar.MINUTE, 0);
        fromCalendar.set(Calendar.SECOND, 0);
        fromCalendar.set(Calendar.MILLISECOND, 0);

        Calendar toCalendar = Calendar.getInstance();
        toCalendar.setTime(endDate);
        toCalendar.set(Calendar.HOUR_OF_DAY, 0);
        toCalendar.set(Calendar.MINUTE, 0);
        toCalendar.set(Calendar.SECOND, 0);
        toCalendar.set(Calendar.MILLISECOND, 0);

        return (int) ((toCalendar.getTime().getTime() - fromCalendar.getTime().getTime()) / (1000 * 60 * 60 * 24)) + 1;
    }

    public static Date stringToDate(String strTime) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        date = formatter.parse(strTime);
        return date;
    }


}
