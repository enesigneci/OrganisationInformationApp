package com.enesigneci.dernek.admin;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentTransaction;
import android.text.InputType;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.enesigneci.dernek.R;
import com.enesigneci.dernek.base.BaseActivity;
import com.enesigneci.dernek.model.Event;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.roomorama.caldroid.CaldroidFragment;
import com.roomorama.caldroid.CaldroidListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by rdcmac on 19.04.2018.
 */

public class AddEventToCalendarActivity extends BaseActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event_to_calendar);
        setTitle("Etkinlik Ekle");
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
                        caldroidFragment.setTextColorForDate(Color.RED,event.getWhen());
                    }
                    caldroidFragment.refreshView();
                }
            }
        });
        CaldroidListener listener = new CaldroidListener() {

            @Override
            public void onSelectDate(final Date date, View view) {
                for (Event event: eventList){
                    if (event.getWhen().equals(date)){
                        MaterialDialog dialog=new MaterialDialog.Builder(AddEventToCalendarActivity.this).customView(R.layout.show_event_dialog,true).build();
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
                    }else{
                        final FirebaseFirestore db= FirebaseFirestore.getInstance();
                        MaterialDialog dialog=new MaterialDialog.Builder(getActivity()).title("Etkinlik Detayları")
                                .customView(R.layout.add_event_dialog,true).onPositive(new MaterialDialog.SingleButtonCallback() {
                                    @Override
                                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                        EditText whoET=dialog.getCustomView().findViewById(R.id.who);
                                        EditText whereET=dialog.getCustomView().findViewById(R.id.where);
                                        RadioGroup typeRG=dialog.getCustomView().findViewById(R.id.type);
                                        String who=whoET.getText().toString();
                                        String where=whereET.getText().toString();
                                        int type = typeRG.indexOfChild(findViewById(typeRG.getCheckedRadioButtonId()));
                                        if (!who.isEmpty() && !where.isEmpty()){
                                            db.collection("event").add(new Event(type,who,where,date)).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                                @Override
                                                public void onComplete(@NonNull Task<DocumentReference> task) {
                                                    if (task.isSuccessful()){
                                                        Toast.makeText(AddEventToCalendarActivity.this, "Etkinlik eklendi", Toast.LENGTH_SHORT).show();
                                                    }
                                                }
                                            }).addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Log.d("Error",e.getMessage());
                                                }
                                            });
                                        }
                                    }
                                }).build();
                        dialog.setActionButton(DialogAction.POSITIVE,"Etkinlik Takvimine Ekle");
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
    public static float convertPixelsToDp(float px, Context context){
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float dp = px / ((float)metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return dp;
    }
    public static float convertDpToPixel(float dp, Context context){
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float px = dp * ((float)metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return px;
    }
}
