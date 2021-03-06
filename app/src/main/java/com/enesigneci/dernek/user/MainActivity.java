package com.enesigneci.dernek.user;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.enesigneci.dernek.AdminActivity;
import com.enesigneci.dernek.R;
import com.enesigneci.dernek.adapters.MenuAdapter;
import com.enesigneci.dernek.base.BaseActivity;
import com.enesigneci.dernek.listeners.RecyclerItemClickListener;
import com.enesigneci.dernek.notifications.RegistrationIntentService;
import com.enesigneci.dernek.util.GridSpacingItemDecoration;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.LinkedHashMap;

public class MainActivity extends BaseActivity {

    int isAdminCount=0;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseMessaging.getInstance().subscribeToTopic("all");
        Intent regService = new Intent(this, RegistrationIntentService.class);
        startService(regService);
        RecyclerView menuGrid=findViewById(R.id.menugrid);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,R.layout.menuitem);
        gridLayoutManager.setSpanCount(2);
        menuGrid.setLayoutManager(gridLayoutManager);
        menuGrid.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), menuGrid, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                switch (position){
                    case 0:
                        Intent aboutVillageIntent=new Intent(MainActivity.this,AboutVillageActivity.class);
                        startActivity(aboutVillageIntent);
                        break;
                    case 1:
                        Intent photoGalleryIntent=new Intent(MainActivity.this,PhotoGalleryActivity.class);
                        startActivity(photoGalleryIntent);
                        break;
                    case 2:
                        Intent eventCalendarIntent=new Intent(MainActivity.this,EventCalendarActivity.class);
                        startActivity(eventCalendarIntent);
                        break;
                    case 3:
                        Intent managersIntent=new Intent(MainActivity.this,ManagersActivity.class);
                        startActivity(managersIntent);
                        break;
                    case 4:
                        Intent organisationMembers=new Intent(MainActivity.this,MembersActivity.class);
                        startActivity(organisationMembers);
                        break;

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
        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.grid_layout_margin);
        menuGrid.addItemDecoration(new GridSpacingItemDecoration(2, spacingInPixels, true, 0));
        LinkedHashMap<Integer,String> linkedHashMap=new LinkedHashMap<>();
        linkedHashMap.put(R.drawable.about_village,"Köy Hakkında");
        linkedHashMap.put(R.drawable.photo_gallery,"Fotoğraf Galerisi");
        linkedHashMap.put(R.drawable.events,"Etkinlik Takvimi");
        linkedHashMap.put(R.drawable.managers,"Dernek Yönetimi");
        linkedHashMap.put(R.drawable.members,"Dernek Üyeleri");
        linkedHashMap.put(R.drawable.announcement,"Duyurular");
        MenuAdapter menuAdapter=new MenuAdapter(MainActivity.this,linkedHashMap);
        View titleBar=findViewById(R.id.activity_title);
        titleBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isAdminCount++;
                if(isAdminCount==5){
                    isAdminCount=0;
                    Intent adminIntent = new Intent(MainActivity.this,AdminActivity.class);
                    startActivity(adminIntent);
                }
            }
        });
        menuGrid.setAdapter(menuAdapter);
        menuAdapter.notifyDataSetChanged();

    }
}
