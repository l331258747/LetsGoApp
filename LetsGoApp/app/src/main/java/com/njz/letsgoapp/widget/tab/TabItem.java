package com.njz.letsgoapp.widget.tab;


import android.support.v4.app.Fragment;


public class TabItem {

	public int imageResId;
	public int lableResId;
	public int dotNum = 0;

	public Class<? extends Fragment> tagFragment;

	public TabItem(int imageResId, int lableResId, Class<? extends Fragment> tagFragment) {
		this.imageResId = imageResId;
		this.lableResId = lableResId;

		this.tagFragment = tagFragment;
	}

	public void setDotNum(int dotNum) {
		this.dotNum = dotNum;
	}

	public TabItem(int imageResId, int lableResId, int dotNum, Class<? extends Fragment> tagFragment) {
		this.imageResId = imageResId;
		this.lableResId = lableResId;
		this.dotNum = dotNum;

		this.tagFragment = tagFragment;
	}

}
