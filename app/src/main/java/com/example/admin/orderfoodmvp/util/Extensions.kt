package com.example.admin.orderfoodmvp.util

import android.widget.ImageView
import com.example.admin.orderfoodmvp.R
import com.squareup.picasso.Picasso

fun ImageView.load(imageUrl: String, picasso: () -> Picasso = { Picasso.get() }) {
    if (imageUrl.isNotEmpty()) {
        picasso().load(imageUrl).placeholder(R.drawable.ic_curry).into(this)
    }
}