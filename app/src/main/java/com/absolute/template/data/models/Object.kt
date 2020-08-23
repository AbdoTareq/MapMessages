package com.absolute.template.data.models;

import com.google.gson.annotations.SerializedName;

data class FeedContainer(

    @SerializedName("feed")
    val feed: Feed
)

data class Feed(
    @SerializedName("entry")
    val entry: List<Entry>
)

data class Entry(
    @SerializedName("content")
    val content: Content
)

data class Content(
    @SerializedName("type")
    val type: String?,
    @SerializedName("\$t")
    val t: String?
)
data class Message(
    @SerializedName("messageid")
    val messageid: String,
    @SerializedName("message")
    val message: String,
    @SerializedName("sentiment")
    val sentiment: String
)
