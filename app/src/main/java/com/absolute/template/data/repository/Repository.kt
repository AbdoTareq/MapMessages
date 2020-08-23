package com.absolute.template.data.repository

import com.absolute.template.data.models.Feed
import com.absolute.template.data.models.FeedContainer
import com.absolute.template.data.network.ApiObj
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.HttpException
import timber.log.Timber
import java.net.SocketTimeoutException


class Repository {

    private var job = Job()

    private val coroutineScope = CoroutineScope(job + Dispatchers.Main)

    fun getFeed(feedInterface: FeedInterface) {

        coroutineScope.launch {
            try {
                val feed = ApiObj.retrofitService.getFeed()
                feedInterface.onSuccess(feed)
            } catch (e: HttpException) {
                Timber.e("${e.code()}")
                feedInterface.onFail("${e.code()}")
            } catch (e: SocketTimeoutException) {
                Timber.e("Timeout")
                feedInterface.onFail("-2")
            } catch (e: Exception) {
                Timber.e(e)
                feedInterface.onFail(e.toString())
            }
        }
    }

    fun cancelJob() {
        job.cancel()
    }


}

interface FeedInterface {
    fun onSuccess(feedContainer: FeedContainer)
    fun onFail(responseCode: String)
}

