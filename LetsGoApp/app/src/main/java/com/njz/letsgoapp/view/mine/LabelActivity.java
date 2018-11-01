package com.njz.letsgoapp.view.mine;

import android.content.DialogInterface;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
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
import com.njz.letsgoapp.dialog.DialogUtil;
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

    private TextView tv_add;
    private TagFlowLayout flowlayout_custom;
    private List<LabelItemModel> customLabels = new ArrayList<>();

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
        tv_add = $(R.id.tv_add);
        flowlayout_custom = $(R.id.flowlayout_custom);

        tv_add.setOnClickListener(this);
    }

    @Override
    public void initData() {
        mPresenter = new LabelPresenter(context, this);
        mPresenter.userLabels();

    }

    public void initFlow(final TagFlowLayout tagFlowLayout, final List<LabelItemModel> mVals) {
        initFlow(tagFlowLayout,mVals,false);
    }

    public void initFlow(final TagFlowLayout tagFlowLayout, final List<LabelItemModel> mVals,final boolean isCustom) {
        final LayoutInflater mInflater = LayoutInflater.from(activity);

        TagAdapter adapter1 = new TagAdapter<LabelItemModel>(mVals) {
            @Override
            public View getView(FlowLayout parent, int position, LabelItemModel s) {
                if(isCustom){
                    final TextView tv = (TextView) mInflater.inflate(R.layout.item_flow_label_custom, tagFlowLayout, false);
                    tv.setText(s.getName());
                    tv.setOnLongClickListener(new View.OnLongClickListener() {
                        @Override
                        public boolean onLongClick(View v) {
                            DialogUtil.getInstance().getDefaultDialog(context, "确认删除 " + tv.getText().toString() + " 标签?",new DialogUtil.DialogCallBack() {
                                @Override
                                public void exectEvent(DialogInterface alterDialog) {
                                    for (int i =0;i<customLabels.size();i++){
                                        if(TextUtils.equals(customLabels.get(i).getName(),tv.getText().toString()))
                                            customLabels.remove(i);
                                    }
                                    initFlow(flowlayout_custom,customLabels,true);
                                    if(getRightTv().isEnabled())
                                        return ;
                                    getRightTv().setEnabled(true);
                                    getRightTv().setTextColor(ContextCompat.getColor(context, R.color.color_theme));
                                }
                            }).show();
                            return false;
                        }
                    });
                    return tv;
                }else{
                    TextView tv = (TextView) mInflater.inflate(R.layout.item_flow_label, tagFlowLayout, false);
                    tv.setText(s.getName());
                    return tv;
                }
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
        switch (v.getId()){
            case R.id.right_tv:
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
                break;
            case R.id.tv_add:
                DialogUtil.getInstance().getEditDialog(context, new DialogUtil.DialogEditCallBack() {
                    @Override
                    public void exectEvent(DialogInterface alterDialog,String str) {
                        if(TextUtils.isEmpty(str)) return;
                        LabelItemModel labelItemModel = new LabelItemModel();
                        labelItemModel.setSelect(true);
                        labelItemModel.setName(str);
                        customLabels.add(labelItemModel);
                        initFlow(flowlayout_custom,customLabels,true);

                        if(getRightTv().isEnabled())
                            return ;
                        getRightTv().setEnabled(true);
                        getRightTv().setTextColor(ContextCompat.getColor(context, R.color.color_theme));
                    }
                }).show();
                break;
        }
    }

}
