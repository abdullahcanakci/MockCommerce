package com.mockcommerce.modules.orders

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.mockcommerce.R
import kotlinx.android.synthetic.main.fragment_orders.view.*
import org.koin.android.ext.android.inject

class OrdersFragment : Fragment() {

    companion object {
        fun newInstance() = OrdersFragment()
    }

    private val viewModel by inject<OrdersViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_orders, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = OrderAdapter() { id ->
            val action = OrdersFragmentDirections.actionOrderToOrderFragment(id)
            findNavController().navigate(action)

        }
        view.orders_recycler.adapter = adapter
        view.orders_recycler.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        viewModel.orders.observe(viewLifecycleOwner, Observer {
            adapter.update(it)
        })

        ArrayAdapter.createFromResource(
            view.context,
            R.array.order_qualifier,
            R.layout.item_spinner
        ).also {
            it.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            view.spinner_order_qualifier.adapter = it
        }


    }
}
