# GymForge Android V3

This is a real Android app shell for your existing GymForge site. It adds native Android notifications and native photo picking/camera support while the UI continues to update from GitHub Pages.

## App URL
`https://dynamicop-art.github.io/GymForge-Ultimate/`

## Native reminders
When notification permission is enabled from GymForge, the Android app schedules daily Smart Coach checks at:
- 1:30 PM
- 6:30 PM
- 9:30 PM

The website sends the latest remaining calories/protein and food suggestion to native Android storage. The alarms can therefore notify you even if the app is closed.

## Easiest APK build: GitHub Actions
1. Create a new public repo named `GymForge-Android`.
2. Upload **all contents of this android-app folder**, including the hidden `.github` folder.
3. Commit to `main`.
4. Open **Actions → Build GymForge Android APK**.
5. Wait for the green build.
6. Open the run → Artifacts → download `GymForge-v3-APK`.
7. Extract it and install `GymForge-v3-debug.apk` on your Android phone.

No Play Store is required for personal use. Android may ask you to allow installation from your browser/file manager once.

## Android Studio alternative
Open this folder in Android Studio and run the `app` configuration on your phone.
