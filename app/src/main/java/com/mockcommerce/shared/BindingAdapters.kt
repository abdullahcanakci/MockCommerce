package com.mockcommerce.shared

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.mockcommerce.R

@BindingAdapter("android:text")
fun setText(view: TextView, value: Int) {
    view.text = value.toString()
}

@BindingAdapter("android:text")
fun setText(view: TextView, value: Float) {
    view.text = view.context!!.getString(R.string.price, value)
}
