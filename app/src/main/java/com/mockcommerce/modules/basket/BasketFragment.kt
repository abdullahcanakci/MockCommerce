package com.mockcommerce.modules.basket

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.mockcommerce.R
import kotlinx.android.synthetic.main.basket_fragment.view.*
import timber.log.Timber

class BasketFragment : Fragment() {

    enum class ADAPTER_ACTION {DELETE, ADD, SUBSTRACT, POSTPONE, ADD_TO_CART}

    lateinit var basketRecycler: RecyclerView
    lateinit var postponedRecycler: RecyclerView

    companion object {
        fun newInstance() = BasketFragment()
    }

    private lateinit var viewModel: BasketViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewModel = ViewModelProviders.of(this).get(BasketViewModel::class.java)

        val v = inflater.inflate(R.layout.basket_fragment, container, false)

        basketRecycler = v.list
        basketRecycler.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)

        basketRecycler.adapter = BasketListAdapter() {product, action ->
            val basket_temp = viewModel.basket_items.value
            val index = basket_temp!!.indexOf(product)

            when(action) {
                ADAPTER_ACTION.DELETE -> {
                    basket_temp.remove(product)

                }
                ADAPTER_ACTION.ADD -> {
                    product.numbersInBasket += 1
                    basket_temp[index] = product
                }
                ADAPTER_ACTION.SUBSTRACT -> {
                    product.numbersInBasket -= 1
                    if(product.numbersInBasket == 0){
                        product.numbersInBasket = 1
                    }
                    basket_temp[index] = product
                }
                ADAPTER_ACTION.POSTPONE -> {
                    basket_temp.remove(product)
                    val post_temp = viewModel.postponed_items.value
                    post_temp!!.add(product)
                    viewModel.postponed_items.postValue(post_temp)
                }
                else -> Timber.d("Invalid operation %i", action)
            }

            viewModel.basket_items.postValue(basket_temp)
        }

        basketRecycler.addItemDecoration(DividerItemDecoration(context, RecyclerView.VERTICAL))

        viewModel.basket_items.observe(this.viewLifecycleOwner, Observer { t ->
            (basketRecycler.adapter as BasketListAdapter).updateItems(t)
        })


        postponedRecycler = v.list_postpone
        postponedRecycler.layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)

        postponedRecycler.adapter = PostponedListAdapter() { product, action ->
            val post_temp = viewModel.postponed_items.value
            if(post_temp != null) {
                post_temp.remove(product)
                viewModel.postponed_items.postValue(post_temp)
            }

            if(action == ADAPTER_ACTION.ADD_TO_CART){
                val basket_temp = viewModel.basket_items.value
                if(basket_temp != null) {
                    basket_temp.add(product)
                    viewModel.basket_items.postValue(basket_temp)
                }
            }
        }

        viewModel.postponed_items.observe(this.viewLifecycleOwner, Observer { t ->
            (postponedRecycler.adapter as PostponedListAdapter).updateItems(t)
        })


        return v
    }
}
