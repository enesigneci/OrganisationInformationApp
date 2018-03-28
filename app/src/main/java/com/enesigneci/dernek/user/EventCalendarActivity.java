package com.enesigneci.dernek.user;

import android.os.Bundle;
import android.widget.CalendarView;

import com.enesigneci.dernek.R;
import com.enesigneci.dernek.base.BaseActivity;

/**
 * Created by rdcmac on 27.03.2018.
 */

public class EventCalendarActivity extends BaseActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_calendar);
        setTitle("Etkinlik Takvimi");
        CalendarView calendarView=findViewById(R.id.event_calendar);
    }
}
