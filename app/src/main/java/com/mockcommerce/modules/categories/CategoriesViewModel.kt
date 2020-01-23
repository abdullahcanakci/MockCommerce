package com.mockcommerce.modules.categories

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mockcommerce.AppRepository
import com.mockcommerce.models.CategoryModel

class CategoriesViewModel(val appRepository: AppRepository) : ViewModel() {

    private val categoryList = MutableLiveData<ArrayList<CategoryModel>>(ArrayList<CategoryModel>())

    fun getCategoryList(id: Int?) : MutableLiveData<ArrayList<CategoryModel>> {
        appRepository.getCategory(id) {
            categoryList.postValue(it)
        }
        return categoryList
    }

}
