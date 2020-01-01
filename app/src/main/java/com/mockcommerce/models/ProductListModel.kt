package com.mockcommerce.models

import android.view.View

class ProductListModel(
    public val id: Int,
    public val name: String,
    public val price: Float,
    public val oldPrice: Float?
) {

    fun getPrice(): String {
        return price.toString()
    }

    fun getOldPrice(): String{
        return oldPrice.toString()
    }

    public fun getDiscountVisibility(): Int {
        if (oldPrice != null)
            return View.VISIBLE
        return View.GONE
    }

    fun getDiscountPercent(): Int {
        if (oldPrice != null)
            return View.VISIBLE
        return View.INVISIBLE
    }

    public fun getDiscount(): String {
        if (oldPrice == null)
            return "0"

        return "%" +((1-(price/oldPrice)) * 100 ).toInt().toString()
    }


}