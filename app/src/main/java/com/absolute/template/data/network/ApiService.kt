package com.absolute.template.data.network

import android.content.Context
import com.absolute.template.data.models.Content
import com.absolute.template.data.models.Feed
import com.absolute.template.data.models.FeedContainer
import com.itkacher.okhttpprofiler.OkHttpProfilerInterceptor

import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.ResponseBody
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

private const val BASE_URL = "https://spreadsheets.google.com/feeds/list/"

interface ApiService {
    @GET("0Ai2EnLApq68edEVRNU0xdW9QX1BqQXhHRl9sWDNfQXc/od6/public/basic?alt=json")
    suspend fun getFeed(): FeedContainer
}

object ApiObj {
    val retrofitService: ApiService by lazy {
        createRetrofit().create(ApiService::class.java)
    }
}

fun createRetrofit(): Retrofit {
    // this for debugging network calls
    val builder = OkHttpClient.Builder()
    builder.addInterceptor(OkHttpProfilerInterceptor())

    val client = builder.build()

    return Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BASE_URL)
        .client(client)
        .build()
}
