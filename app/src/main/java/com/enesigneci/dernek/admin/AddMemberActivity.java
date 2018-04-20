package com.enesigneci.dernek.admin;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.enesigneci.dernek.R;
import com.enesigneci.dernek.base.BaseActivity;
import com.enesigneci.dernek.model.User;
import com.enesigneci.dernek.util.FileUtil;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by rdcmac on 16.04.2018.
 */

public class AddMemberActivity extends BaseActivity {
    private static final int FILE_SELECT_CODE = 0;
    private static final int REQ_CAMERA = 2;
    private String mCurrentPhotoPath;
    private Uri photoUriToSave;

    FirebaseStorage storage=FirebaseStorage.getInstance();
    StorageReference storageRef= storage.getReference().child("dernek");
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    User user=new User();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_member);
        setTitle("Üye Ekle");
        Button photo=findViewById(R.id.photo);
        photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText name=findViewById(R.id.name);
                EditText surname=findViewById(R.id.surname);
                EditText phone=findViewById(R.id.phone);
                EditText tcId=findViewById(R.id.tcid);
                EditText address=findViewById(R.id.address);
                if (!name.getText().toString().isEmpty() && !surname.getText().toString().isEmpty() && !phone.getText().toString().isEmpty() && !tcId.getText().toString().isEmpty() && !address.getText().toString().isEmpty()){
                    user.setName(name.getText().toString());
                    user.setSurname(surname.getText().toString());
                    user.setTCId(tcId.getText().toString());
                    user.setPhoneNumber(phone.getText().toString());
                    user.setAddress(address.getText().toString());
                }
                FileUtil.showFileChooser(getActivity());
            }
        });
        Button camera=findViewById(R.id.camera);
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText name=findViewById(R.id.name);
                EditText surname=findViewById(R.id.surname);
                EditText phone=findViewById(R.id.phone);
                EditText tcId=findViewById(R.id.tcid);
                EditText address=findViewById(R.id.address);
                if (!name.getText().toString().isEmpty() && !surname.getText().toString().isEmpty() && !phone.getText().toString().isEmpty() && !tcId.getText().toString().isEmpty() && !address.getText().toString().isEmpty()){
                    user.setName(name.getText().toString());
                    user.setSurname(surname.getText().toString());
                    user.setTCId(tcId.getText().toString());
                    user.setPhoneNumber(phone.getText().toString());
                    user.setAddress(address.getText().toString());
                    openCamera();
                }
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

                    storageRef.child("dernek").child("User/"+uri.getLastPathSegment()).putFile(uri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(AddMemberActivity.this, "Upload completed", Toast.LENGTH_SHORT).show();
                                Log.d("Upload completed",task.getResult().getDownloadUrl().toString());
                                user.setPhoto("User/"+uri.getLastPathSegment());
                                db.collection("user").add(user).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                    @Override
                                    public void onSuccess(DocumentReference documentReference) {
                                        Log.d("Write completed",documentReference.getPath());
                                        db.collection("user").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                if (task.isSuccessful()){
                                                    startActivity(new Intent(AddMemberActivity.this,MembersAdminActivity.class));
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
            case REQ_CAMERA:
                if (resultCode != 0) {
                    Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                    File f = new File(mCurrentPhotoPath);
                    Uri contentUri = Uri.fromFile(f);
                    mediaScanIntent.setData(contentUri);
                    getActivity().sendBroadcast(mediaScanIntent);

                    final Uri uri = contentUri;
                    Log.d("", "File Uri: " + uri.toString());

                    storageRef.child("dernek").child("User/"+uri.getLastPathSegment()).putFile(uri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(AddMemberActivity.this, "Upload completed", Toast.LENGTH_SHORT).show();
                                Log.d("Upload completed",task.getResult().getDownloadUrl().toString());
                                user.setPhoto("User/"+uri.getLastPathSegment());
                                db.collection("user").add(user).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                    @Override
                                    public void onSuccess(DocumentReference documentReference) {
                                        Log.d("Write completed",documentReference.getPath());
                                        db.collection("user").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                if (task.isSuccessful()){
                                                    startActivity(new Intent(AddMemberActivity.this,MembersAdminActivity.class));
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
    private void openCamera() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        takePictureIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        takePictureIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoUriToSave = FileProvider.getUriForFile(getActivity(),
                        "com.enesigneci.dernek",
                        createImageFile());
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUriToSave);
                startActivityForResult(takePictureIntent, REQ_CAMERA);
            } catch (IOException ex) {

            }
        }
    }
    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }


}
