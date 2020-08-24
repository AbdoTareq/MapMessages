package com.absolute.template.ui

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.absolute.template.R
import com.absolute.template.databinding.ActivityMainBinding
import com.absolute.template.viewmodel.MessageViewModel
import com.absolute.template.viewmodel.MessageViewModelFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import timber.log.Timber

class MainActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var binding: ActivityMainBinding
    private lateinit var lm: LocationManager
    private lateinit var map: GoogleMap
    private val REQUEST_LOCATION_PERMISSION = 1

    private val viewModel: MessageViewModel by lazy {
        val viewModelFactory = MessageViewModelFactory(this@MainActivity.application)
        ViewModelProvider(this, viewModelFactory).get(MessageViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.viewModel = viewModel

        binding.lifecycleOwner = this

        val adapter = MessageAdapter()

        binding.recyclerView.adapter = adapter

        lm = this.getSystemService(Context.LOCATION_SERVICE) as LocationManager

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)


    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        viewModel.messages.observe(this, Observer {

            CoroutineScope(Job() + Dispatchers.Main)
                .launch {
                    viewModel.setMarker(map,it)
                }
        })
        enableMyLocation()
    }

    // To check if permissions are granted
    private fun isPermissionGranted(): Boolean {
        return ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }

    // To enable location tracking
    private fun enableMyLocation() {
        if (isPermissionGranted())
            map.isMyLocationEnabled = true
        else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                REQUEST_LOCATION_PERMISSION
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        // Check if location permissions are granted and if so enable the
        // location data layer.
        if (requestCode == REQUEST_LOCATION_PERMISSION) {
            if (grantResults.size > 0 && (grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                enableMyLocation()
            }
        }
    }

}

