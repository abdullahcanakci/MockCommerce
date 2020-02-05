package com.mockcommerce.shared

import android.widget.ImageButton
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import com.bumptech.glide.Glide
import com.mockcommerce.R

fun ImageView.loadImage(url: String) {
    Glide.with(rootView)
        .load("https://raw.githubusercontent.com/abdullahcanakci/MockCommerce/master/mockserver/${url}")
        .placeholder(R.drawable.ic_product_image)
        .into(this)
}

fun ImageButton.setDrawable(drawableId: Int, colorId: Int) {

    var drawable = resources.getDrawable(drawableId, null)
    drawable = DrawableCompat.wrap(drawable)
    DrawableCompat.setTint(
        drawable,
        ContextCompat.getColor(context, colorId)
    )

    setImageDrawable(drawable)
    invalidateDrawable(drawable)
}