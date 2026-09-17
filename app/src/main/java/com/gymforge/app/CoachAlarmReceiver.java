package com.gymforge.app;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import org.json.JSONObject;

public class CoachAlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String raw = context.getSharedPreferences("gymforge_native", Context.MODE_PRIVATE)
                .getString("coach_state", "");
        String title = "GymForge Smart Coach";
        String body = "Open GymForge and update today's meals so your coach can calculate what is left.";

        try {
            if (raw != null && !raw.isEmpty()) {
                JSONObject j = new JSONObject(raw);
                int cal = j.optInt("caloriesLeft", 0);
                int protein = j.optInt("proteinLeft", 0);
                boolean covered = j.optBoolean("targetCovered", false);
                String suggestion = j.optString("suggestion", "Open Smart Coach");
                if (covered) {
                    body = "Today's calorie and protein targets are covered. Nice — focus on recovery and sleep.";
                } else if (protein > 12 || cal > 250) {
                    body = "About " + cal + " kcal & " + protein + " g protein left. Easy option: " + suggestion + ".";
                } else {
                    body = "You're close to today's target. Open GymForge for the final check.";
                }
            }
        } catch (Exception ignored) {}

        NotificationHelper.show(context, title, body);
    }
}
