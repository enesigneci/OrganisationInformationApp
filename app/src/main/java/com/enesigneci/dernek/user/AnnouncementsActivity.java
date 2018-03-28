package com.enesigneci.dernek.user;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.enesigneci.dernek.R;
import com.enesigneci.dernek.adapters.AnnouncementsAdapter;
import com.enesigneci.dernek.base.BaseActivity;
import com.enesigneci.dernek.database.AppDatabase;
import com.enesigneci.dernek.listeners.RecyclerItemClickListener;

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
        final AnnouncementsAdapter announcementsAdapter=new AnnouncementsAdapter(AnnouncementsActivity.this,AppDatabase.getAppDatabase(getApplicationContext()).notificationDao().getAll());
        announcementsList.setAdapter(announcementsAdapter);
        LinearLayoutManager layoutManager=new LinearLayoutManager(AnnouncementsActivity.this,LinearLayoutManager.VERTICAL,false);
        announcementsList.setLayoutManager(layoutManager);
        announcementsAdapter.notifyDataSetChanged();
        announcementsList.addOnItemTouchListener(new RecyclerItemClickListener(AnnouncementsActivity.this, announcementsList, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(AnnouncementsActivity.this, "Deneme", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongItemClick(View view, final int position) {
                MaterialDialog.Builder materialDialogBuilder=new MaterialDialog.Builder(AnnouncementsActivity.this);
                materialDialogBuilder.onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        AppDatabase.getAppDatabase(getApplicationContext()).notificationDao().delete(announcementsAdapter.getItem(position));
                        announcementsAdapter.setNotificationList(AppDatabase.getAppDatabase(getApplicationContext()).notificationDao().getAll());
                    }
                })
                        .title("Silmek istediğinize emin misiniz?")
                        .content("Bu duyuruyu silmek istediğinize emin misiniz?")
                        .positiveText("Sil")
                        .negativeText("İptal")
                        .show();
            }
        }));
    }
}
