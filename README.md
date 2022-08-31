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
implementation "com.github.storyous.hwbridge:hwbridge:[last_version]"
```
