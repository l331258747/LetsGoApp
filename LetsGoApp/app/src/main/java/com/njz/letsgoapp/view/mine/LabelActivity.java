package com.njz.letsgoapp.view.mine;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.njz.letsgoapp.R;
import com.njz.letsgoapp.base.BaseActivity;
import com.njz.letsgoapp.widget.flowlayout.FlowLayout;
import com.njz.letsgoapp.widget.flowlayout.TagAdapter;
import com.njz.letsgoapp.widget.flowlayout.TagFlowLayout;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by LGQ
 * Time: 2018/8/20
 * Function:
 */

public class LabelActivity extends BaseActivity {

    TagFlowLayout flowlayout_1, flowlayout_2;
    EditText et_label;


    private String[] mVals1 = new String[]
            {"会开车", "会攀岩", "会潜水", "会骑行", "会急救", "会拍照"
                    , "会水上运动", "会冰雪运动", "会游玩", "会开飞机", "会跳伞", "会蹦极"};

    private boolean[] mboolean1 = new boolean[]
            {false, false, true, false, false, true, false, false, true, false, false, true};

    private String[] mVals2 = new String[]
            {"热情", "善良", "聪明", "颜值担当", "进取", "阳光"
                    , "爱笑", "洒脱不羁", "敏捷", "沉稳", "开朗", "幽默"};

    private boolean[] mboolean2 = new boolean[]
            {false, false, false, true, false, false, false, true, false, false, true, true};

    @Override
    public int getLayoutId() {
        return R.layout.activity_label;
    }

    @Override
    public void initView() {
        showLeftAndTitle("标签");

        flowlayout_1 = $(R.id.flowlayout_1);
        flowlayout_2 = $(R.id.flowlayout_2);
        et_label = $(R.id.et_label);


    }

    @Override
    public void initData() {
        initFlow();

        et_label.setText(getSelectContent());
    }

    public void initFlow() {
        final LayoutInflater mInflater = LayoutInflater.from(activity);

        TagAdapter adapter1 = new TagAdapter<String>(mVals1) {
            @Override
            public View getView(FlowLayout parent, int position, String s) {
                TextView tv = (TextView) mInflater.inflate(R.layout.item_flow_label, flowlayout_1, false);
                tv.setText(s);
                return tv;
            }
        };
        adapter1.setSelectedList(getSelectIndex(mboolean1));
        flowlayout_1.setAdapter(adapter1);

        flowlayout_1.setOnTagClickListener(new TagFlowLayout.OnTagClickListener(){
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent){
                mboolean1[position] = !mboolean1[position];
                et_label.setText(getSelectContent());
                return true;
            }
        });


        TagAdapter adapter2 = new TagAdapter<String>(mVals2) {
            @Override
            public View getView(FlowLayout parent, int position, String s) {
                TextView tv = (TextView) mInflater.inflate(R.layout.item_flow_label, flowlayout_2, false);
                tv.setText(s);
                return tv;
            }
        };
        adapter2.setSelectedList(getSelectIndex(mboolean2));
        flowlayout_2.setAdapter(adapter2);

        flowlayout_2.setOnTagClickListener(new TagFlowLayout.OnTagClickListener(){
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent){
                mboolean2[position] = !mboolean2[position];
                et_label.setText(getSelectContent());
                return true;
            }
        });
    }

    public Set<Integer> getSelectIndex(boolean[] mboolean) {
        Set<Integer> ints = new HashSet<>();
        for (int i = 0; i < mboolean.length; i++) {
            if (mboolean[i] == true) {
                ints.add(i);
            }
        }
        return ints;
    }

    StringBuffer etContent = new StringBuffer();

    public String getSelectContent(){
        String strContent = "";
        for (int i = 0; i < mboolean1.length; i++) {
            if (mboolean1[i] == true) {
                etContent.append(mVals1[i]);
                etContent.append(",");
            }
        }
        for (int i = 0; i < mboolean2.length; i++) {
            if (mboolean2[i] == true) {
                etContent.append(mVals2[i]);
                etContent.append(",");
            }
        }

        if(etContent.length() == 0)
            return "";

        strContent = etContent.substring(0,etContent.length() - 1);
        etContent.setLength(0);
        return strContent;
    }
}
