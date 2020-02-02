package com.mockcommerce.modules.shared.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.mockcommerce.R
import com.mockcommerce.shared.loadImage
import kotlinx.android.synthetic.main.view_image.view.*

class ImageAdapter : RecyclerView.Adapter<ImageAdapter.ImageViewHolder>() {
    private val imageUrls: ArrayList<String> = ArrayList()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val view= LayoutInflater.from(parent.context).inflate(
            R.layout.view_image,
            parent,
            false
        )

        return ImageViewHolder(view)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val v = holder.itemView.product_image as ImageView
        val id = imageUrls[position]
        v.loadImage(id)
    }

    fun update(newURLs: ArrayList<String>){
        imageUrls.clear()
        imageUrls.addAll(newURLs)
        notifyDataSetChanged()
    }


    override fun getItemCount(): Int {
        return imageUrls.size
    }

    inner class ImageViewHolder(val view: View) : RecyclerView.ViewHolder(view){
    }
}