package com.mockcommerce.modules.explore.views

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mockcommerce.R
import com.mockcommerce.models.CarouselItemModel
import com.mockcommerce.modules.views.CarouselItemView

class CarouselAdapter(private val items: List<CarouselItemModel>) : RecyclerView.Adapter<CarouselAdapter.CarouselItemViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarouselItemViewHolder {

        val view: CarouselItemView = LayoutInflater.from(parent.context).inflate(
            R.layout.view_carousel_item,
            parent,
            false
        ) as CarouselItemView

        return CarouselItemViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: CarouselItemViewHolder, position: Int) {
        holder.bind(items.get(position))
    }

    inner class CarouselItemViewHolder(private val view: CarouselItemView) : RecyclerView.ViewHolder(view){
        fun bind(item: CarouselItemModel){
            view.setModel(item)
        }
    }
}



