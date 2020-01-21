package com.mockcommerce.modules.checkout

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mockcommerce.R
import kotlinx.android.synthetic.main.activity_checkout.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class CheckoutActivity : AppCompatActivity() {

    val sharedViewModel by viewModel<CheckoutViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_checkout)
        setSupportActionBar(toolbar)

    }
}
