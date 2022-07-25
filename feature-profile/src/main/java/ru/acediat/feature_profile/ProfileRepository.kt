package ru.acediat.feature_profile

import io.reactivex.rxjava3.schedulers.Schedulers
import ru.acediat.feature_profile.apis.ProfileApi
import javax.inject.Inject

class ProfileRepository @Inject constructor(
    private val api : ProfileApi
) {

    fun authorize(id : Int, pass: String) = api.authorize(id.toString(), pass)
        .subscribeOn(Schedulers.io())
        .observeOn(Schedulers.io())
}