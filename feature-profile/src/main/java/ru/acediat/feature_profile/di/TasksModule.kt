package ru.acediat.feature_profile.di

import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import ru.acediat.core_network.NetworkModule
import ru.acediat.core_network.buildApi
import ru.acediat.feature_profile.apis.TasksApi
import ru.acediat.feature_profile.model.NewTasksRepository
import ru.acediat.feature_profile.ui.adapters.TasksAdapter

@Module(includes = [NetworkModule::class])
class TasksModule {

    @Provides
    fun provideTasksApi(builder : Retrofit.Builder) = builder.buildApi<TasksApi>()

    @Provides
    fun provideNewTasksRepository(api: TasksApi) = NewTasksRepository(api)

    @Provides
    fun provideTasksAdapter() = TasksAdapter()
}