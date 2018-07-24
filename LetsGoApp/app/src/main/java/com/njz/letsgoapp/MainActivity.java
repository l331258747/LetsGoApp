package com.njz.letsgoapp;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.njz.letsgoapp.base.BaseActivity;
import com.njz.letsgoapp.bean.MovieSubject;
import com.njz.letsgoapp.util.LogUtil;
import com.njz.letsgoapp.util.glide.GlideUtil;
import com.njz.letsgoapp.util.http.MethodApi;
import com.njz.letsgoapp.util.http.OnSuccessAndFaultSub;
import com.njz.letsgoapp.util.http.ResponseCallback;
import com.njz.letsgoapp.view.home.HomeActivity;
import com.njz.letsgoapp.view.pay.PayActivity;
import com.tencent.bugly.Bugly;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {

        httpSet();
        paySet();
        imageSet();
        homeSet();

//        buglySet();



    }

    private void buglySet() {

        List<String> datas = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            datas.add(i + "");
        }

        for (int i = 0; i < 5; i++) {
            LogUtil.e(datas.get(i));
        }
    }

    private void homeSet() {
        Button btnHome = $(R.id.btn_home);
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, HomeActivity.class));
            }
        });
    }

    private void httpSet() {
        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getUserData();
            }
        });
    }

    private void paySet() {
        Button paybtn = (Button) findViewById(R.id.btn_confirm);
        paybtn.setText("立即支付");
        paybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PayActivity.startActivity(MainActivity.this, 12314341);

            }
        });
    }

    private void imageSet() {
        ImageView iv1 = $(R.id.iv1);
        ImageView iv2 = $(R.id.iv2);

        String photo = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1532339453709&di=c506e751bd24c08cb2221d51ac3300c7&imgtype=0&src=http%3A%2F%2Fimg.80tian.com%2Fblog%2F201403%2F20140323170732_1145.jpg";

        GlideUtil.LoadCircleImage(this, photo, iv1);
        GlideUtil.LoadRoundImage(this, photo, iv2);

    }

    @Override
    public void initData() {

    }


    private void getUserData() {
        ResponseCallback getTopListener = new ResponseCallback<MovieSubject>() {
            @Override
            public void onSuccess(MovieSubject t) {
                LogUtil.e("onSuccess");
            }

            @Override
            public void onFault(String errorMsg) {
                LogUtil.e("onFault");
            }
        };
        MethodApi.getTop250(new OnSuccessAndFaultSub(getTopListener));
    }
}
