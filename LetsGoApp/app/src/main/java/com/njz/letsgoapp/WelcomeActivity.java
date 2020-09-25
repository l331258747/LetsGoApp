package com.njz.letsgoapp;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.njz.letsgoapp.base.BaseActivity;
import com.njz.letsgoapp.constant.Constant;
import com.njz.letsgoapp.util.AppUtils;
import com.njz.letsgoapp.util.StringUtils;
import com.njz.letsgoapp.view.home.GuideContractActivity;
import com.njz.letsgoapp.view.homeFragment.HomeActivity;
import com.njz.letsgoapp.view.other.WebViewActivity;

import java.util.ArrayList;

/**
 * Created by LGQ
 * Time: 2018/8/21
 * Function:
 */

public class WelcomeActivity extends BaseActivity {

    private ViewPager mViewPager;
    private int[] mImageIds = new int[]{R.mipmap.welcome1, R.mipmap.welcome2, R.mipmap.welcome3};
    private ArrayList<ImageView> mImageViewList;
    private LinearLayout llContainer;
    private ImageView ivRedPoint;
    private int mPaintDis;
    private TextView start_btn;
    private LinearLayout ll_agreement;
    private TextView tv_user_agreement,tv_privacy_policy;

    private int pagePosition = 0;
    private boolean pageChange = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        hideBottomUIMenu();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_welcome;
    }

    /**
     * 隐藏虚拟按键，并且全屏
     */
    protected void hideBottomUIMenu() {
        //隐藏虚拟按键，并且全屏
        if (Build.VERSION.SDK_INT > 11 && Build.VERSION.SDK_INT < 19) { // lower api
            View v = this.getWindow().getDecorView();
            v.setSystemUiVisibility(View.GONE);
        } else if (Build.VERSION.SDK_INT >= 19) {
            //for new api versions.
            View decorView = getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_FULLSCREEN;
            decorView.setSystemUiVisibility(uiOptions);
        }
    }

    private void initAgreement() {
        ll_agreement = $(R.id.ll_agreement);

        tv_user_agreement = $(R.id.tv_user_agreement);
        StringUtils.setHtml(tv_user_agreement, getResources().getString(R.string.welcome_user_agreement));
        tv_user_agreement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, GuideContractActivity.class);
                intent.putExtra("CONTRACT_TYPE", 1);
                startActivity(intent);
            }
        });

        tv_privacy_policy = $(R.id.tv_privacy_policy);
        StringUtils.setHtml(tv_privacy_policy, getResources().getString(R.string.login_privacy_policy));
        tv_privacy_policy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, WebViewActivity.class);
                intent.putExtra(Constant.EXTRA_URL,"http://www.njzou.com/yszc/");
                intent.putExtra(Constant.IS_USE_WIDE_VIEW_PORT,true);
                intent.putExtra(Constant.EXTRA_TITLE,"隐私政策");
                startActivity(intent);
            }
        });
    }

    public void initView() {
        initAgreement();
        mViewPager = (ViewPager) findViewById(R.id.vp_guide);
        llContainer = (LinearLayout) findViewById(R.id.ll_container);
        ivRedPoint = (ImageView) findViewById(R.id.iv_red);
        start_btn = (TextView) findViewById(R.id.start_btn);

        start_btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //点击进入的时候直接跳转到登录界面
                Intent intent = new Intent(WelcomeActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });

        initData2();
        GuideAdapter adapter = new GuideAdapter();
//        //添加动画效果
//        mViewPager.setPageTransformer(true, new DepthPageTransformer());
        mViewPager.setAdapter(adapter);

        //监听布局是否已经完成  布局的位置是否已经确定
        ivRedPoint.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                //避免重复回调        出于兼容性考虑，使用了过时的方法
                ivRedPoint.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                //布局完成了就获取第一个小灰点和第二个之间left的距离
                mPaintDis = llContainer.getChildAt(1).getLeft() - llContainer.getChildAt(0).getLeft();
                System.out.println("距离：" + mPaintDis);
            }
        });


        //ViewPager滑动Pager监听
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            //滑动过程中的回调
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                //当滑到第二个Pager的时候，positionOffset百分比会变成0，position会变成1，所以后面要加上position*mPaintDis
                int letfMargin = (int) (mPaintDis * positionOffset) + position * mPaintDis;
                //在父布局控件中设置他的leftMargin边距
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) ivRedPoint.getLayoutParams();
                params.leftMargin = letfMargin;
                ivRedPoint.setLayoutParams(params);
            }


            /**
             * 设置按钮最后一页显示，其他页面隐藏
             * @param position
             */
            @Override
            public void onPageSelected(int position) {
                pagePosition = position;
                pageChange = true;
                if (position == mImageViewList.size() - 1) {
                    ll_agreement.setVisibility(View.VISIBLE);
                    start_btn.setVisibility(View.VISIBLE);
                    llContainer.setVisibility(View.GONE);
                    ivRedPoint.setVisibility(View.GONE);
                } else {
                    ll_agreement.setVisibility(View.GONE);
                    start_btn.setVisibility(View.GONE);
                    llContainer.setVisibility(View.VISIBLE);
                    ivRedPoint.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                //state:1开始滑动，2滑动结束，0滑动动画做完的状态
                //在最后一页的时候，如果只触发了onPageScrollStateChanged，没有触发onPageSelected，则跳到首页
                if(state == 1){
                    pageChange = false;
                }
                if(state == 0 && pagePosition == mImageViewList.size() - 1 && !pageChange){
                    Intent intent = new Intent(WelcomeActivity.this, HomeActivity.class);
                    startActivity(intent);
                    finish();
                    overridePendingTransition(R.anim.left_out,R.anim.right_in);
                }
            }
        });
    }

    @Override
    public void initData() {
        hideTitleLayout();
    }

    public void initData2() {
        mImageViewList = new ArrayList<>();
        for (int i = 0; i < mImageIds.length; i++) {
            //创建ImageView把mImgaeViewIds放进去
            ImageView view = new ImageView(this);
            view.setScaleType(ImageView.ScaleType.CENTER_CROP);
            view.setImageDrawable(ContextCompat.getDrawable(this,mImageIds[i]));
//            view.setBackgroundResource(mImageIds[i]);
            //添加到ImageView的集合中
            mImageViewList.add(view);
            //小圆点
            ImageView pointView = new ImageView(this);
            pointView.setImageResource(R.drawable.shape_point1);
            //初始化布局参数，父控件是谁，就初始化谁的布局参数
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//            if (i > 0) {
                //当添加的小圆点的个数超过一个的时候就设置当前小圆点的左边距为20dp;
                params.leftMargin = AppUtils.dip2px(8);
                params.rightMargin = AppUtils.dip2px(8);
//            }
            //设置小灰点的宽高包裹内容
            pointView.setLayoutParams(params);
            //将小灰点添加到LinearLayout中
            llContainer.addView(pointView);
        }
    }

    class GuideAdapter extends PagerAdapter {

        //item的个数
        @Override
        public int getCount() {
            return mImageViewList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        //初始化item布局
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView view = mImageViewList.get(position);
            container.addView(view);
            return view;
        }

        //销毁item
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }


//    public class DepthPageTransformer implements ViewPager.PageTransformer {
//        private float MIN_SCALE = 0.75f;
//
//        @SuppressLint("NewApi")
//        @Override
//        public void transformPage(View view, float position) {
//            int pageWidth = view.getWidth();
//            if (position < -1) { // [-Infinity,-1)
//                // This page is way off-screen to the left.
//                view.setAlpha(0);
//            } else if (position <= 0) { // [-1,0]
//                // Use the default slide transition when
//                // moving to the left page
//                view.setAlpha(1);
//                view.setTranslationX(0);
//                view.setScaleX(1);
//                view.setScaleY(1);
//            } else if (position <= 1) { // (0,1]
//                // Fade the page out.
//                view.setAlpha(1 - position);
//                // Counteract the default slide transition
//                view.setTranslationX(pageWidth * -position);
//                // Scale the page down (between MIN_SCALE and 1)
//                float scaleFactor = MIN_SCALE + (1 - MIN_SCALE)
//                        * (1 - Math.abs(position));
//                view.setScaleX(scaleFactor);
//                view.setScaleY(scaleFactor);
//            } else { // (1,+Infinity]
//                // This page is way off-screen to the right.
//                view.setAlpha(0);
//
//            }
//        }
//    }

}
