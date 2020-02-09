package com.mockcommerce.modules.basket

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mockcommerce.databinding.ItemBasketPostponedBinding
import com.mockcommerce.models.ProductModel

class PostponedListAdapter(val listener: ((product: ProductModel, action: BasketFragment.ADAPTER_ACTION) -> Unit)) : RecyclerView.Adapter<PostponedListAdapter.PostponedViewHolder>() {
    private val items: ArrayList<ProductModel> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostponedViewHolder {
        val b = ItemBasketPostponedBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return PostponedViewHolder(b)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: PostponedViewHolder, position: Int) {
        if(position >= itemCount){
            return
        }
        holder.binding.product = items[position]
        holder.binding.buttonDelete.setOnClickListener{
            listener(items[position], BasketFragment.ADAPTER_ACTION.DELETE)
        }
        holder.binding.addToCart.setOnClickListener{
            val temp = items[position]
            listener(temp, BasketFragment.ADAPTER_ACTION.ADD_TO_CART)
        }
        holder.binding.root.setOnClickListener{
            listener(items[position], BasketFragment.ADAPTER_ACTION.VIEW)
        }
    }

    fun updateItems(newItems: List<ProductModel>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }

    inner class PostponedViewHolder(val binding: ItemBasketPostponedBinding) :
        RecyclerView.ViewHolder(binding.root)
}