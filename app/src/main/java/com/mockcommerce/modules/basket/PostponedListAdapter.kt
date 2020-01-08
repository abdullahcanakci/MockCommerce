package com.mockcommerce.modules.basket

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.mockcommerce.databinding.BasketPostponedListItemBinding
import com.mockcommerce.models.ProductModel

class PostponedListAdapter(val listener: ((product: ProductModel, action: BasketFragment.ADAPTER_ACTION) -> Unit)) : RecyclerView.Adapter<PostponedListAdapter.PostponedViewHolder>() {
    private val items: ArrayList<ProductModel> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostponedViewHolder {
        val b = BasketPostponedListItemBinding.inflate(
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
        holder.bind(items[position])
        holder.binding.buttonDelete.setOnClickListener{
            listener(items[position], BasketFragment.ADAPTER_ACTION.DELETE)
        }
        holder.binding.addToCart.setOnClickListener{
            val temp = items[position]
            listener(temp, BasketFragment.ADAPTER_ACTION.ADD_TO_CART)
        }
    }

    fun updateItems(newItems: ArrayList<ProductModel>){
        val diffUtil = PostponeDiffCallback(items, newItems)
        val result = DiffUtil.calculateDiff(diffUtil)

        items.clear()
        items.addAll(newItems)
        result.dispatchUpdatesTo(this)
    }

    private fun deleteItem(position: Int){
        items.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, items.size)
    }

    inner class PostponedViewHolder(val binding: BasketPostponedListItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(i: ProductModel){
            binding.model = i
        }
    }

    inner class PostponeDiffCallback(val old: ArrayList<ProductModel>,val new: ArrayList<ProductModel>) : DiffUtil.Callback() {
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
                    o.oldPrice == n.oldPrice)
        }

        override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
            return super.getChangePayload(oldItemPosition, newItemPosition)
        }
    }
}