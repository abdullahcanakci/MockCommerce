package com.mockcommerce.modules.explore

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.viewpager2.widget.ViewPager2
import com.mockcommerce.R
import com.mockcommerce.models.CarouselItemModel
import com.mockcommerce.models.CarouselModel
import com.mockcommerce.models.ShowcaseItem
import com.mockcommerce.modules.explore.views.ShowcaseAdapter
import com.mockcommerce.shared.ZoomOutPageTransformer
import kotlinx.android.synthetic.main.fragment_explore.view.*

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

        val v = inflater.inflate(R.layout.fragment_explore, container, false)


        setupShowcase(v.showcase)

        val c = CarouselModel(
            "Başlık",
            true,
            listOf(
                CarouselItemModel("Başlık 1", "Alt Başlık 1", "Başlık 1", R.mipmap.carousel_small_1),
                CarouselItemModel("Başlık 2", "Alt Başlık 2", "Başlık 2", R.mipmap.carousel_small_2),
                CarouselItemModel("Başlık 3", "Alt Başlık 3", "Başlık 3", R.mipmap.carousel_small_3),
                CarouselItemModel("Başlık 4", "Alt Başlık 4", "Başlık 4", R.mipmap.carousel_small_4),
                CarouselItemModel("Başlık 5", "Alt Başlık 5", "Başlık 5", R.mipmap.carousel_small_5),
                CarouselItemModel("Başlık 6", "Alt Başlık 6", "Başlık 6", R.mipmap.carousel_small_6)
            ))

        v.test.setModel(c)

        v.test1.setModel(
            CarouselModel(
                "Bilgi",
                false,
                listOf(
                    CarouselItemModel("", "", "Detay 1", R.mipmap.info_1),
                    CarouselItemModel("", "", "Detay 2", R.mipmap.info_2),
                    CarouselItemModel("", "", "Detay 3", R.mipmap.info_3),
                    CarouselItemModel("", "", "Detay 4", R.mipmap.info_4),
                    CarouselItemModel("", "", "Detay 5", R.mipmap.info_5)
                )
            )
        )

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
}
