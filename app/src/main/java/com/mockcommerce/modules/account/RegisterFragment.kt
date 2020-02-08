package com.mockcommerce.modules.account


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.mockcommerce.AppRepository
import com.mockcommerce.R
import com.mockcommerce.databinding.FragmentRegisterBinding
import com.mockcommerce.utils.BaseFragment
import kotlinx.android.synthetic.main.fragment_register.view.*
import org.koin.android.ext.android.get
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * A simple [Fragment] subclass.
 */
class RegisterFragment : BaseFragment() {

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

    private fun onRegister() {
        if (viewModel.isUserValid()) {
            val appRepository = get<AppRepository>()
            val disposable = appRepository
                .register(viewModel.getUser())
                .subscribe(
                    {

                        findNavController().navigateUp()
                    },
                    {
                        Toast.makeText(context, "Kayıt gerçekleştirilemedi.", Toast.LENGTH_SHORT)
                            .show()
                    }
                )

            addToDisposable(disposable)
        } else {
            Toast.makeText(context, "Eksik alanlar bulunuyor.", Toast.LENGTH_SHORT).show()
        }
    }

    fun onCancel() {
        findNavController().navigateUp()
    }


}
