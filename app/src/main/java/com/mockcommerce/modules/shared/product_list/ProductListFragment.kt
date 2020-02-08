package com.mockcommerce.modules.shared.product_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mockcommerce.AppRepository
import com.mockcommerce.R
import com.mockcommerce.modules.shared.adapters.GenericProductAdapter
import com.mockcommerce.utils.BaseFragment
import kotlinx.android.synthetic.main.fragment_productlist.view.*
import org.koin.android.ext.android.get
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProductListFragment : BaseFragment() {

    val viewModel: ProductListViewModel by viewModel()
    val args: ProductListFragmentArgs by navArgs()

    companion object {
        fun newInstance() = ProductListFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.fragment_productlist, container, false)

        v.product_list.layoutManager = GridLayoutManager(context, 2, RecyclerView.VERTICAL, false)

        val adapter = GenericProductAdapter(R.layout.item_productlist) { id ->
            val action = ProductListFragmentDirections.actionDestProductListToProduct(id)
            findNavController().navigate(action)
        }
        v.product_list.adapter = adapter


        val appRepository = get<AppRepository>()

        val disposable = appRepository
            .getProductList(args.listIdentifier)
            .subscribe { result -> viewModel.products.postValue(result) }

        addToDisposable(disposable)


        viewModel.products.observe(this.viewLifecycleOwner, Observer { list ->
            adapter.updateProducts(list)
        })

        return v
    }
}
