package com.mockcommerce.modules.categories

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mockcommerce.AppRepository
import com.mockcommerce.models.CategoryModel

class CategoriesViewModel(val appRepository: AppRepository) : ViewModel() {

    val categoryList = appRepository.getCategory()



}
