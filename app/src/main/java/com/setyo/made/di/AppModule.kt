package com.setyo.made.di

import com.setyo.made.ui.popular.PopoularViewModel
import com.setyo.made.ui.toprated.TopRatedViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { PopoularViewModel(get()) }
    viewModel { TopRatedViewModel(get()) }
}