package com.njz.letsgoapp.view.im;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hyphenate.chat.EMChatManager;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMTextMessageBody;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.ui.EaseChatFragment;
import com.hyphenate.easeui.widget.chatrow.EaseCustomChatRowProvider;
import com.hyphenate.exceptions.HyphenateException;
import com.njz.letsgoapp.bean.MySelfInfo;
import com.njz.letsgoapp.bean.other.IMUserModel;
import com.njz.letsgoapp.constant.Constant;
import com.njz.letsgoapp.util.DateUtil;
import com.njz.letsgoapp.util.http.MethodApi;
import com.njz.letsgoapp.util.http.OnSuccessAndFaultSub;
import com.njz.letsgoapp.util.http.ResponseCallback;
import com.njz.letsgoapp.util.log.LogUtil;
import com.njz.letsgoapp.view.home.GuideDetailActivity;
import com.njz.letsgoapp.view.im.cache.UserCacheManager;

import java.util.List;
import java.util.Map;

/**
 * Created by LGQ
 * Time: 2019/1/23
 * Function:
 */

public class MyEaseChatFragment extends EaseChatFragment implements EaseChatFragment.EaseChatFragmentHelper {

//    private boolean isRobot;
    private boolean isRobot = true;
    String name;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    protected void setUpView() {
        setChatFragmentHelper(this);
//        if (chatType == EaseConstant.CHATTYPE_SINGLE) {
//            Map<String, RobotUser> robotMap = HxEaseuiHelper.getInstance().getRobotList();
//            if (robotMap != null && robotMap.containsKey(toChatUsername)) {
//                isRobot = true;
//            }
//        }

        if(UserCacheManager.notExistedOrExpired(toChatUsername)){
            ResponseCallback listener = new ResponseCallback<IMUserModel>() {
                @Override
                public void onSuccess(IMUserModel datas) {
                    if (datas == null) return;
                    UserCacheManager.save(datas.getImId(), datas.getName(), datas.getUserImg());
                    titleBar.setTitle(datas.getName());

                }

                @Override
                public void onFault(String errorMsg) {
                    LogUtil.e(errorMsg);
                }
            };
            MethodApi.getUserByIMUsername(toChatUsername, new OnSuccessAndFaultSub(listener, null, false));
        }

        super.setUpView();

        setWelcomMsg();
    }


    public void setWelcomMsg(){

        String msgtime0 = DateUtil.longToStr(System.currentTimeMillis(),"yyyyMMddHH");
        String msgtime;

        name = fragmentArgs.getString(EaseConstant.EXTRA_USER_NAME, "");

        List<EMMessage> msgs = conversation.getAllMessages();
        for (EMMessage msg : msgs){
            msgtime = DateUtil.longToStr(msg.getMsgTime(),"yyyyMMddHH");
            String attributeId = msg.getStringAttribute("attribute_id","");

            if(TextUtils.equals(attributeId,Constant.IM_WELCOME+toChatUsername) && Integer.valueOf(msgtime0) - Integer.valueOf(msgtime) < 24){
                return;
            }
        }

        EMMessage emMessage = EMMessage.createReceiveMessage(EMMessage.Type.TXT);
        emMessage.setFrom(toChatUsername);//发送人
        emMessage.addBody(new EMTextMessageBody("您好，我是当地向导" + name + "，很高兴为您服务!"));//创建消息
        emMessage.setUnread(true);//是否已读
        emMessage.setChatType(EMMessage.ChatType.Chat);//聊天类型
        emMessage.setMsgTime(System.currentTimeMillis());//消息时间
        emMessage.setTo(MySelfInfo.getInstance().getImId());//发送给
        emMessage.setAttribute("attribute_id",Constant.IM_WELCOME+toChatUsername);

        EMClient.getInstance().chatManager().saveMessage(emMessage);
    }

    @Override
    public void onSetMessageAttributes(EMMessage message) {
        if (isRobot) {
            //set message extension
            message.setAttribute("em_robot_message", isRobot);
        }
        //设置要发送扩展消息用户昵称
//        message.setAttribute(Constant.IM_NICKNAME, MySelfInfo.getInstance().getUserNickname());
//        message.setAttribute(Constant.IM_HEADIMG, MySelfInfo.getInstance().getUserImgUrl());

        UserCacheManager.setMsgExt(message);
    }

    @Override
    public void onEnterToChatDetails() {

    }

    @Override
    public void onAvatarClick(String username) {
        if(TextUtils.equals(username,MySelfInfo.getInstance().getImId())
                || TextUtils.isEmpty(username))
            return;
        Intent intent = new Intent(getContext(), GuideDetailActivity.class);
        int guideId = Integer.valueOf(username.split("_")[1]);
        intent.putExtra(GuideDetailActivity.GUIDEID, guideId);
        getContext().startActivity(intent);
    }

    @Override
    public void onAvatarLongClick(String username) {

    }

    @Override
    public boolean onMessageBubbleClick(EMMessage message) {
        return false;
    }

    @Override
    public void onMessageBubbleLongClick(EMMessage message) {

    }

    @Override
    public boolean onExtendMenuItemClick(int itemId, View view) {
        return false;
    }

    @Override
    public EaseCustomChatRowProvider onSetCustomChatRowProvider() {
        return null;
    }
}
