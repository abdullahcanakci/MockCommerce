package com.mockcommerce.shared

import android.content.Context
import android.widget.Toast

class Notifier(val context: Context) {
    fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}