package com.mockcommerce.modules.basket

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mockcommerce.models.ProductModel

class BasketViewModel : ViewModel() {
    val basket_items: MutableLiveData<ArrayList<ProductModel>> = MutableLiveData(ArrayList(listOf(
        ProductModel(0, "Ürün adı ve bilgisi", 25.3F, null,  1),
        ProductModel(1, "Ürün adı ve bilgisi", 125.3F, null,  3),
        ProductModel(2, "Ürün adı ve bilgisi", 25F, null, 10),
        ProductModel(3, "Ürün adı ve bilgisi", 5.3F, null,  2),
        ProductModel(4, "Ürün adı ve bilgisi", 2.5F, null,  7),
        ProductModel(5, "Ürün adı ve bilgisi", 23F, null,  5),
        ProductModel(4, "Ürün adı ve bilgisi", 2.5F, null, 7),
        ProductModel(5, "Ürün adı ve bilgisi", 23F, null, 5)
        )))

    var postponed_items: MutableLiveData<ArrayList<ProductModel>> = MutableLiveData(ArrayList())
}
