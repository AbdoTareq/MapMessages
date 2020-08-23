package com.absolute.template.viewmodel

import android.app.Application
import android.location.Address
import android.location.Geocoder
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.absolute.template.data.models.ErrorStatus.Codes.getErrorMessage
import com.absolute.template.data.models.FeedContainer
import com.absolute.template.data.models.Message
import com.absolute.template.data.repository.FeedInterface
import com.absolute.template.data.repository.Repository
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import timber.log.Timber
import java.io.IOException


enum class ApiStatus { LOADING, ERROR, DONE }

/**
 * ViewModel for SleepTrackerFragment.
 */
class MessageViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = Repository()
    private val applicationCon = application

    private lateinit var mMap: GoogleMap

    private val _status = MutableLiveData<ApiStatus>()
    val statusType: LiveData<ApiStatus>
        get() = _status

    private val _messages = MutableLiveData<List<Message>>()
    val messages: LiveData<List<Message>>
        get() = _messages

    init {
        _status.value = ApiStatus.LOADING
        repository.getFeed(object : FeedInterface {
            override fun onSuccess(feedContainer: FeedContainer) {
                val temp = ArrayList<Message>()
                _status.value = ApiStatus.DONE
                for (m in feedContainer.feed.entry) {
                    temp.add(getMessageFromString(m.content.t!!))
                }
                _messages.value = temp
                Timber.e("${_messages.value!!}")
            }

            override fun onFail(responseCode: String) {
                _status.value = ApiStatus.ERROR
                _messages.value = ArrayList()
                Toast.makeText(application, getErrorMess(responseCode), Toast.LENGTH_LONG).show()
            }
        })

    }

    fun getErrorMess(code: String): String {
        return getErrorMessage(code, applicationCon)
    }

    override fun onCleared() {
        super.onCleared()
        repository.cancelJob()
    }

    private fun getLocations(location: String): ArrayList<LatLng> {
        if (Geocoder.isPresent()) {
            try {
                val gc = Geocoder(applicationCon)
                val addresses: List<Address> =
                    gc.getFromLocationName(location, 5) // get the found Address Objects
                // A list to save the coordinates if they are available
                val ll: ArrayList<LatLng> =
                    ArrayList<LatLng>(addresses.size)
                for (a in addresses) {
                    if (a.hasLatitude() && a.hasLongitude()) {
                        ll.add(LatLng(a.latitude, a.longitude))
                    }
                }
                return ll
            } catch (e: IOException) {
                // handle the exception
                Timber.e("$e")
                return ArrayList()
            }
        } else
            return ArrayList()
    }

    fun setMarker(map: GoogleMap) {
        mMap = map

        for (city in getCityName()){
            val destination = getLocations(city)

            for (i in destination) {
                mMap.addMarker(MarkerOptions().position(i))
                mMap.addMarker(
                    MarkerOptions().position(i)
                        // this for styling the marker
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))
                )
            }
        }
    }


    private fun getCityName(): List<String> {
        return listOf(
            "Damascus",
            "Mogadishu",
            "Ibiza",
            "Cairo",
            "Tahrir",
            "Nairobi",
            "Kathmandu",
            "Bernabau",
            "Athens",
            "Istanbul"
        )
    }

    fun getMessageFromString(st: String): Message {
        val m = st.split(", ", "messageid", "message", "sentiment", ": ")
        return Message(
            messageid = m[2], message = m[5], sentiment = m[8]
        )
    }

}
