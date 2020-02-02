package com.mockcommerce.modules.orders

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.mockcommerce.R
import com.mockcommerce.databinding.FragmentOrderBinding
import com.mockcommerce.modules.shared.adapters.GenericProductAdapter
import kotlinx.android.synthetic.main.fragment_order.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class OrderFragment : Fragment() {

    val args: OrderFragmentArgs by navArgs()
    val viewModel by viewModel<OrderViewModel>()

    companion object {
        fun newInstance() = OrderFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_order, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val orderId = args.orderId

        val adapter = GenericProductAdapter(R.layout.item_order_product, null)

        recycler_order.adapter = adapter
        recycler_order.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)


        val binding = FragmentOrderBinding.bind(view)
        viewModel.getOrder(orderId).observe(viewLifecycleOwner, Observer { order ->
            binding.order = order
            adapter.updateProducts(order.products)

            viewModel.getAddress(order.billingAddressId)
                .observe(viewLifecycleOwner, Observer { address ->
                    binding.billing = address
                })

            viewModel.getAddress(order.shippingAddressId)
                .observe(viewLifecycleOwner, Observer { address ->
                    binding.shipment = address
                })
        })

    }
}
