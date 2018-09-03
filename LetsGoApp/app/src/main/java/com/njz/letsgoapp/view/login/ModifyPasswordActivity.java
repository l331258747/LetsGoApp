package com.njz.letsgoapp.view.login;

import android.support.v4.content.ContextCompat;
import android.text.InputType;
import android.view.View;
import android.widget.TextView;

import com.njz.letsgoapp.R;
import com.njz.letsgoapp.base.BaseActivity;
import com.njz.letsgoapp.bean.EmptyModel;
import com.njz.letsgoapp.bean.MySelfInfo;
import com.njz.letsgoapp.mvp.login.ModifyPasswordContract;
import com.njz.letsgoapp.mvp.login.ModifyPasswordPresenter;
import com.njz.letsgoapp.util.LoginUtil;
import com.njz.letsgoapp.widget.LoginItemView2;

/**
 * Created by LGQ
 * Time: 2018/8/10
 * Function:
 */

public class ModifyPasswordActivity extends BaseActivity implements View.OnClickListener,ModifyPasswordContract.View {

    LoginItemView2 loginViewPasswordOld,loginViewPassword,loginViewPasswordAgin;
    TextView btnSubmit;

    ModifyPasswordPresenter mPresenter;

    boolean isSeeLoginViewPasswordOld;
    boolean isSeeLoginViewPassword;
    boolean isSeeLoginViewPasswordAgin;

    @Override
    public int getLayoutId() {
        return R.layout.activity_modify_password;
    }

    @Override
    public void initView() {
//        showLeftAndTitle("修改密码");
        showLeftIcon();
        loginViewPasswordOld = $(R.id.login_view_password_old);
        loginViewPasswordOld.setEtInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        loginViewPassword = $(R.id.login_view_password);
        loginViewPassword.setEtInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        loginViewPasswordAgin = $(R.id.login_view_password_agin);
        loginViewPasswordAgin.setEtInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);

        btnSubmit = $(R.id.btn_submit);
        btnSubmit.setOnClickListener(this);

        loginViewPasswordOld.setRightImgOnClickLisener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setEtState(loginViewPasswordOld, isSeeLoginViewPasswordOld);
                isSeeLoginViewPasswordOld = !isSeeLoginViewPasswordOld;
            }
        });
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
        mPresenter = new ModifyPasswordPresenter(context,this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_submit:
                if (!LoginUtil.verifyPassword(loginViewPasswordOld.getEtContent()))
                    return;
                if (!LoginUtil.verifyPassword(loginViewPassword.getEtContent()))
                    return;
                if (!LoginUtil.verifyPasswordDouble(loginViewPassword.getEtContent(), loginViewPasswordAgin.getEtContent()))
                    return;
                mPresenter.changePwd(MySelfInfo.getInstance().getUserToken(),loginViewPasswordOld.getEtContent(),loginViewPassword.getEtContent());
                break;
        }
    }

    @Override
    public void changePwdSuccess(EmptyModel str) {
        showShortToast("修改成功");
        finish();
    }

    @Override
    public void changePwdFailed(String msg) {
        showShortToast(msg);
    }
}
