package com.enesigneci.dernek;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.enesigneci.dernek.adapters.MembersAdapter;
import com.enesigneci.dernek.base.BaseActivity;
import com.enesigneci.dernek.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rdcmac on 26.03.2018.
 */

public class MembersActivity extends BaseActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_members);
        setTitle("Dernek Üyeleri");
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        final List<User> userList = new ArrayList<User>();
        RecyclerView memberList=findViewById(R.id.rv_members);
        memberList.setLayoutManager(new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false));
        final MembersAdapter membersAdapter=new MembersAdapter(MembersActivity.this,userList);
        memberList.setAdapter(membersAdapter);
        db.collection("user").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                    for (QueryDocumentSnapshot document:task.getResult()){
                        userList.add(document.toObject(User.class));
                        membersAdapter.notifyDataSetChanged();
                    }
                }
            }
        });
    }
}
