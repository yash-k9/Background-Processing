package com.yashk9.backgroundthreads

import android.content.Intent
import android.os.*
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import com.yashk9.backgroundthreads.customImpl.CustomHandler
import com.yashk9.backgroundthreads.customImpl.CustomHandlerThread
import com.yashk9.backgroundthreads.customImpl.CustomLooper
import com.yashk9.backgroundthreads.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    companion object{
        private const val TAG = "MainActivity"
        const val HANDLER_FLAG = 0
    }

    private var looperThread = CustomLooper()
    private var customHandler: CustomHandler? = null
    private var customHandlerThread = CustomHandlerThread("custom_handler")
    private var handler: Handler? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        customHandlerThread.start()
        intiViews()
    }

    private fun intiViews() {
        with(binding){
            startThread.setOnClickListener { looperThread.start() }
            stopThread.setOnClickListener { looperThread.looper?.quit() }
            taskA.setOnClickListener { startTaskLooper() }
            taskB.setOnClickListener { startTaskHandler() }
            taskC.setOnClickListener { startTaskHandlerThread() }
            taskD.setOnClickListener { startAsyncTask() }
        }
    }

    private fun startTaskLooper() {
        handler = looperThread.looper?.let { it1 -> Handler(it1) }
        handler?.let {
            it.post {
                for (i in 1..40) {
                    SystemClock.sleep(200)
                    Log.d(TAG, "run: $i -> ${Thread.currentThread().name}")
                }
            }
        }
    }

    private fun startTaskHandler() {
        customHandler = looperThread.looper?.let { it2 -> CustomHandler(it2) }
        val msg = Message.obtain(null, HANDLER_FLAG, 123, 0)
        customHandler?.sendMessage(msg)
    }

    private fun startTaskHandlerThread() {
       val handler = customHandlerThread.getHandler()
        handler?.post{
            for(i in 1..25){
                SystemClock.sleep(200)
                Log.d(TAG, "startTaskC: $i -> ${Thread.currentThread().name}")
            }
        }
    }

    private fun startAsyncTask() {
        Intent(this, AsyncTaskActivity::class.java).apply {
            startActivity(this)
        }
    }

    override fun onDestroy() {
        customHandlerThread.quit()
        super.onDestroy()
    }

}