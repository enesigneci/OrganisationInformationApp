package com.enesigneci.dernek;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.enesigneci.dernek.adapters.MenuAdapter;
import com.enesigneci.dernek.admin.PhotoGalleryAdminActivity;
import com.enesigneci.dernek.base.BaseActivity;
import com.enesigneci.dernek.listeners.RecyclerItemClickListener;
import com.enesigneci.dernek.user.EventCalendarActivity;
import com.enesigneci.dernek.user.ManagersActivity;
import com.enesigneci.dernek.user.MembersActivity;
import com.enesigneci.dernek.user.PhotoGalleryActivity;
import com.enesigneci.dernek.util.GridSpacingItemDecoration;

import java.util.LinkedHashMap;

/**
 * Created by rdcmac on 26.03.2018.
 */

public class AdminActivity extends BaseActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Dernek Yönetimi");
        RecyclerView menuGrid=findViewById(R.id.menugrid);
        menuGrid.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), menuGrid, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                switch (position){
                    case 0:
                        Intent photoGalleryAdminIntent=new Intent(getActivity(),PhotoGalleryAdminActivity.class);
                        startActivity(photoGalleryAdminIntent);
                        break;
                    case 1:
                        Intent eventCalendarIntent=new Intent(getActivity(),EventCalendarActivity.class);
                        startActivity(eventCalendarIntent);
                        break;
                    case 2:
                        Intent managersIntent=new Intent(getActivity(),ManagersActivity.class);
                        startActivity(managersIntent);
                        break;
                    case 3:
                        Intent organisationMembers=new Intent(getActivity(),MembersActivity.class);
                        startActivity(organisationMembers);
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
        LinkedHashMap<Integer,String> linkedHashMap=new LinkedHashMap<>();
        linkedHashMap.put(R.drawable.photo_gallery,"Fotoğraf Galerisi");
        linkedHashMap.put(R.drawable.events,"Etkinlik Takvimi");
        linkedHashMap.put(R.drawable.managers,"Dernek Yönetimi Listesi");
        linkedHashMap.put(R.drawable.members,"Dernek Üyeleri Listesi");
        MenuAdapter menuAdapter=new MenuAdapter(AdminActivity.this,linkedHashMap);
        menuGrid.setAdapter(menuAdapter);
    }
}
