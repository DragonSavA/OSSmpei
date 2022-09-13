package ru.acediat.feature_profile.model

import android.content.SharedPreferences
import android.net.Uri
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.ktx.messaging
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
    private val deleteGroupComplete = MutableLiveData<String>()
    private val error = MutableLiveData<Throwable>()

    fun setGroupsObserver(lifecycleOwner: LifecycleOwner, observer: (ArrayList<String>) -> Unit) =
        groups.observe(lifecycleOwner, observer)

    fun setSaveCompleteObserver(lifecycleOwner: LifecycleOwner, observer: (String) -> Unit) =
        saveComplete.observe(lifecycleOwner, observer)

    fun setGroupDeleteCompleteObserver(lifecycleOwner: LifecycleOwner, observer: (String) -> Unit) =
        deleteGroupComplete.observe(lifecycleOwner, observer)

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

    fun deleteFavoriteGroup(group: String): Disposable = repository
        .deleteFavoriteGroup(getUserId(), group)
        .subscribe({
            deleteGroupComplete.postValue(group)
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
            .subscribe({}, {
                error.postValue(it)
            })
    }

    fun setTasksNotificationEnabled(isEnabled: Boolean){
        preferences.edit()
            .putBoolean(NOTIFICATION_ENABLED, isEnabled)
            .apply()
        if(isEnabled) {
            Firebase.messaging.subscribeToTopic(TASKS_NOTIFICATION)
                .addOnCompleteListener { task -> }
        }else {
            Firebase.messaging.unsubscribeFromTopic(TASKS_NOTIFICATION)
        }
    }

    fun getTasksEnabled() = preferences.getBoolean(NOTIFICATION_ENABLED, true)

    fun getCurrentGroup() = preferences.getString(CURRENT_GROUP, "") ?: ""

    fun clearPreferences() = preferences.edit().clear().apply()

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