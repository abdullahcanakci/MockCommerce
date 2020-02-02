package com.mockcommerce.modules.shared.adapters

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.mockcommerce.R
import com.mockcommerce.shared.loadImage
import java.text.SimpleDateFormat
import java.util.*

@BindingAdapter("android:text")
fun setText(view: TextView, value: Int) {
    view.text = value.toString()
}

@BindingAdapter("android:text")
fun setText(view: TextView, value: Float) {
    view.text = view.context!!.getString(R.string.price, value)
}

@BindingAdapter("android:text")
fun setText(view: TextView, value: Float?) {
    if (value != null) {
        view.text = view.context!!.getString(R.string.price, value)
    }
}

@BindingAdapter("android:text")
fun setText(view: TextView, value: Long) {
    val date = Date(value)
    val locale = Locale("tr", "TR")
    val formatter = SimpleDateFormat("dd MMMM HH:mm", locale)

    view.text = formatter.format(date)
}

@BindingAdapter("imageUrl")
fun setImageUrl(imageView: ImageView, url: String) {
    imageView.loadImage(url)
}

@BindingAdapter("imageUrl")
fun setImageUrl(imageView: ImageView, url: ArrayList<String>) {
    imageView.loadImage(url[0])
}