package com.mockcommerce.modules.shared.adapters


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.mockcommerce.BR
import com.mockcommerce.models.ProductModel

class GenericProductAdapter(
    private val layoutId: Int,
    private val onClickListener: ((id: Int) -> Unit)?
) : RecyclerView.Adapter<GenericProductAdapter.GenericProductViewHolder>() {

    private val products = ArrayList<ProductModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenericProductViewHolder {
        return GenericProductViewHolder(
            DataBindingUtil.inflate<ViewDataBinding>(
                LayoutInflater.from(parent.context),
                layoutId,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return products.size
    }

    override fun onBindViewHolder(holder: GenericProductViewHolder, position: Int) {
        val item = products[position]
        holder.binding.setVariable(BR.product, item)
        holder.binding.executePendingBindings()
        onClickListener?.let { listener ->
            holder.binding.root.setOnClickListener { listener(item.id) }
        }
    }

    fun updateProducts(newProducts: ArrayList<ProductModel>) {
        val diffUtil = ProductModelDiffCallback(products, newProducts)
        val result = DiffUtil.calculateDiff(diffUtil)

        products.clear()
        products.addAll(newProducts)

        result.dispatchUpdatesTo(this)
    }

    inner class GenericProductViewHolder(val binding: ViewDataBinding) :
        RecyclerView.ViewHolder(binding.root)


}