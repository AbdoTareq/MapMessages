package com.absolute.template

import android.app.Application
import timber.log.Timber


/**
 * Controller responsible to start timber debug tree
 * */

class ApplicationController : Application() {
    override fun onCreate() {
        super.onCreate()

        Timber.plant(Timber.DebugTree())
    }

}