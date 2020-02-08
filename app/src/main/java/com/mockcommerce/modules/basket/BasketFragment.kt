package com.mockcommerce.modules.basket

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mockcommerce.AppRepository
import com.mockcommerce.R
import com.mockcommerce.modules.checkout.CheckoutActivity
import com.mockcommerce.utils.BaseFragment
import com.mockcommerce.utils.TokenInterceptor
import kotlinx.android.synthetic.main.fragment_basket.view.*
import org.koin.android.ext.android.get
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class BasketFragment : BaseFragment() {

    enum class ADAPTER_ACTION { DELETE, ADD, SUBSTRACT, POSTPONE, ADD_TO_CART, VIEW }

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

        basketAdapter = BasketListAdapter { product, action ->
            when (action) {
                ADAPTER_ACTION.DELETE -> {
                    viewModel.removeFromBasket(product)
                }
                ADAPTER_ACTION.ADD -> {
                    viewModel.add(product)
                }
                ADAPTER_ACTION.SUBSTRACT -> {
                    viewModel.subtract(product)
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
        postponedRecycler.layoutManager =
            LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)

        postponedAdapter = PostponedListAdapter { product, action ->
            when (action) {
                ADAPTER_ACTION.ADD_TO_CART -> {
                    viewModel.addToBasket(product)
                }
                ADAPTER_ACTION.DELETE -> {
                    viewModel.removeFromPostponed(product)
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

        v.basket_purchase.setOnClickListener {
            val tokenInterceptor: TokenInterceptor = get()

            if (tokenInterceptor.token.isNotEmpty()) {
                val intent = Intent(context, CheckoutActivity::class.java).apply {}
                startActivity(intent)
            } else if (!viewModel.isLoggedIn()) {
                Toast.makeText(
                    context,
                    "Kullanıcı oturumu açık değil. Oturum açın",
                    Toast.LENGTH_SHORT
                ).show()
            } else if (!viewModel.isBasketPopulated()) {
                Toast.makeText(
                    context,
                    "Sepette ürün bulunmuyor.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.basketTotal.observe(viewLifecycleOwner, Observer {
            view.basket_total.text = view.context!!.getString(R.string.price, it)
        })

        val repository: AppRepository = get()

        val disposable = repository
            .getBasket()
            .subscribe(
                { result -> viewModel.basketItems.postValue(result) },
                { error -> Timber.d("Error while retrieving basket items $error") }
            )

        addToDisposable(disposable)


        val disposable1 = repository
            .getBasketPostponed()
            .subscribe(
                { result -> viewModel.postponedItems.postValue(result) },
                { error -> Timber.d("Error while retrieving basket items $error") }
            )

        addToDisposable(disposable1)
    }
}
