package com.mockcommerce.shared

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.mockcommerce.R

fun ImageView.loadImage(url: String) {
    Glide.with(rootView)
        .load("https://raw.githubusercontent.com/abdullahcanakci/MockCommerce/master/mockserver/${url}")
        .placeholder(R.drawable.ic_product_image)
        .into(this)
}