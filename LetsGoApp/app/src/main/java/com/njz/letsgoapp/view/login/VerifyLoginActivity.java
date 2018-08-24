package com.njz.letsgoapp.view.login;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.njz.letsgoapp.R;
import com.njz.letsgoapp.base.BaseActivity;
import com.njz.letsgoapp.bean.MySelfInfo;
import com.njz.letsgoapp.bean.login.LoginModel;
import com.njz.letsgoapp.bean.login.VerifyModel;
import com.njz.letsgoapp.mvp.login.VerifyLoginContract;
import com.njz.letsgoapp.mvp.login.VerifyLoginPresenter;
import com.njz.letsgoapp.util.AppUtils;
import com.njz.letsgoapp.util.LoginUtil;
import com.njz.letsgoapp.view.homeFragment.HomeActivity;
import com.njz.letsgoapp.widget.LoginItemView;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

/**
 * Created by LGQ
 * Time: 2018/8/10
 * Function:
 */

public class VerifyLoginActivity extends BaseActivity implements View.OnClickListener,VerifyLoginContract.View {

    LoginItemView loginViewPhone, loginViewVerify;
    Button btnVerify, btnLogin;
    TextView tvLogin;

    Disposable disposable;

    VerifyLoginPresenter mPresenter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_verify_login;
    }

    @Override
    public void initView() {

        showLeftIcon();

        loginViewPhone = $(R.id.login_view_phone);
        loginViewPhone.setEtInputType(InputType.TYPE_CLASS_NUMBER);
        loginViewVerify = $(R.id.login_view_verify);
        loginViewVerify.setEtInputType(InputType.TYPE_CLASS_NUMBER);

        btnLogin = $(R.id.btn_login);
        btnVerify = $(R.id.btn_verify);
        tvLogin = $(R.id.tv_login);

        btnLogin.setOnClickListener(this);
        btnVerify.setOnClickListener(this);
        tvLogin.setOnClickListener(this);

    }

    @Override
    public void initData() {
        mPresenter = new VerifyLoginPresenter(context,this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                if(!LoginUtil.verifyPhone(loginViewPhone.getEtContent()))
                    return;
                if (!LoginUtil.verifyVerify(loginViewVerify.getEtContent()))
                    return;
                mPresenter.msgCheckLogin(loginViewPhone.getEtContent(),loginViewVerify.getEtContent());
                break;
            case R.id.btn_verify:
                if (!LoginUtil.verifyPhone(loginViewPhone.getEtContent()))
                    return;
                verifyEvent();
                mPresenter.userSmsSend(loginViewPhone.getEtContent(),"login");
                break;
            case R.id.tv_login:
                finish();
                break;

        }
    }

    public void verifyEvent() {
        final int count = 60;//倒计时10秒
        Observable.interval(0, 1, TimeUnit.SECONDS)//设置0延迟，每隔一秒发送一条数据
                .take(count + 1)//设置循环次数
                .map(new Function<Long, Long>() {
                    @Override
                    public Long apply(Long aLong) throws Exception {
                        return count - aLong;
                    }
                })
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        btnVerify.setEnabled(false);//在发送数据的时候设置为不能点击
                        btnVerify.setBackground(ContextCompat.getDrawable(AppUtils.getContext(), R.drawable.btn_gray_solid_r5));
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())//ui线程中进行控件更新
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable = d;
                    }

                    @Override
                    public void onNext(Long num) {
                        btnVerify.setText("剩余" + num + "秒");
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {
                        //回复原来初始状态
                        btnVerify.setEnabled(true);
                        btnVerify.setText("发送验证码");
                        //@drawable/btn_blue_solid_r5
                        btnVerify.setBackground(ContextCompat.getDrawable(AppUtils.getContext(), R.drawable.btn_blue_solid_r5));//背景色设为灰色
                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (disposable != null && !disposable.isDisposed())
            disposable.dispose();
    }

    @Override
    public void msgCheckLoginSuccess(LoginModel str) {
        MySelfInfo.getInstance().setData(str);
        startActivity(new Intent(context, HomeActivity.class));
        finish();
    }

    @Override
    public void msgCheckLoginFailed(String msg) {
        showShortToast(msg);
    }

    @Override
    public void userSmsSendSuccess(VerifyModel str) {
        showLongToast("验证码：" + str.getMsgCode());
    }

    @Override
    public void userSmsSendFailed(String msg) {
        showShortToast(msg);
    }
}
