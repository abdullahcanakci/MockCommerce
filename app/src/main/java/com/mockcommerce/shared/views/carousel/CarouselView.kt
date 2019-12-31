package com.mockcommerce.shared.views.carousel

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mockcommerce.R
import com.mockcommerce.models.CarouselModel
import com.mockcommerce.shared.views.carousel.Info.CarouselInfoAdapter
import com.mockcommerce.shared.views.carousel.promo.CarouselPromoAdapter
import com.mockcommerce.shared.CarouselViewInterface

class CarouselView(context: Context, attrs: AttributeSet) : LinearLayout(context, attrs) {

    var title: TextView
    var seeMore: TextView
    var carousel: RecyclerView
    var adapter: CarouselViewInterface

    init{
        View.inflate(context, R.layout.view_carousel, this)

        title = findViewById(R.id.title)
        seeMore = findViewById(R.id.see_more_button)
        carousel = findViewById(R.id.carousel)


        val attributeSet = context.obtainStyledAttributes(attrs, R.styleable.CarouselView)

        title.text = attributeSet.getString(R.styleable.CarouselView_title)
        seeMore.visibility = if (
            attributeSet.getBoolean(R.styleable.CarouselView_see_more, false)
        ) View.VISIBLE
        else View.INVISIBLE

        val type = attributeSet.getInt(R.styleable.CarouselView_type, 0)

        attributeSet.recycle()

        if(type == 0){
            adapter = CarouselPromoAdapter()
        } else {
            adapter = CarouselInfoAdapter()
        }
        carousel.adapter = adapter.getAdapter()
        carousel.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
    }

    fun setModel(model: CarouselModel){
        title.text = model.title
        if(model.seeMore)
            seeMore.visibility = View.VISIBLE
        else
            seeMore.visibility = View.INVISIBLE

        adapter.setItems(model.items)
    }



}