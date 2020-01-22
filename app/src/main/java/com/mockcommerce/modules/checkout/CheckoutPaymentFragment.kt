package com.mockcommerce.modules.checkout


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.mockcommerce.R
import com.mockcommerce.databinding.FragmentCheckoutPaymentBinding
import kotlinx.android.synthetic.main.fragment_checkout_payment.view.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class CheckoutPaymentFragment : Fragment() {

    private val sharedViewModel by sharedViewModel<CheckoutViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_checkout_payment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        view.checkout_complete.setOnClickListener {
            findNavController().navigate(R.id.action_checkoutPaymentFragment_to_checkoutEndFragment)
        }

        view.checkout_checkbox_aggrement.setOnCheckedChangeListener { buttonView, isChecked ->
            view.checkout_complete.isEnabled = isChecked
        }

        val v = FragmentCheckoutPaymentBinding.bind(view)
        v.model = sharedViewModel
    }

}
