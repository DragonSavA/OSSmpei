package ru.acediat.feature_auth

import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class RegistrationRepository @Inject constructor(
    private val api : RegisterApi
) {

    fun signUp(
        email: String,
        name: String,
        surname: String,
        gender: String,
        group: String,
        password: String
    ) = api.register(email, name, surname, gender, group, password)
        .subscribeOn(Schedulers.io())
        .observeOn(Schedulers.io())

    fun isGroupValid(group: String) = api.isGroupValid(group)
        .subscribeOn(Schedulers.io())
        .observeOn(Schedulers.io())

}