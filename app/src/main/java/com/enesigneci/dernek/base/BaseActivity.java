package com.enesigneci.dernek.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.enesigneci.dernek.R;

/**
 * Created by rdcmac on 20.03.2018.
 */

public class BaseActivity extends AppCompatActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
            if (getSupportActionBar()!=null)
                getSupportActionBar().hide();
    }
    public void setTitle(String title){
        TextView activityTitle=findViewById(R.id.activity_title);
        activityTitle.setText(title);
    }
    public Activity getActivity(){
        return BaseActivity.this;
    }
}
