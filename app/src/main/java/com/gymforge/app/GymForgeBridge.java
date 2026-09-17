package com.gymforge.app;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.provider.Settings;
import android.webkit.JavascriptInterface;

public class GymForgeBridge {
    private final MainActivity activity;

    GymForgeBridge(MainActivity activity) {
        this.activity = activity;
    }

    @JavascriptInterface
    public String getNotificationPermission() {
        if (Build.VERSION.SDK_INT < 33) return "granted";
        return activity.checkSelfPermission(Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED ? "granted" : "prompt";
    }

    @JavascriptInterface
    public void requestNotificationPermission() {
        activity.runOnUiThread(activity::requestNotificationPermissionFromWeb);
    }

    @JavascriptInterface
    public void showNotification(String title, String body) {
        activity.runOnUiThread(() -> NotificationHelper.show(activity, title, body));
    }

    @JavascriptInterface
    public void syncCoachState(String json) {
        activity.getSharedPreferences("gymforge_native", Context.MODE_PRIVATE)
                .edit().putString("coach_state", json).apply();
    }

    @JavascriptInterface
    public void scheduleDefaultCoachReminders() {
        CoachScheduler.scheduleAll(activity);
    }

    @JavascriptInterface
    public void cancelCoachReminders() {
        CoachScheduler.cancelAll(activity);
    }

    @JavascriptInterface
    public void openNotificationSettings() {
        Intent intent = new Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS);
        intent.putExtra(Settings.EXTRA_APP_PACKAGE, activity.getPackageName());
        activity.startActivity(intent);
    }
}
