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

import java.util.LinkedHashMap;

/**
 * Created by rdcmac on 20.03.2018.
 */

public class MenuAdapter extends RecyclerView.Adapter<MenuViewHolder> {
    LinkedHashMap<Integer,String> menuItems;
    Context context;

    public MenuAdapter(Context context,LinkedHashMap<Integer, String> menuItems) {
        this.menuItems = menuItems;
        this.context=context;
    }

    @Override
    public MenuViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.menuitem, parent, false);
        return new MenuViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MenuViewHolder holder, int position) {
        holder.menuText.setText(menuItems.values().toArray()[position].toString());
        Glide.with(context).load(menuItems.keySet().toArray()[position]).into(holder.menuImage);
    }

    @Override
    public int getItemCount() {
        return menuItems.size();
    }
}

class MenuViewHolder extends RecyclerView.ViewHolder{
    TextView menuText;
    ImageView menuImage;
    MenuViewHolder(View itemView) {
        super(itemView);
        menuText=itemView.findViewById(R.id.menutext);
        menuImage=itemView.findViewById(R.id.menuimage);
    }
}