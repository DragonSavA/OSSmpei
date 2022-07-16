package ru.acediat.feature_auth.di

import dagger.Component
import ru.acediat.feature_auth.AuthViewModel
import ru.acediat.feature_auth.RegistrationViewModel
import javax.inject.Singleton

@Singleton
@Component(modules = [AuthModule::class])
interface AuthComponent {

    fun inject(viewModel : AuthViewModel)
    fun inject(viewModel : RegistrationViewModel)

    companion object{

        fun init() : AuthComponent = DaggerAuthComponent.builder()
            .authModule(AuthModule())
            .build()
    }
}