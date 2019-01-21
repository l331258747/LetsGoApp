package com.njz.letsgoapp.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.njz.letsgoapp.MyApplication;
import com.njz.letsgoapp.bean.other.WXInfoModel;
import com.njz.letsgoapp.constant.Constant;
import com.njz.letsgoapp.util.log.LogUtil;
import com.njz.letsgoapp.util.ToastUtil;
import com.njz.letsgoapp.view.login.LoginActivity;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;

import org.json.JSONException;
import org.json.JSONObject;


public class WXEntryActivity extends Activity implements IWXAPIEventHandler {

    private static final int RETURN_MSG_TYPE_LOGIN = 1;
    private static final int RETURN_MSG_TYPE_SHARE = 2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtil.e("WXEntryActivity onCreate");
        //如果没回调onResp，八成是这句没有写
        MyApplication.mWxApi.handleIntent(getIntent(), this);
    }

    // 微信发送请求到第三方应用时，会回调到该方法
    @Override
    public void onReq(BaseReq req) {
        LogUtil.e("---onReq----");
    }

    // 第三方应用发送到微信的请求处理后的响应结果，会回调到该方法
    //app发送消息给微信，处理返回消息的回调
    @Override
    public void onResp(BaseResp resp) {
        LogUtil.e("---onResp----");
        LogUtil.e("error_code:---->" + resp.errCode);
        LogUtil.e("错误码 : " + resp.errCode + "");
        int type = resp.getType(); //类型：分享还是登录
        switch (resp.errCode) {
            case BaseResp.ErrCode.ERR_AUTH_DENIED:
                //用户拒绝授权
                LogUtil.e("拒绝授权微信登录");
            case BaseResp.ErrCode.ERR_USER_CANCEL:
                //用户取消
                String message = "";
                if (type == RETURN_MSG_TYPE_LOGIN) {
                    message = "取消了微信登录";
                } else if (type == RETURN_MSG_TYPE_SHARE) {
                    message = "取消了微信分享";
                }
                LogUtil.e(message);
                break;
            case BaseResp.ErrCode.ERR_OK:
                switch (resp.getType()) {
                    case RETURN_MSG_TYPE_LOGIN:
                        LogUtil.e("微信登录成功");
                        //用户换取access_token的code，仅在ErrCode为0时有效
                        //拿到了微信返回的code,立马再去请求access_token
                        String code = ((SendAuth.Resp) resp).code;
                        LogUtil.e("code = " + code);

                        //就在这个地方，用网络库什么的或者自己封的网络api，发请求去咯，注意是get请求

                        //这里拿到了这个code，去做2次网络请求获取access_token和用户个人信息
                        getAccessToken(code);
//						finish();
//						Intent intent = new Intent(this, LoginActivity.class);
//						intent.putExtra("code", code);
//						startActivity(intent);
                        break;
                    case RETURN_MSG_TYPE_SHARE:
                        ToastUtil.showShortToast(this, "微信分享成功");
                        finish();
                        break;
                }
                break;
        }
        this.finish();
    }

    private void getAccessToken(String code) {
        //获取授权
        StringBuffer loginUrl = new StringBuffer();
        loginUrl.append("https://api.weixin.qq.com/sns/oauth2/access_token")
                .append("?appid=")
                .append(Constant.WEIXIN_APP_ID)
                .append("&secret=")
                .append(Constant.WEIXIN_APP_KRY)
                .append("&code=")
                .append(code)
                .append("&grant_type=authorization_code");
        OkHttpUtils.ResultCallback<String> resultCallback = new OkHttpUtils.ResultCallback<String>() {
            @Override
            public void onSuccess(String response) {
                String access = null;
                String openId = null;
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    access = jsonObject.getString("access_token");
                    openId = jsonObject.getString("openid");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                //获取个人信息
                String getUserInfo = "https://api.weixin.qq.com/sns/userinfo?access_token=" + access + "&openid=" + openId;
                OkHttpUtils.ResultCallback<String> reCallback = new OkHttpUtils.ResultCallback<String>() {
                    @Override
                    public void onSuccess(String responses) {
                        try {
                            WXInfoModel wxInfoModel = new WXInfoModel();
                            JSONObject jsonObject = new JSONObject(responses);
                            LogUtil.e(jsonObject.toString());
                            wxInfoModel.setRealName("");
                            wxInfoModel.setUserName(jsonObject.getString("nickname"));
                            wxInfoModel.setFaceImage(jsonObject.getString("headimgurl"));
                            wxInfoModel.setGender(jsonObject.getString("sex"));
                            wxInfoModel.setIntroduction("");
                            wxInfoModel.setPlatCode("wechat");
                            wxInfoModel.setPlatUid(jsonObject.getString("openid"));
                            wxInfoModel.setMobile("");
                            wxInfoModel.setLoginType("0");

                            finish();
                            Intent intent = new Intent(WXEntryActivity.this, LoginActivity.class);
                            intent.putExtra("code", wxInfoModel);
                            startActivity(intent);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onFailure(Exception e) {
                        Toast.makeText(WXEntryActivity.this, "登录失败", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                };
                OkHttpUtils.get(getUserInfo, reCallback);
            }

            @Override
            public void onFailure(Exception e) {
                Toast.makeText(WXEntryActivity.this, "登录失败", Toast.LENGTH_SHORT).show();
                finish();
            }
        };
        OkHttpUtils.get(loginUrl.toString(), resultCallback);
    }


}