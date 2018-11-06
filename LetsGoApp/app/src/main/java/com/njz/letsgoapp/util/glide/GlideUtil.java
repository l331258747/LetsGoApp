package com.njz.letsgoapp.util.glide;

import android.content.Context;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.njz.letsgoapp.R;

/**
 * Created by LGQ
 * Time: 2018/7/23
 * Function: glide工具类
 */

public class GlideUtil {


    /**
     * 设置缓存策略 diskCacheStrategy
     * all:缓存源资源和转换后的资源
     * none:不作任何磁盘缓存
     * source:缓存源资源
     * result：缓存转换后的资源
     *
     * 占位图 默认图片 placeholder
     * 加载失败 error
     *
     * 设置加载动画 crossFade
     *
     * 设置加载尺寸 override
     *
     * 设置动态转换
     *  api提供了比如：centerCrop()、fitCenter()等函数也可以通过自定义Transformation，举例说明：比如一个人圆角转化器
     *
     *  缓存的动态清理
     *  Glide.get(this).clearDiskCache();//清理磁盘缓存 需要在子线程中执行
     *  Glide.get(this).clearMemory();//清理内存缓存  可以在UI主线程中进行
     *
     */

    //加载网络图片
    public static void LoadImage(Context mContext, String path,
                                 ImageView imageview) {
        if(TextUtils.isEmpty(path)) path = "";
        Glide.with(mContext).load(path).centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.SOURCE).into(imageview);
    }

    public static void LoadImageFitCenter(Context mContext, String path,
                                 ImageView imageview) {
        if(TextUtils.isEmpty(path)) path = "";
        Glide.with(mContext).load(path).fitCenter()
                .diskCacheStrategy(DiskCacheStrategy.SOURCE).into(imageview);
    }


    //加载本地图片
    public static void LoadImageWithLocation(Context mContext, Integer path,
                                             ImageView imageview) {
        Glide.with(mContext).load(path).diskCacheStrategy(DiskCacheStrategy.RESULT)
                .into(imageview);
    }

    //圆形加载
    public static void LoadCircleImage(Context mContext, String path,
                                       ImageView imageview) {
        if(TextUtils.isEmpty(path)) path = "";
        Glide.with(mContext).load(path).centerCrop().error(R.mipmap.defult_head)
                .transform(new GlideCircleTransform(mContext,2,mContext.getResources().getColor(R.color.white)))
                .diskCacheStrategy(DiskCacheStrategy.SOURCE).into(imageview);

    }

    //加载圆角图片
    public static void LoadRoundImage(Context mContext, String path,
                                       ImageView imageview,int radius) {
        if(TextUtils.isEmpty(path)) path = "";
        Glide.with(mContext).load(path).asBitmap().placeholder(R.mipmap.head_default)
                .transform(new CenterCrop(mContext),new GlideRoundTransform(mContext, radius))
                .diskCacheStrategy(DiskCacheStrategy.SOURCE).into(imageview);

    }

    //加载上边圆角图片
    public static void LoadTopRoundImage(Context mContext, String path,
                                      ImageView imageview,int radius) {
        if(TextUtils.isEmpty(path)) path = "";

        CornerTransform transformation = new CornerTransform(mContext, radius);
        //只是绘制左上角和右上角圆角
        transformation.setExceptCorner(false, false, true, true);//false表示为圆角

        Glide.with(mContext).load(path).asBitmap().placeholder(R.mipmap.head_default)
                .transform(new CenterCrop(mContext),transformation)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE).into(imageview);

    }

    public static void LoadLeftRoundImage(Context mContext, String path,
                                         ImageView imageview,int radius) {
        if(TextUtils.isEmpty(path)) path = "";

        CornerTransform transformation = new CornerTransform(mContext, radius);
        //只是绘制左上角和右上角圆角
        transformation.setExceptCorner(false, true, false, true);//false表示为圆角

        Glide.with(mContext).load(path).asBitmap().placeholder(R.mipmap.head_default)
                .transform(new CenterCrop(mContext),transformation)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE).into(imageview);

    }

    //----------默认图

    //加载网络图片
    public static void LoadDefaultImage(Context mContext, String path,
                                 ImageView imageview,int defultImg) {
        if(TextUtils.isEmpty(path)) path = "";
        Glide.with(mContext).load(path).centerCrop().placeholder(defultImg).error(defultImg)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE).into(imageview);
    }

}
