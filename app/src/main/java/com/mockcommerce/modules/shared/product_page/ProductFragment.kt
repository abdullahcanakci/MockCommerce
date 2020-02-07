package com.mockcommerce.modules.shared.product_page

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.mockcommerce.R
import com.mockcommerce.databinding.FragmentProductBinding
import com.mockcommerce.modules.shared.adapters.ImageAdapter
import com.mockcommerce.shared.ZoomOutPageTransformer
import com.mockcommerce.shared.setDrawable
import kotlinx.android.synthetic.main.fragment_product.view.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class ProductFragment : Fragment() {

    val args: ProductFragmentArgs by navArgs()
    val viewModel: ProductViewModel by viewModel()

    companion object {
        fun newInstance() = ProductFragment()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_product, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val binding = FragmentProductBinding.bind(view)
        viewModel.productId = args.productId

        val imageAdapter = ImageAdapter()
        binding.imageCarousel.adapter = imageAdapter
        binding.imageCarousel.setPageTransformer(ZoomOutPageTransformer())

        view.add_to_basket.setOnClickListener {
            viewModel.addToBasket()
        }

        viewModel.getProduct(null).observe(viewLifecycleOwner, Observer { product ->

            binding.product = product
            imageAdapter.update(product.images)

        })

        view.button_favourite.setOnClickListener {
            viewModel.toggleFavourite()
        }

        view.button_comments.setOnClickListener {
            findNavController().navigate(R.id.action_productFragment_to_commentsFragment)
        }
    }

    private fun setFavouriteButton() {
        view?.let {
            val button = it.button_favourite

            if (viewModel.favourite) {
                button.setDrawable(R.drawable.ic_favorite, R.color.colorPrimary)
            } else {
                button.setDrawable(R.drawable.ic_favorite_border, R.color.black)
            }
        } ?: Timber.d("Favourite Button state changed but button is null.")
    }
}