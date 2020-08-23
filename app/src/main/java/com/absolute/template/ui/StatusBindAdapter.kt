package com.absolute.template.ui

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.absolute.template.R
import com.absolute.template.data.models.Message
import com.absolute.template.viewmodel.ApiStatus

@BindingAdapter("listData")
fun bindMessageRecyclerView(recyclerView: RecyclerView, data: List<Message>?) {
    val adapter = recyclerView.adapter as MessageAdapter
    adapter.submitList(data)
}

@BindingAdapter("progressApiStatus")
fun bindProgress(statusProgress: View, statusType: ApiStatus?) {
    when (statusType) {
        ApiStatus.LOADING -> {
            statusProgress.visibility = View.VISIBLE
        }
        else -> {
            statusProgress.visibility = View.GONE
        }
    }
}

@BindingAdapter("apiStatus")
fun bindStatus(statusImageView: ImageView, status: ApiStatus?) {
    when (status) {
        ApiStatus.ERROR -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.ic_connection_error)
        }
        else -> {
            statusImageView.visibility = View.GONE
        }
    }
}
