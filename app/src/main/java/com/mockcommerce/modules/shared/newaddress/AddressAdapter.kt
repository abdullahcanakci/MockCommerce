package com.mockcommerce.modules.shared.newaddress

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mockcommerce.databinding.LayoutAddressBinding
import com.mockcommerce.models.AddressModel

class AddressAdapter(val onItemSelectedListener: ((id: String) -> Unit)) :
    RecyclerView.Adapter<AddressAdapter.AddressHolder>() {
    private val addresses = ArrayList<AddressModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddressHolder {
        return AddressHolder(
            LayoutAddressBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return addresses.size
    }

    override fun onBindViewHolder(holder: AddressHolder, position: Int) {
        holder.binding.model = addresses[position]
        holder.binding.root.setOnClickListener {
            onItemSelectedListener(addresses[position].id!!)
        }
    }

    fun update(list: List<AddressModel>) {
        addresses.clear()
        addresses.addAll(list)
        notifyDataSetChanged()
    }

    inner class AddressHolder(val binding: LayoutAddressBinding) :
        RecyclerView.ViewHolder(binding.root) {}
}