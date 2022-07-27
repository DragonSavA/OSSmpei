package ru.acediat.feature_auth

import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class RestorePassRepository @Inject constructor(
    private val api: RestorePassApi
) {

    fun restorePass(email: String) = api.restorePass(email)
        .subscribeOn(Schedulers.io())
        .observeOn(Schedulers.io())

}