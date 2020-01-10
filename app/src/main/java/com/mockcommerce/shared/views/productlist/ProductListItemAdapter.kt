package com.mockcommerce.shared.views.productlist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mockcommerce.databinding.LayoutProductListItemBinding
import com.mockcommerce.models.ProductListModel

class ProductListItemAdapter(private var items: List<ProductListModel>, val listener: () -> Unit) : RecyclerView.Adapter<ProductListItemAdapter.ProductListViewHolder>() {
    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ProductListViewHolder, position: Int) {
        holder.bind(items.get(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductListViewHolder {
        val b = LayoutProductListItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        b.root.setOnClickListener{
            listener()
        }

        return ProductListViewHolder(b)
    }

    inner class ProductListViewHolder(private val v: LayoutProductListItemBinding) :
        RecyclerView.ViewHolder(v.root) {
        fun bind(m: ProductListModel) {
            v.model = m
        }
    }
}
