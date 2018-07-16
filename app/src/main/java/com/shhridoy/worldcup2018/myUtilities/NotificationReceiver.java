package com.shhridoy.worldcup2018.myUtilities;

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

import com.shhridoy.worldcup2018.MainActivity;
import com.shhridoy.worldcup2018.R;
import com.shhridoy.worldcup2018.myDataBase.DatabaseHelper;
import com.shhridoy.worldcup2018.myRecyclerViewData.Flags;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Dream Land on 2/24/2018.
 */

public class NotificationReceiver extends BroadcastReceiver {

    List<String> dateList = new ArrayList<>();
    List<String> team1List = new ArrayList<>();
    List<String> team2List = new ArrayList<>();
    final static int[] UNIQUE_ID = {100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110};

    @SuppressLint("UnsafeProtectedBroadcastReceiver")
    @Override
    public void onReceive(Context context, Intent intent) {

        Calendar c = Calendar.getInstance();
        int currYear = c.get(Calendar.YEAR); // like 2018
        int currMonth = c.get(Calendar.MONTH)+1; // count month from 0 to 11 (1, 2, 3 and so on)
        int currDay = c.get(Calendar.DATE); // like 1, 2, 3 and so on

        try {
            DatabaseHelper databaseHelper = new DatabaseHelper(context);
            Cursor cursor = databaseHelper.retrieveMatchesData();
            while (cursor.moveToNext()){
                String dateTime = cursor.getString(1);
                String team1 = cursor.getString(3);
                String team2 = cursor.getString(4);

                String[] splitedDateTime = dateTime.split(" ");
                String[] splitDate = splitedDateTime[1].split("\\.");
                if (Integer.parseInt(splitDate[0]) == currDay && Integer.parseInt(splitDate[1]) == currMonth
                        && Integer.parseInt(splitDate[2]) == currYear) {

                    String[] splitZone = dateTime.split("/");
                    String timeZone = Settings.getSettings(context, "International Zone") ? splitZone[0] : splitZone[1];
                    dateList.add(timeZone);
                    team1List.add(team1);
                    team2List.add(team2);

                }
            }

            multipleNotifications(context, intent);

        } catch (Exception e) {
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    private void multipleNotifications(Context context, Intent intent) {
        for (int j=0; j < dateList.size(); j++) {
            createNotification(context, intent, dateList.get(j), team1List.get(j), team2List.get(j), UNIQUE_ID[j]);
        }
    }

    public void createNotification (Context context, Intent intent, String date, String team1, String team2, int ID) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Intent notificIntent = new Intent(context, MainActivity.class);
        notificIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 100, notificIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        // Using RemoteViews to bind custom layouts into Notification
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.custom_notification);

        // Locate and set the Image into custom notification text.xml ImageViews
        remoteViews.setImageViewResource(R.id.imgTeam1, Flags.getFlag(team1));
        remoteViews.setImageViewResource(R.id.imgTeam2, Flags.getFlag(team2));

        // Locate and set the Text into custom notification text.xml TextViews
        remoteViews.setTextViewText(R.id.TVTitle, "Today's Match");
        remoteViews.setTextViewText(R.id.TVDate, date);
        remoteViews.setTextViewText(R.id.TVTeam1, team1);
        remoteViews.setTextViewText(R.id.TVTeam2, team2);
        remoteViews.setTextViewText(R.id.TVMiddleText, "vs");


        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.ic_action_circle)
                .setTicker("Matches")
                //.setStyle(new NotificationCompat.DecoratedCustomViewStyle())
                .setCustomContentView(remoteViews)
                //.setContent(remoteViews)
                .setCustomBigContentView(remoteViews)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent);

        if (Settings.getSettings(context, "Sound") && Settings.getSettings(context, "Vibration")) {
            builder.setDefaults(NotificationCompat.DEFAULT_ALL);
        } else if (Settings.getSettings(context, "Sound")) {
            builder.setDefaults(NotificationCompat.DEFAULT_SOUND);
        } else if (Settings.getSettings(context, "Vibration")) {
            builder.setDefaults(NotificationCompat.DEFAULT_VIBRATE);
        }

        if (notificationManager != null) {
            if (Settings.getSettings(context, "Notification")) {
                notificationManager.notify(ID, builder.build());
            } else {
                notificationManager.cancelAll();
            }
        }

    }

}
