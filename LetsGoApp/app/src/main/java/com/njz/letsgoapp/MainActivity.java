package com.njz.letsgoapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.njz.letsgoapp.base.BaseActivity;

public class MainActivity extends BaseActivity {

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {
        findViewById(R.id.tv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "去哪儿", Toast.LENGTH_SHORT).show();
            }
        });


        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getUserData();
            }
        });


        Button paybtn = (Button) findViewById(R.id.btn_confirm);
        paybtn.setText("立即支付");
        paybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });
    }

    @Override
    public void initData() {

    }


    private void getUserData() {
//        ResponseCallback testDataList = new ResponseCallback() {
//            @Override
//            public void onSuccess(BaseResponse result) {//成功回调
//
//            }
//
//            @Override
//            public void onFault(String errorMsg) {//失败的回调
//
//            }
//        };
//
//        UserApi.register(new OnSuccessAndFaultSub(testDataList), "13212615202","12345678","666555",1);
    }


}
