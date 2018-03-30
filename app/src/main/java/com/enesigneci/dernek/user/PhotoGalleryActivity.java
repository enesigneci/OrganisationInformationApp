package com.enesigneci.dernek.user;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.enesigneci.dernek.R;
import com.enesigneci.dernek.adapters.PhotoGalleryAdapter;
import com.enesigneci.dernek.admin.PhotoGalleryAdminActivity;
import com.enesigneci.dernek.base.BaseActivity;
import com.enesigneci.dernek.model.Photo;
import com.enesigneci.dernek.util.GridSpacingItemDecoration;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

/**
 * Created by rdcmac on 27.03.2018.
 */

public class PhotoGalleryActivity extends BaseActivity {
    ArrayList<Photo> photosList=new ArrayList<>();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_gallery);
        setTitle("Fotoğraf Galerisi");
        RecyclerView photoGallery=findViewById(R.id.rv_photo_gallery);
        final PhotoGalleryAdapter photoGalleryAdapter=new PhotoGalleryAdapter(PhotoGalleryActivity.this,new ArrayList<Photo>());
        GridLayoutManager gridLayoutManager=new GridLayoutManager(PhotoGalleryActivity.this,R.layout.photo_gallery_item);
        gridLayoutManager.setSpanCount(2);
        photoGallery.setLayoutManager(gridLayoutManager);
        photoGallery.addItemDecoration(new GridSpacingItemDecoration(2, getResources().getDimensionPixelSize(R.dimen.grid_layout_margin), true, 0));
        photoGallery.setAdapter(photoGalleryAdapter);
        FirebaseFirestore db=FirebaseFirestore.getInstance();
        db.collection("PhotoGallery").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                    for (QueryDocumentSnapshot document:task.getResult()){
                        photosList.add(document.toObject(Photo.class));
                        photoGalleryAdapter.setPhotoList(photosList);
                    }
                }
            }
        });
    }
}
