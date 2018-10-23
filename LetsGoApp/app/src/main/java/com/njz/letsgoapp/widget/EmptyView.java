package com.njz.letsgoapp.widget;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.njz.letsgoapp.R;

/**
 * Created by LGQ
 * Time: 2018/10/23
 * Function:
 */

public class EmptyView extends RelativeLayout {

    Context context;
    ImageView iv_empty_img;
    TextView tv_empty_content,tv_empty_content_2,tv_empty_btn;

    public EmptyView(Context context) {
        this(context,null);
    }

    public EmptyView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public EmptyView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        View view = LayoutInflater.from(context).inflate(R.layout.layout_empty, this, true);

        this.context = context;

        iv_empty_img = findViewById(R.id.iv_empty_img);
        tv_empty_content = findViewById(R.id.tv_empty_content);
        tv_empty_content_2 = findViewById(R.id.tv_empty_content_2);
        tv_empty_btn = findViewById(R.id.tv_empty_btn);

    }


    public void setVisible(boolean visible){
        this.setVisibility(visible?VISIBLE:GONE);
    }

    public void setEmptyData(int imgId,String content){
        this.setEmptyData(imgId,content,null,null);
    }

    public void setEmptyData(int imgId,String content,String content2){
        this.setEmptyData(imgId,content,content2,null);
    }

    public void setEmptyData(int imgId,String content,String content2,String btnStr){
        setImg(imgId);
        setContent(content);
        if(TextUtils.isEmpty(content2)){
            tv_empty_content_2.setVisibility(GONE);
        }else{
            setContent2(content2);
        }
        if(TextUtils.isEmpty(btnStr)){
            tv_empty_btn.setVisibility(GONE);
        }else{
            setBtn(btnStr);
        }
    }

    private void setImg(int resourceId){
        iv_empty_img.setImageDrawable(ContextCompat.getDrawable(context,resourceId));
    }
    private void setContent(String str){
        tv_empty_content.setText(str);
    }
    private void setContent2(String str){
        tv_empty_content_2.setVisibility(VISIBLE);
        tv_empty_content_2.setText(str);
    }

    private void setBtn(String str){
        tv_empty_btn.setVisibility(VISIBLE);
        tv_empty_btn.setText(str);

        tv_empty_btn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(btnClickLisener == null) return;
                btnClickLisener.onClick();
            }
        });
    }


    BtnClickLisener btnClickLisener;
    public interface BtnClickLisener {
        void onClick();
    }
    public void setBtnClickLisener(BtnClickLisener btnClickLisener){
        this.btnClickLisener = btnClickLisener;
    }


}
