package com.mockcommerce.modules.orders

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mockcommerce.databinding.ItemOrderProductBinding
import com.mockcommerce.models.ProductModel
import com.mockcommerce.shared.loadImage
import timber.log.Timber

class OrderProductAdapter : RecyclerView.Adapter<OrderProductAdapter.OrderProductViewHolder>() {
    private val items = ArrayList<ProductModel>()

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: OrderProductViewHolder, position: Int) {
        holder.bind(items.get(position))

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderProductViewHolder {
        val b = ItemOrderProductBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return OrderProductViewHolder(b)
    }

    fun update(newList: ArrayList<ProductModel>) {
        items.clear()
        items.addAll(newList)
        notifyDataSetChanged()
        Timber.d("Layout Completed")
    }

    inner class OrderProductViewHolder(val binding: ItemOrderProductBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(m: ProductModel) {
            binding.product = m
            binding.imageView.loadImage("https://raw.githubusercontent.com/abdullahcanakci/MockCommerce/master/mockserver/${m.images[0]}")
        }
    }
}
