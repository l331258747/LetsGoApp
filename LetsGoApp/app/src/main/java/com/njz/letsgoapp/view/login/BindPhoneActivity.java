package com.njz.letsgoapp.view.login;

import android.support.v4.content.ContextCompat;
import android.text.InputType;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;
import com.njz.letsgoapp.R;
import com.njz.letsgoapp.base.ActivityCollect;
import com.njz.letsgoapp.base.BaseActivity;
import com.njz.letsgoapp.bean.EmptyModel;
import com.njz.letsgoapp.bean.MySelfInfo;
import com.njz.letsgoapp.bean.login.LoginModel;
import com.njz.letsgoapp.bean.other.WXInfoModel;
import com.njz.letsgoapp.constant.Constant;
import com.njz.letsgoapp.mvp.login.LoginByWxContract;
import com.njz.letsgoapp.mvp.login.LoginByWxPresenter;
import com.njz.letsgoapp.mvp.login.LoginByWxVerifyContract;
import com.njz.letsgoapp.mvp.login.LoginByWxVerifyPresenter;
import com.njz.letsgoapp.mvp.login.VerifyCodeContract;
import com.njz.letsgoapp.mvp.login.VerifyCodePresenter;
import com.njz.letsgoapp.mvp.login.VerifyLoginContract;
import com.njz.letsgoapp.mvp.login.VerifyLoginPresenter;
import com.njz.letsgoapp.util.AppUtils;
import com.njz.letsgoapp.util.LoginUtil;
import com.njz.letsgoapp.util.StringUtils;
import com.njz.letsgoapp.util.glide.GlideUtil;
import com.njz.letsgoapp.util.jpush.JpushAliasUtil;
import com.njz.letsgoapp.util.log.LogUtil;
import com.njz.letsgoapp.widget.LoginItemView2;

import java.util.concurrent.TimeUnit;

import cn.jpush.android.api.JPushInterface;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

/**
 * Created by LGQ
 * Time: 2019/1/17
 * Function:
 */

public class BindPhoneActivity extends BaseActivity implements View.OnClickListener,VerifyCodeContract.View,LoginByWxContract.View{
    ImageView iv_head;
    TextView tv_name,btn_submit,tvVerify;
    LoginItemView2 loginViewPhone,loginViewVerify;

    VerifyCodePresenter verifyCodePresenter;
    LoginByWxPresenter loginByWxPresenter;

    Disposable disposable;

    WXInfoModel wxInfoModel;

    @Override
    public void getIntentData() {
        super.getIntentData();
        wxInfoModel = intent.getParcelableExtra("WX_INFO");
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_bind_phone;
    }

    @Override
    public void initView() {
        showLeftAndTitle("绑定手机号码");

        iv_head = $(R.id.iv_head);
        tv_name = $(R.id.tv_name);
        btn_submit = $(R.id.btn_submit);

        loginViewPhone = $(R.id.login_view_phone);
        loginViewPhone.setEtInputType(InputType.TYPE_CLASS_NUMBER);
        loginViewVerify = $(R.id.login_view_verify);
        loginViewVerify.setEtInputType(InputType.TYPE_CLASS_NUMBER);
        tvVerify = loginViewVerify.getRightText();
        tvVerify.setTextColor(ContextCompat.getColor(context,R.color.color_theme));
        tvVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!LoginUtil.verifyPhone(loginViewPhone.getEtContent()))
                    return;
                verifyCodePresenter.userSmsSend(loginViewPhone.getEtContent(),"loginByThird");
            }
        });

    }

    @Override
    public void initData() {
        GlideUtil.LoadCircleImage(context, wxInfoModel.getFaceImage(), iv_head);
        tv_name.setText(wxInfoModel.getUserName());

        btn_submit.setOnClickListener(this);

        verifyCodePresenter = new VerifyCodePresenter(context,this);
        loginByWxPresenter = new LoginByWxPresenter(context,this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_submit:
                if (!LoginUtil.verifyPhone(loginViewPhone.getEtContent()))
                    return;
                if (!LoginUtil.verifyVerify(loginViewVerify.getEtContent()))
                    return;
                wxInfoModel.setMobile(loginViewPhone.getEtContent());
                wxInfoModel.setMsgCode(loginViewVerify.getEtContent());
                loginByWxPresenter.loginByWeixin(wxInfoModel,true);
                break;

        }
    }

    @Override
    public void userSmsSendSuccess(String str) {
        verifyEvent();
    }

    @Override
    public void userSmsSendFailed(String msg) {
        showShortToast(msg);
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
        if (disposable != null && !disposable.isDisposed())
            disposable.dispose();
    }

    /**
     * 登录成功后数据处理
     */
    public void setLoginData(LoginModel loginModel){
        MySelfInfo.getInstance().setData(loginModel);

        LogUtil.e("getRegistrationID:"+ JPushInterface.getRegistrationID(context));
        JpushAliasUtil.setTagAndAlias();

        if(AppUtils.getVersionCodeInt() % 100 != 0){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        EMClient.getInstance().createAccount("u_"+MySelfInfo.getInstance().getUserId(), Constant.IM_PASSWORD);
                        LogUtil.e("im 注册成功");
                    } catch (HyphenateException e) {
                        e.printStackTrace();
                        int errorCode = e.getErrorCode();
                        String message = e.getMessage();
                        LogUtil.e("im 注册失败");
                        LogUtil.e("errorCode:" + errorCode);
                        LogUtil.e("message:" + message);
                    }
                }
            }).start();
        }

        EMClient.getInstance().login("u_"+MySelfInfo.getInstance().getUserId(), Constant.IM_PASSWORD, new EMCallBack() {
            @Override
            public void onSuccess() {
                // 加载所有会话到内存
                EMClient.getInstance().chatManager().loadAllConversations();
                LogUtil.e("im 登录成功");

                MySelfInfo.getInstance().setImLogin(true);
            }

            @Override
            public void onError(final int i, final String s) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        LogUtil.e("im 登录失败 code: " + i + ",message: " + s);
                        LogUtil.e("code: " + i + ",message: " + s);
                        MySelfInfo.getInstance().setImLogin(false);
                    }
                });
            }

            @Override
            public void onProgress(int i, String s) {

            }
        });

        finish();
        ActivityCollect.getAppCollect().finishActivity(LoginActivity.class);
    }


    @Override
    public void loginByWeixinSuccess(LoginModel model) {
        setLoginData(model);
    }

    @Override
    public void loginByWeixinFailed(String msg) {
        showShortToast(msg);
    }
}
