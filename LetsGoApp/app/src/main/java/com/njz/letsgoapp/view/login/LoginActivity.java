package com.njz.letsgoapp.view.login;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.njz.letsgoapp.R;
import com.njz.letsgoapp.base.BaseActivity;
import com.njz.letsgoapp.bean.login.LoginModel;
import com.njz.letsgoapp.mvp.login.LoginContract;
import com.njz.letsgoapp.mvp.login.LoginPresenter;
import com.njz.letsgoapp.util.AppUtils;
import com.njz.letsgoapp.util.LoginUtil;
import com.njz.letsgoapp.view.homeFragment.HomeActivity;
import com.njz.letsgoapp.widget.LoginItemView;

/**
 * Created by LGQ
 * Time: 2018/8/10
 * Function:
 */

public class LoginActivity extends BaseActivity implements View.OnClickListener,LoginContract.View{

    LoginItemView loginViewPhone, loginViewPassword;
    Button btnLogin;
    TextView tvForget, tvVerifyLogin;

    LoginPresenter mPresenter;


    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void initView() {
        showLeftIcon();
        showRightTv();
        getRightTv().setText("注册");
        getRightTv().setOnClickListener(this);
        getRightTv().setTextColor(ContextCompat.getColor(AppUtils.getContext(), R.color.colorPrimary));


        loginViewPhone = $(R.id.login_view_phone);
        loginViewPhone.setEtInputType(InputType.TYPE_CLASS_NUMBER);
        loginViewPassword = $(R.id.login_view_password);
        loginViewPassword.setEtInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);

        btnLogin = $(R.id.btn_login);
        tvForget = $(R.id.tv_forgett);
        tvVerifyLogin = $(R.id.tv_verify_login);

        btnLogin.setOnClickListener(this);
        tvForget.setOnClickListener(this);
        tvVerifyLogin.setOnClickListener(this);


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
            case R.id.tv_forgett:
                showShortToast("忘记密码");
                startActivity(new Intent(context, ForgetActivity.class));
                break;
            case R.id.tv_verify_login:
                showShortToast("验证码登陆");
                startActivity(new Intent(context, VerifyLoginActivity.class));
                break;
            case R.id.right_tv:
                showShortToast("注册");
                startActivity(new Intent(context, RegistActivity.class));
                break;
        }
    }

    @Override
    public void loginSeccess(LoginModel loginModel) {

    }

    @Override
    public void loginFailed(String msg) {
        showShortToast(msg);
    }
}
