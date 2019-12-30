package com.mockcommerce.modules.explore

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2

import com.mockcommerce.R
import com.mockcommerce.models.CarouselItem
import com.mockcommerce.modules.explore.views.CarouselAdapter
import com.mockcommerce.shared.ZoomOutPageTransformer
import kotlinx.android.synthetic.main.explore_fragment.view.*

class ExploreFragment : Fragment() {

    companion object {
        fun newInstance() = ExploreFragment()
    }

    private lateinit var viewModel: ExploreViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProviders.of(this).get(ExploreViewModel::class.java)

        val v =  inflater.inflate(R.layout.explore_fragment, container, false)

        setupCarousel(v.carousel)

        return v
    }

    private fun setupCarousel(carousel: ViewPager2){
        val adapter = CarouselAdapter(listOf(
            CarouselItem("Başlık 1", "Alt Başlık 1", "Başlık 1", R.mipmap.carousel_1),
            CarouselItem("Başlık 2", "Alt Başlık 2", "Başlık 2", R.mipmap.carousel_2),
            CarouselItem("Başlık 3", "Alt Başlık 3", "Başlık 3", R.mipmap.carousel_3),
            CarouselItem("Başlık 4", "Alt Başlık 4", "Başlık 4", R.mipmap.carousel_4)
        ),
            this.activity!!)

        carousel.adapter = adapter

        carousel.setPageTransformer(ZoomOutPageTransformer())
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

}
