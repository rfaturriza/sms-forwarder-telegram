package com.rizz.smslistener

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.HttpURLConnection
import java.net.URL

class SmsWorker(appContext: Context, workerParams: WorkerParameters) :
    CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
        val sender = inputData.getString("sender")
        val messageBody = inputData.getString("messageBody")

        return@withContext try {
            forwardToTelegram(sender!!, messageBody!!)
            Result.success()
        } catch (e: Exception) {
            Result.retry()
        }
    }

    private fun forwardToTelegram(sender: String, messageBody: String) {
        val botToken = BuildConfig.TELEGRAM_BOT_TOKEN
        val chatId = BuildConfig.TELEGRAM_CHAT_ID

        val urlString =
            "https://api.telegram.org/bot$botToken/sendMessage?chat_id=$chatId&text=Sender:$sender%0AMessage:$messageBody"

        val url = URL(urlString)
        val conn = url.openConnection() as HttpURLConnection
        conn.requestMethod = "GET"
        conn.connect()

        val responseCode = conn.responseCode
        if (responseCode != HttpURLConnection.HTTP_OK) {
            throw Exception("Failed to forward message. Response code: $responseCode")
        }
    }
}