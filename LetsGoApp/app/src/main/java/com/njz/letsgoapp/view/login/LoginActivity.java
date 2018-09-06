package com.njz.letsgoapp.view.login;

import android.content.Intent;
import android.text.InputType;
import android.view.View;
import android.widget.TextView;

import com.njz.letsgoapp.R;
import com.njz.letsgoapp.base.BaseActivity;
import com.njz.letsgoapp.bean.MySelfInfo;
import com.njz.letsgoapp.bean.login.LoginModel;
import com.njz.letsgoapp.mvp.login.LoginContract;
import com.njz.letsgoapp.mvp.login.LoginPresenter;
import com.njz.letsgoapp.util.LoginUtil;
import com.njz.letsgoapp.util.log.LogUtil;
import com.njz.letsgoapp.view.homeFragment.HomeActivity;
import com.njz.letsgoapp.widget.LoginItemView2;

/**
 * Created by LGQ
 * Time: 2018/8/10
 * Function:
 */

public class LoginActivity extends BaseActivity implements View.OnClickListener,LoginContract.View{

    LoginItemView2 loginViewPhone, loginViewPassword;
    TextView btnLogin;
    TextView tvRegister, tvVerifyLogin;

    LoginPresenter mPresenter;


    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void initView() {
        showLeftIcon();

        loginViewPhone = $(R.id.login_view_phone);
        loginViewPhone.setEtInputType(InputType.TYPE_CLASS_NUMBER);
        loginViewPassword = $(R.id.login_view_password);
        loginViewPassword.setEtInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);

        loginViewPassword.setOnClickLisener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, ForgetActivity.class));
            }
        });

        btnLogin = $(R.id.btn_login);
        tvRegister = $(R.id.tv_register);
        tvVerifyLogin = $(R.id.tv_verify_login);

        btnLogin.setOnClickListener(this);
        tvRegister.setOnClickListener(this);
        tvVerifyLogin.setOnClickListener(this);

        initEt();
    }

    //TODO
    public void initEt(){
        loginViewPhone.getEtView().setText("18826420934");
        loginViewPassword.getEtView().setText("941740");
    }

    @Override
    public void initData() {
        mPresenter = new LoginPresenter(this,context);
    }

    @Override
    public void onClick(View v) {
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
                startActivity(new Intent(context, VerifyLoginActivity.class));
                break;
        }
    }

    @Override
    public void loginSuccess(LoginModel loginModel) {
        MySelfInfo.getInstance().setData(loginModel);
        LogUtil.e("....."+loginModel.getTravelZoneVO().getTravelMacroEntitys());
//        startActivity(new Intent(context,HomeActivity.class));
        finish();
    }

    @Override
    public void loginFailed(String msg) {
        showShortToast(msg);
    }
}
