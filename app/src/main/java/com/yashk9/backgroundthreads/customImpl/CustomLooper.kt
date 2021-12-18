package com.yashk9.backgroundthreads.customImpl

import android.os.Looper
import android.util.Log

class CustomLooper: Thread() {
    var looper: Looper? = null

    companion object{
        private const val TAG = "BThread"
    }

    override fun run() {
        Log.d(TAG, "run: Started Looper Thread")
        Looper.prepare()
        looper = Looper.myLooper()
        Looper.loop()
        Log.d(TAG, "run: Stopped Looper Thread")
    }
}