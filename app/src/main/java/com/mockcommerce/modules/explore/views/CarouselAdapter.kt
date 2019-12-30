package com.mockcommerce.modules.explore.views

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.mockcommerce.R
import com.mockcommerce.databinding.CarouselItemSmallBinding
import com.mockcommerce.models.CarouselItem

class CarouselAdapter(private val items: List<CarouselItem>) : RecyclerView.Adapter<CarouselAdapter.CarouselItemViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarouselItemViewHolder {
        val binding: CarouselItemSmallBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.carousel_item_small,
            parent,
            false
        )

        return CarouselItemViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: CarouselItemViewHolder, position: Int) {
        holder.bind(items.get(position))
    }

    inner class CarouselItemViewHolder(private val binding: CarouselItemSmallBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(item: CarouselItem){
            binding.model = item
        }
    }
}



