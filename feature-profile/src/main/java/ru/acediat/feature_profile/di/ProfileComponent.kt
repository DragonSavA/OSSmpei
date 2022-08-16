package ru.acediat.feature_profile.di

import android.content.Context
import dagger.Component
import ru.acediat.core_android.di.AndroidModule
import ru.acediat.feature_profile.model.NewTasksViewModel
import ru.acediat.feature_profile.model.ProductDetailViewModel
import ru.acediat.feature_profile.ui.ProfileFragment
import ru.acediat.feature_profile.model.ProfileViewModel
import ru.acediat.feature_profile.ui.ShopFragment
import ru.acediat.feature_profile.model.ShopViewModel
import ru.acediat.feature_profile.ui.NewTasksFragment
import ru.acediat.feature_profile.ui.ProductDetailFragment
import javax.inject.Singleton

@Singleton
@Component(modules = [
    ProfileModule::class,
    ShopModule::class,
    TasksModule::class
])
interface ProfileComponent {

    fun inject(model : ProfileViewModel)
    fun inject(model: ShopViewModel)
    fun inject(model: ProductDetailViewModel)
    fun inject(model: NewTasksViewModel)

    fun inject(profileFragment: ProfileFragment)
    fun inject(shopFragment: ShopFragment)
    fun inject(detailFragment: ProductDetailFragment)
    fun inject(newTasksFragment: NewTasksFragment)

    companion object{

        @JvmStatic
        fun init(context: Context) : ProfileComponent = DaggerProfileComponent.builder()
            .androidModule(AndroidModule(context))
            .profileModule(ProfileModule())
            .shopModule(ShopModule())
            .tasksModule(TasksModule())
            .build()

    }

}