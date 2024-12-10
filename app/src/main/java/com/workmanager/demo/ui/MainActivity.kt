package com.workmanager.demo.ui

import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.workmanager.demo.databinding.ActivityMainBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.File

class MainActivity : AppCompatActivity() {
    private val viewModel: DownloadViewModel by viewModel()
    private lateinit var viewBinding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        viewBinding.lottieMaterialProgress.visibility = View.VISIBLE
        Toast.makeText(this, "We are downloading image using WorkManager", Toast.LENGTH_LONG).show()
        viewModel.doImageDownload()
        observeImageDownload()
        observeBlurImage()
    }

    private fun observeBlurImage() {
        viewModel.isBlurringImageDone.observe(this) { blurStatus ->
            if (blurStatus != null && blurStatus.state.isFinished) {
                getBitmapFromCacheAndDisplay(true)
                Toast.makeText(this, "blur applied", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun observeImageDownload() {
        viewModel.isDownloadImageFinished.observe(this) { downloadStatus ->
            if (downloadStatus != null && downloadStatus.state.isFinished) {
                getBitmapFromCacheAndDisplay(false)
                Toast.makeText(
                    this,
                    "We are applying Blur to the downloaded image using WorkManager",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    private fun getBitmapFromCacheAndDisplay(isBlurred: Boolean) {
        val filename = if (isBlurred) "birdsblur.png" else "birds.png"
        val cacheFile = File(applicationContext.cacheDir, filename)
        val picture = BitmapFactory.decodeFile(cacheFile.path)
        viewBinding.lottieMaterialProgress.visibility = View.GONE
        viewBinding.ivDownload.visibility = View.VISIBLE
        viewBinding.ivDownload.setImageBitmap(picture)
    }
}