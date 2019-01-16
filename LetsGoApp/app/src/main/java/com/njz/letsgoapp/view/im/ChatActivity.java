package com.njz.letsgoapp.view.im;

import android.content.pm.PackageManager;
import android.support.annotation.NonNull;

import com.hyphenate.easeui.ui.EaseChatFragment;
import com.hyphenate.easeui.widget.EaseChatPrimaryMenu;
import com.njz.letsgoapp.R;
import com.njz.letsgoapp.base.BaseActivity;
import com.njz.letsgoapp.util.PermissionUtil;

/**
 * Created by LGQ
 * Time: 2019/1/15
 * Function:
 */

public class ChatActivity extends BaseActivity {
    @Override
    public int getLayoutId() {
        return R.layout.activity_chat;
    }

    @Override
    public void initView() {
        hideTitleLayout();

        EaseChatFragment easeChatFragment = new EaseChatFragment();  //环信聊天界面
        easeChatFragment.setArguments(getIntent().getExtras()); //需要的参数
        getSupportFragmentManager().beginTransaction().add(R.id.layout_chat,easeChatFragment).commit();  //Fragment切换
    }

    @Override
    public void initData() {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == EaseChatPrimaryMenu.PERMISSION_AUDIO_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_DENIED) {//选择了不再提示按钮
                PermissionUtil.getInstance().showAccreditDialog(context, "温馨提示\n" +
                        "您需要同意那就走使用【录音】权限才能正常发送语音，" +
                        "由于您选择了【禁止（不再提示）】，将导致无法发送语音，" +
                        "需要到设置页面手动授权开启【麦克风】权限，才能发送语音。");
                return;
            }
        }
    }
}
