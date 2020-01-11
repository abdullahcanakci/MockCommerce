package com.mockcommerce.modules.shared.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.mockcommerce.R

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
        holder.itemView.findViewById<ImageView>(R.id.product_image).setImageResource(R.drawable.ic_product_image)
    }

    fun update(newURLs: ArrayList<String>){
        imageUrls.clear()
        imageUrls.addAll(newURLs)
    }


    override fun getItemCount(): Int {
        return imageUrls.size
    }

    inner class ImageViewHolder(val view: View) : RecyclerView.ViewHolder(view){
    }
}