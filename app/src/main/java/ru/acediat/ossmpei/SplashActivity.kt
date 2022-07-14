package ru.acediat.ossmpei

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class SplashActivity : AppCompatActivity() {

    //TODO: сделать подгрузку из preferences настоящего положения дел
    private val isAuth = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        if(isAuth)
            startMainActivity()
        else
            startAuthActivity()

        finish()
    }

    private fun startMainActivity() = startActivity(Intent(this, MainActivity::class.java))

    private fun startAuthActivity() = startActivity(Intent(this, AuthActivity::class.java))
}