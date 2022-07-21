package ru.acediat.feature_profile.di

import android.content.Context
import dagger.Component
import ru.acediat.core_android.di.AndroidModule
import ru.acediat.feature_profile.ProfileFragment
import ru.acediat.feature_profile.ProfileViewModel
import javax.inject.Singleton

@Singleton
@Component(modules = [ProfileModule::class, AndroidModule::class])
interface ProfileComponent {

    fun inject(model : ProfileViewModel)
    fun inject(profileFragment: ProfileFragment)

    companion object{

        @JvmStatic
        fun init(context: Context) : ProfileComponent = DaggerProfileComponent.builder()
            .androidModule(AndroidModule(context))
            .profileModule(ProfileModule())
            .build()

    }

}