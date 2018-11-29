package com.example.shanu.tutorialdemoapp.FirebaseNotification;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.example.shanu.tutorialdemoapp.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    String TAG = MyFirebaseMessagingService.class.getName();
    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);

        Log.d(TAG, "onNewToken: "+s);

    }

    @Override
    public void onMessageSent(String s) {
        super.onMessageSent(s);

        Log.d(TAG, "onMessageSent: "+s);

    }


    //this method will be called when you are in foreground otherwise it wont be called
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        Log.d(TAG, "onMessageReceived: payload -> "+remoteMessage.getData());

        Intent intent = new Intent(this, FirebaseNotificationDisplayActivity.class);
        intent.putExtra("payload", remoteMessage.getData().toString());
        //startActivity(intent);


        Intent open = new Intent(Intent.ACTION_VIEW, Uri.parse("www.google.com"));


        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(111 ,
        new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setAutoCancel(true)
                .setContentIntent(PendingIntent.getActivity(this,
                        101,
                        intent, PendingIntent.FLAG_UPDATE_CURRENT))
                .build()
        );
    }


}
