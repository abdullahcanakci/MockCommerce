package com.mockcommerce.shared.views.productlist

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.mockcommerce.R
import com.mockcommerce.models.ProductListModel
import kotlinx.android.synthetic.main.product_list_fragment.view.*

class ProductListFragment : Fragment() {

    companion object {
        fun newInstance() = ProductListFragment()
    }

    private lateinit var viewModel: ProductListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.product_list_fragment, container, false)

        v.product_list.layoutManager = GridLayoutManager(context, 2, RecyclerView.VERTICAL,false)

        val m = listOf(
            ProductListModel(0, "Ürün 1", 10.0F, null),
            ProductListModel(1, "Ürün 2", 10.0F, 15.5F),
            ProductListModel(2, "Ürün 3", 10.0F, 20.0F),
            ProductListModel(3, "Ürün 4", 75.50F, 120.0F),
            ProductListModel(4, "Ürün 5", 65.90F, null),
            ProductListModel(5, "Ürün 6", 10.0F, null)
        )

        v.product_list.adapter = ProductListItemAdapter(m){
            findNavController().navigate(R.id.action_dest_product_list_to_productFragment)
        }

        return v
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(ProductListViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
