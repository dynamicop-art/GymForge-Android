package com.gymforge.app;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import java.util.Calendar;

public final class CoachScheduler {
    private static final int[] IDS = {1330, 1830, 2130};
    private static final int[][] TIMES = {{13,30}, {18,30}, {21,30}};

    private CoachScheduler() {}

    public static void scheduleAll(Context context) {
        for (int i = 0; i < IDS.length; i++) scheduleDaily(context, IDS[i], TIMES[i][0], TIMES[i][1]);
        context.getSharedPreferences("gymforge_native", Context.MODE_PRIVATE)
                .edit().putBoolean("alarms_enabled", true).apply();
    }

    private static void scheduleDaily(Context context, int requestCode, int hour, int minute) {
        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, CoachAlarmReceiver.class);
        intent.putExtra("slot", requestCode);
        PendingIntent pi = PendingIntent.getBroadcast(
                context, requestCode, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
        );

        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, hour);
        c.set(Calendar.MINUTE, minute);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        if (c.getTimeInMillis() <= System.currentTimeMillis()) c.add(Calendar.DAY_OF_YEAR, 1);

        am.setInexactRepeating(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pi);
    }

    public static void cancelAll(Context context) {
        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        for (int id : IDS) {
            Intent intent = new Intent(context, CoachAlarmReceiver.class);
            PendingIntent pi = PendingIntent.getBroadcast(
                    context, id, intent, PendingIntent.FLAG_NO_CREATE | PendingIntent.FLAG_IMMUTABLE
            );
            if (pi != null) am.cancel(pi);
        }
        context.getSharedPreferences("gymforge_native", Context.MODE_PRIVATE)
                .edit().putBoolean("alarms_enabled", false).apply();
    }
}
