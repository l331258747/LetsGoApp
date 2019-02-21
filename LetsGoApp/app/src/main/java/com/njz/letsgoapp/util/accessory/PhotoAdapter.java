package com.njz.letsgoapp.util.accessory;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.io.File;
import java.util.ArrayList;

import me.iwf.photopicker.utils.AndroidLifecycleUtils;

/**
 * Created by donglua on 15/5/31.
 */
public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.PhotoViewHolder> {

  private ArrayList<String> photoPaths = new ArrayList<String>();
  private LayoutInflater inflater;

  private Context mContext;

  public final static int TYPE_ADD = 1;
  public final static int TYPE_PHOTO = 2;

  public final static int IMAGE_LINE = 5;
  public final static int MAX = 6;
  public final static int MAX_4 = 4;
  private int type = 0;

  private boolean imageChange;

  public PhotoAdapter(Context mContext, ArrayList<String> photoPaths,boolean imageChange) {
    this.photoPaths = photoPaths;
    this.mContext = mContext;
    inflater = LayoutInflater.from(mContext);
    this.imageChange = imageChange;
  }

  public PhotoAdapter(Context mContext, ArrayList<String> photoPaths) {
//    this.photoPaths = photoPaths;
//    this.mContext = mContext;
//    inflater = LayoutInflater.from(mContext);
    this(mContext,photoPaths,false);
  }

  public PhotoAdapter(Context mContext, ArrayList<String> photoPaths, int type) {
    this.photoPaths = photoPaths;
    this.mContext = mContext;
    inflater = LayoutInflater.from(mContext);
    this.type = type;
  }


  @Override
  public PhotoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View itemView = null;
    switch (viewType) {
      case TYPE_ADD:
        if(imageChange){
          itemView = inflater.inflate(com.njz.letsgoapp.R.layout.item_add2, parent, false);
        }else{
          itemView = inflater.inflate(com.njz.letsgoapp.R.layout.item_add, parent, false);
        }
        break;
      case TYPE_PHOTO:
        itemView = inflater.inflate(me.iwf.photopicker.R.layout.__picker_item_photo, parent, false);
        break;
    }
    return new PhotoViewHolder(itemView);
  }


  @Override
  public void onBindViewHolder(final PhotoViewHolder holder, final int position) {
    if (getItemViewType(position) == TYPE_PHOTO) {

      String path = photoPaths.get(position);
      final Uri uri;
      if (path.startsWith("http") || path.startsWith("https")) {
        uri = Uri.parse(path);
      } else {
        uri = Uri.fromFile(new File(path));
      }

//      Uri uri = Uri.fromFile(new File(photoPaths.get(position)));

      boolean canLoadImage = AndroidLifecycleUtils.canLoadImage(holder.ivPhoto.getContext());

      if (canLoadImage) {
        Glide.with(mContext)
                .load(uri)
                .centerCrop()
                .thumbnail(0.1f)
                .placeholder(me.iwf.photopicker.R.drawable.__picker_ic_photo_black_48dp)
                .error(me.iwf.photopicker.R.drawable.__picker_ic_broken_image_black_48dp)
                .into(holder.ivPhoto);
      }
    }
  }


  @Override
  public int getItemCount() {
    if (type != 0) {
      return photoPaths.size();
    } else {
      int count = photoPaths.size() + 1;
      if (count > MAX) {
        count = MAX;
      }
      return count;
    }
  }

  @Override
  public int getItemViewType(int position) {
    if (type != 0) {
      return TYPE_PHOTO;
    } else {
      return (position == photoPaths.size() && position != MAX) ? TYPE_ADD : TYPE_PHOTO;
    }
  }

  public static class PhotoViewHolder extends RecyclerView.ViewHolder {
    private ImageView ivPhoto;
    private View vSelected;
    public PhotoViewHolder(View itemView) {
      super(itemView);
      ivPhoto   = (ImageView) itemView.findViewById(me.iwf.photopicker.R.id.iv_photo);
      vSelected = itemView.findViewById(me.iwf.photopicker.R.id.v_selected);
      if (vSelected != null) vSelected.setVisibility(View.GONE);
    }
  }

}
