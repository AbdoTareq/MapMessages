package com.absolute.template

import android.app.Application
import com.alfares.tracking.BuildConfig
import timber.log.Timber


/**
 * Controller responsible to start timber debug tree
 * */

class ApplicationController : Application() {
    override fun onCreate() {
        super.onCreate()

        // this condition to prevent logs in release
        // take care BuildConfig.DEBUG shouldn't import from libraries to work
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

}