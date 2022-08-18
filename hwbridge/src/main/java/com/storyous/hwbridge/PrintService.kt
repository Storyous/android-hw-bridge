package com.storyous.hwbridge

import android.app.Service
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.ResolveInfo
import android.content.pm.ServiceInfo
import android.graphics.Bitmap
import android.os.IBinder
import remoter.annotations.ParamIn
import remoter.annotations.Remoter

const val ACTION = "com.storyous.hwbridge.printer"

open class PrintService : Service(), IPrinter {

    private val binder by lazy { IPrinter_Stub(this) }

    override fun onBind(intent: Intent?): IBinder = binder
}

@Remoter
interface IPrinter {
    suspend fun print(@ParamIn bitmap: Bitmap): Unit = throw UnsupportedOperationException()
}

fun connectPrinter(ctx: Context, serviceInfo: ServiceInfo): Connection<IPrinter> = Connection(
    IPrinter_Proxy(
        ctx,
        Intent(ACTION).setComponent(serviceInfo.let { ComponentName(it.packageName, it.name) })
    )
)

fun findAvailablePrinterApps(ctx: Context): List<ResolveInfo> {
    return ctx.packageManager.queryIntentServices(Intent(ACTION), 0)
}
