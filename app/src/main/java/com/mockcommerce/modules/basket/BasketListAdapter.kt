package com.mockcommerce.modules.basket

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mockcommerce.R
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
        if(items.isEmpty()){
            items.addAll(newItems)
            notifyDataSetChanged()
        }
        else if(newItems.isEmpty()){
            items.clear()
            notifyDataSetChanged()
        }
        else {
            val diffUtil = ProductModelDiffCallback(items, newItems)
            val result = DiffUtil.calculateDiff(diffUtil)

            items.clear()
            items.addAll(newItems)
            result.dispatchUpdatesTo(this)
        }
        //Don't change, for some reason result.dispatchUpdates won't update itemrange and will
        //cause indexOutOfRangeException
        notifyItemRangeChanged(0, itemCount)
    }

    inner class BasketListItemHolder(val binding: BasketListItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(model: ProductModel) {
            binding.model = model
            Glide.with(binding.root)
                .load("https://raw.githubusercontent.com/abdullahcanakci/MockCommerce/master/mockserver/" + model.images[0])
                .placeholder(R.drawable.ic_product_image)
                .into(binding.productImage)
        }
    }

}