package com.njz.letsgoapp.widget.tab;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.njz.letsgoapp.R;

import java.util.Locale;

/**
 * Class Name: TabView.java
 *
 * Function:
 *
 * .....
 *
 */
public class TabView extends RelativeLayout implements View.OnClickListener {

	private ImageView imageView;
	private TextView lableView;
	private TextView dotView;

	public TabView(Context context) {
		super(context);
		initView(context);
	}

	public TabView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView(context);
	}

	public TabView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		initView(context);
	}

	private void initView(Context context){
		setGravity(Gravity.CENTER);
		LayoutInflater.from(context).inflate(R.layout.tab_view_layout, this, true);

		imageView = (ImageView) findViewById(R.id.tab_image);
		lableView = (TextView) findViewById(R.id.tab_lable);
		dotView = (TextView) findViewById(R.id.tab_dot);
	}

	public void initData(TabItem tabItem){
		imageView.setImageResource(tabItem.imageResId);
		lableView.setText(tabItem.lableResId);
		setDotNum(tabItem.dotNum);
	}

	/**
	 *
	 * @param count
	 */
	public void setDotNum(int count){
		if(count < 0){
			dotView.setVisibility(VISIBLE);
		}else if(count == 0){
			dotView.setVisibility(GONE);
		}else{
			dotView.setVisibility(VISIBLE);
			if (count < 100) {
				dotView.setText(String.format(Locale.getDefault() ,"%d", count));
			} else {
				dotView.setText("99");
			}
		}
		dotView.invalidate();
	}

	@Override
	public void onClick(View v) {

	}
}
