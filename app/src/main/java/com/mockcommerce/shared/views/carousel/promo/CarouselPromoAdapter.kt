package com.mockcommerce.shared.views.carousel.promo

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mockcommerce.R
import com.mockcommerce.models.CarouselItemModel
import com.mockcommerce.shared.CarouselViewInterface
import com.mockcommerce.shared.views.carousel.CarouselItemViewHolder

class CarouselPromoAdapter() : RecyclerView.Adapter<CarouselItemViewHolder>(), CarouselViewInterface{
    private var items: List<CarouselItemModel>? = null

    override fun setItems(newItems: List<CarouselItemModel>) {
        items = newItems
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarouselItemViewHolder {

        val view: CarouselPromoView = LayoutInflater.from(parent.context).inflate(
            R.layout.view_carousel_promo,
            parent,
            false
        ) as CarouselPromoView

        return CarouselItemViewHolder(view)
    }

    override fun getItemCount(): Int {
        if(items == null)
            return 0
        return items!!.size
    }

    override fun onBindViewHolder(holder: CarouselItemViewHolder, position: Int) {
        holder.bind(items!!.get(position))
    }

    override fun getAdapter(): RecyclerView.Adapter<RecyclerView.ViewHolder> {
        return this as RecyclerView.Adapter<RecyclerView.ViewHolder>
    }
}



