package ru.acediat.core_android.ext

import android.app.Activity
import android.view.View
import com.google.android.material.snackbar.Snackbar
import ru.acediat.core_res.R as resR

fun Activity.showError(view : View, message: String) = Snackbar
    .make(view, message, Snackbar.LENGTH_LONG)
    .setBackgroundTint(getColor(resR.color.main_red))
    .setTextColor(getColor(resR.color.white))
    .show()