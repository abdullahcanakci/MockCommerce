package com.mockcommerce.modules.shared.product_page

import androidx.lifecycle.ViewModel
import com.mockcommerce.AppRepository

class ProductViewModel(val appRepository: AppRepository) : ViewModel() {

    var favourite: Boolean = false

    fun toggleFavourite() {
        favourite != favourite
    }
}
