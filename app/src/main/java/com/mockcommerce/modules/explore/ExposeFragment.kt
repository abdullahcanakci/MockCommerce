package com.mockcommerce.modules.explore

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.mockcommerce.R

class ExposeFragment : Fragment() {

    companion object {
        fun newInstance() = ExposeFragment()
    }

    private lateinit var viewModel: ExposeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.expose_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(ExposeViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
