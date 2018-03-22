package com.enesigneci.dernek.notifications;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.RemoteViews;

import com.enesigneci.dernek.MainActivity;
import com.enesigneci.dernek.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyFirebaseMessagingService extends FirebaseMessagingService{
    public static final String TAG = MyFirebaseMessagingService.class.getSimpleName();
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        Log.d(TAG, "Message data payload: " + remoteMessage.getData());
        String smallTitle=remoteMessage.getData().get("type")+" duyurusu: "+remoteMessage.getData().get("date");
        String bigTitle=remoteMessage.getData().get("who")+ " üyemizin " +remoteMessage.getData().get("type")+" duyurusu: "+remoteMessage.getData().get("date");
        String smallDescription=remoteMessage.getData().get("type")+" duyurusu: "+remoteMessage.getData().get("date");
        String bigDescription=remoteMessage.getData().get("who")+" adlı üyemizin "+remoteMessage.getData().get("date")+" tarihinde "+remoteMessage.getData().get("address")+" isimli mekanda "+remoteMessage.getData().get("type")+" daveti vardır.";
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("Message", smallTitle);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
                intent, PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri = RingtoneManager
                .getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Bitmap bitmapIcon = BitmapFactory.decodeResource(getApplicationContext().getResources(),
                R.drawable.announcement);

        RemoteViews contentView = new RemoteViews(getApplicationContext().getPackageName(), R.layout.application_notification_layout_big);
        contentView.setImageViewResource(R.id.notification_icon, R.drawable.announcement);
        contentView.setTextViewText(R.id.notification_title, bigTitle);
        contentView.setTextViewText(R.id.notification_content, bigDescription);


        // Sets an ID for the notification, so it can be updated.
        int notifyID = 1;
        String CHANNEL_ID = "my_channel_01";// The id of the channel.
        CharSequence name = "general";// The user-visible name of the channel.
        int importance = NotificationManager.IMPORTANCE_HIGH;


        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(getApplicationContext())
                        .setSmallIcon(R.drawable.announcement)
                        .setLargeIcon(bitmapIcon)
                        .setColor(getColor(R.color.colorPrimary))
                        .setAutoCancel(true)
                        .setSound(defaultSoundUri)
                        .setContentIntent(pendingIntent)
                        .setChannelId(CHANNEL_ID)
                        .setContent(contentView)
                        .setStyle(new NotificationCompat.BigTextStyle().bigText(bigDescription))
                        .setContentTitle(bigTitle);

        android.app.Notification notification = notificationBuilder.build();
        notification.flags |= android.app.Notification.FLAG_AUTO_CANCEL;
        notification.defaults |= android.app.Notification.DEFAULT_SOUND;
        notification.defaults |= android.app.Notification.DEFAULT_VIBRATE;


        NotificationManager notificationManager =
                (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID, name, importance);
            notificationManager.createNotificationChannel(mChannel);

        }
        notificationManager.notify(notifyID, notification);
    }

}
