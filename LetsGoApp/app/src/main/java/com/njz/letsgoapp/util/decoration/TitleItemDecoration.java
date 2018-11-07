package com.njz.letsgoapp.util.decoration;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;

import com.njz.letsgoapp.bean.home.DynamicModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LGQ
 * Time: 2018/11/7
 * Function:
 */

public class TitleItemDecoration extends RecyclerView.ItemDecoration {
    private Rect textBound;//文字的范围i
    private Paint mPaint;
    private List<DynamicModel> subjectList = new ArrayList<>();
    private int mTitleHeight;//title的高
    private final int COLOR_BG = Color.parseColor("#303F9F");
    private final int COLOR_FONT = Color.WHITE;
    private final int decoration;

    public TitleItemDecoration(Context context, List<DynamicModel> subjectList, int decoration) {
        this.decoration = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, decoration, context.getResources().getDisplayMetrics());
        this.subjectList.clear();
        this.subjectList.addAll(subjectList);
        mTitleHeight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 30, context.getResources().getDisplayMetrics());
        //title字体大小
        final float mFontSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 16, context.getResources().getDisplayMetrics());
        textBound = new Rect();
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setTextSize(mFontSize);
    }

    /**
     * 设置Title的空间
     */
    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        int position = parent.getChildLayoutPosition(view);
        if(position >= subjectList.size()) return;
        if (position == 0) {//position为0，第一个item有title
            outRect.set(0, mTitleHeight, 0, decoration);//留下title空间
        } else {//对是否需要留白 进行判断
            DynamicModel resourceBean = subjectList.get(position);
            DynamicModel lastBean = subjectList.get(position - 1);
            if (resourceBean == null || lastBean == null) return;
            if (!resourceBean.getStartTimeTwo().equals(lastBean.getStartTimeTwo())) {//和上一个的科目不一样
                outRect.set(0, mTitleHeight, 0, decoration);//留下title空间
            } else {
                outRect.set(0, 0, 0, decoration);
            }
        }
    }

    /**
     * 绘制Title
     */
    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
        final int left = parent.getPaddingLeft();
        final int right = parent.getWidth() - parent.getPaddingRight();
        final int current = parent.getChildCount();//屏幕当前item数量
        for (int i = 0; i < current; i++) {
            final View child = parent.getChildAt(i);//获取childView
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) child.getLayoutParams();
            int position = parent.getChildLayoutPosition(child);//child的adapter position
            if(position >= subjectList.size()) return;
            if (position == 0) {//为0  绘制title
                DynamicModel resourceBean = subjectList.get(position);
                if (resourceBean == null) return;
                String title = resourceBean.getStartTimeTwo();
                drawTitle(c, left, right, child, layoutParams, title);//进行绘制
            } else {//不为0时 判断 是否需要绘制title
                DynamicModel resourceBean = subjectList.get(position);
                DynamicModel lastBean = subjectList.get(position - 1);
                if (resourceBean == null || lastBean == null) return;
                if (!resourceBean.getStartTimeTwo().equals(lastBean.getStartTimeTwo())) {//和上一个的科目不一样
                    drawTitle(c, left, right, child, layoutParams, resourceBean.getStartTimeTwo());
                }
            }
        }
    }

    /**
     * 绘制文字
     */
    private void drawTitle(Canvas c, int left, int right, View child, RecyclerView.LayoutParams layoutParams, String titleText) {
        //绘制背景色
        mPaint.setColor(COLOR_BG);
        c.drawRect(left, child.getTop() - layoutParams.topMargin - mTitleHeight, right, child.getTop() - layoutParams.topMargin, mPaint);
        //绘制文字，没有使用计算baseline的方式
        mPaint.setColor(COLOR_FONT);
        mPaint.getTextBounds(titleText, 0, titleText.length(), textBound);
        c.drawText(titleText, child.getPaddingLeft(), child.getTop() - layoutParams.topMargin - (mTitleHeight / 2 - textBound.height() / 2), mPaint);
    }

}