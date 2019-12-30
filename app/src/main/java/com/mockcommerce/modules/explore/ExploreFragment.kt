package com.mockcommerce.modules.explore

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2

import com.mockcommerce.R
import com.mockcommerce.models.ShowcaseItem
import com.mockcommerce.modules.explore.views.ShowcaseAdapter
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
        val adapter = ShowcaseAdapter(listOf(
            ShowcaseItem("Başlık 1", "Alt Başlık 1", "Başlık 1", R.mipmap.showcase_1),
            ShowcaseItem("Başlık 2", "Alt Başlık 2", "Başlık 2", R.mipmap.showcase_2),
            ShowcaseItem("Başlık 3", "Alt Başlık 3", "Başlık 3", R.mipmap.showcase_3),
            ShowcaseItem("Başlık 4", "Alt Başlık 4", "Başlık 4", R.mipmap.showcase_4)
        ),
            this.activity!!)

        carousel.adapter = adapter

        carousel.setPageTransformer(ZoomOutPageTransformer())
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

}
