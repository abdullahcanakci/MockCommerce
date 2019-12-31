package com.mockcommerce.shared.views.carousel

import android.view.View
import com.mockcommerce.models.CarouselItemModel

interface CarouselItemViewInterface {

    fun setModel(model: CarouselItemModel)
    fun getView(): View
}