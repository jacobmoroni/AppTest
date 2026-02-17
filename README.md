# Calculator Android App

Simple Android calculator app (Kotlin) that can be built in GitHub Actions and downloaded as an APK.

## What is included

- Native Android app module (`app/`)
- Basic calculator operations: `+`, `-`, `×`, `÷`, decimals, clear, and backspace
- Unit tests for expression evaluation
- GitHub Actions workflow to build `app-debug.apk` on every push
- Optional release asset upload when pushing a `v*` tag

## Build locally

```bash
./gradlew :app:assembleDebug
```

APK output:

`app/build/outputs/apk/debug/app-debug.apk`

## Download APK from GitHub

1. Open the repository on GitHub.
2. Go to **Actions**.
3. Open the latest **Build Android APK** workflow run.
4. Download the `calculator-debug-apk` artifact.
5. Extract the ZIP and locate `app-debug.apk`.
6. Move the APK to your phone, or just download the artifact directly from your phone browser.

### Direct release download (optional)

If you push a tag like `v1.0.0`, the workflow also uploads the APK to that GitHub Release.

## Install on Samsung Galaxy S25 (Android)

1. Open `app-debug.apk` on your phone (from **My Files** or your browser downloads list).
2. If prompted, tap **Settings** to allow installs from that source.
   - You may see: **Install unknown apps** -> choose the app you used to open the APK (for example **Chrome** or **My Files**) -> enable **Allow permission**.
3. Go back and tap **Install**.
4. After install, tap **Open**.

### Updating to a newer APK later

1. Download the latest `app-debug.apk`.
2. Open it and tap **Update**.
3. If Android blocks it, confirm you downloaded from the same trusted source and that install permission is still enabled for that app.

### If installation is blocked

- Check free storage space on the phone.
- Make sure the APK download completed fully (re-download if needed).
- If you already installed an app with the same package name from a different signing key, uninstall that app first, then install this APK again.

> Note: This project currently ships a debug APK for easy testing and side-loading.