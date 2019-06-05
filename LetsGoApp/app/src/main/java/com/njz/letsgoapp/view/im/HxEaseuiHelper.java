package com.njz.letsgoapp.view.im;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.hyphenate.EMConnectionListener;
import com.hyphenate.EMError;
import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMOptions;
import com.hyphenate.easeui.EaseUI;
import com.hyphenate.easeui.domain.EaseUser;
import com.hyphenate.easeui.model.EaseNotifier;
import com.hyphenate.easeui.utils.EaseCommonUtils;
import com.hyphenate.push.EMPushConfig;
import com.njz.letsgoapp.base.ActivityCollect;
import com.njz.letsgoapp.util.AppUtils;
import com.njz.letsgoapp.util.log.LogUtil;
import com.njz.letsgoapp.util.rxbus.RxBus2;
import com.njz.letsgoapp.util.rxbus.busEvent.NotifyEvent;
import com.njz.letsgoapp.view.homeFragment.HomeActivity;
import com.njz.letsgoapp.view.im.cache.UserCacheManager;

import java.util.List;
import java.util.Map;

/**
 * Created by LGQ
 * Time: 2019/1/23
 * Function:
 */

public class HxEaseuiHelper {
    private static HxEaseuiHelper instance = null;
    EMMessageListener messageListener;
    EaseUI easeUI;
    private Map<String, EaseUser> contactList;

    public synchronized static HxEaseuiHelper getInstance() {
        if (instance == null) {
            instance = new HxEaseuiHelper();
        }
        return instance;
    }

    public void init(Context context) {
        /**
         * SDK初始化的一些配置
         * 关于 EMOptions 可以参考官方的 API 文档
         * http://www.easemob.com/apidoc/android/chat3.0/classcom_1_1hyphenate_1_1chat_1_1_e_m_options.html
         */
        EMOptions options = new EMOptions();
        // 设置Appkey，如果配置文件已经配置，这里可以不用设置
        if (AppUtils.getVersionCodeInt() % 100 == 0) {
            options.setAppKey("1121190111010133#najiuzouim");
        } else {
            options.setAppKey("1101190116107839#najiuzou");
        }
        // 设置自动登录
        options.setAutoLogin(true);
        // 设置是否需要发送已读回执
        options.setRequireAck(true);
        // 设置是否需要发送回执，TODO 这个暂时有bug，上层收不到发送回执
        options.setRequireDeliveryAck(true);
        // 设置是否需要服务器收到消息确认
        options.setAutoTransferMessageAttachments(true);
        // 收到好友申请是否自动同意，如果是自动同意就不会收到好友请求的回调，因为sdk会自动处理，默认为true
        options.setAcceptInvitationAlways(false);
        // 设置是否自动接收加群邀请，如果设置了当收到群邀请会自动同意加入
        options.setAutoAcceptGroupInvitation(false);
        // 设置（主动或被动）退出群组时，是否删除群聊聊天记录
        options.setDeleteMessagesAsExitGroup(false);
        // 设置是否允许聊天室的Owner 离开并删除聊天室的会话
        options.allowChatroomOwnerLeave(true);
        // 设置google GCM推送id，国内可以不用设置
        // options.setGCMNumber(MLConstants.ML_GCM_NUMBER);
        // 设置集成小米推送的appid和appkey
        // options.setMipushConfig(MLConstants.ML_MI_APP_ID, MLConstants.ML_MI_APP_KEY);

        setPushConfig(context, options);

        // 调用初始化方法初始化sdk
//        EaseUI.getInstance().init(context, options);

        if (EaseUI.getInstance().init(context, options)) {

            easeUI = EaseUI.getInstance();

            initConnection();

            setEaseUIProviders();
            //设置全局监听
            setGlobalListeners();

        }
    }

    protected void setPushConfig(Context context, EMOptions options) {
        /**
         * NOTE:你需要设置自己申请的账号来使用三方推送功能，详见集成文档
         */
        EMPushConfig.Builder builder = new EMPushConfig.Builder(context);
        builder
                .enableMeiZuPush("120862", "b6b0c9e160354d4d93416fbf9089ba09")
                .enableMiPush("2882303761517874405", "5631787477405")
                .enableOppoPush("da07f5225b8f4a539eca82274a165f12", "b4fbe49d69b64bc18b9ec1495c902f88")
                .enableHWPush() // 需要在AndroidManifest.xml中配置appId //开发者需要调用该方法来开启华为推送
                ;
        //.enableFCM("921300338324")  .enableVivoPush() // 推送证书相关信息配置在AndroidManifest.xml中
        options.setPushConfig(builder.build());
    }

    protected void setGlobalListeners() {
        registerMessageListener();
    }

    public static boolean isShow = false;

    protected void registerMessageListener() {
        messageListener = new EMMessageListener() {
            @Override
            public void onMessageReceived(List<EMMessage> messages) {
                for (EMMessage message : messages) {

                    LogUtil.e("------------------------ onMessageReceived id : " + message.getMsgId());
                    UserCacheManager.save(message.ext());

                    //设置本地消息推送通知
                    if (!easeUI.hasForegroundActivies()) {
                        getNotifier().notify(message);
                    }
                    EaseUI.getInstance().getNotifier().vibrateAndPlayTone(message);
                }

                isShow = true;//初始化进入homeactivity 红点提示

                //在homeactivty，默认首页标签，消息标签页未点过，红点提示
                HomeActivity activity2 = (HomeActivity) ActivityCollect.getAppCollect().findActivity(HomeActivity.class);
                if(!activity2.getNotifyFragment().isMessageListInited()){
                    RxBus2.getInstance().post(new NotifyEvent(true));
                }
            }

            @Override
            public void onCmdMessageReceived(List<EMMessage> messages) {
            }

            @Override
            public void onMessageRead(List<EMMessage> messages) {
            }

            @Override
            public void onMessageDelivered(List<EMMessage> message) {
            }

            @Override
            public void onMessageRecalled(List<EMMessage> list) {

            }

            @Override
            public void onMessageChanged(EMMessage message, Object change) {

            }
        };

        EMClient.getInstance().chatManager().addMessageListener(messageListener);
    }

    private void initConnection() {
        EMConnectionListener connectionListener = new EMConnectionListener() {
            @Override
            public void onConnected() {
                Log.e("im", "环信 连接成功");
            }

            @Override
            public void onDisconnected(int i) {
                if (i == EMError.USER_REMOVED) {
                    Log.e("im", "环信 账号被移除");
                } else if (i == EMError.USER_LOGIN_ANOTHER_DEVICE) {
                    Log.e("im", "环信 账号在别的设备登录");
                }

            }
        };
        //注册连接监听
        EMClient.getInstance().addConnectionListener(connectionListener);
    }

    protected void setEaseUIProviders() {
        // set profile provider if you want easeUI to handle avatar and nickname
        EaseUI.getInstance().setUserProfileProvider(new EaseUI.EaseUserProfileProvider() {

            @Override
            public EaseUser getUser(String username) {
                return getUserInfo(username);
            }
        });
    }

    private EaseUser getUserInfo(String username) {

        EaseUser user = UserCacheManager.getEaseUser(username);

        //如果用户不是你的联系人，则进行初始化
        if (user == null) {
            user = new EaseUser(username);
            EaseCommonUtils.setUserInitialLetter(user);
        } else {
            if (TextUtils.isEmpty(user.getNickname())) {//如果名字为空，则显示环信号码
                user.setNickname(user.getUsername());
            }
        }

        return user;
    }

    public EaseNotifier getNotifier() {
        return EaseUI.getInstance().getNotifier();
    }

}
