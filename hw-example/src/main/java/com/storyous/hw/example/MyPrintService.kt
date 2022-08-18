package com.storyous.hw.example

import android.graphics.Bitmap
import android.widget.Toast
import com.storyous.hwbridge.PrintService
import com.storyous.hwbridge_printer.store
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MyPrintService : PrintService() {
    override suspend fun print(bitmap: Bitmap) {
        val uri = bitmap.store(this)
        withContext(Dispatchers.Main) {
            Toast.makeText(this@MyPrintService, "Received bitmap", Toast.LENGTH_SHORT).show()
            MainActivity.start(this@MyPrintService, uri)
        }
    }
}
