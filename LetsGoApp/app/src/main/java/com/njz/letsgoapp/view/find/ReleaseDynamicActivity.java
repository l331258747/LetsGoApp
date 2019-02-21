package com.njz.letsgoapp.view.find;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.njz.letsgoapp.R;
import com.njz.letsgoapp.base.BaseActivity;
import com.njz.letsgoapp.bean.EmptyModel;
import com.njz.letsgoapp.bean.other.LocationModel;
import com.njz.letsgoapp.dialog.DialogUtil;
import com.njz.letsgoapp.map.LocationUtil;
import com.njz.letsgoapp.mvp.find.ReleaseDynamicContract;
import com.njz.letsgoapp.mvp.find.ReleaseDynamicPresenter;
import com.njz.letsgoapp.util.PermissionUtil;
import com.njz.letsgoapp.util.accessory.ImageUtils;
import com.njz.letsgoapp.util.accessory.PhotoAdapter;
import com.njz.letsgoapp.util.accessory.RecyclerItemClickListener;
import com.njz.letsgoapp.util.dialog.LoadingDialog;
import com.njz.letsgoapp.util.log.LogUtil;
import com.njz.letsgoapp.util.photo.TackPicturesUtil;
import com.njz.letsgoapp.util.rxbus.RxBus2;
import com.njz.letsgoapp.util.rxbus.busEvent.UpLoadPhotos;
import com.njz.letsgoapp.util.thread.MyThreadPool;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import me.iwf.photopicker.PhotoPicker;
import me.iwf.photopicker.PhotoPreview;

/**
 * Created by LGQ
 * Time: 2018/9/4
 * Function:
 */

public class ReleaseDynamicActivity extends BaseActivity implements View.OnClickListener,ReleaseDynamicContract.View {

    private EditText etContent;
    private TextView tvLocation;

    private RecyclerView mPhotoRecyclerView;
    private PhotoAdapter photoAdapter;
    private ArrayList<String> selectedPhotos = new ArrayList<>();
    private ArrayList<String> upLoadPhotos = new ArrayList<>();
    private Disposable disposable;

    private ReleaseDynamicPresenter mPresenter;
    private LoadingDialog loadingDialog;
    private LocationUtil locationUtil;
    private double longitude = 0;
    private double latitude = 0;
    private String city = "";

    @Override
    public int getLayoutId() {
        return R.layout.activity_release_dynamic;
    }

    @Override
    public void initView() {
        showLeftAndTitle("发布动态");
        showRightTv();
        getRightTv().setText("发表");
        getRightTv().setOnClickListener(this);
        getRightTv().setTextColor(ContextCompat.getColor(context, R.color.color_theme));

        etContent = $(R.id.et_content);
        tvLocation = $(R.id.tv_location);

        initAddPhoto();

        loadingDialog = new LoadingDialog(this);
    }

    @Override
    public void initData() {
        mPresenter = new ReleaseDynamicPresenter(context,this);
        locationUtil = new LocationUtil();

        getLocation();

        tvLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!PermissionUtil.getInstance().isGpsAvailable(context)) return;
                if(!PermissionUtil.getInstance().getPremission(context,PermissionUtil.PERMISSION_LOCATION,PermissionUtil.PERMISSION_LOCATION_CODE)) return;
                getLocation();
            }
        });
    }

    public void getLocation(){
        tvLocation.setText("定位中...");
        tvLocation.setEnabled(false);
        locationUtil.startLocation(new LocationUtil.LocationListener() {
            @Override
            public void getAdress(int code, LocationModel adress) {
                LogUtil.e("code:" + code + " adress:" + adress);
                if(code == 0){
                    tvLocation.setText(adress.getCity()+adress.getCityChild());
                    tvLocation.setEnabled(false);
                    longitude = adress.getLongitude();
                    latitude = adress.getLatitude();
                    city = adress.getCity()+adress.getCityChild();
                }else{
                    tvLocation.setText("重新定位");
                    tvLocation.setEnabled(true);
                }
            }
        });
    }

    //------------附件图片
    private void initAddPhoto(){
        //------------附件
        mPhotoRecyclerView = $(R.id.recycler_view);
        photoAdapter = new PhotoAdapter(context, selectedPhotos,true);
        mPhotoRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(3, OrientationHelper.VERTICAL));
        mPhotoRecyclerView.setAdapter(photoAdapter);
        mPhotoRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(context,
                new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        if (photoAdapter.getItemViewType(position) == PhotoAdapter.TYPE_ADD) {
                            PhotoPicker.builder()
                                    .setPhotoCount(PhotoAdapter.MAX)
                                    .setShowCamera(true)
                                    .setPreviewEnabled(false)
                                    .setSelected(selectedPhotos)
                                    .start(ReleaseDynamicActivity.this);
                        } else {
                            PhotoPreview.builder()
                                    .setPhotos(selectedPhotos)
                                    .setCurrentItem(position)
                                    .start(ReleaseDynamicActivity.this);
                        }
                    }
                }));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == -1 &&
                (requestCode == PhotoPicker.REQUEST_CODE || requestCode == PhotoPreview.REQUEST_CODE)) {

            List<String> photos = null;
            if (data != null) {
                photos = data.getStringArrayListExtra(PhotoPicker.KEY_SELECTED_PHOTOS);
            }
            selectedPhotos.clear();

            if (photos != null) {

                selectedPhotos.addAll(photos);
            }
            photoAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.right_tv){
            if(TextUtils.isEmpty(city)){
                DialogUtil.getInstance().getDefaultDialog(context,"定位之后才能发布动态！").show();
                return;
            }
            if(TextUtils.isEmpty(etContent.getText().toString()) && selectedPhotos.size() == 0){
                DialogUtil.getInstance().getDefaultDialog(context,"请填写动态内容或添加图片才可进行动态发布！").show();
                return;
            }

            disposable = RxBus2.getInstance().toObservable(UpLoadPhotos.class, new Consumer<UpLoadPhotos>() {
                @Override
                public void accept(UpLoadPhotos upLoadPhotos) throws Exception {
                    submit();
                    disposable.dispose();
                }
            });
            loadingDialog.showDialog("正在上传中...");
            loadingDialog.setCancelable(false);
            compressImage();
        }
    }

    private void compressImage() {
        MyThreadPool.getInstance().submit(new Runnable() {
            @Override
            public void run() {
                upLoadPhotos.clear();
                for (String path : selectedPhotos) {
                    File file = new File(path);
                    if(!file.getName().startsWith("crop") || file.length()>1024*100) {
                        String savePath = TackPicturesUtil.IMAGE_CACHE_PATH + "crop" + file.getName();
                        ImageUtils.getImage(path, savePath);
                        upLoadPhotos.add(savePath);
                    }else{
                        upLoadPhotos.add(path);
                    }
                }
                RxBus2.getInstance().post(new UpLoadPhotos());
            }
        });
    }

    @Override
    public void sendSterSuccess(EmptyModel models) {
        loadingDialog.dismiss();
        showShortToast("发布成功");
        finish();
    }

    @Override
    public void sendSterFailed(String msg) {
        loadingDialog.dismiss();
        showShortToast(msg);
    }

    public void submit(){
        mPresenter.sendSter(city,longitude,latitude,etContent.getText().toString(),upLoadPhotos);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        locationUtil.stopLocation();
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PermissionUtil.PERMISSION_LOCATION_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_DENIED) {//选择了不再提示按钮
                PermissionUtil.getInstance().showAccreditDialog(context, "温馨提示\n" +
                        "您需要同意那就走使用【定位】权限才能正常发布动态，" +
                        "由于您选择了【禁止（不再提示）】，将导致无法定位，" +
                        "需要到设置页面手动授权开启【定位】权限，才能发布动态。");
                return;
            }
        }
    }

    @Override
    public void onBackPressed() {
        if(!TextUtils.isEmpty(etContent.getText().toString()) || selectedPhotos.size() > 0){
            DialogUtil.getInstance().getDefaultDialog(context, "您是否确认退出?", new DialogUtil.DialogCallBack() {
                @Override
                public void exectEvent(DialogInterface alterDialog) {
                    ReleaseDynamicActivity.super.onBackPressed();
                }
            }).show();
        }else{
            super.onBackPressed();
        }
    }
}
