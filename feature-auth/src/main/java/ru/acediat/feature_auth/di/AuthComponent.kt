package ru.acediat.feature_auth.di

import dagger.Component
import ru.acediat.feature_auth.AuthViewModel
import javax.inject.Singleton

@Singleton
@Component(modules = [AuthModule::class])
interface AuthComponent {

    fun inject(viewModel : AuthViewModel)

    companion object{

        fun init() : AuthComponent = DaggerAuthComponent.builder()
            .authModule(AuthModule())
            .build()
    }
}