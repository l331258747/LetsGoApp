package com.njz.letsgoapp.view.mine;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.bigkoo.pickerview.view.TimePickerView;
import com.njz.letsgoapp.R;
import com.njz.letsgoapp.base.BaseActivity;
import com.njz.letsgoapp.bean.MySelfInfo;
import com.njz.letsgoapp.bean.mine.MyInfoData;
import com.njz.letsgoapp.mvp.mine.MyInfoContract;
import com.njz.letsgoapp.mvp.mine.MyInfoPresenter;
import com.njz.letsgoapp.util.AppUtils;
import com.njz.letsgoapp.util.DateUtil;
import com.njz.letsgoapp.util.SPUtils;
import com.njz.letsgoapp.util.accessory.ImageUtils;
import com.njz.letsgoapp.util.dialog.LoadingDialog;
import com.njz.letsgoapp.util.glide.GlideUtil;
import com.njz.letsgoapp.util.photo.TackPicturesUtil;
import com.njz.letsgoapp.util.rxbus.RxBus2;
import com.njz.letsgoapp.util.rxbus.busEvent.UpLoadPhotos;
import com.njz.letsgoapp.util.thread.MyThreadPool;
import com.njz.letsgoapp.widget.MineItemView;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by LGQ
 * Time: 2018/8/9
 * Function:
 */

public class MyInfoActivity extends BaseActivity implements View.OnClickListener,MyInfoContract.View {
    ImageView iv_head;
    MineItemView info_sex, info_birthday, info_tag;
    EditText et_nikename,et_real_name,et_explain;

    String bNickName, bBirthday, bSex,bRealName,bExplain,bImgUrl;
    List<String> sexs;

    TextView btn_submit;


    private LoadingDialog loadingDialog;

    private TackPicturesUtil tackPicUtil;

    private String headpath;// 头像地址
    private String headCompressPath;

    private MyInfoPresenter mPresenter;

    private Disposable disposable;

    @Override
    public int getLayoutId() {
        return R.layout.activity_my_info;
    }

    @Override
    public void initView() {

        showLeftAndTitle("编辑资料");

        iv_head = $(R.id.iv_head);
        et_nikename = $(R.id.et_name);
        info_sex = $(R.id.info_sex);
        info_birthday = $(R.id.info_birthday);
        et_real_name = $(R.id.et_real_name);
        et_explain = $(R.id.et_explain);
        info_tag = $(R.id.info_tag);
        btn_submit = $(R.id.btn_submit);

        info_birthday.setOnClickListener(this);
        info_sex.setOnClickListener(this);
        info_tag.setOnClickListener(this);
        iv_head.setOnClickListener(this);
        btn_submit.setOnClickListener(this);
        btn_submit.setBackground(ContextCompat.getDrawable(context,R.drawable.btn_99_solid_r40));
        btn_submit.setEnabled(false);

        loadingDialog = new LoadingDialog(context);

    }

    TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            isChange();
        }
    };

    @Override
    public void initData() {
        setHeadImg(MySelfInfo.getInstance().getUserImgUrl());
        et_nikename.setText(MySelfInfo.getInstance().getUserNickname());
        et_real_name.setText(MySelfInfo.getInstance().getUserName());
        info_sex.setContent(MySelfInfo.getInstance().getUserGender());
        info_birthday.setContent(MySelfInfo.getInstance().getUserBirthday());
        et_explain.setText(SPUtils.getInstance().getString(SPUtils.SP_USER_PERSONAL_STATEMENT));

        bNickName = et_nikename.getText().toString();
        bBirthday = info_birthday.getContent();
        bSex = info_sex.getContent();
        bExplain = et_explain.getText().toString();
        bRealName = et_real_name.getText().toString();
        bImgUrl = MySelfInfo.getInstance().getUserImgUrl();
        sexs = new ArrayList<>();
        sexs.add("男");
        sexs.add("女");

        mPresenter = new MyInfoPresenter(context,this);

        et_nikename.addTextChangedListener(textWatcher);
        et_explain.addTextChangedListener(textWatcher);
        et_real_name.addTextChangedListener(textWatcher);

        et_explain.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // scrollview 与 edittext 滑动冲突
                v.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });


        tackPicUtil = new TackPicturesUtil(this);
        getPicPermission(context);
    }

//    Disposable desDisposable;
//    Disposable desDisposable2;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
//            case R.id.info_lacation:
//                Intent intent = new Intent(context, CityPickActivity.class);
//                intent.putExtra(CityPickActivity.CITYPICK_TAG, CityPickActivity.CITYPICK_LOCATION);
//                startActivity(intent);
//                activity.overridePendingTransition(0, 0);
//                desDisposable = RxBus2.getInstance().toObservable(CityPickEvent.class, new Consumer<CityPickEvent>() {
//                    @Override
//                    public void accept(CityPickEvent cityPickEvent) throws Exception {
//                        info_location.setContent(cityPickEvent.getCity());
//                        desDisposable.dispose();
//                        isChange();
//                    }
//                });
//                break;
//            case R.id.info_country:
//                Intent intent2 = new Intent(context, CityPickActivity.class);
//                intent2.putExtra(CityPickActivity.CITYPICK_TAG, CityPickActivity.CITYPICK_COUNTRY);
//                startActivity(intent2);
//                activity.overridePendingTransition(0, 0);
//                desDisposable2 = RxBus2.getInstance().toObservable(CityPickEvent.class, new Consumer<CityPickEvent>() {
//                    @Override
//                    public void accept(CityPickEvent cityPickEvent) throws Exception {
//                        info_country.setContent(cityPickEvent.getCity());
//                        desDisposable2.dispose();
//                        isChange();
//                    }
//                });
//                break;
            case R.id.btn_submit:
                mPresenter.userChangePersonalData(myInfoData);
                break;
            case R.id.info_birthday:
                AppUtils.HideKeyboard(info_birthday);
                //时间选择器
                TimePickerView pvTime = new TimePickerBuilder(context,
                        new OnTimeSelectListener() {
                            @Override
                            public void onTimeSelect(Date date, View v) {
                                info_birthday.setContent(DateUtil.dateToStr(date));
                                isChange();
                            }
                        })
                        .setDate(DateUtil.getSelectedDate(info_birthday.getContent()))// 如果不设置的话，默认是系统时间*/
                        .setRangDate(DateUtil.getStartDate(), DateUtil.getEndDate())//起始终止年月日设定
                        .build();
                pvTime.show();
                break;
            case R.id.info_sex:
                AppUtils.HideKeyboard(info_sex);
                //条件选择器
                OptionsPickerView pvOptions = new OptionsPickerBuilder(context,
                        new OnOptionsSelectListener() {
                            @Override
                            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                                info_sex.setContent(sexs.get(options1));
                                isChange();
                            }
                        })
                        .build();
                pvOptions.setPicker(sexs, null, null);
                pvOptions.show();
                break;
            case R.id.info_tag:
                startActivity(new Intent(context, LabelActivity.class));
                break;
            case R.id.iv_head:
                tackPicUtil.showDialog(context);
                break;
        }
    }


    public MyInfoData myInfoData = new MyInfoData();

    //返回true为可以保存
    public void isChange() {
        boolean isChange = false;
        if (!TextUtils.equals(MySelfInfo.getInstance().getUserImgUrl(), bImgUrl)) {
            myInfoData.setImgUrl(bImgUrl);
            isChange = true;
        }
        if (!TextUtils.equals(et_nikename.getText().toString(), bNickName)) {
            myInfoData.setNickname(et_nikename.getText().toString());
            isChange = true;
        }
        if (!TextUtils.equals(info_sex.getContent(), bSex)) {
            myInfoData.setGender(info_sex.getContent());
            isChange = true;
        }
        if (!TextUtils.equals(info_birthday.getContent(), bBirthday)) {
            myInfoData.setBirthday(info_birthday.getContent());
            isChange = true;
        }
        if (!TextUtils.equals(et_real_name.getText().toString(), bRealName)) {
            myInfoData.setName(et_real_name.getText().toString());
            isChange = true;
        }
        if (!TextUtils.equals(et_explain.getText().toString(), bExplain)) {
            myInfoData.setPersonalStatement(et_explain.getText().toString());
            isChange = true;
        }
        if (isChange) {
            btn_submit.setBackground(ContextCompat.getDrawable(context,R.drawable.btn_theme_solid_r40));
            btn_submit.setEnabled(true);
        } else {
            btn_submit.setBackground(ContextCompat.getDrawable(context,R.drawable.btn_99_solid_r40));
            btn_submit.setEnabled(false);
        }
    }



    //-----------start 拍照-----------

    //拍照，存储权限
    public void getPicPermission(Context context) {
        tackPicUtil.checkPermission(context);
    }


    /**
     * 获取图片回调
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case TackPicturesUtil.CHOOSE_PIC:
            case TackPicturesUtil.TACK_PIC:
            case TackPicturesUtil.CROP_PIC:
                String path = tackPicUtil.getPicture(requestCode, resultCode, data, false);
                if (path == null)
                    return;
                headpath = path;
                upFile();
                break;
            default:
                break;
        }
    }

    public void upFile() {
        disposable = RxBus2.getInstance().toObservable(UpLoadPhotos.class, new Consumer<UpLoadPhotos>() {
            @Override
            public void accept(UpLoadPhotos upLoadPhotos) throws Exception {
                sendHead();
                disposable.dispose();
            }
        });

        loadingDialog.showDialog("上传头像...");
        compressImage();
    }

    public void sendHead() {
        //构建要上传的文件
        File file = new File(headCompressPath);
        mPresenter.upUpload(file);
    }

    private void compressImage() {
        MyThreadPool.getInstance().submit(new Runnable() {
            @Override
            public void run() {
                File file = new File(headpath);
                String savePath = TackPicturesUtil.IMAGE_CACHE_PATH + "crop" + file.getName();
                ImageUtils.getImage(headpath, savePath);
                headCompressPath = savePath;
                RxBus2.getInstance().post(new UpLoadPhotos());
            }
        });
    }

    private void setHeadImg(String path) {
        GlideUtil.LoadCircleImage(context, path, iv_head);
    }

    @Override
    public void userChangePersonalDataSuccess(String str) {
        showShortToast("修改成功");
        if(!TextUtils.isEmpty(myInfoData.getName())){
            MySelfInfo.getInstance().setUserName(myInfoData.getName());
        }
        if(!TextUtils.isEmpty(myInfoData.getNickname())){
            MySelfInfo.getInstance().setUserNickname(myInfoData.getNickname());
        }
        if(!TextUtils.equals(myInfoData.getGender(),MySelfInfo.getInstance().getUserGender())){
            MySelfInfo.getInstance().setUserGender(info_sex.getContent());
        }
        if(!TextUtils.isEmpty(myInfoData.getBirthday())){
            MySelfInfo.getInstance().setUserBirthday(myInfoData.getBirthday());
        }
        if(!TextUtils.isEmpty(myInfoData.getPersonalStatement())){
            MySelfInfo.getInstance().setUserStatement(myInfoData.getPersonalStatement());
        }
        if(!TextUtils.isEmpty(myInfoData.getImgUrl())){
            MySelfInfo.getInstance().setUserImgUrl(myInfoData.getImgUrl());
        }
        finish();
    }

    @Override
    public void userChangePersonalDataFailed(String msg) {
        showShortToast(msg);
    }

    @Override
    public void upUploadSuccess(String str) {
        loadingDialog.dismiss();

        bImgUrl = str;

        showShortToast("头像上传成功");
        setHeadImg(headpath);
        isChange();
    }

    @Override
    public void upUploadFailed(String msg) {
        loadingDialog.dismiss();
        showShortToast(msg);
    }

    //----------------end 拍照

}
