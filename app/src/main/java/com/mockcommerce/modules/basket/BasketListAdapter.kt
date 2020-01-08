package com.mockcommerce.modules.basket

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mockcommerce.databinding.BasketListItemBinding
import com.mockcommerce.models.ProductModel

class BasketListAdapter(val listener: ((product: ProductModel, action: BasketFragment.ADAPTER_ACTION) -> Unit)) : RecyclerView.Adapter<BasketListAdapter.BasketListItemHolder>() {
    private val items: ArrayList<ProductModel> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BasketListItemHolder {
        val b = BasketListItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return BasketListItemHolder(b)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: BasketListItemHolder, position: Int) {
        holder.bind(items.get(position))
        holder.binding.buttonAdd.setOnClickListener{
            listener(items[position], BasketFragment.ADAPTER_ACTION.ADD)
        }
        holder.binding.buttonDelete.setOnClickListener{
            listener(items[position], BasketFragment.ADAPTER_ACTION.DELETE)
        }
        holder.binding.buttonSubstract.setOnClickListener{
            listener(items[position], BasketFragment.ADAPTER_ACTION.SUBSTRACT)
        }
        holder.binding.buttonPostpone.setOnClickListener{
            listener(items[position], BasketFragment.ADAPTER_ACTION.POSTPONE)
        }

    }

    fun updateItems(newItems: ArrayList<ProductModel>){
        items.clear()
        items.addAll(newItems)
        items.trimToSize()
        notifyDataSetChanged()
    }

    private fun deleteItem(position: Int){
        items.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, items.size)
    }


    inner class BasketListItemHolder(val binding: BasketListItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(model: ProductModel) {
            binding.model = model
        }
    }
}