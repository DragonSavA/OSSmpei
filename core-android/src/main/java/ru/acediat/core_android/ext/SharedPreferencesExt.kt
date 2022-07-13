package ru.acediat.core_android.ext

import android.content.SharedPreferences

fun SharedPreferences.getCurrentGroup() : String? = getString("currentGroup", "A-07-20")