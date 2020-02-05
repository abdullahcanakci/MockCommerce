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

    lateinit var binding: FragmentProductBinding
    lateinit var adapter: ImageAdapter

    companion object {
        fun newInstance() = ProductFragment()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProductBinding.inflate(inflater)
        adapter = ImageAdapter()

        binding.imageCarousel.adapter = adapter
        binding.imageCarousel.setPageTransformer(ZoomOutPageTransformer())

        viewModel.product.observe(this.viewLifecycleOwner, Observer { t ->
            adapter.update(t.images)
            binding.product = t
        })

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val id = args.productId
        viewModel.productId = id

        view.add_to_basket.setOnClickListener{
            viewModel.addToBasket()
        }

        viewModel.product.observe(viewLifecycleOwner, Observer { product ->
            viewModel.favourite = product.favourite
            setFavouriteButton()
        })

        view.button_favourite.setOnClickListener { _ ->
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
