package com.njz.letsgoapp.view.order;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.EditText;

import com.njz.letsgoapp.R;
import com.njz.letsgoapp.base.BaseActivity;
import com.njz.letsgoapp.bean.EmptyModel;
import com.njz.letsgoapp.bean.order.EvaluateTypeModel;
import com.njz.letsgoapp.mvp.order.OrderEvaluateContract;
import com.njz.letsgoapp.mvp.order.OrderEvaluatePresenter;
import com.njz.letsgoapp.util.accessory.ImageUtils;
import com.njz.letsgoapp.util.accessory.PhotoAdapter;
import com.njz.letsgoapp.util.accessory.RecyclerItemClickListener;
import com.njz.letsgoapp.util.dialog.LoadingDialog;
import com.njz.letsgoapp.util.photo.TackPicturesUtil;
import com.njz.letsgoapp.util.rxbus.RxBus2;
import com.njz.letsgoapp.util.rxbus.busEvent.UpLoadPhotos;
import com.njz.letsgoapp.util.thread.MyThreadPool;
import com.njz.letsgoapp.widget.EvaluateView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import me.iwf.photopicker.PhotoPicker;
import me.iwf.photopicker.PhotoPreview;

/**
 * Created by LGQ
 * Time: 2018/9/11
 * Function:
 */

public class OrderEvaluateActivity extends BaseActivity implements View.OnClickListener, OrderEvaluateContract.View {

    private RecyclerView mPhotoRecyclerView;
    private PhotoAdapter photoAdapter;
    private ArrayList<String> selectedPhotos = new ArrayList<>();
    private ArrayList<String> upLoadPhotos = new ArrayList<>();
    private Disposable disposable;
    private LoadingDialog loadingDialog;

    private EvaluateView ev_guide, ev_car, ev_substituting, ev_trip;
    private EditText et_special;

    private OrderEvaluatePresenter mPresenter;

    private int orderId;
    private int guideId;
    private EvaluateTypeModel evaluateTypeModel;

    @Override
    public void getIntentData() {
        super.getIntentData();
        orderId = intent.getIntExtra("ORDER_ID", 0);
        guideId = intent.getIntExtra("GUIDE_ID", 0);
        evaluateTypeModel = intent.getParcelableExtra("evaluateType");
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_order_evaluate;
    }

    @Override
    public void initView() {
        showLeftAndTitle("评价");

        showRightTv();
        getRightTv().setText("提交");
        getRightTv().setOnClickListener(this);
        getRightTv().setTextColor(ContextCompat.getColor(context, R.color.color_theme));

        ev_guide = $(R.id.ev_guide);
        ev_car = $(R.id.ev_car);
        ev_substituting = $(R.id.ev_substituting);
        ev_trip = $(R.id.ev_trip);
        et_special = $(R.id.et_special);

        initAddPhoto();

        if(!evaluateTypeModel.isCar()){
            ev_car.setVisibility(View.GONE);
            ev_car.getRatingBar().setRating(0);
        }
        if(!evaluateTypeModel.isGuide()){
            ev_guide.setVisibility(View.GONE);
            ev_guide.getRatingBar().setRating(0);
        }
        if(!evaluateTypeModel.isTravel()){
            ev_substituting.setVisibility(View.GONE);
            ev_substituting.getRatingBar().setRating(0);
        }
        if(!evaluateTypeModel.isTrip()){
            ev_trip.setVisibility(View.GONE);
            ev_trip.getRatingBar().setRating(0);
        }

    }

    @Override
    public void initData() {
        loadingDialog = new LoadingDialog(this);

        mPresenter = new OrderEvaluatePresenter(context,this);
    }


    //------------附件图片
    private void initAddPhoto() {
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
        if (v.getId() == R.id.right_tv) {
            disposable = RxBus2.getInstance().toObservable(UpLoadPhotos.class, new Consumer<UpLoadPhotos>() {
                @Override
                public void accept(UpLoadPhotos upLoadPhotos) throws Exception {
                    submit();
                    disposable.dispose();
                }
            });
            loadingDialog.showDialog("正在上传中...");
            compressImage();

        }
    }

    private void submit() {
        mPresenter.upUserReview(orderId, guideId, ev_guide.getRating(),
                ev_car.getRating(), ev_substituting.getRating(),
                ev_trip.getRating(), et_special.getText().toString(),
                upLoadPhotos);
    }

    private void compressImage() {
        MyThreadPool.getInstance().submit(new Runnable() {
            @Override
            public void run() {
                upLoadPhotos.clear();
                for (String path : selectedPhotos) {
                    File file = new File(path);
                    if (!file.getName().startsWith("crop") || file.length() > 1024 * 100) {
                        String savePath = TackPicturesUtil.IMAGE_CACHE_PATH + "crop" + file.getName();
                        ImageUtils.getImage(path, savePath);
                        upLoadPhotos.add(savePath);
                    } else {
                        upLoadPhotos.add(path);
                    }
                }
                RxBus2.getInstance().post(new UpLoadPhotos());
            }
        });
    }

    @Override
    public void upUserReviewSuccess(EmptyModel str) {
        loadingDialog.dismiss();
        showShortToast("评价成功");
        finish();
    }

    @Override
    public void upUserReviewFailed(String msg) {
        loadingDialog.dismiss();
        showShortToast(msg);
    }
}
