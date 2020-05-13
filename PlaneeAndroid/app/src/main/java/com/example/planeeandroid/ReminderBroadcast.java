package com.example.planeeandroid;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class ReminderBroadcast extends BroadcastReceiver {
    private MyDBAdapter myDb;


    @Override
    public void onReceive(Context context, Intent intent) {
        myDb = new MyDBAdapter(context);
        myDb.open();
        Evenement event = myDb.getEvent(intent.getLongExtra("IdEvent", 0));
        myDb.close();

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "NotifyLemubit")
                .setSmallIcon(R.drawable.ikki)
                .setContentTitle("Planee")
                .setContentText("C'est l'heure de " + event.getNom())
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        Log.i("Notification----", "" + event.getId());
        Log.i("Notification----", event.getNom());
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(200, builder.build());
    }
}
