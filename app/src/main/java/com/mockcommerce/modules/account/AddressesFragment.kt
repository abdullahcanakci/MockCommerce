package com.mockcommerce.modules.account

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.mockcommerce.AppRepository
import com.mockcommerce.R
import com.mockcommerce.modules.shared.newaddress.AddressAdapter
import com.mockcommerce.usecases.UserUseCase
import com.mockcommerce.utils.BaseFragment
import kotlinx.android.synthetic.main.addresses_fragment.view.*
import org.koin.android.ext.android.get
import org.koin.android.ext.android.inject

class AddressesFragment : BaseFragment() {

    private val repository by inject<AppRepository>()

    companion object {
        fun newInstance() = AddressesFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.addresses_fragment, container, false)
    }

    @SuppressLint("CheckResult")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.header.actionListener = { _ ->
            findNavController().navigate(AddressesFragmentDirections.actionAddressesFragmentToNewAddressFragment2())
        }

        view.button_go_back.setOnClickListener {
            onGoBack()
        }

        val adapter = AddressAdapter {
            repository.setDefaultAddress(it)
        }

        val userUseCase: UserUseCase = get()
        userUseCase
            .getAddress()
            ?.subscribe { result ->
                adapter.update(result)
            }

        view.address_recycler.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        view.address_recycler.adapter = adapter
    }

    fun onGoBack() {
        findNavController().navigateUp()
    }
}
