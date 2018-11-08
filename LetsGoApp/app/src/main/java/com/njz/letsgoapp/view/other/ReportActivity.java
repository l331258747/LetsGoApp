package com.njz.letsgoapp.view.other;

import android.content.Intent;
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
import com.njz.letsgoapp.mvp.other.ReportContract;
import com.njz.letsgoapp.mvp.other.ReportPresenter;
import com.njz.letsgoapp.util.accessory.ImageUtils;
import com.njz.letsgoapp.util.accessory.PhotoAdapter;
import com.njz.letsgoapp.util.accessory.RecyclerItemClickListener;
import com.njz.letsgoapp.util.dialog.LoadingDialog;
import com.njz.letsgoapp.util.photo.TackPicturesUtil;
import com.njz.letsgoapp.util.rxbus.RxBus2;
import com.njz.letsgoapp.util.rxbus.busEvent.UpLoadPhotos;
import com.njz.letsgoapp.util.thread.MyThreadPool;
import com.njz.letsgoapp.widget.ReportSelectView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import me.iwf.photopicker.PhotoPicker;
import me.iwf.photopicker.PhotoPreview;

/**
 * Created by LGQ
 * Time: 2018/8/16
 * Function:
 */

public class ReportActivity extends BaseActivity implements View.OnClickListener,ReportContract.View {

    private RecyclerView mPhotoRecyclerView;
    private PhotoAdapter photoAdapter;
    private ArrayList<String> selectedPhotos = new ArrayList<>();
    private ArrayList<String> upLoadPhotos = new ArrayList<>();

    EditText et_special;
    ReportSelectView report_select_view;
    TextView tv_submit;

    private LoadingDialog loadingDialog;
    private Disposable disposable;
    private ReportPresenter mPresenter;

    private int reportId;
    private int reportClass;

    @Override
    public void getIntentData() {
        super.getIntentData();
        reportId = intent.getIntExtra("reportId",0);
        reportClass = intent.getIntExtra("reportClass",0);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_report;
    }

    @Override
    public void initView() {
        showLeftAndTitle("举报");

        initAddPhoto();

        et_special = $(R.id.et_special);
        report_select_view = $(R.id.report_select_view);
        tv_submit = $(R.id.tv_submit);

        tv_submit.setOnClickListener(this);
    }

    @Override
    public void initData() {
        mPresenter = new ReportPresenter(context,this);
        loadingDialog = new LoadingDialog(this);

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
                                    .setPhotoCount(PhotoAdapter.MAX_4)
                                    .setShowCamera(true)
                                    .setPreviewEnabled(false)
                                    .setSelected(selectedPhotos)
                                    .start(activity);
                        } else {
                            PhotoPreview.builder()
                                    .setPhotos(selectedPhotos)
                                    .setCurrentItem(position)
                                    .start(activity);
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
        switch (v.getId()){
            case R.id.tv_submit:
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

                break;
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

    public void submit(){
        mPresenter.upUserReport(reportId,et_special.getText().toString(),report_select_view.getContent(),reportClass,upLoadPhotos);
    }

    @Override
    public void upUserReportSuccess(EmptyModel models) {
        loadingDialog.dismiss();
        showShortToast("举报成功");
        finish();
    }

    @Override
    public void upUserReportFailed(String msg) {
        loadingDialog.dismiss();
        showShortToast(msg);
    }
}
