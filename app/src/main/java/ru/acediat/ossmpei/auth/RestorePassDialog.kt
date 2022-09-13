package ru.acediat.ossmpei.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import ru.acediat.core_android.Logger
import ru.acediat.core_android.OSS_TAG
import ru.acediat.feature_auth.RestorePassViewModel
import ru.acediat.feature_auth.databinding.DialogForgotPassBinding
import ru.acediat.feature_auth.di.AuthComponent
import ru.acediat.core_res.R as resR

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
            else
                Toast.makeText(requireContext(), getString(resR.string.wrong_email), Toast.LENGTH_LONG).show()
        }
    }

    private fun onRestorePassRequestSend(b: Boolean){
        dismiss()
        Toast.makeText(requireContext(), getString(resR.string.mail_send), Toast.LENGTH_LONG).show()
    }
}