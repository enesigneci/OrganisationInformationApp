package com.enesigneci.dernek.user;

import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.enesigneci.dernek.R;
import com.enesigneci.dernek.admin.AddEventToCalendarActivity;
import com.enesigneci.dernek.base.BaseActivity;
import com.enesigneci.dernek.model.Event;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.roomorama.caldroid.CaldroidFragment;
import com.roomorama.caldroid.CaldroidListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.SimpleTimeZone;

/**
 * Created by rdcmac on 27.03.2018.
 */

public class EventCalendarActivity extends BaseActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_calendar);
        setTitle("Etkinlik Takvimi");
        final SimpleDateFormat formatter=new SimpleDateFormat("dd/MM/YYYY");
        final CaldroidFragment caldroidFragment = new CaldroidFragment();
        Bundle args = new Bundle();
        Calendar cal = Calendar.getInstance();
        args.putInt(CaldroidFragment.MONTH, cal.get(Calendar.MONTH) + 1);
        args.putInt(CaldroidFragment.YEAR, cal.get(Calendar.YEAR));
        FirebaseFirestore db=FirebaseFirestore.getInstance();
        final List<Event> eventList=new ArrayList<>();
        db.collection("event").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                    for (Event event:task.getResult().toObjects(Event.class)){
                        eventList.add(event);
                        caldroidFragment.setBackgroundDrawableForDate(getResources().getDrawable(R.drawable.ic_notification),event.getWhen());
                    }
                    caldroidFragment.refreshView();
                }
            }
        });
        CaldroidListener listener = new CaldroidListener() {

            @Override
            public void onSelectDate(Date date, View view) {
                for (Event event: eventList){
                    if (event.getWhen().equals(date)){
                        MaterialDialog dialog=new MaterialDialog.Builder(EventCalendarActivity.this).customView(R.layout.show_event_dialog,true).build();
                        TextView type=dialog.getCustomView().findViewById(R.id.type);
                        TextView who=dialog.getCustomView().findViewById(R.id.who);
                        TextView where=dialog.getCustomView().findViewById(R.id.where);
                        if (event.getType()==-1){
                            type.setText("Düğün");
                        }else{
                            type.setText("Nişan");
                        }
                        who.setText(event.getWho());
                        where.setText(event.getWhere());
                        dialog.getCustomView().setPadding(0,0,0,0);
                        dialog.getCustomView().setTop(0);
                        dialog.getCustomView().setBottom(0);
                        dialog.getCustomView().setLeft(0);
                        dialog.getCustomView().setRight(0);
                        dialog.show();
                    }
                }
            }

            @Override
            public void onChangeMonth(int month, int year) {
                String text = "month: " + month + " year: " + year;
                Toast.makeText(getApplicationContext(), text,
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongClickDate(Date date, View view) {
                Toast.makeText(getApplicationContext(),
                        "Long click " + formatter.format(date),
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCaldroidViewCreated() {
                Toast.makeText(getApplicationContext(),
                        "Caldroid view is created",
                        Toast.LENGTH_SHORT).show();
            }

        };


        caldroidFragment.setArguments(args);
        caldroidFragment.setCaldroidListener(listener);
        Locale locale = new Locale("tr", "TR");
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
        FragmentTransaction t = getSupportFragmentManager().beginTransaction();
        t.replace(R.id.event_calendar, caldroidFragment);
        t.commit();
    }
}
