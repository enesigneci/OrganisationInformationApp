package com.enesigneci.dernek.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.enesigneci.dernek.R;
import com.enesigneci.dernek.model.User;

import java.util.List;

/**
 * Created by rdcmac on 27.03.2018.
 */

public class PhotoGalleryAdapter extends RecyclerView.Adapter<PhotoGalleryViewHolder> {
    List<User> photoList;
    Context context;

    public PhotoGalleryAdapter(Context context,List<User> photoList) {
        this.photoList = photoList;
        this.context=context;
    }

    @Override
    public PhotoGalleryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.photo_gallery_item, parent, false);
        return new PhotoGalleryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PhotoGalleryViewHolder holder, int position) {
        Glide.with(context).load(photoList.get(position).getPhoto()).into(holder.photo);
    }

    @Override
    public int getItemCount() {
        return photoList.size();
    }
    public User getItem(int position){
        return photoList.get(position);
    }
    public void setPhotoList(List<User> list){
        photoList=list;
        notifyDataSetChanged();
    }
}

class PhotoGalleryViewHolder extends RecyclerView.ViewHolder{
    ImageView photo;
    PhotoGalleryViewHolder(View itemView) {
        super(itemView);
        photo=itemView.findViewById(R.id.photo);
    }
}
