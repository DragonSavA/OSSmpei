package ru.acediat.feature_auth

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import javax.inject.Inject

class RestorePassViewModel: ViewModel() {

    @Inject lateinit var repository: RestorePassRepository

    private val emailRegex = Regex("[A-Za-z0-9.]+@mpei\\.ru")

    private val restoreRequestSend = MutableLiveData<Boolean>()
    private val error = MutableLiveData<Throwable>()

    fun addRestoreRequestSendObserver(lifecycleOwner: LifecycleOwner, observer: (Boolean) -> Unit) =
        restoreRequestSend.observe(lifecycleOwner, observer)

    fun restorePass(email: String) = repository.restorePass(email)
        .subscribe({
            restoreRequestSend.postValue(true)
        }, {
            error.postValue(it)
        })

    fun isEmailValid(email: String) = email.matches(emailRegex)
}