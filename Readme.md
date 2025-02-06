Below is a professional and well-structured `README.md` file for your Android app that listens to SMS activity and forwards it to a Telegram bot. This documentation includes setup instructions, usage details, and troubleshooting tips.

---

# SMS Forwarder to Telegram Bot

This Android app listens for incoming SMS messages and forwards them to a Telegram bot. It supports both foreground and background execution using `WorkManager` for reliable message forwarding.

## Table of Contents
1. [Features](#features)
2. [Prerequisites](#prerequisites)
3. [Setup Instructions](#setup-instructions)
    - [Step 1: Create a Telegram Bot](#step-1-create-a-telegram-bot)
    - [Step 2: Clone the Repository](#step-2-clone-the-repository)
    - [Step 3: Configure the App](#step-3-configure-the-app)
4. [Usage](#usage)
5. [Permissions](#permissions)
6. [Troubleshooting](#troubleshooting)
7. [License](#license)

---

## Features
- Listens for incoming SMS messages in real-time.
- Forwards SMS content (sender and message body) to a Telegram bot.
- Works in both **foreground** and **background** modes.
- Uses `WorkManager` for reliable background task execution.
- Compatible with devices without telephony hardware (optional feature).

---

## Prerequisites
Before setting up the app, ensure you have the following:
1. A Telegram account and a Telegram bot created using [BotFather](https://core.telegram.org/bots#botfather).
2. Android Studio installed on your development machine.
3. Basic knowledge of Android development and Kotlin.

---

## Setup Instructions

### Step 1: Create a Telegram Bot
1. Open Telegram and search for **BotFather**.
2. Start a chat with BotFather and use the `/newbot` command to create a new bot.
3. Follow the instructions to name your bot and get the **API Token**.
4. Add your bot to a chat or group and retrieve the **Chat ID** by following the instructions [here](#how-to-get-chat-id).

### Step 2: Clone the Repository
Clone this repository to your local machine:
```bash
git clone https://github.com/rfaturriza/sms-forwarder-telegram.git
cd sms-forwarder-telegram
```

Alternatively, download the source code as a ZIP file and extract it.

### Step 3: Configure the App
1. Open the project in Android Studio.
2. Rename example.env.properties to env.properties and update the following values:
   ```text
   TELEGRAM_BOT_TOKEN=YOUR_TELEGRAM_BOT_TOKEN
   TELEGRAM_CHAT_ID=YOUR_CHAT_ID
   ```
3. Sync the project with Gradle files to ensure all dependencies are resolved.

---

## Usage
1. Build and run the app on a physical Android device (SMS permissions won't work on emulators).
2. Grant the required permissions (`RECEIVE_SMS`, `READ_SMS`) when prompted.
3. Send an SMS to the device running the app.
4. The app will forward the SMS content (sender and message body) to your Telegram bot.

---

## Permissions
The app requires the following permissions:
- `RECEIVE_SMS`: To listen for incoming SMS messages.
- `READ_SMS`: To read the content of incoming SMS messages.
- `INTERNET`: To send data to the Telegram bot.

These permissions are declared in the `AndroidManifest.xml` file:
```xml
<uses-permission android:name="android.permission.RECEIVE_SMS" />
<uses-permission android:name="android.permission.READ_SMS" />
<uses-permission android:name="android.permission.INTERNET" />
```

Additionally, the `<uses-feature>` tag ensures compatibility with devices without telephony hardware:
```xml
<uses-feature
    android:name="android.hardware.telephony"
    android:required="false" />
```

---

## Troubleshooting
### 1. **Telegram Bot Not Receiving Messages**
- Ensure that the `botToken` and `chatId` in `SmsWorker.kt` are correct.
- Verify that the bot has been added to the chat or group where you want to receive messages.

### 2. **App Not Receiving SMS**
- Ensure that the app has been granted the `RECEIVE_SMS` and `READ_SMS` permissions.
- Check if other apps (e.g., default SMS apps) are interfering with SMS reception.

### 3. **Background Execution Issues**
- If the app fails to forward messages in the background, ensure that battery optimization is disabled for the app:
  ```kotlin
  val intent = Intent(Settings.ACTION_IGNORE_BATTERY_OPTIMIZATION_SETTINGS)
  startActivity(intent)
  ```

### 4. **Missing Dependencies**
- If you encounter errors related to `WorkManager`, ensure that the dependency is added to your `build.gradle` file:
  ```gradle
  implementation "androidx.work:work-runtime-ktx:2.8.0"
  ```

---

## License
This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.

---

## How to Get Chat ID
To retrieve the Chat ID for your Telegram bot:
1. Send a message to your bot in a private chat or group.
2. Use the following URL to retrieve updates:
   ```
   https://api.telegram.org/bot<YOUR_BOT_TOKEN>/getUpdates
   ```
3. Look for the `chat` object in the JSON response. The `id` field under `chat` is your **Chat ID**.

For example:
```json
{
    "ok": true,
    "result": [
        {
            "update_id": 123456789,
            "message": {
                "message_id": 1,
                "from": {
                    "id": 123456789,
                    "is_bot": false,
                    "first_name": "John",
                    "last_name": "Doe"
                },
                "chat": {
                    "id": 123456789,
                    "first_name": "John",
                    "last_name": "Doe",
                    "type": "private"
                },
                "date": 1672531198,
                "text": "Hello, bot!"
            }
        }
    ]
}
```
In this case, the Chat ID is `123456789`.

---

Feel free to contribute to this project or report issues on the [GitHub repository](https://github.com/yourusername/sms-forwarder-telegram).

---