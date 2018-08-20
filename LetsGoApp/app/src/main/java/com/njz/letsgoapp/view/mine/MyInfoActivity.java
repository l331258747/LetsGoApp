package com.njz.letsgoapp.view.mine;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.bigkoo.pickerview.view.TimePickerView;
import com.njz.letsgoapp.R;
import com.njz.letsgoapp.base.BaseActivity;
import com.njz.letsgoapp.util.DateUtil;
import com.njz.letsgoapp.util.glide.GlideUtil;
import com.njz.letsgoapp.util.rxbus.RxBus2;
import com.njz.letsgoapp.util.rxbus.busEvent.CityPickEvent;
import com.njz.letsgoapp.view.cityPick.CityPickActivity;
import com.njz.letsgoapp.widget.MineItemView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by LGQ
 * Time: 2018/8/9
 * Function:
 */

public class MyInfoActivity extends BaseActivity implements View.OnClickListener {
    ImageView iv_head;
    MineItemView info_sex, info_birthday, info_location, info_country, info_introduce, info_tag;
    EditText et_name;

    String bName, bBirthday, bLocation, bCountry, bSex;

    List<String> sexs;

    @Override
    public int getLayoutId() {
        return R.layout.activity_my_info;
    }

    @Override
    public void initView() {

        showLeftAndTitle("个人信息");
        showRightTv();
        getRightTv().setText("保存");
        getRightTv().setOnClickListener(this);
        getRightTv().setTextColor(ContextCompat.getColor(context, R.color.black_66));
        getRightTv().setEnabled(false);

        iv_head = $(R.id.iv_head);
        String photo = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1532339453709&di=c506e751bd24c08cb2221d51ac3300c7&imgtype=0&src=http%3A%2F%2Fimg.80tian.com%2Fblog%2F201403%2F20140323170732_1145.jpg";
        GlideUtil.LoadCircleImage(context, photo, iv_head);


        et_name = $(R.id.et_name);
        info_sex = $(R.id.info_sex);
        info_birthday = $(R.id.info_birthday);
        info_location = $(R.id.info_lacation);
        info_country = $(R.id.info_country);

        info_introduce = $(R.id.info_introduce);
        info_tag = $(R.id.info_tag);

        et_name.setText("那就走");

        info_location.setOnClickListener(this);
        info_country.setOnClickListener(this);
        info_birthday.setOnClickListener(this);
        info_sex.setOnClickListener(this);

        info_tag.setOnClickListener(this);

    }

    @Override
    public void initData() {
        bName = et_name.getText().toString();
        bBirthday = info_birthday.getContent();
        bLocation = info_location.getContent();
        bCountry = info_country.getContent();
        bSex = info_sex.getContent();
        sexs = new ArrayList<>();
        sexs.add("男");
        sexs.add("女");

        et_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                isChange();
            }
        });
    }

    Disposable desDisposable;
    Disposable desDisposable2;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.info_lacation:
                Intent intent = new Intent(context, CityPickActivity.class);
                intent.putExtra(CityPickActivity.CITYPICK_TAG, CityPickActivity.CITYPICK_LOCATION);
                startActivity(intent);
                activity.overridePendingTransition(0, 0);
                desDisposable = RxBus2.getInstance().toObservable(CityPickEvent.class, new Consumer<CityPickEvent>() {
                    @Override
                    public void accept(CityPickEvent cityPickEvent) throws Exception {
                        info_location.setContent(cityPickEvent.getCity());
                        desDisposable.dispose();
                        isChange();
                    }
                });
                break;
            case R.id.info_country:
                Intent intent2 = new Intent(context, CityPickActivity.class);
                intent2.putExtra(CityPickActivity.CITYPICK_TAG, CityPickActivity.CITYPICK_COUNTRY);
                startActivity(intent2);
                activity.overridePendingTransition(0, 0);
                desDisposable2 = RxBus2.getInstance().toObservable(CityPickEvent.class, new Consumer<CityPickEvent>() {
                    @Override
                    public void accept(CityPickEvent cityPickEvent) throws Exception {
                        info_country.setContent(cityPickEvent.getCity());
                        desDisposable2.dispose();
                        isChange();
                    }
                });
                break;
            case R.id.right_tv:
                showShortToast("保存");
                break;
            case R.id.info_birthday:
                //时间选择器
                TimePickerView pvTime = new TimePickerBuilder(context,
                        new OnTimeSelectListener() {
                            @Override
                            public void onTimeSelect(Date date, View v) {
                                info_birthday.setContent(DateUtil.dateToStr(date));
                                isChange();
                            }
                        })
                        .setDate(DateUtil.getSelectedDate(info_birthday.getContent()))// 如果不设置的话，默认是系统时间*/
                        .setRangDate(DateUtil.getStartDate(), DateUtil.getEndDate())//起始终止年月日设定
                        .build();
                pvTime.show();
                break;
            case R.id.info_sex:
                //条件选择器
                OptionsPickerView pvOptions = new OptionsPickerBuilder(context,
                        new OnOptionsSelectListener() {
                            @Override
                            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                                showShortToast(sexs.get(options1));
                            }
                        })
                        .build();
                pvOptions.setPicker(sexs, null, null);
                pvOptions.show();
                break;
            case R.id.info_tag:
                startActivity(new Intent(context, LabelActivity.class));
                break;
        }
    }


    //返回true为可以保存
    public void isChange() {
        boolean isChange = false;
        if (!TextUtils.equals(et_name.getText().toString(), bName)) {
            isChange = true;
        }
        if (!TextUtils.equals(info_sex.getContent(), bSex)) {
            isChange = true;
        }
        if (!TextUtils.equals(info_country.getContent(), bCountry)) {
            isChange = true;
        }
        if (!TextUtils.equals(info_location.getContent(), bLocation)) {
            isChange = true;
        }
        if (!TextUtils.equals(info_birthday.getContent(), bBirthday)) {
            isChange = true;
        }
        if (isChange) {
            getRightTv().setTextColor(ContextCompat.getColor(context, R.color.blue));
            getRightTv().setEnabled(true);
        } else {
            getRightTv().setTextColor(ContextCompat.getColor(context, R.color.black_66));
            getRightTv().setEnabled(false);
        }
    }

}
