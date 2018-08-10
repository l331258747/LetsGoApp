package com.njz.letsgoapp.view.login;

import android.text.InputType;
import android.view.View;
import android.widget.Button;

import com.njz.letsgoapp.R;
import com.njz.letsgoapp.base.BaseActivity;
import com.njz.letsgoapp.util.LoginUtil;
import com.njz.letsgoapp.widget.LoginItemView;

/**
 * Created by LGQ
 * Time: 2018/8/10
 * Function:
 */

public class ModifyPasswordActivity extends BaseActivity implements View.OnClickListener {

    LoginItemView loginViewPasswordOld,loginViewPassword,loginViewPasswordAgin;
    Button btnSubmit;

    @Override
    public int getLayoutId() {
        return R.layout.activity_modify_password;
    }

    @Override
    public void initView() {
        showLeftIcon();
        loginViewPasswordOld = $(R.id.login_view_password_old);
        loginViewPasswordOld.setEtInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        loginViewPassword = $(R.id.login_view_password);
        loginViewPassword.setEtInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        loginViewPasswordAgin = $(R.id.login_view_password_agin);
        loginViewPasswordAgin.setEtInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);

        btnSubmit = $(R.id.btn_submit);
        btnSubmit.setOnClickListener(this);


    }

    @Override
    public void initData() {

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
                showShortToast("修改成功");
                break;
        }
    }
}
