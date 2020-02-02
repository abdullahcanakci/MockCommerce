package com.mockcommerce.modules.account


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.mockcommerce.AppRepository
import com.mockcommerce.R
import com.mockcommerce.modules.shared.adapters.GenericProductAdapter
import kotlinx.android.synthetic.main.fragment_favourites.*
import org.koin.android.ext.android.inject

/**
 * A simple [Fragment] subclass.
 */
class FavouritesFragment : Fragment() {

    val repository by inject<AppRepository>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favourites, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        product_list.layoutManager =
            GridLayoutManager(context, 2, LinearLayoutManager.VERTICAL, false)
        val adapter = GenericProductAdapter(R.layout.item_productlist) { id ->
            val action = FavouritesFragmentDirections.actionFavouritesFragmentToProduct(id)
            findNavController().navigate(action)
        }
        product_list.adapter = adapter
        repository.getFavouriteProducts().observe(viewLifecycleOwner, Observer {
            adapter.updateProducts(it)
        })
    }
}
