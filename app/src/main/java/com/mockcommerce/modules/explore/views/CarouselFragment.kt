package com.mockcommerce.modules.explore.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.mockcommerce.R
import com.mockcommerce.databinding.CarouselItemBinding
import com.mockcommerce.models.CarouselItem

class CarouselFragment : Fragment() {
    lateinit var binding: CarouselItemBinding
    lateinit var model: CarouselItem

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate<CarouselItemBinding>(inflater, R.layout.carousel_item, container, false)
        binding.model = model
        return binding.root
    }

    fun set(item: CarouselItem){
        model = item
    }
}