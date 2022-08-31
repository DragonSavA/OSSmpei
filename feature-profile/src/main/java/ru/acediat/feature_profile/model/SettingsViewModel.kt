package ru.acediat.feature_profile.model

import android.content.SharedPreferences
import android.net.Uri
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import io.reactivex.rxjava3.disposables.Disposable
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import ru.acediat.core_android.*
import java.io.File
import javax.inject.Inject

class SettingsViewModel : BaseViewModel() {

    @Inject lateinit var repository: SettingsRepository
    @Inject lateinit var preferences: SharedPreferences

    var imageUri: Uri? = null

    private val groups = MutableLiveData<ArrayList<String>>()
    private val saveComplete = MutableLiveData<String>()
    private val updateAvatarComplete = MutableLiveData<Boolean>()
    private val error = MutableLiveData<Throwable>()

    fun setGroupsObserver(lifecycleOwner: LifecycleOwner, observer: (ArrayList<String>) -> Unit) =
        groups.observe(lifecycleOwner, observer)

    fun setSaveCompleteObserver(lifecycleOwner: LifecycleOwner, observer: (String) -> Unit) =
        saveComplete.observe(lifecycleOwner, observer)

    fun setUpdateCompleteObserver(lifecycleOwner: LifecycleOwner, observer: (Boolean) -> Unit) =
        updateAvatarComplete.observe(lifecycleOwner, observer)

    fun setErrorObserver(lifecycleOwner: LifecycleOwner, observer: (Throwable) -> Unit) =
        error.observe(lifecycleOwner, observer)

    fun saveGroup(group: String): Disposable = repository.isGroupValid(group)
        .subscribe({
            if(it.isValid)
                saveGroupToServer(group)
            //TODO: Добавить уведомление об ошибке в else ветку
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

    fun setCurrentGroup(group: String): Disposable = repository.isGroupValid(group)
        .subscribe({
            if(it.isValid)
                saveCurrentGroup(group)
        }, {
            error.postValue(it)
        })

    fun updateAvatar(imageFile: File){
        val requestBody = imageFile.asRequestBody("multipart/form-data".toMediaTypeOrNull())
        val imageFileBody = MultipartBody.Part.createFormData("image", imageFile.name, requestBody)
        repository.updateAvatar(getUserId(), imageFile.name, imageFileBody)
            .subscribe({
                updateAvatarComplete.postValue(true)
            }, {
                error.postValue(it)
            })
    }

    fun getCurrentGroup() = preferences.getString(CURRENT_GROUP, "") ?: ""

    private fun saveCurrentGroup(group: String) = preferences.edit()
        .putString(CURRENT_GROUP, group)
        .apply()

    private fun saveGroupToServer(group: String) = repository.saveGroup(getUserId(), group)
        .subscribe({
            saveComplete.postValue(group)
        }, {
            error.postValue(it)
        })

    private fun getUserId() = preferences.getInt(PROFILE_ID, 0)

}