package ru.acediat.ossmpei.auth

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import ru.acediat.core_android.ext.onBackPressed
import ru.acediat.feature_auth.RestorePassViewModel
import ru.acediat.feature_auth.databinding.DialogForgotPassBinding

class RestorePassDialog: DialogFragment() {

    private lateinit var binding: DialogForgotPassBinding

    private val viewModel = ViewModelProvider
        .NewInstanceFactory()
        .create(RestorePassViewModel::class.java)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogForgotPassBinding.inflate(inflater, container, false)
        viewModel.addRestoreRequestSendObserver(viewLifecycleOwner, ::onRestorePassRequestSend)
        bindViews()
        return binding.root
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
    }

    override fun onCancel(dialog: DialogInterface) {
        super.onCancel(dialog)
    }

    private fun bindViews() = with(binding){
        cancelButton.setOnClickListener { dismiss() }
        okButton.setOnClickListener { viewModel.restorePass(emailEditText.text.toString()) }
    }

    private fun onRestorePassRequestSend(b: Boolean){
        dismiss()
        //TODO: сделать уведомление о том, что письмо с восстановлением пароля отправлено
    }
}