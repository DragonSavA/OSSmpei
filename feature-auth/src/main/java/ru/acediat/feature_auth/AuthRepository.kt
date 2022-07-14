package ru.acediat.feature_auth

import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val api : AuthApi
) {

    fun authenticate(email : String, password : String) = api.authenticate(email, password)
        .subscribeOn(Schedulers.io())
        .observeOn(Schedulers.io())
}