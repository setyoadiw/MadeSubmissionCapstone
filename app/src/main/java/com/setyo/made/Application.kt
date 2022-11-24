package com.setyo.made

import com.google.android.play.core.splitcompat.SplitCompatApplication
import com.setyo.common.di.commonModule
import com.setyo.common.di.commonViewModelModule
import com.setyo.made.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class Application : SplitCompatApplication() {


    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger(Level.ERROR)
            androidContext(this@Application)
            modules(
                commonModule,
                commonViewModelModule,
                viewModelModule
            )
        }
    }
}