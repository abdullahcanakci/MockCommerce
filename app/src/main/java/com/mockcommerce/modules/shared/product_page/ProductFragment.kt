package com.mockcommerce.modules.shared.product_page

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.mockcommerce.databinding.ProductFragmentBinding
import com.mockcommerce.modules.shared.adapters.ImageAdapter
import com.mockcommerce.shared.ZoomOutPageTransformer
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProductFragment : Fragment() {

    val args: ProductFragmentArgs by navArgs()
    val viewModel: ProductViewModel by viewModel()

    lateinit var binding: ProductFragmentBinding
    lateinit var adapter: ImageAdapter

    companion object {
        fun newInstance() = ProductFragment()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ProductFragmentBinding.inflate(inflater)
        adapter = ImageAdapter()

        binding.imageCarousel.adapter = adapter
        binding.imageCarousel.setPageTransformer(ZoomOutPageTransformer())

        viewModel.product.observe(this.viewLifecycleOwner, Observer { t ->
            adapter.update(t.images)
            binding.model = t
        })

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val id = args.productId
        viewModel.productId = id
    }
}
