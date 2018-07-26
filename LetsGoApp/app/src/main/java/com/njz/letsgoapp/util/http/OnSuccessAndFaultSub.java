package com.njz.letsgoapp.util.http;

import android.content.Context;
import android.util.Log;

import com.njz.letsgoapp.util.log.LogUtil;
import com.njz.letsgoapp.util.dialog.LoadingDialog;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import javax.net.ssl.SSLHandshakeException;

import io.reactivex.annotations.NonNull;
import io.reactivex.observers.DisposableObserver;
import retrofit2.HttpException;

/**
 * Created by LGQ
 * Time: 2018/7/18
 * Function: DisposableObserver
 */

public class OnSuccessAndFaultSub<T> extends DisposableObserver<T> implements ProgressCancelListener {


    /**
     * 是否需要显示默认Loading
     */
    private boolean showProgress = true;
    private ResponseCallback mResponseCallback;

    private Context context;
    private LoadingDialog progressDialog;

    /**
     * 无对话框
     * @param mResponseCallback 成功回调监听
     */
    public OnSuccessAndFaultSub(ResponseCallback mResponseCallback) {
        this.mResponseCallback = mResponseCallback;
        showProgress = false;
    }

    /**
     * 有对话框
     * @param mResponseCallback 成功回调监听
     * @param context                    上下文
     */
    public OnSuccessAndFaultSub(ResponseCallback mResponseCallback, Context context) {
        this.mResponseCallback = mResponseCallback;
        this.context = context;
        showProgress = true;
        progressDialog = new LoadingDialog(context);
    }

    private void showProgressDialog() {
        if (showProgress && null != progressDialog) {
            progressDialog.show();
        }
    }

    private void dismissProgressDialog() {
        if (showProgress && null != progressDialog) {
            progressDialog.dismiss();
        }
    }

    /**
     * 订阅开始时调用
     * 显示ProgressDialog
     */
    @Override
    public void onStart() {
        showProgressDialog();
    }

    /**
     * 完成，隐藏ProgressDialog
     */
    @Override
    public void onComplete() {
        dismissProgressDialog();
        progressDialog = null;
    }

    @Override
    public void onNext(@NonNull T t) {
        mResponseCallback.onSuccess(t);
    }

    /**
     * 对错误进行统一处理
     * 隐藏ProgressDialog
     */
    @Override
    public void onError(Throwable e) {
        try {

            if (e instanceof SocketTimeoutException) {//请求超时
                mResponseCallback.onFault("网络连接超时");
            } else if (e instanceof ConnectException) {//网络连接超时
                mResponseCallback.onFault("网络连接失败");
            } else if (e instanceof SSLHandshakeException) {//安全证书异常
                mResponseCallback.onFault("安全证书异常");
            } else if (e instanceof HttpException) {//请求的地址不存在
                int code = ((HttpException) e).code();
                if (code == 504) {
                    mResponseCallback.onFault("网络异常，请检查您的网络状态");
                } else if (code == 404) {
                    mResponseCallback.onFault("请求的地址不存在");
                } else {
                    mResponseCallback.onFault("请求失败");
                }
            } else if (e instanceof UnknownHostException) {//域名解析失败
                mResponseCallback.onFault("域名解析失败");
            } else {
                mResponseCallback.onFault("error:" + e.getMessage());
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        } finally {
            Log.e("OnSuccessAndFaultSub", "error:" + e.getMessage());
            dismissProgressDialog();
            progressDialog = null;
        }
    }

    public String decompress(InputStream inputStream){
        String result = "";
        try {
            StringBuilder sb = new StringBuilder();
            String line;

            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            result = sb.toString();
        }catch (Exception e) {
            e.printStackTrace();
        }
        LogUtil.e(result);

        return result;
    }

    /**
     * 当result等于1回调给调用者，否则自动显示错误信息，若错误信息为401跳转登录页面。
     * ResponseBody  body = response.body();//获取响应体
     * InputStream inputStream = body.byteStream();//获取输入流
     * byte[] bytes = body.bytes();//获取字节数组
     * String str = body.string();//获取字符串数据
     */
//    @Override
//    public void onNext(BaseResponse body) {
//        try {
//            final String result = decompress(body.byteStream());
//            LogUtil.e(result);
//            mResponseCallback.onSuccess(result);
//            JSONObject jsonObject = new JSONObject(result);
//            int resultCode = jsonObject.getInt("ErrorCode");
//            if (resultCode == 1) {
//                mResponseCallback.onSuccess(result);
//            } else {
//                String errorMsg = jsonObject.getString("ErrorMessage");
//                mResponseCallback.onFault(errorMsg);
//                Log.e("OnSuccessAndFaultSub", "errorMsg: " + errorMsg);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        if(body.getCode()==1){
//            mResponseCallback.onSuccess(body);
//        }else{
//            mResponseCallback.onFault(body.getMsg());
//        }
//    }

    /**
     * 取消ProgressDialog的时候，取消对observable的订阅，同时也取消了http请求
     */
    @Override
    public void onCancelProgress() {
        if (!this.isDisposed()) {
            this.dispose();
        }
    }
}
