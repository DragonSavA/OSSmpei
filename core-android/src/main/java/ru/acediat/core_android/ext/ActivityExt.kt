package ru.acediat.core_android.ext

import android.app.Activity
import android.view.View
import com.google.android.material.snackbar.Snackbar

fun Activity.showError(view : View, message: String) = Snackbar.make(
    view, message, Snackbar.LENGTH_LONG
).show()