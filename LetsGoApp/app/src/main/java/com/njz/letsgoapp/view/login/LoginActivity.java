package com.njz.letsgoapp.view.login;

import android.content.DialogInterface;
import android.content.Intent;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.njz.letsgoapp.R;
import com.njz.letsgoapp.base.BaseActivity;
import com.njz.letsgoapp.bean.MySelfInfo;
import com.njz.letsgoapp.bean.login.LoginModel;
import com.njz.letsgoapp.constant.Constant;
import com.njz.letsgoapp.constant.URLConstant;
import com.njz.letsgoapp.dialog.DialogUtil;
import com.njz.letsgoapp.mvp.login.LoginContract;
import com.njz.letsgoapp.mvp.login.LoginPresenter;
import com.njz.letsgoapp.util.AppUtils;
import com.njz.letsgoapp.util.LoginUtil;
import com.njz.letsgoapp.util.StringUtils;
import com.njz.letsgoapp.util.http.HttpMethods;
import com.njz.letsgoapp.util.jpush.JpushAliasUtil;
import com.njz.letsgoapp.util.jpushim.HandleResponseCode;
import com.njz.letsgoapp.util.jpushim.JpushImUtil;
import com.njz.letsgoapp.util.jpushim.SharePreferenceManager;
import com.njz.letsgoapp.util.jpushim.UserEntry;
import com.njz.letsgoapp.util.log.LogUtil;
import com.njz.letsgoapp.view.home.GuideContractActivity;
import com.njz.letsgoapp.widget.LoginItemView2;

import java.io.File;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.model.UserInfo;
import cn.jpush.im.api.BasicCallback;

/**
 * Created by LGQ
 * Time: 2018/8/10
 * Function:
 */

public class LoginActivity extends BaseActivity implements View.OnClickListener,LoginContract.View{

    LoginItemView2 loginViewPhone, loginViewPassword;
    TextView btnLogin,tv_user_agreement;
    TextView tvRegister, tvVerifyLogin;
    ImageView iv_change_url;

    LoginPresenter mPresenter;

    String loginPhone;

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


        iv_change_url = $(R.id.iv_change_url);
        loginViewPhone = $(R.id.login_view_phone);
        loginViewPhone.setEtInputType(InputType.TYPE_CLASS_NUMBER);
        if(!TextUtils.isEmpty(loginPhone))
            loginViewPhone.getEtView().setText(loginPhone);
        loginViewPassword = $(R.id.login_view_password);
        loginViewPassword.setEtInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);

        loginViewPassword.setOnClickLisener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ForgetActivity.class);
                intent.putExtra("LOGIN_PHONE",loginViewPhone.getEtContent());
                startActivity(intent);
            }
        });

        btnLogin = $(R.id.btn_login);
        tvRegister = $(R.id.tv_register);
        tvVerifyLogin = $(R.id.tv_verify_login);


        btnLogin.setOnClickListener(this);
        tvRegister.setOnClickListener(this);
        tvVerifyLogin.setOnClickListener(this);

        tv_user_agreement = $(R.id.tv_user_agreement);
        StringUtils.setHtml(tv_user_agreement, getResources().getString(R.string.login_user_agreement));
        tv_user_agreement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, GuideContractActivity.class);
                intent.putExtra("CONTRACT_TYPE",1);
                startActivity(intent);
            }
        });

        changeUrl();
    }

    int changeInt = 0;
    private void changeUrl() {
        if(AppUtils.getVersionCodeInt() % 100 == 0) return;
        iv_change_url.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeInt = changeInt + 1;
                if(changeInt == 5){
                    changeInt = 0;
                    DialogUtil.getInstance().getEditDialog(context, new DialogUtil.DialogEditCallBack() {
                        @Override
                        public void exectEvent(DialogInterface alterDialog, String str) {
                            HttpMethods.getInstance().changeBaseUrl(str+"/");
                        }
                    },60,"http://").show();
                }
            }
        });
    }

    @Override
    public void initData() {
        mPresenter = new LoginPresenter(this,context);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()){
            case R.id.btn_login:
                if(!LoginUtil.verifyPhone(loginViewPhone.getEtContent()))
                    return;
                if(!LoginUtil.verifyPassword(loginViewPassword.getEtContent()))
                    return;
                mPresenter.login(loginViewPhone.getEtContent(),loginViewPassword.getEtContent());
                break;
            case R.id.tv_register:
                startActivity(new Intent(context, RegistActivity.class));
                break;
            case R.id.tv_verify_login:
                intent = new Intent(context, VerifyLoginActivity.class);
                intent.putExtra("LOGIN_PHONE",loginViewPhone.getEtContent());
                startActivity(intent);
                break;
        }
    }

    @Override
    public void loginSuccess(LoginModel loginModel) {

        JpushImUtil.login(loginViewPhone.getEtContent(),loginViewPassword.getEtContent());

        MySelfInfo.getInstance().setData(loginModel);

        LogUtil.e("getRegistrationID:"+JPushInterface.getRegistrationID(context));
        JpushAliasUtil.setTagAndAlias();

        finish();
    }

    @Override
    public void loginFailed(String msg) {
        showShortToast(msg);
    }



}
