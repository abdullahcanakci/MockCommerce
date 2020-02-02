package com.mockcommerce.modules.basket

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.mockcommerce.databinding.ItemBasketMainBinding
import com.mockcommerce.models.ProductModel
import com.mockcommerce.modules.shared.adapters.ProductModelDiffCallback

class BasketListAdapter(val listener: ((product: ProductModel, action: BasketFragment.ADAPTER_ACTION) -> Unit)) : RecyclerView.Adapter<BasketListAdapter.BasketListItemHolder>() {
    private val items: ArrayList<ProductModel> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BasketListItemHolder {
        val b = ItemBasketMainBinding.inflate(
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
        holder.binding.product = items[position]
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
        holder.binding.root.setOnClickListener{
            listener(items[position], BasketFragment.ADAPTER_ACTION.VIEW)
        }
    }

    fun updateItems(newItems: ArrayList<ProductModel>){
        if(items.isEmpty()){
            items.addAll(newItems)
            notifyDataSetChanged()
        }
        else if(newItems.isEmpty()){
            items.clear()
            notifyDataSetChanged()
        }
        else {
            val diffUtil =
                ProductModelDiffCallback(items, newItems)
            val result = DiffUtil.calculateDiff(diffUtil)

            items.clear()
            items.addAll(newItems)
            result.dispatchUpdatesTo(this)
        }
        //Don't change, for some reason result.dispatchUpdates won't update itemrange and will
        //cause indexOutOfRangeException
        notifyItemRangeChanged(0, itemCount)
    }

    inner class BasketListItemHolder(val binding: ItemBasketMainBinding) :
        RecyclerView.ViewHolder(binding.root)

}