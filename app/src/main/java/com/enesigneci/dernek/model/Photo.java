package com.enesigneci.dernek.model;

import android.net.Uri;

/**
 * Created by rdcmac on 29.03.2018.
 */

public class Photo {
    String imageUrl;
    Uri imageUri;

    public Uri getImageUri() {
        return imageUri;
    }

    public void setImageUri(Uri imageUri) {
        this.imageUri = imageUri;
    }

    public Photo(Uri imageUri) {

        this.imageUri = imageUri;
    }

    public Photo() {
    }

    public Photo(String imageUrl) {

        this.imageUrl = imageUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
