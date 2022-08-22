package ru.acediat.feature_profile.model

import android.content.Context
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import androidx.core.content.FileProvider
import androidx.core.net.toFile
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import ru.acediat.core_android.BaseViewModel
import ru.acediat.core_android.FileUtil
import ru.acediat.core_android.PROFILE_ID
import ru.acediat.feature_profile.model.dtos.TaskDTO
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

private const val IMAGE_URI_BUNDLE = "image uri"

class EditReportViewModel : BaseViewModel() {

    @Inject lateinit var repository: TasksRepository
    @Inject lateinit var preferences: SharedPreferences

    private var imageUri: Uri? = null
    private var onCompleteReportSend: () -> Unit = {}
    private val error = MutableLiveData<Throwable>()

    var task: TaskDTO? = null

    fun getUri() = imageUri

    fun setUri(uri: Uri){
        imageUri = uri
    }

    fun deleteUri(){
        imageUri = null
    }

    fun setOnCompleteSendReport(callback: () -> Unit){
        onCompleteReportSend = callback
    }

    fun setOnErrorObserver(lifecycleOwner: LifecycleOwner, observer: (Throwable) -> Unit) =
        error.observe(lifecycleOwner, observer)

    fun sendReport(comment: String, imageFile: File) = task?.let {
        val requestBody = imageFile.asRequestBody("multipart/form-data".toMediaTypeOrNull())
        val imageFileBody = MultipartBody.Part.createFormData("image", imageFile.name, requestBody)
        repository.sendReport(it.id!!, getUserId(), comment, imageFile.name, imageFileBody)
            .subscribe({
                onCompleteReportSend()
            }, {
                error.postValue(it)
            })
    }

    fun createImageFile(context: Context): Uri {
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val imageFileName = "JPEG_" + timeStamp + "_"
        val image = File.createTempFile(
            imageFileName, ".jpg",
            context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        )
        imageUri = FileProvider.getUriForFile(context, "ru.acediat", image)
        return imageUri!!
    }

    override fun restoreData(savedInstanceState: Bundle) {
        imageUri = savedInstanceState.getSerializable(IMAGE_URI_BUNDLE) as Uri?
    }

    override fun saveData(outState: Bundle) = with(outState){
        putParcelable(IMAGE_URI_BUNDLE, imageUri)
    }

    private fun getUserId() = preferences.getInt(PROFILE_ID, 0)
}