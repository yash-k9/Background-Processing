package com.yashk9.backgroundthreads

import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import android.widget.Toast
import com.yashk9.backgroundthreads.databinding.ActivityAsyncTaskBinding
import java.lang.ref.WeakReference

class AsyncTaskActivity : AppCompatActivity() {

    companion object{
        private const val TAG = "AsyncTaskActivity"
    }
    private var binding: ActivityAsyncTaskBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAsyncTaskBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        binding?.let{
            it.startAsync.setOnClickListener { startTask() }
        }
    }

    private fun startTask() {
        binding?.let { DownloadTask(it).execute(100) }
    }

    class DownloadTask(AsyncBinding: ActivityAsyncTaskBinding): AsyncTask<Int, Int, String>(){
        private var bindingRef: WeakReference<ActivityAsyncTaskBinding> = WeakReference(AsyncBinding)

        override fun onPreExecute() {
            super.onPreExecute()
            val binding = bindingRef.get()
            binding?.let{
                binding.progressBar.progress = 0
                binding.progressBar.max = 100
            }
        }

        override fun doInBackground(vararg array: Int?): String {
            for(i in 0..array[0]!!){
                publishProgress(i)
                SystemClock.sleep(100)
            }
            return "Finished!!!"
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            Log.d(TAG, "onPostExecute: Task is Finished!!!")
            val binding = bindingRef.get()
            binding?.let {
                Toast.makeText(binding.root.context, result, Toast.LENGTH_SHORT).show();
                it.progressBar.progress = 0
            }
        }

        override fun onProgressUpdate(vararg values: Int?) {
            super.onProgressUpdate(*values)
            val binding = bindingRef.get()
            binding?.let {
                it.progressBar.progress = values[0]!!
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}