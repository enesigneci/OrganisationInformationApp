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

import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by rdcmac on 23.03.2018.
 */

public class AnnouncementsAdapter extends RecyclerView.Adapter<AnnouncementsViewHolder> {
    List<Notification> notificationList;
    Context context;

    public AnnouncementsAdapter(Context context,List<Notification> notificationList) {
        this.notificationList = notificationList;
        this.context=context;
    }

    @Override
    public AnnouncementsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.application_notification_layout_big, parent, false);
        return new AnnouncementsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AnnouncementsViewHolder holder, int position) {
        holder.notificationTitle.setText(notificationList.get(position).getTitle());
        holder.notificationContent.setText(notificationList.get(position).getDescription());
        Glide.with(context).load(R.drawable.announcement).into(holder.notificationIcon);
    }

    @Override
    public int getItemCount() {
        return notificationList.size();
    }
}

class AnnouncementsViewHolder extends RecyclerView.ViewHolder{
    TextView notificationTitle;
    TextView notificationContent;
    ImageView notificationIcon;
    AnnouncementsViewHolder(View itemView) {
        super(itemView);
        notificationTitle=itemView.findViewById(R.id.notification_title);
        notificationContent=itemView.findViewById(R.id.notification_content);
        notificationIcon=itemView.findViewById(R.id.notification_icon);
    }
}
