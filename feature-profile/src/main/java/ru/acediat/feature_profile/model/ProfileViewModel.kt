package ru.acediat.feature_profile.model

import android.content.SharedPreferences
import android.os.Bundle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.disposables.Disposable
import ru.acediat.core_android.PASSWORD
import ru.acediat.core_android.PROFILE_ID
import ru.acediat.feature_profile.ui.PROFILE_BUNDLE
import javax.inject.Inject

class ProfileViewModel : ViewModel() {

    @Inject lateinit var repository: ProfileRepository
    @Inject lateinit var preferences: SharedPreferences

    private val profile = MutableLiveData<Profile>()
    private val error = MutableLiveData<Throwable>()

    fun setProfileObserver(lifecycleOwner: LifecycleOwner, observer: (Profile) -> Unit) =
        profile.observe(lifecycleOwner, observer)

    fun setErrorObserver(lifecycleOwner: LifecycleOwner, observer: (Throwable) -> Unit) =
        error.observe(lifecycleOwner, observer)

    fun authorize(): Disposable = repository.authorize(
        getProfileId(),
        getPassword()
    ).subscribe({
        profile.postValue(it)
    }, {
        error.postValue(it)
    })

    fun restoreData(data: Bundle) =
        profile.postValue(data.getSerializable(PROFILE_BUNDLE) as Profile?)

    fun saveState(outState: Bundle) = outState.putSerializable(PROFILE_BUNDLE, profile.value)

    private fun getProfileId() = preferences.getInt(PROFILE_ID, 0)

    private fun getPassword() = preferences.getString(PASSWORD, "") ?: ""

}