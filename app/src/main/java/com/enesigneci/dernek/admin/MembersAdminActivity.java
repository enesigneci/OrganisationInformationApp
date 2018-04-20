package com.enesigneci.dernek.admin;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.enesigneci.dernek.R;
import com.enesigneci.dernek.adapters.MembersAdminAdapter;
import com.enesigneci.dernek.base.BaseActivity;
import com.enesigneci.dernek.listeners.RecyclerItemClickListener;
import com.enesigneci.dernek.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;

/**
 * Created by rdcmac on 4.04.2018.
 */

public class MembersAdminActivity extends BaseActivity {
    ArrayList<User> usersList=new ArrayList<>();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_members_admin);
        setTitle("Dernek Üyeleri Yönetimi");
        Button addNewMemberButton=findViewById(R.id.add_member);
        addNewMemberButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MembersAdminActivity.this,AddMemberActivity.class));
            }
        });
        final RecyclerView membersList=findViewById(R.id.members_list);
        FirebaseFirestore db=FirebaseFirestore.getInstance();
        final FirebaseStorage storage=FirebaseStorage.getInstance();
        membersList.setLayoutManager(new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false));
        final MembersAdminAdapter membersAdminAdapter=new MembersAdminAdapter(MembersAdminActivity.this,usersList);
        membersList.setAdapter(membersAdminAdapter);
        db.collection("user").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                    for (QueryDocumentSnapshot document:task.getResult()){
                        usersList.add(document.toObject(User.class));
                        membersAdminAdapter.notifyDataSetChanged();
                    }
                }
            }
        });
        membersList.addOnItemTouchListener(new RecyclerItemClickListener(MembersAdminActivity.this, membersList, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

            }

            @Override
            public void onLongItemClick(View view, final int position) {
                MaterialDialog.Builder materialDialogBuilder = new MaterialDialog.Builder(MembersAdminActivity.this);
                materialDialogBuilder.title("Üye Silme İşlemi");
                materialDialogBuilder.content("Bu üyeyi silmek istediğinize emin misiniz?");
                materialDialogBuilder.positiveText("Evet");
                materialDialogBuilder.negativeText("Hayır");
                materialDialogBuilder.onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        final FirebaseFirestore db=FirebaseFirestore.getInstance();
                        final User userToDelete=membersAdminAdapter.getItem(position);
                        db.collection("user").whereEqualTo("tcid",userToDelete.getTCId()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull final Task<QuerySnapshot> fullDeleteTask) {
                                if (fullDeleteTask.isSuccessful()){
                                    if (fullDeleteTask.getResult().getDocuments().size()>0){
                                        storage.getReference().child("dernek").child("dernek").child(userToDelete.getPhoto()).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()){
                                                    fullDeleteTask.getResult().getDocuments().get(0).getReference().delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {
                                                            if (task.isSuccessful()){
                                                                Toast.makeText(MembersAdminActivity.this, "Üye silindi", Toast.LENGTH_SHORT).show();
                                                                db.collection("user").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                                    @Override
                                                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                                        if (task.isSuccessful()){
                                                                            usersList.clear();
                                                                            for (QueryDocumentSnapshot document:task.getResult()){
                                                                                usersList.add(document.toObject(User.class));
                                                                            }
                                                                            membersAdminAdapter.setUserList(usersList);
                                                                        }
                                                                    }
                                                                });
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
                MaterialDialog dialog=materialDialogBuilder.build();
                dialog.show();
            }
        }));
    }
}
