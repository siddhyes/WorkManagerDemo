package com.workmanager.demo.worker

import android.app.Application
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager

class WorkerSetup(val application: Application) {
    fun downloadWorkerRequest(): OneTimeWorkRequest {
        return OneTimeWorkRequest.Builder(DownloadImageWorker::class.java).setConstraints(
            Constraints.Builder().setRequiresBatteryNotLow(true)
                .setRequiredNetworkType(NetworkType.CONNECTED).build()
        ).addTag("imageDownload").build()

    }

    fun blurWorkerRequest(): OneTimeWorkRequest {
        return OneTimeWorkRequest.Builder(BlurImageWorker::class.java).setConstraints(
            Constraints.Builder().setRequiresStorageNotLow(true).setRequiresBatteryNotLow(true)
                .build()
        ).addTag("blurImage").build()

    }

    fun getWorkManagerInstance(): WorkManager {
        return WorkManager.getInstance(application)
    }
}