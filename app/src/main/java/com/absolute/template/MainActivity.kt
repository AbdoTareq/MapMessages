package com.absolute.template

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.alfares.tracking.R
import timber.log.Timber

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Timber.e("$ pass")

    }
}