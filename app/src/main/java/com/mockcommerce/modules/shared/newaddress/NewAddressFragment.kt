package com.mockcommerce.modules.shared.newaddress

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.mockcommerce.R
import com.mockcommerce.databinding.FragmentNewAddressBinding
import kotlinx.android.synthetic.main.fragment_new_address.view.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class NewAddressFragment() : Fragment() {

    private val viewModel by viewModel<NewAddressViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_new_address, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val v = FragmentNewAddressBinding.bind(view)
        v.model = viewModel

        ArrayAdapter.createFromResource(
            view.context,
            R.array.cityArrayList,
            R.layout.item_spinner
        ).also {
            it.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            view.city_spinner.adapter = it
            view.city_spinner.setSelection(viewModel.selectedCityIndex)
        }

        view.city_spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                p1: View?,
                position: Int,
                p3: Long
            ) {
                if (viewModel.selectedCityIndex != position) {
                    viewModel.selectedCityIndex = position
                    viewModel.city = resources.getStringArray(R.array.cityArrayList)[position]
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        view.button_address_save.setOnClickListener {
            viewModel.saveAddress()
            findNavController().navigateUp()
        }
    }
}
