package com.storyous.hw.example

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.storyous.hwbridge_printer.receivePrint

class PrinterReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val print = intent.receivePrint()
            ?: throw IllegalStateException("Missing print data in intent $intent")

        MainActivity.start(context, print)
    }
}
