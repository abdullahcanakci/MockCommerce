package com.mockcommerce.modules.shared

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import com.mockcommerce.R
import kotlinx.android.synthetic.main.wide_button.view.*

class WideButton(context: Context, attrs: AttributeSet) : FrameLayout(context, attrs) {

    init {
        View.inflate(context, R.layout.wide_button, this)

        val attributeSet = context.obtainStyledAttributes(attrs, R.styleable.CustomButton)
        wide_button.text = attributeSet.getText(R.styleable.CustomButton_text)
        val image = attributeSet.getDrawable(R.styleable.CustomButton_button_icon)
        val arrow = resources.getDrawable(R.drawable.ic_arrow_forward)
        wide_button.setCompoundDrawablesWithIntrinsicBounds(image, null, arrow, null)

        attributeSet.recycle()
    }
}