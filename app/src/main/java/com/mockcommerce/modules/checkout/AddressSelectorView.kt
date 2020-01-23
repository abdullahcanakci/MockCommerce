package com.mockcommerce.modules.checkout

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.LinearLayout
import com.mockcommerce.R
import com.mockcommerce.databinding.LayoutAddressBinding
import com.mockcommerce.models.ShipmentAddressModel
import kotlinx.android.synthetic.main.view_address_selector.view.*
import timber.log.Timber

class AddressSelectorView(context: Context, attrs: AttributeSet) : FrameLayout(context, attrs) {

    private val listLayout: LinearLayout

    private var isCollapsed: Boolean = true

    private val models = ArrayList<ShipmentAddressModel>(listOf())

    private var onAddressSelectedListener: ((String) -> Unit)? = null

    private var newAddressListener: (() -> Unit)? = null

    init {
        View.inflate(context, R.layout.view_address_selector, this)

        listLayout = rootView.findViewById(R.id.address_box)

        val attributeSet = context.obtainStyledAttributes(attrs, R.styleable.AddressSelectorView)

        selector_header.setTitle(attributeSet.getString(R.styleable.AddressSelectorView_title))
        selector_header.setActionIcon(R.drawable.ic_add)
        selector_header.actionListener = {
            newAddressListener?.invoke()
        }

        show_more_addr.setOnClickListener {
            isCollapsed = !isCollapsed
            layoutAddresses()
        }

        attributeSet.recycle()
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        layoutAddresses()
    }

    private fun layoutAddresses() {
        listLayout.removeAllViews()

        models.forEach {
            if (isCollapsed) {
                if (it.selected) {
                    val b = LayoutAddressBinding.inflate(LayoutInflater.from(context))
                    b.root.setOnClickListener { _ ->
                        onAddressSelectedListener?.invoke(it.id)
                        collapse()
                    }
                    b.model = it
                    listLayout.addView(b.root)
                }
            } else {
                val b = LayoutAddressBinding.inflate(LayoutInflater.from(context))
                b.root.setOnClickListener { _ ->
                    onAddressSelectedListener?.invoke(it.id)
                    collapse()
                }
                b.model = it
                listLayout.addView(b.root)
            }
        }
        Timber.d("Number of childs" + listLayout.childCount)
        listLayout.invalidate()
        listLayout.requestLayout()
    }

    fun setModel(model: ArrayList<ShipmentAddressModel>) {
        models.clear()
        models.addAll(model)
        layoutAddresses()
    }

    fun setOnSelectedListener(listener: ((id: String) -> Unit)) {
        onAddressSelectedListener = listener
    }

    fun setNewAddressListener(listener: () -> Unit) {
        newAddressListener = listener
    }

    private fun collapse() {
        isCollapsed = true
        layoutAddresses()
    }
}

