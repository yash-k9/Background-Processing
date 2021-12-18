package com.yashk9.backgroundthreads.customImpl

import android.os.Handler
import android.os.HandlerThread
import android.os.Process

class CustomHandlerThread(name: String): HandlerThread(name, Process.THREAD_PRIORITY_BACKGROUND) {
    var handler: Handler? = null

    companion object{
        private const val TAG = "CustomHandlerThread"
    }

    override fun onLooperPrepared() {
        handler = Handler(looper)
        super.onLooperPrepared()
    }

    @JvmName("getHandler1")
    fun getHandler(): Handler? = handler
}