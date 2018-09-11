package com.njz.letsgoapp.view.find;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.njz.letsgoapp.R;
import com.njz.letsgoapp.base.BaseActivity;
import com.njz.letsgoapp.bean.EmptyModel;
import com.njz.letsgoapp.bean.other.LocationModel;
import com.njz.letsgoapp.map.LocationUtil;
import com.njz.letsgoapp.mvp.find.ReleaseDynamicContract;
import com.njz.letsgoapp.mvp.find.ReleaseDynamicPresenter;
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
        getRightTv().setText("发布");
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
        locationUtil.startLocation(new LocationUtil.LocationListener() {
            @Override
            public void getAdress(int code, LocationModel adress) {
                LogUtil.e("code:" + code + " adress:" + adress);
                if(code == 0){
                    tvLocation.setText(adress.getCity()+adress.getCityChild());
                    longitude = adress.getLongitude();
                    latitude = adress.getLatitude();
                    city = adress.getCity()+adress.getCityChild();
                }
                loadingDialog.dismiss();
            }
        });
    }

    //------------附件图片
    private void initAddPhoto(){
        //------------附件
        mPhotoRecyclerView = $(R.id.recycler_view);
        photoAdapter = new PhotoAdapter(context, selectedPhotos);
        mPhotoRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(PhotoAdapter.IMAGE_LINE, OrientationHelper.VERTICAL));
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
            disposable = RxBus2.getInstance().toObservable(UpLoadPhotos.class, new Consumer<UpLoadPhotos>() {
                @Override
                public void accept(UpLoadPhotos upLoadPhotos) throws Exception {
                    submit();
                    disposable.isDisposed();
                }
            });
            loadingDialog.showDialog("正在上传中...");
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
}
