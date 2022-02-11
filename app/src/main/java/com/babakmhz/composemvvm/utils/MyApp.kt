package com.babakmhz.composemvvm.utils

import android.app.Application
import com.babakmhz.composemvvm.BuildConfig
import dagger.hilt.android.HiltAndroidApp
import io.objectbox.BoxStore
import io.objectbox.android.AndroidObjectBrowser
import timber.log.Timber
import javax.inject.Inject

@HiltAndroidApp
class MyApp : Application() {

    @Inject lateinit var boxStore : BoxStore

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG){
            Timber.plant(Timber.DebugTree())
            AndroidObjectBrowser(boxStore).start(this)

        }


    }

}