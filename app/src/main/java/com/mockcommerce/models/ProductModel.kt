package com.mockcommerce.models

import android.view.View

open class ProductModel(
    val id: String,
    val name: String,
    val price: Float,
    val oldPrice: Float? = null,
    var amount: Int = 0,
    var favourite: Boolean = false,
    val images: ArrayList<String>
) {

    constructor(id: String, numbersInBasket: Int) : this(
        id,
        "",
        0F,
        0F,
        numbersInBasket,
        false,
        ArrayList()
    )

    fun getOldPrice(): String {
        return oldPrice.toString()
    }

    fun getDiscountVisibility(): Int {
        if (oldPrice != null)
            return View.VISIBLE
        return View.INVISIBLE
    }

    fun getDiscount(): String {
        if (oldPrice == null)
            return "0"

        val p = ((1 - (price / oldPrice)) * 100).toInt().toString()

        return "%$p"
    }

    fun copy() : ProductModel {
        return ProductModel(
            id, name, price, oldPrice, amount, favourite, images
        )
    }
}