package com.njz.letsgoapp.util.jpushim;

import com.njz.letsgoapp.util.log.LogUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.model.Conversation;
import cn.jpush.im.android.api.model.Message;
import cn.jpush.im.api.BasicCallback;

/**
 * Created by LGQ
 * Time: 2019/1/9
 * Function:
 */

public class JpushImUtil {

    public static void login(String userId, String password) {
        JMessageClient.login(userId, password, new BasicCallback() {
            @Override
            public void gotResult(int responseCode, String responseMessage) {
                if (responseCode == 0) {
                    String username = JMessageClient.getMyInfo().getUserName();
                    String appKey = JMessageClient.getMyInfo().getAppKey();
                    UserEntry user = UserEntry.getUser(username, appKey);
                    if (null == user) {
                        user = new UserEntry(username, appKey);
                        user.save();
                    }
                    //TODO jpush 用户信息设置
//                    String nickName = MySelfInfo.getInstance().getUserNickname();
//                    UserInfo myUserInfo = JMessageClient.getMyInfo();
//                    if (myUserInfo != null) {
//                        myUserInfo.setNickname(nickName);
//                    }
                    LogUtil.e("jpushim 登陆成功");
                } else {
                    LogUtil.e("jpushim 登陆失败");
                }
            }
        });
    }

    //进入单聊
    public static void enterSingleConversation(String username) {
        JMessageClient.enterSingleConversation(username, null);
    }

    //创建单聊
    public static void createSingleConversation(String username) {
        Conversation.createSingleConversation(username, null);
    }

    //获取会话列表 返回List<Conversation>
    public static List<Conversation> getConversationList() {
        return JMessageClient.getConversationList();
    }

    //获取单个单聊会话
    public static Conversation getSingleConversation(String username) {
        return JMessageClient.getSingleConversation(username, null);
    }

    //创建文字消息
    public static Message createSingleTextMessage(String username, String text) {
        return JMessageClient.createSingleTextMessage(username, null, text);
    }

    //创建图片消息
    public static Message createSingleImageMessage(String username, File imageFile) throws FileNotFoundException {
        return JMessageClient.createSingleImageMessage(username, null, imageFile);
    }

    //创建语音消息
    public static Message createSingleVoiceMessage(String username, File voiceFile, int duration) throws FileNotFoundException {
        return JMessageClient.createSingleVoiceMessage(username, null, voiceFile, duration);
    }

    //消息发送结果监听
    public static void setOnSendCompleteCallback(Message message,BasicCallback sendCompleteCallback) {
        message.setOnSendCompleteCallback(sendCompleteCallback);
    }


    //消息发送结果监听
    public static void sendMessage(Message message) {
        JMessageClient.sendMessage(message);
    }


}
