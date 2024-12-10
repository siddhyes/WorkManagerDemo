package com.workmanager.demo.ui

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkInfo
import com.workmanager.demo.worker.WorkerSetup

class DownloadViewModel(private val workerSetup: WorkerSetup) : ViewModel() {
    val isDownloadImageFinished = MediatorLiveData<WorkInfo?>()
    val isBlurringImageDone = MediatorLiveData<WorkInfo?>()

    fun doImageDownload() {
        val downloadWorkerRequest = workerSetup.downloadWorkerRequest()
        val blurWorkerRequest = workerSetup.blurWorkerRequest()
        val workManager = workerSetup.getWorkManagerInstance()
        workManager.beginWith(downloadWorkerRequest).then(blurWorkerRequest).enqueue()
        downloadImageStatus(downloadWorkerRequest)
        blurWorkingStatus(blurWorkerRequest)

    }


    private fun downloadImageStatus(downloadImage: OneTimeWorkRequest) {
        val downloadImageStatus =
            workerSetup.getWorkManagerInstance().getWorkInfoByIdLiveData(downloadImage.id)

        isDownloadImageFinished.addSource(downloadImageStatus) { workStatus ->
            isDownloadImageFinished.value = workStatus

            if (workStatus?.state?.isFinished == true) {
                isDownloadImageFinished.removeSource(downloadImageStatus)
            }
        }
    }

    private fun blurWorkingStatus(blurImage: OneTimeWorkRequest) {
        val blurImageStatus =
            workerSetup.getWorkManagerInstance().getWorkInfoByIdLiveData(blurImage.id)
        isBlurringImageDone.addSource(blurImageStatus) { blurWorkStatus ->
            isBlurringImageDone.value = blurWorkStatus
            if (blurWorkStatus?.state?.isFinished == true) {
                isBlurringImageDone.removeSource(blurImageStatus)
            }
        }
    }
}
