package com.mockcommerce.modules.checkout


import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.mockcommerce.AppRepository
import com.mockcommerce.R
import com.mockcommerce.modules.shared.adapters.GenericProductAdapter
import com.mockcommerce.usecases.UserUseCase
import com.mockcommerce.utils.BaseFragment
import kotlinx.android.synthetic.main.fragment_checkout_summary.view.*
import org.koin.android.ext.android.get
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class CheckoutSummaryFragment : BaseFragment() {

    private val sharedViewModel by sharedViewModel<CheckoutViewModel>()
    private val repository: AppRepository by inject()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_checkout_summary, container, false)
    }

    @SuppressLint("CheckResult")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        view.payment_options.setOnClickListener {
            findNavController().navigate(R.id.action_checkoutSummaryFragment_to_checkoutPaymentFragment)
        }

        val userUseCase: UserUseCase = get()
        userUseCase
            .getAddress()
            ?.subscribe { result ->
                view.shipment_address_selector.setModel(result)
                view.billing_address_selector.setModel(result)
            }

        view.billing_address_selector.setOnSelectedListener {
            sharedViewModel.billingAddressSelected(it)
        }

        view.shipment_address_selector.setOnSelectedListener {
            sharedViewModel.shipmentAddressSelected(it)
        }

        view.shipment_address_selector.setNewAddressListener {
            findNavController().navigate(R.id.action_checkoutSummaryFragment_to_newAddressFragment)
        }

        view.billing_address_selector.setNewAddressListener {
            findNavController().navigate(R.id.action_checkoutSummaryFragment_to_newAddressFragment)
        }

        val adapter = GenericProductAdapter(R.layout.item_checkout, null)
        view.checkout_product_list.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        view.checkout_product_list.adapter = adapter

        userUseCase
            .getBasket()
            .subscribe {
                adapter.updateProducts(it)
            }

    }
}
