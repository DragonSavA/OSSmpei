package ru.acediat.feature_profile

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.acediat.feature_profile.di.ProfileComponent
import javax.inject.Inject

class ProfileViewModel : ViewModel() {

    @Inject lateinit var repository: ProfileRepository

    private val profile = MutableLiveData<Profile>()
    private val error = MutableLiveData<Throwable>()

    init{
        ProfileComponent.init().inject(this)
    }

    fun setProfileObserver(lifecycleOwner: LifecycleOwner, observer: (Profile) -> Unit) =
        profile.observe(lifecycleOwner, observer)

    fun setErrorObserver(lifecycleOwner: LifecycleOwner, observer: (Throwable) -> Unit) =
        error.observe(lifecycleOwner, observer)

    fun authorize(id: Int, pass: String) = repository.authorize(id, pass)
        .subscribe({
            profile.postValue(it)
        }, {
            error.postValue(it)
        })
}