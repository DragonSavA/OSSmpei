package ru.acediat.feature_auth.di

import dagger.Component
import ru.acediat.feature_auth.AuthViewModel
import ru.acediat.feature_auth.RegistrationViewModel
import ru.acediat.feature_auth.RestorePassViewModel
import javax.inject.Singleton

@Singleton
@Component(modules = [AuthModule::class])
interface AuthComponent {

    fun inject(viewModel : AuthViewModel)
    fun inject(viewModel : RegistrationViewModel)
    fun inject(viewModel: RestorePassViewModel)

    companion object{

        fun init() : AuthComponent = DaggerAuthComponent.builder()
            .authModule(AuthModule())
            .build()
    }
}