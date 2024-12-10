package com.workmanager.demo.worker


import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import okhttp3.OkHttpClient
import okhttp3.Request
import okio.buffer
import okio.sink
import java.io.File
import java.io.IOException

class DownloadImageWorker(context: Context, workerParams: WorkerParameters) :
    Worker(context, workerParams) {
    companion object {
        const val KEY_URL =
            "https://www.shutterstock.com/shutterstock/photos/2276290857/display_1500/stock-photo-business-man-and-robot-hand-touch-digital-screen-of-ai-icon-artificial-intelligence-concept-big-2276290857.jpg"
    }
    override fun doWork(): Result {
        val networkClient = OkHttpClient()
        val networkRequest = Request.Builder()
            .url(KEY_URL)
            .build()

        val directory = applicationContext.cacheDir
           val downloadedImage = File(directory, "birds.png")
        try {
            if (downloadedImage.exists()) {
                //delete if the exiting file is already in cache folder
                downloadedImage.delete()
            }
            networkClient.newCall(networkRequest).execute().use { response ->
                val sink = downloadedImage.sink().buffer()
                response.body?.let {
                    sink.writeAll(it.source())
                }
                sink.close()
            }
        } catch (e: IOException) {
            Log.e(e.message, "Exception downloading image")
            return Result.failure()
        }

        return Result.success()
    }


}