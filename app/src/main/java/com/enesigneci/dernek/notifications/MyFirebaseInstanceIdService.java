package com.enesigneci.dernek.notifications;

import android.content.Intent;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by rdcmac on 22.03.2018.
 */

public class MyFirebaseInstanceIdService extends FirebaseInstanceIdService {

    @Override
    public void onTokenRefresh() {
        String token = FirebaseInstanceId.getInstance().getToken();
        Log.d("FirebaseToken",token);
        Intent intent = new Intent(this, RegistrationIntentService.class);
        startService(intent);
    }

}