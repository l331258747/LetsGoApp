package com.njz.letsgoapp.view.login;

import android.support.v4.content.ContextCompat;
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
import com.njz.letsgoapp.util.jpushim.HandleResponseCode;
import com.njz.letsgoapp.util.jpushim.SharePreferenceManager;
import com.njz.letsgoapp.util.log.LogUtil;
import com.njz.letsgoapp.widget.LoginItemView2;

import java.util.concurrent.TimeUnit;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.api.BasicCallback;
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

    boolean isSeeLoginViewPassword;
    boolean isSeeLoginViewPasswordAgin;

    @Override
    public int getLayoutId() {
        return R.layout.activity_regist;
    }

    @Override
    public void initView() {

        showLeftAndTitle("新用户注册");
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
        tvVerify.setTextColor(ContextCompat.getColor(context,R.color.color_theme));
        tvVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!LoginUtil.verifyPhone(loginViewPhone.getEtContent()))
                    return;
                mPresenter.userSmsSend(loginViewPhone.getEtContent(),"register");
            }
        });

        btnRegist = $(R.id.btn_regist);

        btnRegist.setOnClickListener(this);

        loginViewPassword.setRightImgOnClickLisener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setEtState(loginViewPassword, isSeeLoginViewPassword);
                isSeeLoginViewPassword = !isSeeLoginViewPassword;
            }
        });
        loginViewPasswordAgin.setRightImgOnClickLisener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setEtState(loginViewPasswordAgin, isSeeLoginViewPasswordAgin);
                isSeeLoginViewPasswordAgin = !isSeeLoginViewPasswordAgin;
            }
        });

    }

    private void setEtState(LoginItemView2 view,boolean isSee){
        if(isSee){
            view.setEtInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            view.getRightImage().setImageDrawable(ContextCompat.getDrawable(context,R.mipmap.ic_see));
        }else{
            view.setEtInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            view.getRightImage().setImageDrawable(ContextCompat.getDrawable(context,R.mipmap.ic_see_un));
        }
        view.getEtView().setSelection(view.getEtContent().length());
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
                        tvVerify.setTextColor(ContextCompat.getColor(context,R.color.color_68));
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
                        tvVerify.setText("重新发送");
                        tvVerify.setTextColor(ContextCompat.getColor(context,R.color.color_theme));
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

//        final String userId = loginViewPhone.getEtContent();
//        final String password = loginViewPassword.getEtContent();
//        JMessageClient.register(userId, password, new BasicCallback() {
//            @Override
//            public void gotResult(int i, String s) {
//                if (i == 0) {
//                    SharePreferenceManager.setRegisterName(userId);
//                    SharePreferenceManager.setRegistePass(password);
//                    LogUtil.e("jpushim 注册成功");
//                } else {
//                    HandleResponseCode.onHandle(context, i, false);
//                    LogUtil.e("jpushim 注册失败");
//                }
//            }
//        });

        showShortToast("注册成功");
        finish();
    }

    @Override
    public void msgCheckRegisterFailed(String msg) {
        showShortToast(msg);
    }

    @Override
    public void userSmsSendSuccess(String str) {
        verifyEvent();
    }

    @Override
    public void userSmsSendFailed(String msg) {
        showShortToast(msg);
    }
}
