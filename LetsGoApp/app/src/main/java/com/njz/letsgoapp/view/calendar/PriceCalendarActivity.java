package com.njz.letsgoapp.view.calendar;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.CalendarView;
import android.widget.TextView;

import com.njz.letsgoapp.R;
import com.njz.letsgoapp.base.BaseActivity;
import com.njz.letsgoapp.bean.server.PriceCalendarChildModel;
import com.njz.letsgoapp.bean.server.PriceCalendarModel;
import com.njz.letsgoapp.mvp.server.PriceCalendarContract;
import com.njz.letsgoapp.mvp.server.PriceCalendarPresenter;
import com.njz.letsgoapp.util.DateUtil;
import com.njz.letsgoapp.util.rxbus.RxBus2;
import com.njz.letsgoapp.util.rxbus.busEvent.PriceCalendarEvent;
import com.njz.letsgoapp.view.calendarDecorators.DiscardDecorator;
import com.njz.letsgoapp.view.calendarDecorators.HighlightWeekendsDecorator;
import com.njz.letsgoapp.view.calendarDecorators.LunarDecorator;
import com.njz.letsgoapp.view.calendarDecorators.PriceModel;
import com.njz.letsgoapp.view.calendarDecorators.PrimeDayDisableDecorator;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.prolificinteractive.materialcalendarview.OnMonthChangedListener;
import com.prolificinteractive.materialcalendarview.format.TitleFormatter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by LGQ
 * Time: 2018/12/12
 * Function:
 */

public class PriceCalendarActivity extends BaseActivity implements OnMonthChangedListener, OnDateSelectedListener, PriceCalendarContract.View, View.OnClickListener {

    private static final DateFormat FORMATTER = SimpleDateFormat.getDateInstance();
    MaterialCalendarView calendarView;
    TextView textView;

    private LunarDecorator lunarDecorator;
    private PrimeDayDisableDecorator primeDayDisableDecorator;


    private PriceCalendarPresenter pricePresenter;
    private List<PriceModel> priceModels = new ArrayList<>();
    private String year;
    private String month;
    private Date date;
    private String formatIds;
    private int serveId;
    private List<PriceCalendarChildModel> priceCalendarChildModels;
    private int maxSelect;
    private boolean isSubmit = false;


    @Override
    public void getIntentData() {
        super.getIntentData();
        formatIds = intent.getStringExtra("formatIds");
        serveId = intent.getIntExtra("serveId", 0);
        priceCalendarChildModels = intent.getParcelableArrayListExtra("priceCalendarChildModels");
        maxSelect = intent.getIntExtra("maxSelect", 1);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_price_calendarview;
    }

    @Override
    public void initView() {
        showLeftAndTitle("日历");

        calendarView = $(R.id.calendarView);
        textView = $(R.id.textView);
        textView.setOnClickListener(this);

        initCalendarView();
    }


    @Override
    public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
        widget.invalidateDecorators();
    }

    @Override
    public void onMonthChanged(MaterialCalendarView widget, CalendarDay date) {
        this.date = date.getDate();
        year = DateUtil.dateToStr(date.getDate(), "yyyy");
        month = DateUtil.dateToStr(date.getDate(), "MM");
        pricePresenter.serveGetMorePrice(formatIds, year, month, serveId);
    }

    public void initCalendarView() {
        date = Calendar.getInstance().getTime();
        year = DateUtil.dateToStr(date, "yyyy");
        month = DateUtil.dateToStr(date, "MM");
        //设置当前时间
        calendarView.setCurrentDate(date);

        Calendar instance1 = Calendar.getInstance();
        instance1.set(instance1.get(Calendar.YEAR), instance1.get(Calendar.MONTH), 1);

        Calendar instance2 = Calendar.getInstance();
        instance2.add(Calendar.YEAR, 1);
        instance2.set(instance2.get(Calendar.YEAR), instance2.get(Calendar.MONTH), instance2.getActualMaximum(Calendar.DATE));

        calendarView
                .state()
                .edit()
                //设置一周的第一天是周日还是周一
                .setFirstDayOfWeek(Calendar.SUNDAY)
                //设置日期范围
                .setMinimumDate(instance1.getTime())
                .setMaximumDate(instance2.getTime())
                .setCalendarDisplayMode(CalendarMode.MONTHS)
                .commit();
        //设置周的文本
        calendarView.setWeekDayLabels(new String[]{"日", "一", "二", "三", "四", "五", "六"});
        calendarView.setSelectionMode(maxSelect == 1 ? MaterialCalendarView.SELECTION_MODE_SINGLE : MaterialCalendarView.SELECTION_MODE_MULTIPLE);

        calendarView.setSelectionColor(Color.parseColor("#ffe6d5"));

        //设置年月的title
        calendarView.setTitleFormatter(new TitleFormatter() {
            @Override
            public CharSequence format(CalendarDay day) {
                StringBuffer buffer = new StringBuffer();
                int yearOne = day.getYear();
                int monthOne = day.getMonth() + 1;
                buffer.append(yearOne).append("年").append(monthOne).append("月");
                return buffer;
            }
        });
        //日期点击事件
        calendarView.setOnDateChangedListener(this);
        calendarView.setOnMonthChangedListener(this);

        for (int i = 0; i < priceCalendarChildModels.size(); i++) {
            if (priceCalendarChildModels.get(i).isSelect()) {
                calendarView.setDateSelected(priceCalendarChildModels.get(i).getDate(), true);
            }
        }

    }

    @Override
    public void initData() {
        pricePresenter = new PriceCalendarPresenter(context, this);
        pricePresenter.serveGetMorePrice(formatIds, year, month, serveId);

        lunarDecorator = new LunarDecorator(year, month, priceModels);
        primeDayDisableDecorator = new PrimeDayDisableDecorator(new ArrayList<String>());
    }

    @Override
    public void serveGetMorePriceSuccess(PriceCalendarModel model) {
        List<PriceCalendarChildModel> models = model.getNjzGuideServeFormatOnlyPriceVOList();

        priceModels.clear();
        Calendar ca = Calendar.getInstance();
        ca.setTime(date);
        for (int i = 0; i < ca.getActualMaximum(Calendar.DATE); i++) {
            for (int j = 0; j < models.size(); j++) {
                if (TextUtils.equals(year + "-" + month + "-" + DateUtil.getWith0(i + 1), models.get(j).getTime())) {
                    PriceModel item = new PriceModel(models.get(j).getTime(), "" + models.get(j).getAddPrice());
                    priceModels.add(item);
                }
            }
        }
        lunarDecorator.setTime(year, month, priceModels);
        calendarView.addDecorator(lunarDecorator);

        List<String> noTimes = new ArrayList<>();
        String item = DateUtil.dateToStr(date);
        noTimes.add(item);
//        primeDayDisableDecorator.setNoTimes(noTimes);
//        calendarView.addDecorator(primeDayDisableDecorator);


        calendarView.addDecorator(new DiscardDecorator());

    }

    @Override
    public void serveGetMorePriceFailed(String msg) {
        showShortToast(msg);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.textView:
                priceCalendarChildModels.clear();
                if (maxSelect == 1) {
                    if (calendarView.getSelectedDates().size() > 0) {
                        //如果是maxSelect只能选择一天，需要展示第一天被选中，展示后面2天不被选中的ui
                        PriceCalendarChildModel priceCalendarChildModel = new PriceCalendarChildModel();
                        priceCalendarChildModel.setYearInt(calendarView.getSelectedDates().get(0).getYear());
                        priceCalendarChildModel.setMonthInt(calendarView.getSelectedDates().get(0).getMonth() + 1);
                        priceCalendarChildModel.setDateInt(calendarView.getSelectedDates().get(0).getDay());
                        priceCalendarChildModel.setSelect(true);
                        priceCalendarChildModels.add(priceCalendarChildModel);

                        for (int i = 1; i < 3; i++) {
                            PriceCalendarChildModel priceCalendarChildModel2 = new PriceCalendarChildModel();
                            Date date = priceCalendarChildModel.getDate();

                            Calendar ca = Calendar.getInstance();
                            ca.setTime(date);
                            ca.add(Calendar.DATE, i); //向后走一天

                            priceCalendarChildModel2.setYearInt(ca.get(Calendar.YEAR));
                            priceCalendarChildModel2.setMonthInt(ca.get(Calendar.MONTH) + 1);
                            priceCalendarChildModel2.setDateInt(ca.get(Calendar.DAY_OF_MONTH));
                            priceCalendarChildModel2.setSelect(false);
                            priceCalendarChildModels.add(priceCalendarChildModel2);
                        }
                    }

                } else {
                    for (int i = 0; i < calendarView.getSelectedDates().size(); i++) {
                        PriceCalendarChildModel priceCalendarChildModel = new PriceCalendarChildModel();
                        priceCalendarChildModel.setYearInt(calendarView.getSelectedDates().get(i).getYear());
                        priceCalendarChildModel.setMonthInt(calendarView.getSelectedDates().get(i).getMonth() + 1);
                        priceCalendarChildModel.setDateInt(calendarView.getSelectedDates().get(i).getDay());
                        priceCalendarChildModel.setSelect(true);
                        priceCalendarChildModels.add(priceCalendarChildModel);
                    }
                }

                isSubmit = true;
                RxBus2.getInstance().post(new PriceCalendarEvent(priceCalendarChildModels));
                finish();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(!isSubmit)
            RxBus2.getInstance().post(new PriceCalendarEvent(null));
    }
}
