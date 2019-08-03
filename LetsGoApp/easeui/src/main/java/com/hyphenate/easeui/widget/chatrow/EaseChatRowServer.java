package com.hyphenate.easeui.widget.chatrow;

import android.content.Context;
import android.text.TextUtils;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.R;
import com.hyphenate.exceptions.HyphenateException;

/**
 * Created by liguoqiang on 2019/8/3.
 */

public class EaseChatRowServer extends EaseChatRow {

    ImageView iv_img;
    TextView tv_location,tv_title,priceView;

    public EaseChatRowServer(Context context, EMMessage message, int position, BaseAdapter adapter) {
        super(context, message, position, adapter);
    }

    @Override
    protected void onInflateView() {
        inflater.inflate(message.direct() == EMMessage.Direct.RECEIVE ? R.layout.ease_row_received_server : R.layout.ease_row_sent_server,this);
    }

    @Override
    protected void onFindViewById() {
        iv_img = (ImageView) findViewById(R.id.iv_img);
        tv_location = (TextView) findViewById(R.id.tv_location);
        tv_title = (TextView) findViewById(R.id.tv_title);
        priceView = (TextView) findViewById(R.id.priceView);
    }

    @Override
    protected void onViewUpdate(EMMessage msg) {
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onSetUpView() {
        try {

            int serverId = message.getIntAttribute("server_id");
            String serverName = message.getStringAttribute("server_name");
            String ServerArea = message.getStringAttribute("server_area");
            String serverImg = message.getStringAttribute("server_img");
            String serverPrice = message.getStringAttribute("server_price");



            tv_location.setText(ServerArea);
            tv_title.setText(serverName);
            priceView.setText("￥"+serverPrice);

            if(!TextUtils.isEmpty(serverImg)){
                Glide.with(context).load(serverImg).centerCrop()
                        .diskCacheStrategy(DiskCacheStrategy.SOURCE).into(iv_img);
            }else{
                iv_img.setImageDrawable(getResources().getDrawable(R.drawable.ease_chat_image_normal));
            }


        } catch (HyphenateException e) {
            e.printStackTrace();

        }
    }
}
