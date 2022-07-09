package ru.acediat.feature_timetable.di

import dagger.Component
import ru.acediat.feature_timetable.DaysAdapter
import ru.acediat.feature_timetable.TimetableFragment
import javax.inject.Singleton

@Singleton
@Component(modules = [TimetableModule::class])
interface TimetableComponent {

    fun inject(adapter : DaysAdapter)

    fun inject(fragment : TimetableFragment)

    companion object{

        @JvmStatic
        fun init() : TimetableComponent = DaggerTimetableComponent.builder()
            .timetableModule(TimetableModule())
            .build()
    }

}