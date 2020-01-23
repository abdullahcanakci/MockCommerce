package com.mockcommerce.modules.explore

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mockcommerce.databinding.ItemExploreShowcaseBinding
import com.mockcommerce.models.ShowcaseItem

class ShowcaseAdapter : RecyclerView.Adapter<ShowcaseAdapter.ShowcaseViewHolder>() {
    private val models = ArrayList<ShowcaseItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShowcaseViewHolder {
        val binding = ItemExploreShowcaseBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return ShowcaseViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return models.size
    }

    override fun onBindViewHolder(holder: ShowcaseViewHolder, position: Int) {
        holder.bind(models[position])
    }

    fun update(newModels: ArrayList<ShowcaseItem>) {
        models.clear()
        models.addAll(newModels)
        notifyDataSetChanged()
    }

    inner class ShowcaseViewHolder(private val binding: ItemExploreShowcaseBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(model: ShowcaseItem) {
            binding.model = model
        }
    }
}