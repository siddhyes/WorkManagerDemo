package com.workmanager.demo

import android.app.Application
import com.workmanager.demo.di.otherDI
import com.workmanager.demo.di.viewModelDI
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class AppClass : Application() {
    override fun onCreate() {
        super.onCreate()
        val modulesList = viewModelDI+otherDI
        startKoin {
            androidLogger(org.koin.core.logger.Level.DEBUG)
            androidContext(this@AppClass)
            modules(modulesList)
        }
    }
}