package com.storyous.hwbridge

import remoter.RemoterProxy

data class Connection<T>(val device: T) {

    inline fun use(block: T.() -> Unit) {
        device.block()
        close()
    }

    fun close() {
        if (device is RemoterProxy) {
            device.destroyProxy()
        }
    }
}
