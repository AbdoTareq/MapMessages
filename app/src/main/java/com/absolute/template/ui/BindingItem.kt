package com.absolute.template.ui

import android.graphics.Color
import android.widget.TextView
import androidx.databinding.BindingAdapter


@BindingAdapter("sentimentColor")
fun TextView.bindSentiment(itemSentiment:String) {
    when (itemSentiment) {
        "Positive" -> setTextColor(Color.parseColor("#266fc3"))
        "Negative" -> setTextColor(Color.parseColor("#FFE91E1E"))
        else -> setTextColor(Color.parseColor("#FF4CAF50"))
    }
}
