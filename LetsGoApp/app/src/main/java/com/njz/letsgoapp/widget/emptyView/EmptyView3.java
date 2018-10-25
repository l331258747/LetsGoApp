package com.njz.letsgoapp.widget.emptyView;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.njz.letsgoapp.R;

/**
 * Created by LGQ
 * Time: 2018/10/23
 * Function:
 */

public class EmptyView3 extends RelativeLayout {

    Context context;
    ImageView iv_empty_img;
    TextView tv_empty_content;

    public EmptyView3(Context context) {
        this(context,null);
    }

    public EmptyView3(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public EmptyView3(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        View view = LayoutInflater.from(context).inflate(R.layout.layout_empty3, this, true);

        this.context = context;

        iv_empty_img = findViewById(R.id.iv_empty_img);
        tv_empty_content = findViewById(R.id.tv_empty_content);

    }


    public void setVisible(boolean visible){
        this.setVisibility(visible?VISIBLE:GONE);
    }

    public void setEmptyData(int imgId,String content){
        setImg(imgId);
        setContent(content);
    }

    private void setImg(int resourceId){
        iv_empty_img.setImageDrawable(ContextCompat.getDrawable(context,resourceId));
    }
    private void setContent(String str){
        tv_empty_content.setText(str);
    }



    EmptyClickLisener btnClickLisener;
    public void setBtnClickLisener(EmptyClickLisener btnClickLisener){
        this.btnClickLisener = btnClickLisener;
    }


}
