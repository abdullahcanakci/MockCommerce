package com.mockcommerce.shared.views

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import com.mockcommerce.R
import kotlinx.android.synthetic.main.header.view.*

class Header(context: Context, attrs: AttributeSet) : LinearLayout(context, attrs) {

    val actionListener: (((v: View) -> Unit))? = null

    init {
        View.inflate(context, R.layout.header, this)

        val attributeSet = context.obtainStyledAttributes(attrs, R.styleable.Header)

        val desIcon = attributeSet.getResourceId(R.styleable.Header_icon, -1)
        val actionIcon = attributeSet.getResourceId(R.styleable.Header_button_icon, -1)

        val actionColor = attributeSet.getColor(R.styleable.Header_button_color, -1)

        val desIconView = header_descriptive_icon
        if (desIcon == -1) {
            desIconView.visibility = View.GONE
        } else {
            desIconView.setImageResource(desIcon)
        }

        val actionButton = header_action_button
        if (actionIcon == -1) {
            actionButton.visibility = View.INVISIBLE
        } else {
            actionButton.setImageResource(actionIcon)
            actionButton.setOnClickListener {
                actionListener?.invoke(this.rootView)
            }
        }

        if (actionColor != -1) {
            actionButton.setColorFilter(actionColor)
        }

        val title = attributeSet.getString(R.styleable.Header_header_title)

        header_title.text = title

        attributeSet.recycle()
    }

    fun setTitle(title: String?) {
        if (title != null) {
            header_title.text = title
        }
    }

    fun setActionIcon(icon: Int?) {
        if (icon == null) {
            header_action_button.visibility = View.INVISIBLE
        } else {
            header_action_button.visibility = View.VISIBLE
            header_action_button.setImageResource(icon)
        }
    }
}