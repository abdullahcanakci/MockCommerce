package com.mockcommerce.shared.views.carousel

import androidx.recyclerview.widget.RecyclerView
import com.mockcommerce.models.CarouselItemModel

class CarouselItemViewHolder(private val view: CarouselItemViewInterface) : RecyclerView.ViewHolder(view.getView()){
    fun bind(item: CarouselItemModel){
        view.setModel(item)
    }
}