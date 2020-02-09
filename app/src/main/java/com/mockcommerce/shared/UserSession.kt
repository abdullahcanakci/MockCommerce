package com.mockcommerce.shared

import com.mockcommerce.models.ProductModel
import io.reactivex.subjects.BehaviorSubject

class UserSession {
    val basket: BehaviorSubject<List<ProductModel>> = BehaviorSubject.create()
    val postponed: BehaviorSubject<List<ProductModel>> = BehaviorSubject.create()
    val favourites: BehaviorSubject<List<String>> = BehaviorSubject.create()

    var isLogged = false
        private set

    fun login() {
        isLogged = true
    }

    fun logout() {
        basket.onNext(ArrayList())
        postponed.onNext(ArrayList())
        favourites.onNext(ArrayList())
        isLogged = false
    }

}