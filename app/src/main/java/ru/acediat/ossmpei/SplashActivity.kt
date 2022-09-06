package ru.acediat.ossmpei

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.acediat.core_android.APP_PREFERENCES
import ru.acediat.core_android.IS_AUTH
import ru.acediat.ossmpei.auth.AuthActivity

class SplashActivity : AppCompatActivity() {

    private var isAuth = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        isAuth = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE).getBoolean(IS_AUTH, false)

        if(isAuth)
            startMainActivity()
        else
            startAuthActivity()

        finish()
    }

    private fun startMainActivity() = startActivity(Intent(this, MainActivity::class.java))

    private fun startAuthActivity() = startActivity(Intent(this, AuthActivity::class.java))
}