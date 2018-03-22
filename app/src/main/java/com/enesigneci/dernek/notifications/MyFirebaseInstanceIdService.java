package com.enesigneci.dernek.notifications;

import android.content.Intent;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by rdcmac on 22.03.2018.
 */

public class MyFirebaseInstanceIdService extends FirebaseInstanceIdService {

    private static final String TAG = "MyFirebaseIIDService";

    @Override
    public void onTokenRefresh() {
        String token = FirebaseInstanceId.getInstance().getToken();
        Log.d("FirebaseToken",token);
//        InstanceID instanceID = InstanceID.getInstance(this);
//        String token = instanceID.getToken(Constants.SENDER_ID, FirebaseMessaging.INSTANCE_ID_SCOPE, null);
        sendRegistrationToServer(token);
        Intent intent = new Intent(this, RegistrationIntentService.class);
        startService(intent);
    }

    private void sendRegistrationToServer(String token) {
        // token'ı servise gönderme işlemlerini bu methodda yapmalısınız
    }

}