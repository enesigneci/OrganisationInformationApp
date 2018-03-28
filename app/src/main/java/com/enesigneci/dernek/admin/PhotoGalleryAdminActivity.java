package com.enesigneci.dernek.admin;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.enesigneci.dernek.R;
import com.enesigneci.dernek.base.BaseActivity;
import com.enesigneci.dernek.util.FileUtil;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.net.URISyntaxException;
import java.util.Random;

/**
 * Created by rdcmac on 27.03.2018.
 */

public class PhotoGalleryAdminActivity extends BaseActivity {
    private static final int FILE_SELECT_CODE = 0;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_gallery_admin);
        setTitle("Fotoğraf Galerisi Yönetimi");
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
                    Uri uri = data.getData();
                    Log.d("", "File Uri: " + uri.toString());
                    FirebaseStorage storage=FirebaseStorage.getInstance();
                    StorageReference storageRef= storage.getReference();
                    storageRef.child("dernek").child("PhotoGallery/"+uri.getLastPathSegment()).putFile(uri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(PhotoGalleryAdminActivity.this, "Upload completed", Toast.LENGTH_SHORT).show();
                                Log.d("Upload completed",task.getResult().getDownloadUrl().toString());
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
