package com.mockcommerce.modules.checkout


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.mockcommerce.R
import kotlinx.android.synthetic.main.fragment_checkout_complete.view.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class CheckoutCompleteFragment : Fragment() {
    private val sharedViewModel by sharedViewModel<CheckoutViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_checkout_complete, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.button_checkout_complete.setOnClickListener {
            findNavController().navigate(R.id.action_checkoutCompleteFragment_to_checkoutEndFragment)
        }
    }
}
