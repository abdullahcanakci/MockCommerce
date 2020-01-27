package com.mockcommerce.modules.orders

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mockcommerce.databinding.ItemOrderBinding
import com.mockcommerce.models.OrderModel

class OrderAdapter(val onSelectListener: (id: String) -> Unit) :
    RecyclerView.Adapter<OrderAdapter.OrderViewHolder>() {
    private val orders = ArrayList<OrderModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        return OrderViewHolder(
            ItemOrderBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return orders.size
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        holder.binding.model = orders[position]
        holder.binding.root.setOnClickListener {
            onSelectListener(orders[position].id)
        }
    }

    fun update(newList: ArrayList<OrderModel>) {
        orders.clear()
        orders.addAll(newList)
        notifyDataSetChanged()
    }

    inner class OrderViewHolder(val binding: ItemOrderBinding) :
        RecyclerView.ViewHolder(binding.root)
}