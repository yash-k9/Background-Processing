package com.yashk9.backgroundthreads.customImpl

import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Log
import com.yashk9.backgroundthreads.MainActivity

class CustomHandler(looper: Looper): Handler(looper) {

    companion object{
        private const val TAG = "CustomHandler"
    }

    override fun handleMessage(msg: Message) {
        Log.d(TAG, "handleMessage: Got Message!")
        when(msg.what){
            MainActivity.HANDLER_FLAG -> Log.d(TAG, "handleMessage: Got Data ${msg.arg1}")
            else -> super.handleMessage(msg)
        }
    }
}