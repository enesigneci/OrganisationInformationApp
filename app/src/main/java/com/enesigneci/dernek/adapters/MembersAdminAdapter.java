package com.enesigneci.dernek.adapters;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.enesigneci.dernek.R;
import com.enesigneci.dernek.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;

/**
 * Created by rdcmac on 4.04.2018.
 */

public class MembersAdminAdapter extends RecyclerView.Adapter<MembersAdminViewHolder> {
    List<User> userList;
    Context context;

    public MembersAdminAdapter(Context context,List<User> userList) {
        this.userList = userList;
        this.context=context;
    }

    @Override
    public MembersAdminViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.generic_list_item, parent, false);
        return new MembersAdminViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MembersAdminViewHolder holder, int position) {
        holder.userTitle.setText(userList.get(position).getName() + " " + userList.get(position).getSurname());
        holder.userContent.setText(userList.get(position).getPhoneNumber());
        FirebaseStorage storage=FirebaseStorage.getInstance();
        StorageReference storageRef=storage.getReference().child("dernek").child("dernek");
        storageRef.child(userList.get(position).getPhoto()).getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()){
                    Glide.with(context).load(task.getResult()).into(holder.userPhoto);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }
    public User getItem(int position){
        return userList.get(position);
    }
    public void setUserList(List<User> list){
        userList=list;
        notifyDataSetChanged();
    }
}

class MembersAdminViewHolder extends RecyclerView.ViewHolder{
    TextView userTitle;
    TextView userContent;
    ImageView userPhoto;
    MembersAdminViewHolder(View itemView) {
        super(itemView);
        userTitle=itemView.findViewById(R.id.title);
        userContent=itemView.findViewById(R.id.content);
        userPhoto=itemView.findViewById(R.id.photo);
    }
}
