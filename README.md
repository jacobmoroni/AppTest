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
5. Extract it and copy `app-debug.apk` to your phone.

### Direct release download (optional)

If you push a tag like `v1.0.0`, the workflow also uploads the APK to that GitHub Release.

## Install on Samsung Galaxy S25

1. Transfer APK to phone (or download it directly in mobile browser from GitHub).
2. Open the APK file.
3. Allow installs from unknown sources when prompted.
4. Complete installation.

> Note: This project currently ships a debug APK for easy testing and side-loading.