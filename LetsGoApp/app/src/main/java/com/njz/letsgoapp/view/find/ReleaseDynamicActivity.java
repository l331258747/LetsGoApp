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
import com.njz.letsgoapp.util.accessory.PhotoAdapter;
import com.njz.letsgoapp.util.accessory.RecyclerItemClickListener;

import java.util.ArrayList;
import java.util.List;

import me.iwf.photopicker.PhotoPicker;
import me.iwf.photopicker.PhotoPreview;

/**
 * Created by LGQ
 * Time: 2018/9/4
 * Function:
 */

public class ReleaseDynamicActivity extends BaseActivity implements View.OnClickListener {

    EditText etContent;
    TextView tvLocation;

    private RecyclerView mPhotoRecyclerView;
    private PhotoAdapter photoAdapter;
    private ArrayList<String> selectedPhotos = new ArrayList<>();

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
    }

    @Override
    public void initData() {

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

    }
}
