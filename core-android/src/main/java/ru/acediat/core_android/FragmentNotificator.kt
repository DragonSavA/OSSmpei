package ru.acediat.core_android

import android.content.Context
import android.view.View
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import ru.acediat.core_res.R

class FragmentNotificator : Notificator {

    override fun showInfo(view: View, message: String, anchor: View?) = Snackbar
        .make(view, message, Snackbar.LENGTH_LONG).apply {
            anchor?.let{ this.anchorView = anchor }
            setBackgroundTint(getColor(context, R.color.main_blue))
            setTextColor(getColor(context, R.color.white))
            setActionTextColor(getColor(context, R.color.bg_blue))
            setAction(context.getText(R.string.ok)) {
                this.dismiss()
            }
        }.show()

    override fun showComplete(view: View, message: String, anchor: View?) = showInfo(view, message, anchor)

    override fun showError(view: View, message: String, anchor: View?) = Snackbar
        .make(view, message, Snackbar.LENGTH_LONG).apply {
            anchor?.let{ this.anchorView = anchor }
            setBackgroundTint(getColor(context, R.color.main_red))
            setTextColor(getColor(context, R.color.white))
            setActionTextColor(getColor(context, R.color.white))
            setAction(context.getText(R.string.ok)) {
                this.dismiss()
            }
        }.show()

    private fun getColor(context: Context, @ColorRes id: Int) = ContextCompat.getColor(context, id)
}