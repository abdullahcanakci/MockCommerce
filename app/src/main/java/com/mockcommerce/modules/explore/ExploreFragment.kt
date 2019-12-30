package com.mockcommerce.modules.explore

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBinderMapper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2

import com.mockcommerce.R
import com.mockcommerce.databinding.CarouselBinding
import com.mockcommerce.models.Carousel
import com.mockcommerce.models.CarouselItem
import com.mockcommerce.models.ShowcaseItem
import com.mockcommerce.modules.explore.views.CarouselAdapter
import com.mockcommerce.modules.explore.views.ShowcaseAdapter
import com.mockcommerce.shared.ZoomOutPageTransformer
import kotlinx.android.synthetic.main.carousel.view.*
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


        setupShowcase(v.showcase)

        val cBind = CarouselBinding.bind(v.main_carousel)


        val c = Carousel(
            "Başlık",
            true,
            listOf(
                CarouselItem("Başlık 1", "Alt Başlık 1", "Başlık 1", R.mipmap.carousel_small_1),
                CarouselItem("Başlık 2", "Alt Başlık 2", "Başlık 2", R.mipmap.carousel_small_2),
                CarouselItem("Başlık 3", "Alt Başlık 3", "Başlık 3", R.mipmap.carousel_small_3),
                CarouselItem("Başlık 4", "Alt Başlık 4", "Başlık 4", R.mipmap.carousel_small_4),
                CarouselItem("Başlık 5", "Alt Başlık 5", "Başlık 5", R.mipmap.carousel_small_5),
                CarouselItem("Başlık 6", "Alt Başlık 6", "Başlık 6", R.mipmap.carousel_small_6)
            ))

        cBind.model = c

        val cBind1 = CarouselBinding.bind(v.secondary_carousel)
        cBind1.model = c

        setupCarousel(cBind)
        setupCarousel(cBind1)

        return v
    }

    private fun setupShowcase(showcase: ViewPager2){
        val adapter = ShowcaseAdapter(listOf(
            ShowcaseItem("Başlık 1", "Alt Başlık 1", "Başlık 1", R.mipmap.showcase_1),
            ShowcaseItem("Başlık 2", "Alt Başlık 2", "Başlık 2", R.mipmap.showcase_2),
            ShowcaseItem("Başlık 3", "Alt Başlık 3", "Başlık 3", R.mipmap.showcase_3),
            ShowcaseItem("Başlık 4", "Alt Başlık 4", "Başlık 4", R.mipmap.showcase_4)
        ),
            this.activity!!)

        showcase.adapter = adapter

        showcase.setPageTransformer(ZoomOutPageTransformer())
    }

    private fun setupCarousel(carouselBinding: CarouselBinding){


        val adapter = CarouselAdapter(carouselBinding.model!!.items)
        carouselBinding.carousel.adapter = adapter
        carouselBinding.carousel.layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)

    }


}
