package com.njz.letsgoapp.view.login;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.SystemClock;
import android.support.v4.content.ContextCompat;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;
import com.njz.letsgoapp.MyApplication;
import com.njz.letsgoapp.R;
import com.njz.letsgoapp.base.BaseActivity;
import com.njz.letsgoapp.bean.EmptyModel;
import com.njz.letsgoapp.bean.MySelfInfo;
import com.njz.letsgoapp.bean.login.LoginModel;
import com.njz.letsgoapp.bean.other.WXInfoModel;
import com.njz.letsgoapp.constant.Constant;
import com.njz.letsgoapp.dialog.DialogUtil;
import com.njz.letsgoapp.mvp.login.LoginByWxContract;
import com.njz.letsgoapp.mvp.login.LoginByWxPresenter;
import com.njz.letsgoapp.mvp.login.LoginByWxVerifyContract;
import com.njz.letsgoapp.mvp.login.LoginByWxVerifyPresenter;
import com.njz.letsgoapp.mvp.login.LoginContract;
import com.njz.letsgoapp.mvp.login.LoginPresenter;
import com.njz.letsgoapp.mvp.login.VerifyLoginContract;
import com.njz.letsgoapp.mvp.login.VerifyLoginPresenter;
import com.njz.letsgoapp.util.AppUtils;
import com.njz.letsgoapp.util.LoginUtil;
import com.njz.letsgoapp.util.StringUtils;
import com.njz.letsgoapp.util.ToastUtil;
import com.njz.letsgoapp.util.dialog.LoadingDialog;
import com.njz.letsgoapp.util.http.HttpMethods;
import com.njz.letsgoapp.util.jpush.JpushAliasUtil;
import com.njz.letsgoapp.util.log.LogUtil;
import com.njz.letsgoapp.view.home.GuideContractActivity;
import com.njz.letsgoapp.view.im.cache.UserCacheManager;
import com.njz.letsgoapp.widget.LoginItemView2;
import com.njz.letsgoapp.widget.LoginTabView;
import com.njz.letsgoapp.wxapi.WXHelp;
import com.tencent.connect.UserInfo;
import com.tencent.connect.auth.QQToken;
import com.tencent.connect.common.Constants;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.UiError;

import org.json.JSONObject;

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
 * Time: 2018/8/10
 * Function:
 */

public class LoginActivity extends BaseActivity implements View.OnClickListener,
        LoginContract.View,
        VerifyLoginContract.View,
        LoginByWxVerifyContract.View,
        LoginByWxContract.View {

    LoginItemView2 loginViewPhone, loginViewPassword, loginViewVerify;
    TextView btnLogin, tv_user_agreement, btnRegister, tvVerify;
    ImageView iv_change_url, wx_iv, qq_iv;
    LoginTabView tabview_verify_login, tabview_login;

    LoginPresenter mPresenter;
    VerifyLoginPresenter verifyLoginPresenter;
    LoginByWxVerifyPresenter loginByWxVerifyPresenter;
    LoginByWxPresenter loginByWxPresenter;
    Disposable disposable;

    String loginPhone;
    int loginType = 0;//0动态码，1账号密码

    @Override
    public void getIntentData() {
        super.getIntentData();
        loginPhone = intent.getStringExtra("LOGIN_PHONE");
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void initView() {
        showLeftAndTitle("登录");

        initEdit();
        initBtnLogin();
        initAgreement();
        changeUrl();
        initTab();
    }


    //----------init 初始化 start
    private void initBtnLogin() {
        btnLogin = $(R.id.btn_login);
        btnLogin.setOnClickListener(this);
        btnRegister = $(R.id.btn_register);
        btnRegister.setOnClickListener(this);

        wx_iv = $(R.id.wx_iv);
        qq_iv = $(R.id.qq_iv);
        wx_iv.setOnClickListener(this);
        qq_iv.setOnClickListener(this);
    }

    private void initAgreement() {
        tv_user_agreement = $(R.id.tv_user_agreement);
        StringUtils.setHtml(tv_user_agreement, getResources().getString(R.string.login_user_agreement));
        tv_user_agreement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, GuideContractActivity.class);
                intent.putExtra("CONTRACT_TYPE", 1);
                startActivity(intent);
            }
        });
    }

    private void initEdit() {
        loginViewPhone = $(R.id.login_view_phone);
        loginViewPhone.setEtInputType(InputType.TYPE_CLASS_NUMBER);
        if (!TextUtils.isEmpty(loginPhone))
            loginViewPhone.getEtView().setText(loginPhone);
        loginViewPassword = $(R.id.login_view_password);
        loginViewPassword.setEtInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);

        loginViewVerify = $(R.id.login_view_verify);
        loginViewVerify.setEtInputType(InputType.TYPE_CLASS_NUMBER);
        tvVerify = loginViewVerify.getRightText();
        tvVerify.setTextColor(ContextCompat.getColor(context, R.color.color_theme));
        tvVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!LoginUtil.verifyPhone(loginViewPhone.getEtContent()))
                    return;
                verifyLoginPresenter.userSmsSend(loginViewPhone.getEtContent(), "login");
            }
        });

        loginViewPassword.setOnClickLisener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ForgetActivity.class);
                intent.putExtra("LOGIN_PHONE", loginViewPhone.getEtContent());
                startActivity(intent);
            }
        });
    }

    private void initTab() {
        tabview_verify_login = $(R.id.tabview_verify_login);
        tabview_login = $(R.id.tabview_login);
        loginType = 0;

        tabview_verify_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tabview_verify_login.setSelect(true);
                tabview_login.setSelect(false);
                loginViewVerify.setVisibility(View.VISIBLE);
                loginViewPassword.setVisibility(View.GONE);
                loginType = 0;
            }
        });
        tabview_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tabview_verify_login.setSelect(false);
                tabview_login.setSelect(true);
                loginViewVerify.setVisibility(View.GONE);
                loginViewPassword.setVisibility(View.VISIBLE);
                loginType = 1;
            }
        });
    }

    int changeInt = 0;

    private void changeUrl() {
        iv_change_url = $(R.id.iv_change_url);
        if (AppUtils.getVersionCodeInt() % 100 == 0) return;
        iv_change_url.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeInt = changeInt + 1;
                if (changeInt == 5) {
                    changeInt = 0;
                    DialogUtil.getInstance().getEditDialog(context, new DialogUtil.DialogEditCallBack() {
                        @Override
                        public void exectEvent(DialogInterface alterDialog, String str) {
                            HttpMethods.getInstance().changeBaseUrl(str + "/");
                        }
                    }, 60, "http://").show();
                }
            }
        });
    }

    //----------init 初始化 end

    @Override
    public void initData() {
        mPresenter = new LoginPresenter(this, context);
        verifyLoginPresenter = new VerifyLoginPresenter(context, this);
        loginByWxVerifyPresenter = new LoginByWxVerifyPresenter(context, this);
        loginByWxPresenter = new LoginByWxPresenter(context, this);

        loadingDialog = new LoadingDialog(context);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                if (loginType == 1) {
                    if (!LoginUtil.verifyPhone(loginViewPhone.getEtContent()))
                        return;
                    if (!LoginUtil.verifyPassword(loginViewPassword.getEtContent()))
                        return;
                    mPresenter.login(loginViewPhone.getEtContent(), loginViewPassword.getEtContent());
                } else {
                    if (!LoginUtil.verifyPhone(loginViewPhone.getEtContent()))
                        return;
                    if (!LoginUtil.verifyVerify(loginViewVerify.getEtContent()))
                        return;
                    verifyLoginPresenter.msgCheckLogin(loginViewPhone.getEtContent(), loginViewVerify.getEtContent());
                }
                break;
            case R.id.btn_register:
                startActivity(new Intent(context, RegistActivity.class));
                break;
            case R.id.qq_iv:
                if (MyApplication.mTencent.isQQInstalled(LoginActivity.this)) {
                    MyApplication.mTencent.logout(this);
                    MyApplication.mTencent.login(this, "all", loginListener);
                    isQQlogin = false;
                } else {
                    showShortToast("您还未安装QQ客户端");
                }
                break;
            case R.id.wx_iv:
                WXHelp.getInstance().wxLogin(activity);
                break;
        }
    }


    //账号密码登录------------start
    @Override
    public void loginSuccess(LoginModel loginModel) {
        setLoginData(loginModel);
    }

    @Override
    public void loginFailed(String msg) {
        showShortToast(msg);
    }

    //账号密码登录-------------end

    public void checkOpendId(){
        loadingDialog.showNoText();

        if (loginByWxVerifyPresenter == null || code == null) return;
        loginByWxVerifyPresenter.checkOpenId(code.getPlatUid(), code.getPlatCode());
    }

    //微信------start
    WXInfoModel code;

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        LogUtil.e("login onNewIntent");
        code = intent.getParcelableExtra("code");
        checkOpendId();
    }
    //微信------end

    //验证码登录-----start
    @Override
    public void msgCheckLoginSuccess(LoginModel str) {
        setLoginData(str);
    }

    @Override
    public void msgCheckLoginFailed(String msg) {
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
                        tvVerify.setTextColor(ContextCompat.getColor(context, R.color.color_68));
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
                        StringUtils.setHtml(tvVerify, String.format(getResources().getString(R.string.verify), num));
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
                        tvVerify.setTextColor(ContextCompat.getColor(context, R.color.color_theme));
                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (disposable != null && !disposable.isDisposed())
            disposable.dispose();
    }

    //验证码登录-------------end

    // 验证接口------------start
    LoadingDialog loadingDialog;
    @Override
    public void checkOpenIdSuccess(Integer loginModel) {
        if (code == null) return;
        if (loginModel == 0) {
            Intent intent = new Intent(LoginActivity.this, BindPhoneActivity.class);
            intent.putExtra("WX_INFO", code);
            startActivity(intent);

            loadingDialog.dismiss();

            return;
        } else {
            loginByWxPresenter.loginByWeixin(code,false);
        }
    }

    @Override
    public void checkOpenIdFailed(String msg) {
        showShortToast(msg);
        loadingDialog.dismiss();
    }

    // 验证接口------------end

    //第三方登录接口-------------start
    @Override
    public void loginByWeixinSuccess(LoginModel model) {
        loadingDialog.dismiss();
        setLoginData(model);
    }

    @Override
    public void loginByWeixinFailed(String msg) {
        loadingDialog.dismiss();
        showShortToast(msg);
    }

    //第三方登录接口-------------end


    /**
     * 登录成功后数据处理
     */
    public void setLoginData(LoginModel loginModel) {
        MySelfInfo.getInstance().setData(loginModel);

        UserCacheManager.save("u_"+MySelfInfo.getInstance().getUserId(),MySelfInfo.getInstance().getUserNickname(),MySelfInfo.getInstance().getUserImgUrl());

        LogUtil.e("getRegistrationID:" + JPushInterface.getRegistrationID(context));
        JpushAliasUtil.setTagAndAlias();

        if (AppUtils.getVersionCodeInt() % 100 != 0) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        EMClient.getInstance().createAccount("u_" + MySelfInfo.getInstance().getUserId(), Constant.IM_PASSWORD);
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

        EMClient.getInstance().login("u_" + MySelfInfo.getInstance().getUserId(), Constant.IM_PASSWORD, new EMCallBack() {
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
    }

    //-------------------------QQ登录-------------------------------------
    boolean isQQlogin = false;
    IUiListener loginListener = new BaseUiListener() {
        @Override
        protected void doComplete(JSONObject values) {
            if (isQQlogin) return;

            isQQlogin = true;
            LogUtil.e("SDKQQAgentPref:AuthorSwitch_SDK:" + SystemClock.elapsedRealtime());
            initOpenidAndToken(values);
            getUserInfo();
        }
    };

    public void initOpenidAndToken(JSONObject jsonObject) {
        LogUtil.e(jsonObject.toString());
        try {
            String token = jsonObject.getString(Constants.PARAM_ACCESS_TOKEN);
            String expires = jsonObject.getString(Constants.PARAM_EXPIRES_IN);
            String openId = jsonObject.getString(Constants.PARAM_OPEN_ID);
            if (!TextUtils.isEmpty(token) && !TextUtils.isEmpty(expires) && !TextUtils.isEmpty(openId)) {
                MyApplication.mTencent.setAccessToken(token, expires);
                MyApplication.mTencent.setOpenId(openId);
            }
        } catch (Exception e) {
        }
    }

    private void getUserInfo() {
        QQToken token = MyApplication.mTencent.getQQToken();
        UserInfo mInfo = new UserInfo(context, token);
        mInfo.getUserInfo(new IUiListener() {
            @Override
            public void onComplete(Object object) {
                JSONObject jsonObject = (JSONObject) object;
                LogUtil.e(jsonObject.toString());
                try {
                    WXInfoModel wxInfoModel = new WXInfoModel();
                    wxInfoModel.setRealName("");
                    wxInfoModel.setUserName(jsonObject.getString("nickname"));
                    wxInfoModel.setFaceImage(jsonObject.getString("figureurl_qq_2"));
                    wxInfoModel.setGender(jsonObject.getString("gender"));
                    wxInfoModel.setIntroduction("");
                    wxInfoModel.setPlatCode("tencent");
                    wxInfoModel.setPlatUid(MyApplication.mTencent.getOpenId());
                    wxInfoModel.setMobile("");
                    wxInfoModel.setLoginType("0");
                    code = wxInfoModel;
                    checkOpendId();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(UiError uiError) {
            }

            @Override
            public void onCancel() {
            }
        });
    }

    private class BaseUiListener implements IUiListener {

        @Override
        public void onComplete(Object response) {
            if (null == response) {
                ToastUtil.showShortToast(context, "登录失败");
                return;
            }
            JSONObject jsonResponse = (JSONObject) response;
            if (null != jsonResponse && jsonResponse.length() == 0) {
                ToastUtil.showShortToast(context, "登录失败");
                return;
            }
            ToastUtil.showShortToast(context, "登录成功");
            LogUtil.e("登录成功 json:" + response.toString());

            doComplete((JSONObject) response);
        }

        protected void doComplete(JSONObject values) {

        }

        @Override
        public void onError(UiError e) {
            showShortToast("onError: " + e.errorDetail);
        }

        @Override
        public void onCancel() {
            showShortToast("onCancel: ");
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //由于在一些低端机器上，因为内存原因，无法返回到回调onComplete里面，是以onActivityResult的方式返回
        if (requestCode == 11101 && resultCode == RESULT_OK) {
            //处理返回的数据
            if (data == null) {
                ToastUtil.showShortToast(context, "返回数据为空");
            } else {
                MyApplication.mTencent.handleResultData(data, loginListener);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

}
