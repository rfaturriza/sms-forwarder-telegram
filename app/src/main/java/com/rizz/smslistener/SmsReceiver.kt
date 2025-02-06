package com.rizz.smslistener

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.telephony.SmsMessage
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf

class SmsReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action == "android.provider.Telephony.SMS_RECEIVED") {
            val bundle = intent.extras
            if (bundle != null) {
                val pdus = bundle.get("pdus") as Array<*>?
                if (pdus != null) {
                    for (pdu in pdus) {
                        val smsMessage = SmsMessage.createFromPdu(
                            pdu as ByteArray, bundle.getString("format")
                        )

                        val sender = smsMessage.displayOriginatingAddress
                        val messageBody = smsMessage.messageBody

                        // Enqueue work to forward the SMS to Telegram
                        val workRequest = OneTimeWorkRequestBuilder<SmsWorker>()
                            .setInputData(
                                workDataOf(
                                    "sender" to sender,
                                    "messageBody" to messageBody
                                )
                            )
                            .build()

                        WorkManager.getInstance(context!!).enqueue(workRequest)
                    }
                }
            }
        }
    }
}