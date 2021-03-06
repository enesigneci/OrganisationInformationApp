package com.enesigneci.dernek.adapters;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.enesigneci.dernek.R;
import com.enesigneci.dernek.model.Photo;
import com.enesigneci.dernek.model.User;
import com.enesigneci.dernek.util.SquareImageView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;

/**
 * Created by rdcmac on 27.03.2018.
 */

public class PhotoGalleryAdapter extends RecyclerView.Adapter<PhotoGalleryViewHolder> {
    List<Photo> photoList;
    Context context;

    public PhotoGalleryAdapter(Context context,List<Photo> photoList) {
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
    public void onBindViewHolder(final PhotoGalleryViewHolder holder, int position) {
        FirebaseStorage storage=FirebaseStorage.getInstance();
        StorageReference storageRef=storage.getReference().child("dernek").child("dernek");
        storageRef.child(photoList.get(position).getImageUrl()).getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()){
                    Glide.with(context).load(task.getResult()).into(holder.photo);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return photoList.size();
    }
    public Photo getItem(int position){
        return photoList.get(position);
    }
    public void setPhotoList(List<Photo> list){
        photoList=list;
        notifyDataSetChanged();
    }
}

class PhotoGalleryViewHolder extends RecyclerView.ViewHolder{
    SquareImageView photo;
    PhotoGalleryViewHolder(View itemView) {
        super(itemView);
        photo=itemView.findViewById(R.id.photo);
    }
}
