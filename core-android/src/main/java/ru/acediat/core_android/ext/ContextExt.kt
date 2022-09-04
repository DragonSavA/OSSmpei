package ru.acediat.core_android.ext

import android.content.Context
import android.graphics.drawable.Drawable

fun Context.getDrawableFromAssets(filename: String) =
    Drawable.createFromStream(assets.open(filename), null)
