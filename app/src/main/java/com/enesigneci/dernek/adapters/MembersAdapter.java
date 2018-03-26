package com.enesigneci.dernek.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.enesigneci.dernek.R;
import com.enesigneci.dernek.model.Notification;
import com.enesigneci.dernek.model.User;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by rdcmac on 23.03.2018.
 */

public class MembersAdapter extends RecyclerView.Adapter<MembersViewHolder> {
    List<User> userList;
    Context context;

    public MembersAdapter(Context context,List<User> userList) {
        this.userList = userList;
        this.context=context;
    }

    @Override
    public MembersViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.generic_list_item, parent, false);
        return new MembersViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MembersViewHolder holder, int position) {
        holder.userTitle.setText(userList.get(position).getName() + " " + userList.get(position).getSurname());
        holder.userContent.setText(userList.get(position).getPhoneNumber());
        Glide.with(context).load(userList.get(position).getPhoto()).into(holder.userPhoto);
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

class MembersViewHolder extends RecyclerView.ViewHolder{
    TextView userTitle;
    TextView userContent;
    ImageView userPhoto;
    MembersViewHolder(View itemView) {
        super(itemView);
        userTitle=itemView.findViewById(R.id.title);
        userContent=itemView.findViewById(R.id.content);
        userPhoto=itemView.findViewById(R.id.photo);
    }
}
