package com.njz.letsgoapp.wxapi;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.njz.letsgoapp.MyApplication;
import com.njz.letsgoapp.util.LogUtil;
import com.njz.letsgoapp.util.ToastUtil;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;


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
				if (type == RETURN_MSG_TYPE_SHARE) {
					message = "取消了微信分享";
				}
				LogUtil.e(message);
				break;
			case BaseResp.ErrCode.ERR_OK:
				switch (resp.getType()) {
					case RETURN_MSG_TYPE_SHARE:
						ToastUtil.showShortToast(this, "微信分享成功");
						finish();
						break;
				}
				break;
		}
		this.finish();
	}
}