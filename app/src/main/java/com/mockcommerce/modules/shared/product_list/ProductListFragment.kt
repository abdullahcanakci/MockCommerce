package com.mockcommerce.modules.shared.product_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mockcommerce.R
import kotlinx.android.synthetic.main.fragment_productlist.view.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class ProductListFragment : Fragment() {

    val viewModel: ProductListViewModel by viewModel()

    lateinit var adapter: ProductListItemAdapter

    companion object {
        fun newInstance() = ProductListFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.fragment_productlist, container, false)

        v.product_list.layoutManager = GridLayoutManager(context, 2, RecyclerView.VERTICAL, false)

        adapter = ProductListItemAdapter { id ->
            val action = ProductListFragmentDirections.actionDestProductListToProduct(id)
            findNavController().navigate(action)
        }
        v.product_list.adapter = adapter

        viewModel.productList.observe(this.viewLifecycleOwner, Observer { t ->
            Timber.d("Number of elements in the productlist is ${t.size}")
            adapter.update(t)
        })

        return v
    }
}
