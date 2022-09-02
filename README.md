# HW Bridge

Connection between HW app and ePOS app.

## Download

``` groovy
repositories {
    maven {
        url = uri("https://maven.pkg.github.com/storyous/android-hw-bridge")
        credentials {
            username = [name]
            password = [password]
        }
    }
}
```

``` groovy
implementation 'com.storyous.hwbridge:hwbridge:[version]'
```

## HW app

### Printer

Override `PrintService` that implements `IPrinter` interface

``` kotlin
class MyPrintService : PrintService() {
    override suspend fun print(bitmap: Bitmap) {
        TODO()
    }
}

interface IPrinter {
    suspend fun print(bitmap: Bitmap): Unit
}
```

In AndroidManifest use intent filter for new service.

``` xml
<intent-filter>
    <action android:name="com.storyous.hwbridge.printer" />
</intent-filter>
```

## ePOS app

Create connection to app supporting printer.

``` kotlin
fun connectPrinter(ctx: Context, serviceInfo: ServiceInfo): Connection<IPrinter>
```

Helper method to find all printer apps.

``` kotlin
fun findAvailablePrinterApps(ctx: Context): List<ResolveInfo>
```

