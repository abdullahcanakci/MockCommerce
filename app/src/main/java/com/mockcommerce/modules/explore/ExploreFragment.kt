package com.mockcommerce.modules.explore

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController

import com.mockcommerce.R

class ExploreFragment : Fragment() {

    companion object {
        fun newInstance() = ExploreFragment()
    }

    private lateinit var viewModel: ExploreViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v =  inflater.inflate(R.layout.explore_fragment, container, false)

        v.findViewById<Button>(R.id.btn_expose).setOnClickListener{
            this.findNavController().navigate(R.id.action_dest_explore_to_dest_expose)
        }

        v.findViewById<Button>(R.id.btn_info).setOnClickListener{
            this.findNavController().navigate(R.id.action_dest_explore_to_dest_info)
        }

        return v
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(ExploreViewModel::class.java)

    }

}
