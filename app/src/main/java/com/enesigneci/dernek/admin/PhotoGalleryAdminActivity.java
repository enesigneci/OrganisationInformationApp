package com.enesigneci.dernek.admin;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.enesigneci.dernek.R;
import com.enesigneci.dernek.adapters.PhotoGalleryAdminAdapter;
import com.enesigneci.dernek.base.BaseActivity;
import com.enesigneci.dernek.listeners.RecyclerItemClickListener;
import com.enesigneci.dernek.model.Photo;
import com.enesigneci.dernek.util.FileUtil;
import com.enesigneci.dernek.util.GridSpacingItemDecoration;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.net.URISyntaxException;
import java.util.ArrayList;

/**
 * Created by rdcmac on 27.03.2018.
 */

public class PhotoGalleryAdminActivity extends BaseActivity {
    private static final int FILE_SELECT_CODE = 0;
    FirebaseStorage storage=FirebaseStorage.getInstance();
    StorageReference storageRef= storage.getReference().child("dernek");
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    ArrayList<Photo> photosList=new ArrayList<>();

    PhotoGalleryAdminAdapter photoGalleryAdminAdapter=new PhotoGalleryAdminAdapter(PhotoGalleryAdminActivity.this,photosList);
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_gallery_admin);
        setTitle("Fotoğraf Galerisi Yönetimi");
        db.collection("PhotoGallery").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                    for (QueryDocumentSnapshot document:task.getResult()){
                        photosList.add(document.toObject(Photo.class));
                        photoGalleryAdminAdapter.notifyDataSetChanged();
                    }
                }
            }
        });
        RecyclerView photoGallery=findViewById(R.id.rv_photo_gallery);
        GridLayoutManager gridLayoutManager=new GridLayoutManager(PhotoGalleryAdminActivity.this,R.layout.photo_gallery_item);
        gridLayoutManager.setSpanCount(2);
        photoGallery.setLayoutManager(gridLayoutManager);
        photoGallery.addItemDecoration(new GridSpacingItemDecoration(2, getResources().getDimensionPixelSize(R.dimen.grid_layout_margin), true, 0));
        photoGallery.setAdapter(photoGalleryAdminAdapter);
        photoGallery.addOnItemTouchListener(new RecyclerItemClickListener(PhotoGalleryAdminActivity.this, photoGallery, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, final int position) {
                final MaterialDialog.Builder materialDialogBuilder=new MaterialDialog.Builder(PhotoGalleryAdminActivity.this);
                materialDialogBuilder.onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        final Photo photoToDelete=photoGalleryAdminAdapter.getItem(position);

                        FirebaseStorage storage=FirebaseStorage.getInstance();
                        StorageReference storageRef=storage.getReference("dernek").child("dernek");
                        storageRef.child(photoToDelete.getImageUrl()).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Toast.makeText(PhotoGalleryAdminActivity.this, "File Delete Completed", Toast.LENGTH_SHORT).show();
                                final FirebaseFirestore db=FirebaseFirestore.getInstance();
                                CollectionReference photoGalleryRef=db.collection("PhotoGallery");
                                Query deleteQuery=photoGalleryRef.whereEqualTo("imageUrl",photoToDelete.getImageUrl());
                                deleteQuery.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()){
                                            if (task.getResult().getDocuments().size()>0){
                                                task.getResult().getDocuments().get(0).getReference().delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if (task.isSuccessful()){
                                                            Toast.makeText(PhotoGalleryAdminActivity.this, "Delete from database completed", Toast.LENGTH_SHORT).show();
                                                            db.collection("PhotoGallery").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                                @Override
                                                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                                    if (task.isSuccessful()){
                                                                        photosList.clear();
                                                                        for (QueryDocumentSnapshot document:task.getResult()){
                                                                            photosList.add(document.toObject(Photo.class));
                                                                            photoGalleryAdminAdapter.notifyDataSetChanged();
                                                                        }
                                                                    }
                                                                }
                                                            });
                                                        }
                                                    }
                                                });
                                            }
                                        }
                                    }
                                });
                            }
                        });
                    }
                })
                        .title("Silmek istediğinize emin misiniz?")
                        .content("Bu fotoğrafı silmek istediğinize emin misiniz?")
                        .positiveText("Sil")
                        .negativeText("İptal")
                        .show();
            }

            @Override
            public void onLongItemClick(View view, int position) {

            }
        }));
        Button fileChooseButton=findViewById(R.id.button_file_choose);
        fileChooseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FileUtil.showFileChooser(getActivity());
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case FILE_SELECT_CODE:
                if (resultCode == RESULT_OK) {
                    // Get the Uri of the selected file
                    final Uri uri = data.getData();
                    Log.d("", "File Uri: " + uri.toString());

                    storageRef.child("dernek").child("PhotoGallery/"+uri.getLastPathSegment()).putFile(uri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(PhotoGalleryAdminActivity.this, "Upload completed", Toast.LENGTH_SHORT).show();
                                Log.d("Upload completed",task.getResult().getDownloadUrl().toString());
                                db.collection("PhotoGallery").add(new Photo("PhotoGallery/"+uri.getLastPathSegment())).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                    @Override
                                    public void onSuccess(DocumentReference documentReference) {
                                        Log.d("Write completed",documentReference.getPath());
                                        db.collection("PhotoGallery").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                if (task.isSuccessful()){
                                                    photosList.clear();
                                                    for (QueryDocumentSnapshot document:task.getResult()){
                                                        photosList.add(document.toObject(Photo.class));
                                                        photoGalleryAdminAdapter.notifyDataSetChanged();
                                                    }
                                                }
                                            }
                                        });
                                    }
                                });

                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d("Error",e.getMessage());
                        }
                    });
                    // Get the path
                    String path = null;
                    try {
                        path = FileUtil.getPath(this, uri);
                        Log.d("", "File Path: " + path);
                        // Get the file instance
                        // File file = new File(path);
                        // Initiate the upload
                    } catch (URISyntaxException e) {
                        e.printStackTrace();
                    }
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
