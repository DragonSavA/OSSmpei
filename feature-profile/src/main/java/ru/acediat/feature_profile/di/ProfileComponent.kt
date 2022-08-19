package ru.acediat.feature_profile.di

import android.content.Context
import dagger.Component
import ru.acediat.core_android.di.AndroidModule
import ru.acediat.feature_profile.model.*
import ru.acediat.feature_profile.ui.*
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
    fun inject(model: TaskViewModel)
    fun inject(model: TakenTasksViewModel)

    fun inject(profileFragment: ProfileFragment)
    fun inject(shopFragment: ShopFragment)
    fun inject(detailFragment: ProductDetailFragment)
    fun inject(newTasksFragment: NewTasksFragment)
    fun inject(takenTasksFragment: TakenTasksFragment)
    fun inject(taskFragment: TaskFragment)

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