package com.mockcommerce.modules.shared.product_list

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.mockcommerce.AppRepository

import com.mockcommerce.R
import com.mockcommerce.models.ProductModel
import kotlinx.android.synthetic.main.product_list_fragment.view.*
import okhttp3.*
import org.koin.android.ext.android.inject
import timber.log.Timber
import java.io.IOException
import java.lang.reflect.Type

//import okhttp3.RequestBody.Companion.toRequestBody

class ProductListFragment : Fragment() {

    val appRepository : AppRepository by inject()

    companion object {
        fun newInstance() = ProductListFragment()
    }

    private lateinit var viewModel: ProductListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.product_list_fragment, container, false)
        viewModel = ViewModelProviders.of(this).get(ProductListViewModel::class.java)

        v.product_list.layoutManager = GridLayoutManager(context, 2, RecyclerView.VERTICAL,false)

        val adapter = ProductListItemAdapter{id ->
            val action = ProductListFragmentDirections.actionDestProductListToProductFragment(id)
            findNavController().navigate(action)
        }
        v.product_list.adapter = adapter

        viewModel.productList.observe(this.viewLifecycleOwner, Observer { t ->
            adapter.update(t)
        })

        appRepository.getProductList {
            viewModel.productList.postValue(it)
        }
        return v
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        // TODO: Use the ViewModel
    }

}
