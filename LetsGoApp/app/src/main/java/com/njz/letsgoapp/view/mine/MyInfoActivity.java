package com.njz.letsgoapp.view.mine;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.njz.letsgoapp.R;
import com.njz.letsgoapp.base.BaseActivity;
import com.njz.letsgoapp.util.glide.GlideUtil;
import com.njz.letsgoapp.util.rxbus.RxBus2;
import com.njz.letsgoapp.util.rxbus.busEvent.CityPickEvent;
import com.njz.letsgoapp.view.cityPick.CityPickActivity;
import com.njz.letsgoapp.widget.MineItemView;
import com.zaaach.citypicker.model.City;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by LGQ
 * Time: 2018/8/9
 * Function:
 */

public class MyInfoActivity extends BaseActivity implements View.OnClickListener {
    ImageView iv_head;
    MineItemView info_sex, info_birthday, info_lacation, info_country, info_introduce, info_tag;
    EditText et_name;

    @Override
    public int getLayoutId() {
        return R.layout.activity_my_info;
    }

    @Override
    public void initView() {

        showLeftAndTitle("个人信息");

        iv_head = $(R.id.iv_head);
        String photo = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1532339453709&di=c506e751bd24c08cb2221d51ac3300c7&imgtype=0&src=http%3A%2F%2Fimg.80tian.com%2Fblog%2F201403%2F20140323170732_1145.jpg";
        GlideUtil.LoadCircleImage(context, photo, iv_head);


        et_name = $(R.id.et_name);
        info_sex = $(R.id.info_sex);
        info_birthday = $(R.id.info_birthday);
        info_lacation = $(R.id.info_lacation);
        info_country = $(R.id.info_country);
        info_introduce = $(R.id.info_introduce);
        info_tag = $(R.id.info_tag);

        et_name.setText("那就走");

        info_lacation.setOnClickListener(this);
        info_country.setOnClickListener(this);

    }

    @Override
    public void initData() {

    }

    Disposable desDisposable;
    Disposable desDisposable2;

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.info_lacation:
                Intent intent = new Intent(context, CityPickActivity.class);
                intent.putExtra(CityPickActivity.CITYPICK_TAG, CityPickActivity.CITYPICK_LOCATION);
                startActivity(intent);
                activity.overridePendingTransition(0, 0);
                desDisposable = RxBus2.getInstance().toObservable(CityPickEvent.class, new Consumer<CityPickEvent>() {
                    @Override
                    public void accept(CityPickEvent cityPickEvent) throws Exception {
                        info_lacation.setContent(cityPickEvent.getCity());
                        desDisposable.dispose();
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
                    }
                });
                break;
        }
    }



}
