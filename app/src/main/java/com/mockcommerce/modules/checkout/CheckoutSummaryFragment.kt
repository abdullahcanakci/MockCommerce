package com.mockcommerce.modules.checkout


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.mockcommerce.R
import kotlinx.android.synthetic.main.fragment_checkout_summary.*
import kotlinx.android.synthetic.main.fragment_checkout_summary.view.*
import kotlinx.android.synthetic.main.item_checkout.view.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class CheckoutSummaryFragment : Fragment() {

    private val sharedViewModel by sharedViewModel<CheckoutViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_checkout_summary, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        view.payment_options.setOnClickListener {
            findNavController().navigate(R.id.action_checkoutSummaryFragment_to_checkoutPaymentFragment)
        }

        sharedViewModel.addresses.observe(viewLifecycleOwner, Observer {
            //view.shipment_address_selector.setModel(it)
            view.billing_address_selector.setModel(it)
        })

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

        sharedViewModel.productInBasket.observe(viewLifecycleOwner, Observer { list ->
            val root = checkout_product_list
            root.removeAllViews()
            var basketTotal = 0.0F

            list.forEach {
                basketTotal += it.numbersInBasket * it.price

                val v = LayoutInflater
                    .from(context)
                    .inflate(R.layout.item_checkout, root, false)
                v.item_checkout_product_name.text = it.name
                Glide.with(root)
                    .load("https://raw.githubusercontent.com/abdullahcanakci/MockCommerce/master/mockserver/" + it.images[0])
                    .placeholder(R.drawable.ic_product_image)
                    .into(v.item_checkout_product_image)

                root.addView(v)
            }
            checkout_total.text = context!!.getString(R.string.price, basketTotal)
            sharedViewModel.basketTotal.postValue(basketTotal)
            root.invalidate()
            root.requestLayout()
        })
    }
}
