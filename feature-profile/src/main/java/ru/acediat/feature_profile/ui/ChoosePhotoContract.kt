package ru.acediat.feature_profile.ui

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import androidx.activity.result.contract.ActivityResultContract

class ChoosePhotoContract: ActivityResultContract<Any, Uri?>() {

    override fun createIntent(context: Context, input: Any): Intent = Intent(
        Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI
    )

    override fun parseResult(resultCode: Int, intent: Intent?): Uri?{
        return intent?.data
    }

}