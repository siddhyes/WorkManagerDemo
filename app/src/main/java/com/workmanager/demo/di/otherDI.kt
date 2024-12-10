package com.workmanager.demo.di

import com.workmanager.demo.worker.WorkerSetup
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val otherDI = module {
    single { WorkerSetup(androidApplication()) }
}