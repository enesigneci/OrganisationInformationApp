package com.enesigneci.dernek;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.enesigneci.dernek.adapters.AnnouncementsAdapter;
import com.enesigneci.dernek.base.BaseActivity;
import com.enesigneci.dernek.database.AppDatabase;

/**
 * Created by rdcmac on 23.03.2018.
 */

public class AnnouncementsActivity extends BaseActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_announcements);
        setTitle("Duyurular");
        RecyclerView announcementsList=findViewById(R.id.rv_announcements);
        AnnouncementsAdapter announcementsAdapter=new AnnouncementsAdapter(AnnouncementsActivity.this,AppDatabase.getAppDatabase(getApplicationContext()).notificationDao().getAll());
        announcementsList.setAdapter(announcementsAdapter);
        LinearLayoutManager layoutManager=new LinearLayoutManager(AnnouncementsActivity.this,LinearLayoutManager.VERTICAL,false);
        announcementsList.setLayoutManager(layoutManager);
        announcementsAdapter.notifyDataSetChanged();
    }
}
