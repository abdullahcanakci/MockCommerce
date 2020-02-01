package com.mockcommerce.shared

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mockcommerce.R
import timber.log.Timber

class NotifierRecycler(context: Context, attrs: AttributeSet) : RecyclerView(context, attrs) {
    var textView: TextView? = null
    val observer = AdapterObserver()

    var message = ""
    var textId = 0

    init {
        val attributeSet = context.obtainStyledAttributes(attrs, R.styleable.NotifierRecycler)

        textId = attributeSet.getResourceId(R.styleable.NotifierRecycler_textview, -1)

        message = attributeSet.getString(R.styleable.NotifierRecycler_message).toString()

        attributeSet.recycle()
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        try {
            textView = rootView.findViewById<TextView>(textId)
            textView!!.text = message
        } catch (e: IllegalArgumentException) {
            Timber.d("Illegal argument at NotifierRecycler\n $e")
        }
    }

    private fun checkIfEmpty() {
        if (textView != null && adapter != null) {
            val visibility = adapter!!.itemCount == 0
            textView!!.visibility = if (visibility) View.VISIBLE else View.GONE
            this.visibility = if (visibility) View.GONE else View.VISIBLE
        }
        if (isInEditMode) {
            this.visibility = View.VISIBLE
        }
    }

    override fun setAdapter(newAdapter: Adapter<*>?) {
        val oldAdapter = adapter
        oldAdapter?.unregisterAdapterDataObserver(observer)
        super.setAdapter(newAdapter)
        newAdapter?.registerAdapterDataObserver(observer)
        checkIfEmpty()
    }

    inner class AdapterObserver : AdapterDataObserver() {
        override fun onChanged() {
            super.onChanged()
            checkIfEmpty()
        }

        override fun onItemRangeRemoved(positionStart: Int, itemCount: Int) {
            super.onItemRangeRemoved(positionStart, itemCount)
            checkIfEmpty()
        }

        override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
            super.onItemRangeInserted(positionStart, itemCount)
            checkIfEmpty()
        }
    }
}