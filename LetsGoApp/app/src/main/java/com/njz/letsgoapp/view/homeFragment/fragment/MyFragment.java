package com.njz.letsgoapp.view.homeFragment.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.njz.letsgoapp.R;
import com.njz.letsgoapp.base.BaseFragment;
import com.njz.letsgoapp.bean.MySelfInfo;
import com.njz.letsgoapp.dialog.DialogUtil;
import com.njz.letsgoapp.util.StringUtils;
import com.njz.letsgoapp.util.glide.GlideUtil;
import com.njz.letsgoapp.view.login.LoginActivity;
import com.njz.letsgoapp.view.login.ModifyPasswordActivity;
import com.njz.letsgoapp.view.login.ModifyPhoneActivity;
import com.njz.letsgoapp.view.mine.FansListActivity;
import com.njz.letsgoapp.view.mine.MyCommentActivity;
import com.njz.letsgoapp.view.mine.MyInfoActivity;
import com.njz.letsgoapp.view.mine.SpaceActivity;
import com.njz.letsgoapp.view.mine.SystemSettingActivity;
import com.njz.letsgoapp.widget.MineItemView2;

/**
 * Created by LGQ
 * Time: 2018/7/23
 * Function:
 */

public class MyFragment extends BaseFragment implements View.OnClickListener {

    MineItemView2 mine_bind, mine_info, mine_modify, mine_comment, mine_custom, mine_setting;

    ImageView iv_head;
    TextView tv_name,tv_follow,tv_fans,tv_login,tv_space;

    LinearLayout ll_info;


    @Override
    public int getLayoutId() {
        return R.layout.fragment_mine;
    }

    @Override
    public void initView() {
        iv_head = $(R.id.iv_head);
        tv_name = $(R.id.tv_name);

        mine_bind = $(R.id.mine_bind);
        mine_info = $(R.id.mine_info);
        mine_modify = $(R.id.mine_modify);
        mine_comment = $(R.id.mine_comment);
        mine_custom = $(R.id.mine_customer);
        mine_setting = $(R.id.mine_setting);
        tv_fans = $(R.id.tv_fans);
        tv_follow = $(R.id.tv_follow);
        ll_info = $(R.id.ll_info);
        tv_login = $(R.id.tv_login);

        tv_space = $(R.id.tv_space);

        mine_bind.setOnClickListener(this);
        mine_info.setOnClickListener(this);
        mine_modify.setOnClickListener(this);
        mine_comment.setOnClickListener(this);
        mine_setting.setOnClickListener(this);
        mine_custom.setOnClickListener(this);
        tv_fans.setOnClickListener(this);
        tv_follow.setOnClickListener(this);
        iv_head.setOnClickListener(this);
        tv_login.setOnClickListener(this);
        tv_space.setOnClickListener(this);

    }

    @Override
    public void onResume() {
        super.onResume();
        setLoginState();
    }

    public void setLoginState(){
        if (MySelfInfo.getInstance().isLogin()) {//登录状态
            ll_info.setVisibility(View.VISIBLE);
            tv_login.setVisibility(View.GONE);

            tv_name.setText(MySelfInfo.getInstance().getUserNickname());
            StringUtils.setHtml(tv_fans,String.format(getResources().getString(R.string.mine_fans),MySelfInfo.getInstance().getUserFocus()));
            StringUtils.setHtml(tv_follow,String.format(getResources().getString(R.string.mine_follow),MySelfInfo.getInstance().getUserFans()));
            mine_bind.setContent(StringUtils.hidePhone(MySelfInfo.getInstance().getUserMoble()));

            GlideUtil.LoadCircleImage(context, MySelfInfo.getInstance().getUserImgUrl(), iv_head);

        } else {
            ll_info.setVisibility(View.GONE);
            tv_login.setVisibility(View.VISIBLE);

            mine_bind.setContent("");
            GlideUtil.LoadCircleImage(context, "", iv_head);
        }
    }

    @Override
    public void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_login:
                startActivity(new Intent(context,LoginActivity.class));
                break;
            case R.id.mine_bind:
                if(!isLogin()) return;
                startActivity(new Intent(context,ModifyPhoneActivity.class));
                break;
            case R.id.mine_info:
                if(!isLogin()) return;
                startActivity(new Intent(context,MyInfoActivity.class));
                break;
            case R.id.mine_modify:
                if(!isLogin()) return;
                startActivity(new Intent(context,ModifyPasswordActivity.class));
                break;
            case R.id.mine_comment:
                if(!isLogin()) return;
                startActivity(new Intent(context, MyCommentActivity.class));
                break;
            case R.id.mine_customer:
                DialogUtil.getInstance().getDefaultDialog(context, "提示", "13211111111", "呼叫", new DialogUtil.DialogCallBack() {
                    @Override
                    public void exectEvent(DialogInterface alterDialog) {
                        Intent dialIntent =  new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + "13211111111"));
                        startActivity(dialIntent);
                        alterDialog.dismiss();
                    }
                }).show();

                break;
            case R.id.mine_setting:
                startActivity(new Intent(context,SystemSettingActivity.class));
                break;
            case R.id.tv_fans:
                if(!isLogin()) return;
                Intent intentFans = new Intent(context,FansListActivity.class);
                intentFans.putExtra(FansListActivity.TITLE, "我的粉丝");
                intentFans.putExtra(FansListActivity.TYPE,0);
                intentFans.putExtra(FansListActivity.USER_ID,MySelfInfo.getInstance().getUserId());
                startActivity(intentFans);
                break;
            case R.id.tv_follow:
                if(!isLogin()) return;
                Intent intentFollow = new Intent(context,FansListActivity.class);
                intentFollow.putExtra(FansListActivity.TITLE, "我的关注");
                intentFollow.putExtra(FansListActivity.TYPE,1);
                intentFollow.putExtra(FansListActivity.USER_ID,MySelfInfo.getInstance().getUserId());
                startActivity(intentFollow);
                break;
            case R.id.tv_space:
                if(!isLogin()) return;
                Intent intentSpace = new Intent(context, SpaceActivity.class);
                intentSpace.putExtra(SpaceActivity.USER_ID,MySelfInfo.getInstance().getUserId());
                startActivity(intentSpace);
                break;

        }
    }

    public boolean isLogin(){
        if (MySelfInfo.getInstance().isLogin()) {//登录状态
            return true;
        }
        startActivity(new Intent(context,LoginActivity.class));
        return false;
    }
}
