package com.mockcommerce.modules.views

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mockcommerce.R
import com.mockcommerce.models.CarouselModel
import com.mockcommerce.modules.explore.views.CarouselAdapter

class CarouselView(context: Context, attrs: AttributeSet) : LinearLayout(context, attrs) {

    var title: TextView
    var seeMore: TextView
    var carousel: RecyclerView

    init{
        View.inflate(context, R.layout.view_carousel, this)

        title = findViewById<TextView>(R.id.title)
        seeMore = findViewById<TextView>(R.id.see_more_button)
        carousel = findViewById<RecyclerView>(R.id.carousel)


        val attributeSet = context.obtainStyledAttributes(attrs, R.styleable.CarouselView)

        title.text = attributeSet.getString(R.styleable.CarouselView_title)
        seeMore.visibility = if (
            attributeSet.getBoolean(R.styleable.CarouselView_see_more, false)
        ) View.VISIBLE
        else View.INVISIBLE

        attributeSet.recycle()

        carousel.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
    }

    fun setModel(model: CarouselModel){
        title.text = model.title
        if(model.seeMore)
            seeMore.visibility = View.VISIBLE
        else
            seeMore.visibility = View.INVISIBLE

        carousel.adapter = CarouselAdapter(model.items)
    }



}