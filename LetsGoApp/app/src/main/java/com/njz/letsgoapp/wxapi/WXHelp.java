package com.njz.letsgoapp.wxapi;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.njz.letsgoapp.MyApplication;
import com.njz.letsgoapp.R;
import com.njz.letsgoapp.util.ToastUtil;
import com.njz.letsgoapp.util.log.LogUtil;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

/**
 * Created by liguoqiang on 2018/1/4.
 * 微信类
 */

public class WXHelp {

    private static WXHelp wxHelp;

    private WXHelp() {
    }

    public static synchronized WXHelp getInstance() {
        if (null == wxHelp) {
            wxHelp = new WXHelp();
        }
        return wxHelp;
    }


    // flag 0好友，1朋友圈
    public void wxShare(Context context, int flag, String title, String content, String imageUrl, String url) {
        LogUtil.e("wxShare url:" + url);
        if (!MyApplication.mWxApi.isWXAppInstalled()) {
            ToastUtil.showShortToast(context, "您还未安装微信客户端");
            return;
        }

        WXWebpageObject webPage = new WXWebpageObject();
        webPage.webpageUrl = url;
        WXMediaMessage msg = new WXMediaMessage(webPage);
        msg.title = title;
        msg.description = content;
        if(imageUrl!=null && !imageUrl.equals("")) {
            //图片加载是使用的ImageLoader.loadImageSync() 同步方法
            //并且还要创建图片的缩略图，因为微信限制了图片的大小
            Bitmap thumbBmp = Bitmap.createScaledBitmap(GetLocalOrNetBitmap(imageUrl), 200, 200, true);
            msg.setThumbImage(thumbBmp);
            thumbBmp.recycle();
        }else{
            Bitmap thumb = BitmapFactory.decodeResource(context.getResources(), R.mipmap.share_logo);
            msg.setThumbImage(thumb);
            thumb.recycle();
        }
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = String.valueOf(System.currentTimeMillis()); // transaction字段用于唯一标识一个请求
        req.message = msg;

        req.scene = flag == 0 ? SendMessageToWX.Req.WXSceneSession : SendMessageToWX.Req
                .WXSceneTimeline;
        // 调用api接口发送数据到微信
        MyApplication.mWxApi.sendReq(req);
    }

    /**
     * 把网络资源图片转化成bitmap
     * @param url  网络资源图片
     * @return  Bitmap
     */
    public static Bitmap GetLocalOrNetBitmap(String url) {
        Bitmap bitmap = null;
        InputStream in = null;
        BufferedOutputStream out = null;
        try {
            in = new BufferedInputStream(new URL(url).openStream(), 1024);
            final ByteArrayOutputStream dataStream = new ByteArrayOutputStream();
            out = new BufferedOutputStream(dataStream, 1024);
            copy(in, out);
            out.flush();
            byte[] data = dataStream.toByteArray();
            bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
            data = null;
            return bitmap;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    private static void copy(InputStream in, OutputStream out)
            throws IOException {
        byte[] b = new byte[1024];
        int read;
        while ((read = in.read(b)) != -1) {
            out.write(b, 0, read);
        }
    }

}
