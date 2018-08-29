package com.njz.letsgoapp.view.login;

import android.text.InputType;
import android.view.View;
import android.widget.TextView;

import com.njz.letsgoapp.R;
import com.njz.letsgoapp.base.BaseActivity;
import com.njz.letsgoapp.bean.login.VerifyModel;
import com.njz.letsgoapp.mvp.login.RegistContract;
import com.njz.letsgoapp.mvp.login.RegistPresenter;
import com.njz.letsgoapp.util.LoginUtil;
import com.njz.letsgoapp.util.StringUtils;
import com.njz.letsgoapp.widget.LoginItemView2;

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

public class RegistActivity extends BaseActivity implements View.OnClickListener,RegistContract.View{

    LoginItemView2 loginViewPhone, loginViewVerify, loginViewPassword, loginViewPasswordAgin;
    TextView btnRegist;
    TextView tvVerify;

    Disposable disposable;

    RegistPresenter mPresenter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_regist;
    }

    @Override
    public void initView() {

//        showLeftAndTitle("注册");
        showLeftIcon();
        loginViewPhone = $(R.id.login_view_phone);
        loginViewPhone.setEtInputType(InputType.TYPE_CLASS_NUMBER);
        loginViewVerify = $(R.id.login_view_verify);
        loginViewVerify.setEtInputType(InputType.TYPE_CLASS_NUMBER);
        loginViewPassword = $(R.id.login_view_password);
        loginViewPassword.setEtInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        loginViewPasswordAgin = $(R.id.login_view_password_agin);
        loginViewPasswordAgin.setEtInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        tvVerify = loginViewVerify.getRightText();
        tvVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!LoginUtil.verifyPhone(loginViewPhone.getEtContent()))
                    return;
                verifyEvent();
                mPresenter.userSmsSend(loginViewPhone.getEtContent(),"register");
            }
        });

        btnRegist = $(R.id.btn_regist);

        btnRegist.setOnClickListener(this);

    }

    @Override
    public void initData() {
        mPresenter = new RegistPresenter(this,context);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_regist:
                if (!LoginUtil.verifyPhone(loginViewPhone.getEtContent()))
                    return;
                if (!LoginUtil.verifyVerify(loginViewVerify.getEtContent()))
                    return;
                if (!LoginUtil.verifyPassword(loginViewPassword.getEtContent()))
                    return;
                if (!LoginUtil.verifyPasswordDouble(loginViewPassword.getEtContent(), loginViewPasswordAgin.getEtContent()))
                    return;
                mPresenter.msgCheckRegister(loginViewPhone.getEtContent(),loginViewVerify.getEtContent(),loginViewPassword.getEtContent());
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
                        tvVerify.setEnabled(false);//在发送数据的时候设置为不能点击
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
                        StringUtils.setHtml(tvVerify,String.format(getResources().getString(R.string.verify), num));
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {
                        //回复原来初始状态
                        tvVerify.setEnabled(true);
                        tvVerify.setText("发送验证码");
                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(disposable != null && !disposable.isDisposed())
            disposable.dispose();
    }

    @Override
    public void msgCheckRegisterSuccess(String str) {
        showShortToast("注册成功");
        finish();
    }

    @Override
    public void msgCheckRegisterFailed(String msg) {
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
