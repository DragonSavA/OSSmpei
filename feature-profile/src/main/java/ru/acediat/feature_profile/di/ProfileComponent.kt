package ru.acediat.feature_profile.di

import dagger.Component
import ru.acediat.feature_profile.ProfileViewModel
import javax.inject.Singleton

@Singleton
@Component(modules = [ProfileModule::class])
interface ProfileComponent {

    fun inject(model : ProfileViewModel)

    companion object{

        @JvmStatic
        fun init() : ProfileComponent = DaggerProfileComponent.builder()
            .profileModule(ProfileModule())
            .build()

    }

}