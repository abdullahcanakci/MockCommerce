package com.mockcommerce.modules.shared.product_page

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.mockcommerce.AppRepository

import com.mockcommerce.databinding.ProductFragmentBinding
import com.mockcommerce.models.ProductModel
import com.mockcommerce.modules.shared.adapters.ImageAdapter
import com.mockcommerce.shared.ZoomOutPageTransformer
import org.koin.android.ext.android.inject

class ProductFragment : Fragment() {

    val appRepository : AppRepository by inject()
    val args: ProductFragmentArgs by navArgs()


    companion object {
        fun newInstance() = ProductFragment()
    }

    private lateinit var viewModel: ProductViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = ProductFragmentBinding.inflate(inflater)

        viewModel = ViewModelProviders.of(this).get(ProductViewModel::class.java)

        val adapter = ImageAdapter()
        binding.imageCarousel.adapter = adapter
        binding.imageCarousel.setPageTransformer(ZoomOutPageTransformer())

        viewModel.product.observe(this.viewLifecycleOwner, Observer { t ->
            adapter.update(t.images)
            binding.model = t
        })

        val id = args.productId
        appRepository.getProduct(id) {
            viewModel.product.postValue(it)
        }

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        // TODO: Use the ViewModel
    }

}
