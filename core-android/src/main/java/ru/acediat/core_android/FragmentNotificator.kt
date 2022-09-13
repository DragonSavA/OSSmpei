package ru.acediat.core_android

import android.view.View
import com.google.android.material.snackbar.Snackbar
import ru.acediat.core_res.R

class FragmentNotificator : Notificator {

    override fun showInfo(view: View, message: String, anchor: View?) = Snackbar
        .make(view, message, Snackbar.LENGTH_LONG).apply {
            anchor?.let{ this.anchorView = anchor }
            setBackgroundTint(context.getColor(R.color.main_blue))
            setTextColor(context.getColor(R.color.white))
            setActionTextColor(context.getColor(R.color.bg_blue))
            setAction(context.getText(R.string.ok)) {
                this.dismiss()
            }
        }.show()

    override fun showComplete(view: View, message: String, anchor: View?) = showInfo(view, message, anchor)

    override fun showError(view: View, message: String, anchor: View?) = Snackbar
        .make(view, message, Snackbar.LENGTH_LONG).apply {
            anchor?.let{ this.anchorView = anchor }
            setBackgroundTint(context.getColor(R.color.main_red))
            setTextColor(context.getColor(R.color.white))
            setActionTextColor(context.getColor(R.color.white))
            setAction(context.getText(R.string.ok)) {
                this.dismiss()
            }
        }.show()
}