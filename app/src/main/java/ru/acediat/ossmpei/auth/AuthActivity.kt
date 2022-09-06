package ru.acediat.ossmpei.auth

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import ru.acediat.core_android.APP_PREFERENCES
import ru.acediat.core_android.ext.showError
import ru.acediat.feature_auth.AuthViewModel
import ru.acediat.feature_auth.databinding.ActivityAuthBinding
import ru.acediat.ossmpei.*

class AuthActivity : AppCompatActivity() {

    private val viewModel : AuthViewModel = ViewModelProvider
        .NewInstanceFactory()
        .create(AuthViewModel::class.java)

    private lateinit var binding : ActivityAuthBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.setStartMainCallback {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
        viewModel.setErrorObserver(this, ::onError)

        binding = ActivityAuthBinding.inflate(layoutInflater)
        with(binding){
            authButton.setOnClickListener(::authenticateClick)
            regButton.setOnClickListener(::registrationClick)
            forgotPass.setOnClickListener(::forgotPassClick)
            mapsButton.setOnClickListener { onMapsClick() }
            timetableButton.setOnClickListener { onTimetableClick() }
        }
        setContentView(binding.root)
    }

    private fun authenticateClick(view : View) = viewModel.signIn(
        binding.authEmailEditText.text.toString(),
        binding.authPassEditText.text.toString(),
        getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE)
    )

    private fun onMapsClick() = startActivity(
        Intent(this, UnauthorizedActivity::class.java).apply {
            putExtra(MODE_EXTRA, MAPS)
        }
    )

    private fun onTimetableClick() = startActivity(
        Intent(this, UnauthorizedActivity::class.java).apply {
            putExtra(MODE_EXTRA, TIMETABLE)
        }
    )

    private fun registrationClick(view : View) =
        startActivity(Intent(this, RegistrationActivity::class.java))

    private fun forgotPassClick(view : View) = RestorePassDialog().show(supportFragmentManager, "restorePass")

    private fun onError(t: Throwable) = showError(binding.root,  t.message.toString())
}