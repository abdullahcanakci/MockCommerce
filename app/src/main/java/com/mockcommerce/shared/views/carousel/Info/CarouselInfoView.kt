package com.mockcommerce.shared.views.carousel.Info

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.mockcommerce.R
import com.mockcommerce.models.CarouselItemModel
import com.mockcommerce.shared.views.carousel.CarouselItemViewInterface

class CarouselInfoView(context: Context, attrs: AttributeSet) : LinearLayout(context, attrs),
    CarouselItemViewInterface {

    var description: TextView
    var image: ImageView

    init {
        View.inflate(context, R.layout.item_explore_carousel_info, this)

        description = findViewById(R.id.description)
        image = findViewById(R.id.image)

        val attributeSet = context.obtainStyledAttributes(attrs, R.styleable.CarouselInfoView)

        description.text = attributeSet.getText(R.styleable.CarouselInfoView_description)

        image.contentDescription = description.text
        image.setImageResource(attributeSet.getResourceId(R.styleable.CarouselPromoView_imageSrc, R.drawable.ic_launcher_background))

        attributeSet.recycle()
    }

    override fun setModel(model: CarouselItemModel){
        description.text = model.description
        image.setImageResource(model.resId)
        image.contentDescription = model.description
    }

    override fun getView(): View {
        return this
    }
}