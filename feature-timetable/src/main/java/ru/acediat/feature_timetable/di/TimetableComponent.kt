package ru.acediat.feature_timetable.di

import android.content.Context
import dagger.Component
import ru.acediat.core_android.di.AndroidModule
import ru.acediat.feature_timetable.DaysAdapter
import ru.acediat.feature_timetable.TimetableFragment
import ru.acediat.feature_timetable.TimetableViewModel
import javax.inject.Singleton

@Singleton
@Component(modules = [
    TimetableModule::class,
    AndroidModule::class
])
interface TimetableComponent {

    fun inject(adapter : DaysAdapter)

    fun inject(fragment : TimetableFragment)

    fun inject(viewModel : TimetableViewModel)

    companion object{

        @JvmStatic
        fun init(context: Context) : TimetableComponent = DaggerTimetableComponent.builder()
            .timetableModule(TimetableModule())
            .androidModule(AndroidModule(context))
            .build()

    }

}