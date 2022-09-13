package ru.acediat.ossmpei.auth

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import okhttp3.ResponseBody
import ru.acediat.core_android.ext.showError
import ru.acediat.feature_auth.RegistrationViewModel
import ru.acediat.feature_auth.databinding.ActivityRegistrationBinding

class RegistrationActivity : AppCompatActivity() {

    private val viewModel : RegistrationViewModel = ViewModelProvider
        .NewInstanceFactory()
        .create(RegistrationViewModel::class.java)

    private lateinit var binding : ActivityRegistrationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistrationBinding.inflate(layoutInflater)

        with(binding){
            signUpButton.setOnClickListener(::signUpClick)
            signInButton.setOnClickListener{ onBackPressed() }
        }
        setContentView(binding.root)
        viewModel.setRegistrationFinishedObserver(this, ::finishSignUp)
        viewModel.setErrorObserver(this, ::failSignUp)
    }

    private fun signUpClick(v : View) = with(binding){
        val email = emailEdit.text.toString()
        val name = nameEdit.text.toString()
        val surname = surnameEdit.text.toString()
        val maleChecked = maleButton.isChecked
        val femaleChecked = femaleButton.isChecked
        val group = viewModel.getGroup(groupEdit, otherRadioButton)
        val gender = viewModel.getGender(maleChecked, femaleChecked)
        val pass = passEdit.text.toString()
        val repeatPass = repeatPassEdit.text.toString()
        with(viewModel.getErrors(
            email, name, surname,
            maleChecked, femaleChecked,
            pass, repeatPass
        )){
            if (firstOrNull { it != "" } == null)
                viewModel.signUp(email, name, surname, gender, group, pass)
            else
                this.forEach { showError(binding.root, it) }
        }
    }

    //TODO: добавить нормальную верстку диалогового окна
    private fun finishSignUp(responseBody: ResponseBody) = AlertDialog.Builder(this)
        .setMessage(ru.acediat.feature_auth.R.string.finish_signup_message)
        .setPositiveButton(ru.acediat.core_res.R.string.ok) { _: DialogInterface, _: Int ->
            onBackPressed()
        }.create().show()

    private fun failSignUp(t : Throwable) = showError(binding.root, t.message.toString())
}