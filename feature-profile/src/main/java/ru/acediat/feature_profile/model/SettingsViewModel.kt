package ru.acediat.feature_profile.model

import android.content.SharedPreferences
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import io.reactivex.rxjava3.disposables.Disposable
import ru.acediat.core_android.*
import ru.acediat.feature_profile.model.dtos.GroupDTO
import javax.inject.Inject

class SettingsViewModel : BaseViewModel() {

    @Inject lateinit var repository: SettingsRepository
    @Inject lateinit var preferences: SharedPreferences

    private val groups = MutableLiveData<ArrayList<String>>()
    private val saveComplete = MutableLiveData<String>()
    private val error = MutableLiveData<Throwable>()

    fun setGroupsObserver(lifecycleOwner: LifecycleOwner, observer: (ArrayList<String>) -> Unit) =
        groups.observe(lifecycleOwner, observer)

    fun setOnSaveCompleteObserver(lifecycleOwner: LifecycleOwner, observer: (String) -> Unit) =
        saveComplete.observe(lifecycleOwner, observer)

    fun setErrorObserver(lifecycleOwner: LifecycleOwner, observer: (Throwable) -> Unit) =
        error.observe(lifecycleOwner, observer)

    fun saveGroup(group: String): Disposable = repository.saveGroup(getUserId(), group)
        .subscribe({
            saveComplete.postValue(group)
        }, {
            error.postValue(it)
        })

    fun getFavoriteGroups(): Disposable = repository.getFavoriteGroups(getUserId())
        .subscribe({
            val list = ArrayList<String>()
            for(group in it)
                list.add(group.group)
            groups.postValue(list)
        }, {
            error.postValue(it)
        })

    fun setCurrentGroup(group: String) = preferences.edit()
        .putString(CURRENT_GROUP, group)
        .apply()

    fun getCurrentGroup() = preferences.getString(CURRENT_GROUP, "") ?: ""

    private fun getUserId() = preferences.getInt(PROFILE_ID, 0)

}