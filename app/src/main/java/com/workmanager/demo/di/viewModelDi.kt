package com.workmanager.demo.di

import com.workmanager.demo.ui.DownloadViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelDI = module {
    viewModel { DownloadViewModel(get()) }
}
