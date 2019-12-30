package com.mockcommerce.modules.explore

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mockcommerce.models.ShowcaseItem

class ExploreViewModel : ViewModel() {
    val showcaseItems: MutableLiveData<ArrayList<ShowcaseItem>> by lazy {
        MutableLiveData<ArrayList<ShowcaseItem>>()
    }

}
