package ru.acediat.feature_profile.ui

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import androidx.activity.result.contract.ActivityResultContract

class CameraContract : ActivityResultContract<Uri, Int>() {

    override fun createIntent(context: Context, input: Uri): Intent =
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).apply {
            putExtra(MediaStore.EXTRA_OUTPUT, input)
        }

    override fun parseResult(resultCode: Int, intent: Intent?): Int = resultCode

}