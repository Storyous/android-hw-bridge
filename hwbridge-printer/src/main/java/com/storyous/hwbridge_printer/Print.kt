package com.storyous.hwbridge_printer

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Parcelable
import androidx.core.net.toUri
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext
import kotlinx.parcelize.Parcelize
import java.io.File
import java.util.UUID

const val ACTION = "com.storyous.hwbridge_printer.printBitmap"

@Parcelize
data class Print(
    val printId: String,
    val bitmap: Bitmap
) : Parcelable

private fun Context.prepareIntent(appPackageName: String, action: String): Intent {
    val intent = Intent(action)

    packageManager.queryBroadcastReceivers(intent, 0)
        .firstOrNull {
            it.activityInfo.packageName.lowercase().contains(appPackageName) ||
                    it.activityInfo.name.lowercase().contains(appPackageName)
        }
        ?.also {
            intent.setPackage(it.activityInfo.packageName)
        }
        ?: throw IllegalStateException("Package '$appPackageName' handling action '$action' not found.")

    return intent
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

suspend fun Print.send(
    ctx: Context,
    appPackageName: String
) = coroutineScope {
    val bitmapPath = bitmap.store(ctx, printId).toString()

    ctx.sendBroadcast(
        ctx.prepareIntent(appPackageName, ACTION).apply {
            putExtra("printId", printId)
            putExtra("bitmapPath", bitmapPath)
        }
    )
}

fun Intent.receivePrint(): Print? {
    val printId = getStringExtra("printId") ?: return null
    val bitmap = getStringExtra("bitmapPath")
        ?.let { BitmapFactory.decodeFile(it) }
        ?: return null

    return Print(printId, bitmap)
}
