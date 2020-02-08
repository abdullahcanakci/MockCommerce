package com.mockcommerce.modules.account


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.mockcommerce.AppRepository
import com.mockcommerce.R
import com.mockcommerce.utils.BaseFragment
import com.mockcommerce.utils.TokenInterceptor
import kotlinx.android.synthetic.main.fragment_login.view.*
import org.koin.android.ext.android.get
import org.koin.android.ext.android.inject
import timber.log.Timber

class LoginFragment : BaseFragment() {
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
        val disposable = repo.login(email?.text.toString(), password?.text.toString())
            .subscribe(
                { token ->
                    val tokenInterceptor: TokenInterceptor = get()
                    tokenInterceptor.token = token
                    findNavController().navigate(LoginFragmentDirections.actionAccountToAccountLandingFragment())
                },
                { error ->
                    Timber.d(error.message)
                    Toast.makeText(context, "Yanlış kimlik bilgileri!", Toast.LENGTH_SHORT).show()
                }
            )

        addToDisposable(disposable)
    }

    fun onRegister() {
        findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToRegisterFragment())
    }

}
