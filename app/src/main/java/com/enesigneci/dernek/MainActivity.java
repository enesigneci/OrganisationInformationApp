package com.enesigneci.dernek;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.enesigneci.dernek.adapters.MenuAdapter;
import com.enesigneci.dernek.base.BaseActivity;
import com.enesigneci.dernek.listeners.RecyclerItemClickListener;
import com.enesigneci.dernek.notifications.RegistrationIntentService;
import com.enesigneci.dernek.util.GridSpacingItemDecoration;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.LinkedHashMap;

public class MainActivity extends BaseActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseMessaging.getInstance().subscribeToTopic("all");
        Intent regService = new Intent(this, RegistrationIntentService.class);
        startService(regService);
        RecyclerView menuGrid=findViewById(R.id.menugrid);
        menuGrid.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), menuGrid, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                switch (position){
                    case 5:
                        Intent announcementsIntent=new Intent(MainActivity.this,AnnouncementsActivity.class);
                        startActivity(announcementsIntent);
                        break;
                }
            }

            @Override
            public void onLongItemClick(View view, int position) {

            }
        }));
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,R.layout.menuitem);
        gridLayoutManager.setSpanCount(2);
        menuGrid.setLayoutManager(gridLayoutManager);
        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.grid_layout_margin);
        menuGrid.addItemDecoration(new GridSpacingItemDecoration(2, spacingInPixels, true, 0));
        //new String[][]{{"Köy Hakkında","R.drawable.about_village"},{"Fotoğraf Galerisi","R.drawable.photo_gallery"},{"Etkinlik Takvimi","R.drawable.announcement"},{"Dernek Yönetimi","R.drawable.managers"}, {"Dernek Üyeleri","R.drawable.members"}, {"Duyurular","R.drawable.announcements"}}
        LinkedHashMap<Integer,String> linkedHashMap=new LinkedHashMap<>();
        linkedHashMap.put(R.drawable.about_village,"Köy Hakkında");
        linkedHashMap.put(R.drawable.photo_gallery,"Fotoğraf Galerisi");
        linkedHashMap.put(R.drawable.events,"Etkinlik Takvimi");
        linkedHashMap.put(R.drawable.managers,"Dernek Yönetimi");
        linkedHashMap.put(R.drawable.members,"Dernek Üyeleri");
        linkedHashMap.put(R.drawable.announcement,"Duyurular");
        MenuAdapter menuAdapter=new MenuAdapter(MainActivity.this,linkedHashMap);
        menuGrid.setAdapter(menuAdapter);
        menuAdapter.notifyDataSetChanged();

    }
}
