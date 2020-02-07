package com.mockcommerce.modules.categories

import androidx.lifecycle.ViewModel
import com.mockcommerce.AppRepository

class CategoriesViewModel(appRepository: AppRepository) : ViewModel() {
    val categoryList = appRepository.getCategory()
}
