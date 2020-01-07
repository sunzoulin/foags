package com.sbl.foags.utils

import java.util.concurrent.ThreadFactory


object ThreadUtils {

    @JvmStatic
    fun threadFactory(name: String, isDaemon: Boolean): ThreadFactory {
        return ThreadFactory { runnable ->
            val result = Thread(runnable, name)
            result.isDaemon = isDaemon
            result
        }
    }
}