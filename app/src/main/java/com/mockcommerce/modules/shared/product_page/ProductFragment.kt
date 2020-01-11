package com.mockcommerce.modules.shared.product_page

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.mockcommerce.databinding.ProductFragmentBinding
import com.mockcommerce.models.ProductModel
import com.mockcommerce.modules.shared.adapters.ImageAdapter
import com.mockcommerce.shared.ZoomOutPageTransformer

class ProductFragment : Fragment() {

    companion object {
        fun newInstance() = ProductFragment()
    }

    private lateinit var viewModel: ProductViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = ProductFragmentBinding.inflate(inflater)

        val adapter = ImageAdapter()
        binding.imageCarousel.adapter = adapter
        adapter.update(ArrayList(listOf("a", "b", "c", "d", "e")))
        binding.imageCarousel.setPageTransformer(ZoomOutPageTransformer())

        val productModel = ProductModel(0, "Ürün adı", 50.0f, 120.0f, 0)
        binding.model = productModel

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(ProductViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
