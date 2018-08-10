package com.njz.letsgoapp.view.mine;

import android.content.Intent;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.njz.letsgoapp.R;
import com.njz.letsgoapp.base.BaseActivity;
import com.njz.letsgoapp.util.accessory.PhotoAdapter;
import com.njz.letsgoapp.util.accessory.RecyclerItemClickListener;

import java.util.ArrayList;
import java.util.List;

import me.iwf.photopicker.PhotoPicker;
import me.iwf.photopicker.PhotoPreview;

/**
 * Created by LGQ
 * Time: 2018/8/9
 * Function:
 */

public class FeedbackActivity extends BaseActivity {

    private RecyclerView mPhotoRecyclerView;
    private PhotoAdapter photoAdapter;
    private ArrayList<String> selectedPhotos = new ArrayList<>();


    @Override
    public int getLayoutId() {
        return R.layout.activity_feedback;
    }

    @Override
    public void initView() {
        showLeftAndTitle("意见反馈");

        initAddPhoto();

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

    }
}
