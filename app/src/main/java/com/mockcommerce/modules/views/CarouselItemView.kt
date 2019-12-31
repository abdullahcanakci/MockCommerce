package com.mockcommerce.modules.views

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.mockcommerce.R
import com.mockcommerce.models.CarouselItemModel

class CarouselItemView(context: Context, attrs: AttributeSet) : LinearLayout(context, attrs) {
    var title: TextView
    var subtitle: TextView
    var description: TextView
    var image: ImageView


    init {
        View.inflate(context, R.layout.layout_carousel_item, this)

        title = findViewById(R.id.title)
        subtitle = findViewById(R.id.subtitle)
        description = findViewById(R.id.description)
        image = findViewById(R.id.image)

        val attributeSet = context.obtainStyledAttributes(attrs, R.styleable.CarouselItemView)

        title.text = attributeSet.getText(R.styleable.CarouselItemView_title)
        subtitle.text = attributeSet.getText(R.styleable.CarouselItemView_subtitle)
        description.text = attributeSet.getText(R.styleable.CarouselItemView_description)

        image.contentDescription = description.text
        image.setImageResource(attributeSet.getResourceId(R.styleable.CarouselItemView_imageSrc, R.drawable.ic_launcher_background))

        attributeSet.recycle()
    }

    fun setModel(model: CarouselItemModel){
        title.text = model.title
        subtitle.text = model.subtitle
        description.text = model.description
        image.setImageResource(model.resId)
        image.contentDescription = model.description
    }
}