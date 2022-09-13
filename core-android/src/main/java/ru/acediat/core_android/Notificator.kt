package ru.acediat.core_android

import android.view.View

interface Notificator {

    fun showComplete(view: View, message: String, anchor: View? = null)

    fun showInfo(view: View, message: String, anchor: View? = null)

    fun showError(view: View, message: String, anchor: View? = null)

    fun showError(view: View, message: String, throwable: Throwable, anchor: View? = null){}

}