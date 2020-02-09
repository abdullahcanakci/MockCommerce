package com.mockcommerce.modules.shared.adapters

import androidx.recyclerview.widget.DiffUtil
import com.mockcommerce.models.ProductModel

class ProductModelDiffCallback(val old: List<ProductModel>, val new: List<ProductModel>) :
    DiffUtil.Callback() {
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return old[oldItemPosition].id == new[newItemPosition].id
    }

    override fun getOldListSize(): Int {
        return old.size
    }

    override fun getNewListSize(): Int {
        return new.size
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val o = old[oldItemPosition]
        val  n = new[newItemPosition]
        return (o.name == n.name &&
                o.price == n.price &&
                o.oldPrice == n.oldPrice &&
                o.amount == n.amount)
    }
}