package ru.acediat.feature_profile.model

import android.content.SharedPreferences
import android.os.Bundle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import io.reactivex.rxjava3.disposables.Disposable
import ru.acediat.core_android.BaseViewModel
import ru.acediat.core_android.PASSWORD
import ru.acediat.core_android.PROFILE_ID
import ru.acediat.feature_profile.ui.PROFILE_BUNDLE
import javax.inject.Inject

class ProfileViewModel : BaseViewModel() {

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

    override fun restoreData(savedInstanceState: Bundle): Unit {
        (savedInstanceState.getSerializable(PROFILE_BUNDLE) as Profile?)?.let {
            profile.postValue(it)
        } ?: run {
            authorize()
        }
    }

    override fun saveData(outState: Bundle) = outState.putSerializable(PROFILE_BUNDLE, profile.value)

    private fun getProfileId() = preferences.getInt(PROFILE_ID, 0)

    private fun getPassword() = preferences.getString(PASSWORD, "") ?: ""

}