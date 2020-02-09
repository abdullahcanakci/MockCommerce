package com.mockcommerce.modules.basket

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mockcommerce.R
import com.mockcommerce.modules.checkout.CheckoutActivity
import com.mockcommerce.shared.UserSession
import com.mockcommerce.usecases.BasketUseCase
import com.mockcommerce.usecases.UserUseCase
import com.mockcommerce.utils.BaseFragment
import com.mockcommerce.utils.TokenInterceptor
import kotlinx.android.synthetic.main.fragment_basket.view.*
import org.koin.android.ext.android.get
import timber.log.Timber

class BasketFragment : BaseFragment() {

    enum class ADAPTER_ACTION { DELETE, ADD, SUBSTRACT, POSTPONE, ADD_TO_CART, VIEW }

    lateinit var basketAdapter: BasketListAdapter
    lateinit var postponedAdapter: PostponedListAdapter

    companion object {
        fun newInstance() = BasketFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.fragment_basket, container, false)

        val basketUseCase: BasketUseCase = get()

        val basketRecycler = v.list
        basketRecycler.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        basketRecycler.addItemDecoration(DividerItemDecoration(context, RecyclerView.VERTICAL))

        basketAdapter = BasketListAdapter { product, action ->
            when (action) {
                ADAPTER_ACTION.DELETE -> {
                    basketUseCase.removeFromBasket(product.id)
                }
                ADAPTER_ACTION.ADD -> {
                    basketUseCase.editBasketItem(product.id, product.amount + 1)
                }
                ADAPTER_ACTION.SUBSTRACT -> {
                    basketUseCase.editBasketItem(product.id, product.amount - 1)
                }
                ADAPTER_ACTION.POSTPONE -> {
                    //TODO implement
                }
                ADAPTER_ACTION.VIEW -> {
                    val action = BasketFragmentDirections.actionBasketToProduct2(product.id)
                    findNavController().navigate(action)
                }
                else -> Timber.d("Invalid operation %i", action)
            }
        }

        basketRecycler.adapter = basketAdapter

        val postponedRecycler = v.list_postpone
        postponedRecycler.layoutManager =
            LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)

        postponedAdapter = PostponedListAdapter { product, action ->
            when (action) {
                ADAPTER_ACTION.ADD_TO_CART -> {
                    //TODO implement
                }
                ADAPTER_ACTION.DELETE -> {
                    basketUseCase.removeFromPostponed(product.id)
                }
                ADAPTER_ACTION.VIEW -> {
                    val action = BasketFragmentDirections.actionBasketToProduct2(product.id)
                    findNavController().navigate(action)
                }
                else -> Timber.d("Invalid operation %i", action)
            }
        }

        postponedRecycler.adapter = postponedAdapter

        v.basket_purchase.setOnClickListener {
            val tokenInterceptor: TokenInterceptor = get()

            if (tokenInterceptor.token.isNotEmpty()) {
                val intent = Intent(context, CheckoutActivity::class.java).apply {}
                startActivity(intent)
            } else if (get<UserSession>().isLogged) {
                Toast.makeText(
                    context,
                    "Kullanıcı oturumu açık değil. Oturum açın",
                    Toast.LENGTH_SHORT
                ).show()
            } else if (!basketUseCase.isBasketPopulated()) {
                Toast.makeText(
                    context,
                    "Sepette ürün bulunmuyor.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        return v
    }

    @SuppressLint("CheckResult")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val userUseCase: UserUseCase = get()

        userUseCase
            .getBasket()
            .subscribe(
                { result -> basketAdapter.updateItems(result) },
                { error -> Timber.d("Error while retrieving basket items $error") }
            )

        userUseCase
            .getPostponed()
            .subscribe(
                { result -> postponedAdapter.updateItems(result) },
                { error -> Timber.d("Error while retrieving postponed items $error") }
            )

    }
}
