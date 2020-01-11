package com.mockcommerce.modules.basket

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mockcommerce.R
import com.mockcommerce.modules.shared.product_list.ProductListItemAdapter
import kotlinx.android.synthetic.main.basket_fragment.view.*
import timber.log.Timber
import org.koin.androidx.viewmodel.ext.android.viewModel

class BasketFragment : Fragment() {

    enum class ADAPTER_ACTION {DELETE, ADD, SUBSTRACT, POSTPONE, ADD_TO_CART}

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
        val v = inflater.inflate(R.layout.basket_fragment, container, false)

        val basketRecycler = v.list
        basketRecycler.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        basketRecycler.addItemDecoration(DividerItemDecoration(context, RecyclerView.VERTICAL))
        basketAdapter = BasketListAdapter() {product, action ->
            val basket_temp = viewModel.basketItems.value
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
                    val post_temp = viewModel.postponedItems.value
                    post_temp!!.add(product)
                    viewModel.postponedItems.postValue(post_temp)
                }
                else -> Timber.d("Invalid operation %i", action)
            }

            viewModel.basketItems.postValue(basket_temp)
        }
        basketRecycler.adapter = basketAdapter

        viewModel.basketItems.observe(this.viewLifecycleOwner, Observer { t ->
            basketAdapter.updateItems(t)
        })

        val postponedRecycler = v.list_postpone
        postponedRecycler.layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        postponedAdapter = PostponedListAdapter() { product, action ->
            val post_temp = viewModel.postponedItems.value
            if(post_temp != null) {
                post_temp.remove(product)
                viewModel.postponedItems.postValue(post_temp)
            }

            if(action == ADAPTER_ACTION.ADD_TO_CART){
                val basket_temp = viewModel.basketItems.value
                if(basket_temp != null) {
                    basket_temp.add(product)
                    viewModel.basketItems.postValue(basket_temp)
                }
            }
        }
        postponedRecycler.adapter = postponedAdapter

        viewModel.postponedItems.observe(this.viewLifecycleOwner, Observer { t ->
            postponedAdapter.updateItems(t)
        })

        return v
    }
}
