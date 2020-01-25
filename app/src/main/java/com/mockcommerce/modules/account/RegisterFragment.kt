package com.mockcommerce.modules.account


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.mockcommerce.R
import com.mockcommerce.databinding.FragmentRegisterBinding
import kotlinx.android.synthetic.main.fragment_register.view.*
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * A simple [Fragment] subclass.
 */
class RegisterFragment : Fragment() {

    val viewModel by viewModel<RegisterViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_register, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val b = FragmentRegisterBinding.bind(view)
        b.model = viewModel

        view.button_register.setOnClickListener {
            onRegister()
        }

        view.button_cancel.setOnClickListener {
            onCancel()
        }
    }

    fun onRegister() {
        viewModel.register()
        findNavController().navigateUp()
    }

    fun onCancel() {
        findNavController().navigateUp()
    }


}
