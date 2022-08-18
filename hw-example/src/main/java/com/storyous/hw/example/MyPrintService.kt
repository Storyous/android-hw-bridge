package com.storyous.hw.example

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.widget.Toast
import androidx.core.net.toUri
import com.storyous.hwbridge.PrintService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.util.UUID

class MyPrintService : PrintService() {
    override suspend fun print(bitmap: Bitmap) {
        val uri = bitmap.store(this)
        withContext(Dispatchers.Main) {
            Toast.makeText(this@MyPrintService, "Received bitmap", Toast.LENGTH_SHORT).show()
            MainActivity.start(this@MyPrintService, uri)
        }
    }
}

suspend fun Bitmap.store(
    ctx: Context,
    fileName: String = UUID.randomUUID().toString()
): Uri = withContext(Dispatchers.IO) {
    File(ctx.getExternalFilesDir(null), "$fileName.png").apply {
        outputStream().use {
            compress(Bitmap.CompressFormat.PNG, 100, it)
        }
    }.toUri()
}
