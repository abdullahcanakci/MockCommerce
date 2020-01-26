package com.mockcommerce.modules.account


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.mockcommerce.AppRepository
import com.mockcommerce.R
import kotlinx.android.synthetic.main.fragment_login.view.*
import org.koin.android.ext.android.inject

class LoginFragment : Fragment() {
    private val repo: AppRepository by inject()
    var email: EditText? = null
    var password: EditText? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (repo.isLoggedIn()) {
            findNavController().navigate(LoginFragmentDirections.actionAccountToAccountLandingFragment())
        }

        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.button_register.setOnClickListener {
            onRegister()
        }

        view.button_login.setOnClickListener {
            onLogin()
        }
        email = view.login_email
        password = view.login_password
    }

    fun onLogin() {
        if (repo.login(email?.text.toString(), password?.text.toString())) {
            findNavController().navigate(LoginFragmentDirections.actionAccountToAccountLandingFragment())
        } else {
            Toast.makeText(context, "Yanlış kimlik bilgileri!", Toast.LENGTH_SHORT).show()
        }
    }

    fun onRegister() {
        findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToRegisterFragment())
    }


}
