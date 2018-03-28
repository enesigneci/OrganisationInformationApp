package com.enesigneci.dernek.user;

import android.os.Bundle;

import com.enesigneci.dernek.R;
import com.enesigneci.dernek.base.BaseActivity;

/**
 * Created by rdcmac on 27.03.2018.
 */

public class PhotoGalleryActivity extends BaseActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_gallery);
        setTitle("Fotoğraf Galerisi");
    }
}
