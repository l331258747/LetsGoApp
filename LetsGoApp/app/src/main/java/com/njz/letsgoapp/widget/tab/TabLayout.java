package com.njz.letsgoapp.widget.tab;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.njz.letsgoapp.R;

import java.util.ArrayList;


/**
 * Class Name: TabLayout.java
 *
 * Function: tab外层布局
 *
 * .....
 *
 */
public class TabLayout extends LinearLayout implements View.OnClickListener{

	private ArrayList<TabItem> tabItems;
	private TabView tabView;
	private OnTabClickListener tabClickListener;
	private View selectView;

	public TabLayout(Context context) {
		super(context);
	}

	public TabLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public TabLayout(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	public void initView(){
		setOrientation(HORIZONTAL);
	}

	public void initData(ArrayList<TabItem> tabs, OnTabClickListener listener){
		this.tabItems = tabs;
		this.tabClickListener = listener;

		LayoutParams params = new LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT);
		params.weight = 1;

		if (tabs != null && tabs.size() > 0){
			TabView tabView;
			for (int i = 0; i < tabs.size(); i++) {
				tabView = new TabView(getContext());

				TabItem tabItem = tabs.get(i);

				tabView.setTag(tabItem);
				tabView.initData(tabItem);


				tabView.setOnClickListener(this);
				addView(tabView, params);

				if (tabItem.lableResId == R.string.home_notify){
					this.tabView = tabView;
				}
			}
		}else{
			throw new IllegalArgumentException("tabs can not empty.");
		}
	}

	public TabView getTabView() {
		return tabView;
	}

	public void setTabSelect(int i){
		if (i < tabItems.size() && i >= 0){
			View view = getChildAt(i);
			if (selectView != view){
				view.setSelected(true);
//				((TextView)view.findViewById(R.id.tab_lable)).setTextColor(getResources().getColor(R.color.black));
				TextPaint tp = ((TextView)view.findViewById(R.id.tab_lable)).getPaint();
				tp.setFakeBoldText(true);

				if (selectView != null){
					selectView.setSelected(false);
//					((TextView)selectView.findViewById(R.id.tab_lable)).setTextColor(getResources().getColor(R.color.black));

					TextPaint tp2 = ((TextView) selectView.findViewById(R.id.tab_lable)).getPaint();
					tp2.setFakeBoldText(false);
				}
				selectView = view;
			}
		}
	}

	public interface OnTabClickListener{
		void onTabItemClick(TabItem tabItem);
	}

	@Override
	public void onClick(View v) {
		tabClickListener.onTabItemClick((TabItem) v.getTag());
	}
}
