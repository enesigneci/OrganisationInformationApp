package com.enesigneci.dernek.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

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
}
