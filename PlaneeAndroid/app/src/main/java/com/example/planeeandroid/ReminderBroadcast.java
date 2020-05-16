package com.example.planeeandroid;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class ReminderBroadcast extends BroadcastReceiver {

    //Mise en place d'une Notification à l'aide d'un BroadcastReceiver
    @Override
    public void onReceive(Context context, Intent intent) {
        MyDBAdapter myDb = new MyDBAdapter(context);
        myDb.open();
        Evenement event = myDb.getEvent(intent.getLongExtra("IdEvent", 0));
        myDb.close();

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "NotifyLemubit")
                .setSmallIcon(R.drawable.ikki)
                .setContentTitle("Planee")
                .setContentText("C'est l'heure de " + event.getNom())
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(200, builder.build());
    }
}
