package com.mockcommerce.modules.explore.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.mockcommerce.R
import com.mockcommerce.databinding.ItemExploreShowcaseBinding
import com.mockcommerce.models.ShowcaseItem

class ShowcaseFragment : Fragment() {
    lateinit var binding: ItemExploreShowcaseBinding
    lateinit var model: ShowcaseItem

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate<ItemExploreShowcaseBinding>(
            inflater,
            R.layout.item_explore_showcase,
            container,
            false
        )
        binding.model = model
        return binding.root
    }

    fun set(item: ShowcaseItem){
        model = item
    }
}