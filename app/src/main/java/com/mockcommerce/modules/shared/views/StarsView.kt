package com.mockcommerce.modules.shared.views

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageButton
import com.mockcommerce.R
import com.mockcommerce.shared.setDrawable
import kotlinx.android.synthetic.main.layout_stars.view.*

class StarsView(context: Context, attributeSet: AttributeSet) : FrameLayout(context, attributeSet) {

    var stars: Int = 0
        set(value) {
            field = value
            updateStars()
        }

    private val starViews: ArrayList<ImageButton>

    init {
        View.inflate(context, R.layout.layout_stars, this)

        starViews = ArrayList(
            listOf(
                button_star_1,
                button_star_2,
                button_star_3,
                button_star_4,
                button_star_5
            )
        )

        val attrs = context.obtainStyledAttributes(attributeSet, R.styleable.StarsView)
        stars = attrs.getInt(R.styleable.StarsView_stars, 1)
        val disabled = attrs.getBoolean(R.styleable.StarsView_disabled, false)
        attrs.recycle()




        updateStars()

        if (!disabled) {
            setClickListeners()
        }
    }

    private fun updateStars() {
        starViews.forEachIndexed { index, imageButton ->
            if (index < stars) {
                imageButton.setDrawable(R.drawable.ic_star, R.color.star)
            } else {
                imageButton.setDrawable(R.drawable.ic_star_border, R.color.black)
            }
        }
    }

    private fun setClickListeners() {
        for ((index, button) in starViews.withIndex()) {
            button.setOnClickListener {
                stars = index + 1
                updateStars()
            }
        }
    }
}