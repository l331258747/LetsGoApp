package com.njz.letsgoapp.view.mine;

import android.support.v4.content.ContextCompat;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.njz.letsgoapp.R;
import com.njz.letsgoapp.base.BaseActivity;
import com.njz.letsgoapp.bean.MySelfInfo;
import com.njz.letsgoapp.bean.mine.LabelItemModel;
import com.njz.letsgoapp.bean.mine.LabelModel;
import com.njz.letsgoapp.bean.mine.MyInfoData;
import com.njz.letsgoapp.mvp.mine.LabelContract;
import com.njz.letsgoapp.mvp.mine.LabelPresenter;
import com.njz.letsgoapp.util.SPUtils;
import com.njz.letsgoapp.util.log.LogUtil;
import com.njz.letsgoapp.widget.flowlayout.FlowLayout;
import com.njz.letsgoapp.widget.flowlayout.TagAdapter;
import com.njz.letsgoapp.widget.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by LGQ
 * Time: 2018/8/20
 * Function:
 */

public class LabelActivity extends BaseActivity implements LabelContract.View, View.OnClickListener {

    LabelPresenter mPresenter;
    LinearLayout llParent;

    List<LabelItemModel> labelAll = new ArrayList<>();

    @Override
    public int getLayoutId() {
        return R.layout.activity_label;
    }

    @Override
    public void initView() {
        showRightTv();
        getRightTv().setText("保存");
        getRightTv().setOnClickListener(this);
        getRightTv().setEnabled(false);
        getRightTv().setTextColor(ContextCompat.getColor(context, R.color.black_66));

        showLeftAndTitle("标签");
        llParent = $(R.id.ll_parent);
    }

    @Override
    public void initData() {
        mPresenter = new LabelPresenter(context, this);
        mPresenter.userLabels();

    }

    TagAdapter adapter1;
    public void initFlow(final TagFlowLayout tagFlowLayout, final List<LabelItemModel> mVals) {
        final LayoutInflater mInflater = LayoutInflater.from(activity);

        adapter1 = new TagAdapter<LabelItemModel>(mVals) {
            @Override
            public View getView(FlowLayout parent, int position, LabelItemModel s) {
                TextView tv = (TextView) mInflater.inflate(R.layout.item_flow_label, tagFlowLayout, false);
                tv.setText(s.getName());
                return tv;
            }
        };

        adapter1.setSelectedList(getSelectIndex(mVals));
        tagFlowLayout.setAdapter(adapter1);

        tagFlowLayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                mVals.get(position).setSelect(!mVals.get(position).isSelect());

                if(getRightTv().isEnabled())
                    return true;
                getRightTv().setEnabled(true);
                getRightTv().setTextColor(ContextCompat.getColor(context, R.color.color_theme));

                return true;
            }
        });
    }

    public Set<Integer> getSelectIndex(List<LabelItemModel> sVals) {
        Set<Integer> ints = new HashSet<>();
        for (int i = 0; i < sVals.size(); i++) {
            if (sVals.get(i).isSelect() == true) {
                ints.add(i);
            }
        }
        return ints;
    }

    @Override
    public void userLabelsSuccess(List<LabelModel> str) {

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.setMargins(0, 10, 0, 0);

        LinearLayout.LayoutParams lp2 = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);


        for (int i = 0; i < str.size(); i++) {
            TextView tvTitle = new TextView(context);
            tvTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
            tvTitle.setLayoutParams(lp);
            LabelModel labelModel = str.get(i);
            tvTitle.setText(labelModel.getName());
            llParent.addView(tvTitle);


            if (labelModel.getSysMacroEntitys().size() > 0) {
                TagFlowLayout tagFlowLayout = new TagFlowLayout(context);
                tagFlowLayout.setLayoutParams(lp2);
                tagFlowLayout.setPadding(10, 10, 10, 10);
                tagFlowLayout.setMaxSelectCount(-1);
                llParent.addView(tagFlowLayout);

                List<LabelItemModel> labelItemModels = labelModel.getSysMacroEntitys();
                for (LabelItemModel item : labelItemModels){
                    for (LabelItemModel item2 : MySelfInfo.getInstance().getLabels()){
                        if(item.getId() == item2.getId()){
                            item.setSelect(true);
                            continue;
                        }
                    }
                }
                labelAll.addAll(labelItemModels);
                initFlow(tagFlowLayout,labelItemModels);
            }
        }
    }

    @Override
    public void userLabelsFailed(String msg) {
        showShortToast(msg);
    }

    public MyInfoData myInfoData = new MyInfoData();

    @Override
    public void userChangePersonalDataSuccess(String str) {
        showShortToast("修改成功");
        List<LabelItemModel> datas = new ArrayList<>();
        for (LabelItemModel item : labelAll){
            if(item.isSelect()){
                datas.add(item);
            }
        }
        MySelfInfo.getInstance().setLabels(datas);
        finish();
    }

    @Override
    public void userChangePersonalDataFailed(String msg) {
        showShortToast(msg);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.right_tv){
            StringBuffer sb = new StringBuffer("");
            for (LabelItemModel item : labelAll){
                if(item.isSelect()){
                    LogUtil.e(item.getName());
                    sb.append(item.getId() + ",");
                }
            }
            String theLabel = sb.substring(0,sb.length() - 1);
            myInfoData.setTheLabel(theLabel);
            mPresenter.userChangePersonalData(myInfoData);
        }
    }
}
