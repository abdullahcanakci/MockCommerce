package com.mockcommerce.modules.orders

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.mockcommerce.databinding.ItemOrderBinding
import com.mockcommerce.models.OrderModel
import timber.log.Timber

class OrderAdapter : RecyclerView.Adapter<OrderAdapter.OrderViewHolder>() {
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
        Timber.d(orders[position].toString())
        holder.binding.model = orders[position]
        holder.binding.root.setOnClickListener {
            Toast.makeText(
                holder.binding.root.context,
                "Şipariş ekranları henüz oluşturulmadı.",
                Toast.LENGTH_SHORT
            ).show()
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