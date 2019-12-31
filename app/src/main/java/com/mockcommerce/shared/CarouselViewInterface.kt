package com.mockcommerce.shared

import androidx.recyclerview.widget.RecyclerView
import com.mockcommerce.models.CarouselItemModel

interface CarouselViewInterface {
    fun setItems(newItems: List<CarouselItemModel>)
    fun getAdapter(): RecyclerView.Adapter<RecyclerView.ViewHolder>
}