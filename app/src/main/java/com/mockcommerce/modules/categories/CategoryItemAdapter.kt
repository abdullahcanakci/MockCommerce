package com.mockcommerce.modules.categories

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mockcommerce.databinding.ItemCategoryBinding
import com.mockcommerce.models.CategoryModel
import com.mockcommerce.shared.loadImage

class CategoryItemAdapter (private val onSelectListener: ((id: String, isCategory: Boolean) -> Unit)) : RecyclerView.Adapter<CategoryItemAdapter.CategoryItemHolder>(){

    private val models = ArrayList<CategoryModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryItemHolder {
        val b = ItemCategoryBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        b.categoryImage.clipToOutline = true

        return CategoryItemHolder(b)
    }

    override fun getItemCount(): Int {
        return models.size
    }

    override fun onBindViewHolder(holder: CategoryItemHolder, position: Int) {
        holder.bind(models[position])
    }

    fun update(list: ArrayList<CategoryModel>) {
        models.clear()
        models.addAll(list)
        notifyDataSetChanged()
    }

    inner class CategoryItemHolder(private val binding: ItemCategoryBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(model: CategoryModel) {
            binding.model = model
            binding.root.setOnClickListener{
                onSelectListener(model.id, model.destinationCategoryId != null)
            }
            binding.categoryImage.loadImage("https://raw.githubusercontent.com/abdullahcanakci/MockCommerce/master/mockserver/${model.image}")
        }
    }
}