package com.mockcommerce.modules.shared.product_list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mockcommerce.databinding.ItemProductlistBinding
import com.mockcommerce.models.ProductModel
import com.mockcommerce.shared.loadImage
import timber.log.Timber

class ProductListItemAdapter(val listener: (id: Int) -> Unit) : RecyclerView.Adapter<ProductListItemAdapter.ProductListViewHolder>() {
    private val items = ArrayList<ProductModel>()

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ProductListViewHolder, position: Int) {
        holder.bind(items.get(position))

        holder.binding.root.setOnClickListener{
            listener(items[position].id)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductListViewHolder {
        val b = ItemProductlistBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return ProductListViewHolder(b)
    }

    fun update(newList : ArrayList<ProductModel>) {
        items.clear()
        items.addAll(newList)
        notifyDataSetChanged()
        Timber.d("Layout Completed")
    }

    inner class ProductListViewHolder(val binding: ItemProductlistBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(m: ProductModel) {
            binding.model = m
            binding.productImage.loadImage("https://raw.githubusercontent.com/abdullahcanakci/MockCommerce/master/mockserver/${m.images[0]}")
        }
    }
}
