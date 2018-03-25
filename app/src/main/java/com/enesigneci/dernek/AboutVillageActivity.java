package com.enesigneci.dernek;

import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.enesigneci.dernek.base.BaseActivity;

/**
 * Created by rdcmac on 25.03.2018.
 */

public class AboutVillageActivity extends BaseActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_village);
        setTitle("Köy Hakkında");
        ImageView imageVillage= findViewById(R.id.iv_village);
        Glide.with(AboutVillageActivity.this).load("http://www.koyumuz.net/upload/201507214839441180.jpg").into(imageVillage);
    }
}
