package ru.acediat.core_android.ext

import android.app.Activity
import android.view.View
import com.google.android.material.snackbar.Snackbar
import ru.acediat.core_res.R as resR

fun Activity.showError(view : View, message: String) = Snackbar
    .make(view, message, Snackbar.LENGTH_LONG).apply {
        setBackgroundTint(getColor(ru.acediat.core_res.R.color.main_red))
        setTextColor(getColor(ru.acediat.core_res.R.color.white))
        setActionTextColor(getColor(ru.acediat.core_res.R.color.white))
        setAction(getText(ru.acediat.core_res.R.string.ok)) {
            this.dismiss()
        }
    }.show()