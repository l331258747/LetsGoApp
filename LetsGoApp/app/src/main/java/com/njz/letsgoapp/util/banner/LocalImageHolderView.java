package com.njz.letsgoapp.util.banner;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.bigkoo.convenientbanner.holder.Holder;

/**
 * Created by LGQ
 * Time: 2018/8/13
 * Function:
 */

public class LocalImageHolderView<T> implements Holder<T> {

    BannerListener bannerListener;
    private ImageView imageView;
    int scaleType = -1;

    public LocalImageHolderView(BannerListener bannerListener) {
        this(bannerListener,-1);
    }

    public LocalImageHolderView(BannerListener bannerListener,int scaleType){
        this.bannerListener = bannerListener;
        this.scaleType = scaleType;
    }

    @Override
    public View createView(Context context) {
        //你可以通过layout文件来创建，不一定是Image，任何控件都可以进行翻页
        imageView = new ImageView(context);
        if(scaleType != -1){
            imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        }else{
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        }
        return imageView;
    }

    @Override
    public void UpdateUI(Context context, int position, T data) {
        bannerListener.bannerListener(context,position,data,imageView);
    }


    public interface BannerListener<T> {
        void bannerListener(Context context, int position, T data,ImageView view);
    }
}
