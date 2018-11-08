package com.njz.letsgoapp.view.mine;

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
import com.njz.letsgoapp.mvp.other.FeedBackContract;
import com.njz.letsgoapp.mvp.other.FeedBackPresenter;
import com.njz.letsgoapp.util.accessory.ImageUtils;
import com.njz.letsgoapp.util.accessory.PhotoAdapter;
import com.njz.letsgoapp.util.accessory.RecyclerItemClickListener;
import com.njz.letsgoapp.util.dialog.LoadingDialog;
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
 * Time: 2018/8/9
 * Function:
 */

public class FeedbackActivity extends BaseActivity implements View.OnClickListener,FeedBackContract.View {

    private RecyclerView mPhotoRecyclerView;
    private PhotoAdapter photoAdapter;
    private ArrayList<String> selectedPhotos = new ArrayList<>();
    private ArrayList<String> upLoadPhotos = new ArrayList<>();

    private EditText et_explain,et_mobile;
    private TextView btn_submit;

    private FeedBackPresenter mPresenter;
    private LoadingDialog loadingDialog;
    private Disposable disposable;


    @Override
    public int getLayoutId() {
        return R.layout.activity_feedback;
    }

    @Override
    public void initView() {
        showLeftAndTitle("意见反馈");

        initAddPhoto();

        et_explain = $(R.id.et_explain);
        et_mobile = $(R.id.et_mobile);
        btn_submit = $(R.id.btn_submit);

        btn_submit.setOnClickListener(this);

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
                                    .start(FeedbackActivity.this);
                        } else {
                            PhotoPreview.builder()
                                    .setPhotos(selectedPhotos)
                                    .setCurrentItem(position)
                                    .start(FeedbackActivity.this);
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
    public void initData() {
        mPresenter = new FeedBackPresenter(context,this);
        loadingDialog = new LoadingDialog(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_submit:
                if(TextUtils.isEmpty(et_mobile.getText().toString())){
                    showShortToast("请输入联系方式!");
                    return;
                }
                if(TextUtils.isEmpty(et_explain.getText().toString())){
                    showShortToast("请输入反馈信息!");
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
        mPresenter.upUserIdea(et_mobile.getText().toString(),et_explain.getText().toString(),upLoadPhotos);
    }

    @Override
    public void upUserIdeaSuccess(EmptyModel models) {
        loadingDialog.dismiss();
        showShortToast("提交成功");
        finish();
    }

    @Override
    public void upUserIdeaFailed(String msg) {
        loadingDialog.dismiss();
        showShortToast(msg);
    }
}
