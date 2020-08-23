package com.absolute.template.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.absolute.template.data.models.ErrorStatus.Codes.getErrorMessage
import com.absolute.template.data.models.FeedContainer
import com.absolute.template.data.models.Message
import com.absolute.template.data.repository.FeedInterface
import com.absolute.template.data.repository.Repository
import timber.log.Timber

enum class ApiStatus { LOADING, ERROR, DONE }

/**
 * ViewModel for SleepTrackerFragment.
 */
class MessageViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = Repository()
    private val applicationCon = application

    private val _status = MutableLiveData<ApiStatus>()
    val statusType: LiveData<ApiStatus>
        get() = _status

    private val _messages = MutableLiveData<ArrayList<Message>>()
    val messages: LiveData<ArrayList<Message>>
        get() = _messages

    init {
        _status.value = ApiStatus.LOADING
        repository.getFeed(object : FeedInterface {
            override fun onSuccess(feedContainer: FeedContainer) {
                _messages.value = ArrayList()
                _status.value = ApiStatus.DONE
                for (m in feedContainer.feed.entry){
                    _messages.value!!.add(getMessageFromString(m.content.t!!))
                }
                for (i in _messages.value!!)
                Timber.e("${i}")
            }

            override fun onFail(responseCode: String) {
                _status.value = ApiStatus.ERROR
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

    fun getMessageFromString(st: String): Message {
        val m = st.split(", ", "messageid", "message", "sentiment")
        val message = Message(
            messageid = m[1], message = m[3], sentiment = m[5]
        )
        return message
    }

}
