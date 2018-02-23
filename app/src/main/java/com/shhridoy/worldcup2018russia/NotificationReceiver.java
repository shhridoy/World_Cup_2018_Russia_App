package com.shhridoy.worldcup2018russia;

import android.annotation.SuppressLint;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v4.app.NotificationCompat;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.shhridoy.worldcup2018russia.myDataBase.DatabaseHelper;

import java.util.Calendar;

/**
 * Created by Dream Land on 2/24/2018.
 */

public class NotificationReceiver extends BroadcastReceiver {

    @SuppressLint("UnsafeProtectedBroadcastReceiver")
    @Override
    public void onReceive(Context context, Intent intent) {

        Calendar c = Calendar.getInstance();
        int currYear = c.get(Calendar.YEAR); // like 2018
        int currMonth = c.get(Calendar.MONTH)+1; // count month from 0 to 11 (1, 2, 3 and so on)
        int currDay = c.get(Calendar.DATE); // like 1, 2, 3 and so on
        createNotification(context, intent);

        /*DatabaseHelper databaseHelper = new DatabaseHelper(context);
        Cursor cursor = databaseHelper.retrieveMatchesData();
        while (cursor.moveToNext()){
            String id = cursor.getString(0);
            String dateTime = cursor.getString(1);
            String round = cursor.getString(2);
            String team1 = cursor.getString(3);
            String team2 = cursor.getString(4);
            String score = cursor.getString(5);
            String details = cursor.getString(6);
            try {
                String[] splitedDateTime = dateTime.split(" ");
                String[] splitDate = splitedDateTime[1].split(".");
                if (Integer.parseInt(splitDate[0]) == currDay && Integer.parseInt(splitDate[1]) == currMonth
                        && Integer.parseInt(splitDate[2]) == currYear) {
                    createNotification(context, intent);
                    break;
                }
            } catch (Exception e) {
                Toast.makeText(context, "Exception arise while splitting date!!", Toast.LENGTH_SHORT).show();
            }
        }*/

    }

    public void createNotification (Context context, Intent intent) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Intent notificIntent = new Intent(context, MainActivity.class);
        notificIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 100, notificIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        /*RemoteViews contentView = new RemoteViews(context.getPackageName(), R.layout.custom_notification);
        //contentView.setImageViewResource(R.id.image, R.mipmap.ic_launcher);
        contentView.setTextViewText(R.id.tv_team1, "Custom");
        contentView.setTextViewText(R.id.tv_team2, "This layout");*/

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setTicker("Matches")
                .setContentTitle("Round 1")
                .setContentText("Today's Match")
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .setDefaults(NotificationCompat.DEFAULT_ALL);


        if (notificationManager != null) {
            notificationManager.notify(100, builder.build());
        }
    }

}
