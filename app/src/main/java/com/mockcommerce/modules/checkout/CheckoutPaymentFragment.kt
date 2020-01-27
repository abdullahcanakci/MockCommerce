package com.mockcommerce.modules.checkout


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.mockcommerce.R
import com.mockcommerce.databinding.FragmentCheckoutPaymentBinding
import kotlinx.android.synthetic.main.fragment_checkout_payment.*
import kotlinx.android.synthetic.main.fragment_checkout_payment.view.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import timber.log.Timber

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
            Toast.makeText(context, "Kart bilgileri kontrol edilmemektedir.", Toast.LENGTH_SHORT)
                .show()
            sharedViewModel.completePayment()
            findNavController().navigate(R.id.action_checkoutPaymentFragment_to_checkoutEndFragment)
        }

        view.checkout_checkbox_aggrement.setOnCheckedChangeListener { _, isChecked ->
            view.checkout_complete.isEnabled = isChecked
        }

        val v = FragmentCheckoutPaymentBinding.bind(view)
        v.card = sharedViewModel.cardModel

        view.checkout_card_expiration_year.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    p1: View?,
                    position: Int,
                    p3: Long
                ) {
                    Timber.d("Position is $position")
                    if (position == 0) {
                        sharedViewModel.cardModel.dueYear = 0
                    } else {
                        sharedViewModel.cardModel.dueYear = 2020 + position
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }

        view.checkout_card_expiration_month.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    p1: View?,
                    position: Int,
                    p3: Long
                ) {
                    if (position == 0) {
                        sharedViewModel.cardModel.dueYear = 0
                    } else {
                        sharedViewModel.cardModel.dueYear = position
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }

        checkout_total.text = context!!.getString(R.string.price, sharedViewModel.basketTotal.value)
    }

}
