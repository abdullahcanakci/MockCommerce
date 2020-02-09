package com.mockcommerce.usecases

import com.mockcommerce.AppRepository
import com.mockcommerce.models.ProductModel
import com.mockcommerce.shared.UserSession
import timber.log.Timber

class BasketUseCase(
    val appRepository: AppRepository,
    val userSession: UserSession
) {

    fun addToBasket(id: String) {
        userSession.basket.value?.let { list ->
            var handled = false
            list.forEach { item ->
                if (item.id == id) {
                    item.amount += 1
                    handled = true
                    userSession.basket.onNext(list)
                }
            }
            if (!handled) {
                appRepository
                    .addToBasket(id)
                    .subscribe({ item ->
                        item.amount = 1
                        (list as ArrayList).add(item)
                        userSession.basket.onNext(list)
                    }, {
                        Timber.d(it)
                    }

                    )
            }
        }
    }

    fun removeFromBasket(id: String) {
        userSession.basket.value?.let { list ->
            val temp = ArrayList<ProductModel>()
            list.forEach {
                if (it.id != id) {
                    temp.add(it)
                }
            }
            userSession.basket.onNext(temp)
        }
    }

    fun editBasketItem(id: String, amount: Int) {
        userSession.basket.value?.let { list ->
            list.forEach { item ->
                if (item.id == id) {
                    item.amount = amount
                }
            }
            userSession.basket.onNext(list)
        }
    }

    fun addToPostponed(id: String) {
        userSession.postponed.value?.let { list ->
            appRepository
                .getProduct(id)
                .subscribe { item ->
                    (list as ArrayList).add(item)
                    userSession.basket.onNext(list)
                }

        }
    }

    fun removeFromPostponed(id: String) {
        userSession.postponed.value?.let { list ->
            val temp = ArrayList<ProductModel>()
            list.forEach {
                if (it.id != id) {
                    temp.add(it)
                }
            }
            userSession.postponed.onNext(temp)
        }
    }

    fun moveToPostponed(id: String) {
        removeFromBasket(id)
        addToPostponed(id)
    }

    fun moveToBasket(id: String) {
        removeFromPostponed(id)
        addToBasket(id)
    }

    fun isBasketPopulated(): Boolean {
        return userSession.basket.value.isNullOrEmpty()

    }
}