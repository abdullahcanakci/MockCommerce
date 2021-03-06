package com.mockcommerce.modules.account

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.mockcommerce.R
import kotlinx.android.synthetic.main.fragment_account.view.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class AccountFragment : Fragment() {

    val viewModel by viewModel<AccountViewModel>()

    companion object {
        fun newInstance() = AccountFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_account, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.button_logout.setOnClickListener {
            onLogout()
        }

        view.button_favourite.setOnClickListener {
            onFavourite()
        }
        view.button_addresses.setOnClickListener {
            onAddress()
        }
        view.button_settings.setOnClickListener {
            onSettings()
        }
    }

    fun onLogout() {
        viewModel.logout()
        findNavController().navigate(R.id.account)
    }

    fun onFavourite() {
        findNavController().navigate(AccountFragmentDirections.actionAccountLandingFragmentToFavouritesFragment())
    }

    fun onAddress() {
        findNavController().navigate(AccountFragmentDirections.actionAccountLandingFragmentToAddressesFragment())
    }

    fun onSettings() {
        Toast.makeText(
            context,
            "Kullanıcı ayarları henüz eklenmedi.",
            Toast.LENGTH_SHORT
        ).show()
    }

}
