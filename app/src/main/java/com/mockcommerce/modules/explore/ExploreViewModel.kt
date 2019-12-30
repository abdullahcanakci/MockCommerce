package com.mockcommerce.modules.explore

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mockcommerce.models.CarouselItem

class ExploreViewModel : ViewModel() {
    val carouselItems: MutableLiveData<ArrayList<CarouselItem>> by lazy {
        MutableLiveData<ArrayList<CarouselItem>>()
    }

}
