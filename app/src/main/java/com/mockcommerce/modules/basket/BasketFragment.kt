package com.mockcommerce.modules.basket

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mockcommerce.R
import kotlinx.android.synthetic.main.fragment_basket.view.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class BasketFragment : Fragment() {

    enum class ADAPTER_ACTION {DELETE, ADD, SUBSTRACT, POSTPONE, ADD_TO_CART, VIEW}

    lateinit var basketAdapter: BasketListAdapter
    lateinit var postponedAdapter: PostponedListAdapter

    val viewModel: BasketViewModel by viewModel()

    companion object {
        fun newInstance() = BasketFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.fragment_basket, container, false)

        val basketRecycler = v.list
        basketRecycler.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        basketRecycler.addItemDecoration(DividerItemDecoration(context, RecyclerView.VERTICAL))
        basketAdapter = BasketListAdapter() {product, action ->
            when(action) {
                ADAPTER_ACTION.DELETE -> {
                    viewModel.removeFromBasket(product)

                }
                ADAPTER_ACTION.ADD -> {
                    viewModel.add(product)
                }
                ADAPTER_ACTION.SUBSTRACT -> {
                    viewModel.substract(product)
                }
                ADAPTER_ACTION.POSTPONE -> {
                    viewModel.postpone(product)
                }
                ADAPTER_ACTION.VIEW -> {
                    val action = BasketFragmentDirections.actionBasketToProduct2(product.id)
                    findNavController().navigate(action)
                }

                else -> Timber.d("Invalid operation %i", action)
            }
        }

        basketRecycler.adapter = basketAdapter

        viewModel.basketItems.observe(this.viewLifecycleOwner, Observer { t ->
            basketAdapter.updateItems(t)
        })

        val postponedRecycler = v.list_postpone
        postponedRecycler.layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        postponedAdapter = PostponedListAdapter { product, action ->
            when (action){
                ADAPTER_ACTION.ADD_TO_CART -> {
                    viewModel.addToBasket(product)
                }
                ADAPTER_ACTION.DELETE -> {
                    viewModel.removeFromPostPoned(product)
                }
                ADAPTER_ACTION.VIEW -> {
                    val action = BasketFragmentDirections.actionBasketToProduct2(product.id)
                    findNavController().navigate(action)
                }
                else -> Timber.d("Invalid operation %i", action)
            }
        }

        postponedRecycler.adapter = postponedAdapter

        viewModel.postponedItems.observe(this.viewLifecycleOwner, Observer { t ->
            postponedAdapter.updateItems(t)
        })

        viewModel.basketTotal.observe(this.viewLifecycleOwner, Observer { t ->
            v.basket_total.text = t
        })

        return v
    }
}
