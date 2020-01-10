package com.mockcommerce.modules.shared.product_page

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.mockcommerce.R
import kotlinx.android.synthetic.main.view_image.view.*

class ProductImageAdapter : RecyclerView.Adapter<ProductImageAdapter.ProductImageViewHolder>() {
    private val imageUrls: ArrayList<String> = ArrayList()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductImageViewHolder {
        val view= LayoutInflater.from(parent.context).inflate(
            R.layout.view_image,
            parent,
            false
        )

        return ProductImageViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductImageViewHolder, position: Int) {
        holder.itemView.findViewById<ImageView>(R.id.product_image).setImageResource(R.drawable.ic_product_image)
    }

    fun update(newURLs: ArrayList<String>){
        imageUrls.clear()
        imageUrls.addAll(newURLs)
    }


    override fun getItemCount(): Int {
        return imageUrls.size
    }

    inner class ProductImageViewHolder(val view: View) : RecyclerView.ViewHolder(view){
    }
}