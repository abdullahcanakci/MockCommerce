package com.mockcommerce.modules.account

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.mockcommerce.R
import com.mockcommerce.modules.shared.newaddress.AddressAdapter
import kotlinx.android.synthetic.main.addresses_fragment.view.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class AddressesFragment : Fragment() {

    val viewModel by viewModel<AddressesViewModel>()

    companion object {
        fun newInstance() = AddressesFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.addresses_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.header.actionListener = { _ ->
            findNavController().navigate(AddressesFragmentDirections.actionAddressesFragmentToNewAddressFragment2())
        }

        view.button_go_back.setOnClickListener {
            onGoBack()
        }
        val adapter = AddressAdapter() {
            viewModel.addressSelected(it)
        }

        viewModel.addresses.observe(viewLifecycleOwner, Observer {
            adapter.update(it)
        })

        view.address_recycler.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        view.address_recycler.adapter = adapter
    }

    fun onGoBack() {
        findNavController().navigateUp()
    }
}
