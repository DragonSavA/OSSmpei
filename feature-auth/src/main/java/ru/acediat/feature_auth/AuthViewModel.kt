package ru.acediat.feature_auth

import android.content.SharedPreferences
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.acediat.core_android.EMAIL
import ru.acediat.core_android.PROFILE_ID
import ru.acediat.feature_auth.di.AuthComponent
import ru.acediat.feature_auth.entities.AuthParams
import javax.inject.Inject

class AuthViewModel : ViewModel() {

    @Inject lateinit var repository : AuthRepository

    private val errors = MutableLiveData<Throwable>()

    private var startMainCallback : () -> Unit = {}

    init{
        AuthComponent.init().inject(this)
    }

    fun setStartMainCallback(c : () -> Unit){
        startMainCallback = c
    }

    fun signIn(email : String, password : String, prefs: SharedPreferences) = repository.authenticate(email, password)
        .subscribe({
            saveParams(it, prefs)
            startMainCallback()
        }, {
            errors.postValue(it)
        })

    private fun saveParams(authParams: AuthParams, prefs : SharedPreferences){
        with(prefs.edit()){
            putInt(PROFILE_ID, authParams.id)
            putString(EMAIL, authParams.email)
            commit()
        }
    }
}