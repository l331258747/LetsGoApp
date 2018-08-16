package com.njz.letsgoapp.view.home;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.njz.letsgoapp.R;
import com.njz.letsgoapp.base.BaseActivity;
import com.njz.letsgoapp.util.log.LogUtil;
import com.njz.letsgoapp.widget.flowlayout.FlowLayout;
import com.njz.letsgoapp.widget.flowlayout.TagAdapter;
import com.njz.letsgoapp.widget.flowlayout.TagFlowLayout;

import java.util.Set;

/**
 * Created by LGQ
 * Time: 2018/8/16
 * Function:
 */

public class CommentDetailActivity extends BaseActivity {

    TagFlowLayout mFlowLayout;
    private String[] mVals = new String[]
            {"全部(100)", "向导陪游(100)", "私人定制(100)", "车导服务(100)", "代订酒店(100)", "景点门票(100)"};


    @Override
    public int getLayoutId() {
        return R.layout.activity_comment_detail;
    }

    @Override
    public void initView() {
        showLeftAndTitle("全部评论");

        mFlowLayout = $(R.id.id_flowlayout);

        final LayoutInflater mInflater = LayoutInflater.from(activity);

        mFlowLayout.setAdapter(new TagAdapter<String>(mVals) {

            @Override
            public View getView(FlowLayout parent, int position, String s) {
                TextView tv = (TextView) mInflater.inflate(R.layout.item_flow,
                        mFlowLayout, false);
                tv.setText(s);
                return tv;
            }
        });

        mFlowLayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                Toast.makeText(activity, mVals[position], Toast.LENGTH_SHORT).show();
                LogUtil.e("onTagClick:" + mVals[position]);
                //view.setVisibility(View.GONE);
                return true;
            }
        });


        mFlowLayout.setOnSelectListener(new TagFlowLayout.OnSelectListener() {
            @Override
            public void onSelected(Set<Integer> selectPosSet) {
                LogUtil.e("choose:" + selectPosSet.toString());
            }
        });
    }

    @Override
    public void initData() {

    }
}
