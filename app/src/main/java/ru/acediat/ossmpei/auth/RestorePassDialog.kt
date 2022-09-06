package ru.acediat.ossmpei.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import ru.acediat.feature_auth.RestorePassViewModel
import ru.acediat.feature_auth.databinding.DialogForgotPassBinding
import ru.acediat.feature_auth.di.AuthComponent

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
        AuthComponent.init().inject(viewModel)
        viewModel.addRestoreRequestSendObserver(viewLifecycleOwner, ::onRestorePassRequestSend)
        bindViews()
        return binding.root
    }

    private fun bindViews() = with(binding){
        cancelButton.setOnClickListener { dismiss() }
        okButton.setOnClickListener {
            if(viewModel.isEmailValid(emailEditText.text.toString()))
                viewModel.restorePass(emailEditText.text.toString())
            //TODO: уведомление о неправильно введенной почте
        }
    }

    private fun onRestorePassRequestSend(b: Boolean){
        dismiss()
        //TODO: сделать уведомление о том, что письмо с восстановлением пароля отправлено
    }
}