package com.mockcommerce.modules.shared.product_page

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.mockcommerce.AppRepository
import com.mockcommerce.R
import com.mockcommerce.databinding.FragmentProductBinding
import com.mockcommerce.modules.shared.adapters.ImageAdapter
import com.mockcommerce.shared.ZoomOutPageTransformer
import com.mockcommerce.shared.setDrawable
import com.mockcommerce.usecases.BasketUseCase
import com.mockcommerce.utils.BaseFragment
import kotlinx.android.synthetic.main.fragment_product.view.*
import org.koin.android.ext.android.get
import timber.log.Timber

class ProductFragment : BaseFragment() {

    val args: ProductFragmentArgs by navArgs()

    val appRepository = get<AppRepository>()
    private lateinit var binding: FragmentProductBinding
    private val imageAdapter = ImageAdapter()

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

        binding = FragmentProductBinding.bind(view)

        binding.imageCarousel.adapter = imageAdapter
        binding.imageCarousel.setPageTransformer(ZoomOutPageTransformer())

        view.add_to_basket.setOnClickListener {
            val basketUseCase: BasketUseCase = get()
            basketUseCase.addToBasket(args.productId)
        }


        view.button_favourite.setOnClickListener {
            val disp = appRepository
                .setFavourite(args.productId)
                .subscribe { result -> binding.product = result }
            setFavouriteButton()
            addToDisposable(disp)
        }

        updateProduct()
        view.button_comments.setOnClickListener {
            findNavController().navigate(R.id.action_productFragment_to_commentsFragment)
        }
    }

    fun updateProduct() {
        val disposable = appRepository
            .getProduct(args.productId)
            .subscribe(
                { result ->
                    binding.product = result
                    imageAdapter.update(result.images)
                    setFavouriteButton()
                },
                { error -> Timber.d("Error while retrieving product \n $error") }
            )

        addToDisposable(disposable)
    }

    private fun setFavouriteButton() {
        view?.let {
            val button = it.button_favourite

            if (binding.product!!.favourite) {
                button.setDrawable(R.drawable.ic_favorite, R.color.colorPrimary)
            } else {
                button.setDrawable(R.drawable.ic_favorite_border, R.color.black)
            }
        } ?: Timber.d("Favourite Button state changed but button is null.")
    }
}