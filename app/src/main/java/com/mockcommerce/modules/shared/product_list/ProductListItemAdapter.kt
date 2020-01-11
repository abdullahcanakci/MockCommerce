package com.mockcommerce.modules.shared.product_list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mockcommerce.R
import com.mockcommerce.databinding.LayoutProductListItemBinding
import com.mockcommerce.models.ProductModel
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
        val b = LayoutProductListItemBinding.inflate(
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

    inner class ProductListViewHolder(val binding: LayoutProductListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(m: ProductModel) {
            binding.model = m
            Glide.with(binding.root)
                .load("https://raw.githubusercontent.com/abdullahcanakci/MockCommerce/master/mockserver/" + m.images[0])
                .placeholder(R.drawable.ic_product_image)
                .into(binding.productImage)
        }
    }
}
