package com.mockcommerce.usecases

import android.annotation.SuppressLint
import com.mockcommerce.AppRepository
import com.mockcommerce.models.AddressModel
import com.mockcommerce.models.ProductModel
import com.mockcommerce.models.UserModel
import com.mockcommerce.shared.Notifier
import com.mockcommerce.shared.RequestStatus
import com.mockcommerce.shared.UserSession
import com.mockcommerce.utils.TokenInterceptor
import io.reactivex.Single
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject

class UserUseCase(
    val appRepository: AppRepository,
    val userSession: UserSession,
    val tokenInterceptor: TokenInterceptor,
    val notifier: Notifier
) {
    var basketUpdated = false
    var postponedUpdated = false
    var favouritesUpdated = false


    @SuppressLint("CheckResult")
    fun login(email: String, password: String): PublishSubject<RequestStatus> {
        var response = PublishSubject.create<RequestStatus>()
        response.onNext(RequestStatus.WAITING)
        appRepository.login(email, password)
            .subscribe(
                { result ->
                    tokenInterceptor.token = result
                    userSession.login()
                    response.onNext(RequestStatus.COMPLETED)
                },
                {
                    notifier.showToast("Oturum açma esnasında hata.")
                    response.onNext(RequestStatus.ERROR)
                }
            )
        return response
    }

    @SuppressLint("CheckResult")
    fun register(userModel: UserModel): PublishSubject<RequestStatus> {
        var response = PublishSubject.create<RequestStatus>()
        response.onNext(RequestStatus.WAITING)
        appRepository.register(userModel)
            .subscribe(
                { result ->
                    response.onNext(RequestStatus.COMPLETED)
                },
                { error ->
                    notifier.showToast("Kayıt esnasında esnasında hata.")
                    response.onNext(RequestStatus.ERROR)
                }
            )
        return response
    }

    fun logout() {
        if (userSession.isLogged) {
            tokenInterceptor.token = ""
            userSession.logout()
            basketUpdated = false
            postponedUpdated = false
        }
    }

    @SuppressLint("CheckResult")
    fun getBasket(): BehaviorSubject<List<ProductModel>> {
        if (!basketUpdated && userSession.isLogged) {
            appRepository
                .getBasket()
                .subscribe { result ->
                    userSession.basket.onNext(result)
                    basketUpdated = true
                }
        }
        return userSession.basket
    }

    @SuppressLint("CheckResult")
    fun getPostponed(): BehaviorSubject<List<ProductModel>> {
        if (!postponedUpdated && userSession.isLogged) {
            appRepository
                .getBasketPostponed()
                .subscribe { result ->
                    userSession.postponed.onNext(result)
                    postponedUpdated = true
                }
        }
        return userSession.postponed
    }

    fun getAddress(): Single<List<AddressModel>>? {
        if (userSession.isLogged) {
            return appRepository.getAddresses()
        }
        return null
    }


}