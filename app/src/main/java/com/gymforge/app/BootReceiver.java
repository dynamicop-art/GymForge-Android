package com.gymforge.app;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class BootReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        boolean enabled = context.getSharedPreferences("gymforge_native", Context.MODE_PRIVATE)
                .getBoolean("alarms_enabled", false);
        if (enabled) CoachScheduler.scheduleAll(context);
    }
}
