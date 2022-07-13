package ru.acediat.feature_timetable

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import ru.acediat.core_android.Logger
import ru.acediat.core_android.OSS_TAG
import ru.acediat.core_utils.Time
import ru.acediat.feature_timetable.databinding.FragmentTimetableBinding
import ru.acediat.feature_timetable.di.TimetableComponent
import ru.acediat.feature_timetable.entities.Lesson
import javax.inject.Inject

class TimetableFragment : Fragment() {

    @Inject lateinit var pagerAdapter: DaysAdapter

    private val viewModel : TimetableViewModel = ViewModelProvider
        .NewInstanceFactory()
        .create(TimetableViewModel::class.java)

    private lateinit var binding : FragmentTimetableBinding

    private val calendarLauncher = registerForActivityResult(CalendarContract()){
        (binding.daysPager.adapter as DaysAdapter).changeDays(it)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTimetableBinding.inflate(inflater, container, false)

        TimetableComponent.init().inject(this)

        with(binding){
            daysPager.adapter = pagerAdapter
            daysTabs.setupWithViewPager(daysPager, true)
            calendarButton.setOnClickListener{ startCalendar() }
        }

        viewModel.setTimetableObserver(viewLifecycleOwner, ::lessonObserver)
        viewModel.setErrorObserver(viewLifecycleOwner, ::errorObserver)
        viewModel.setCurrentGroup("A-07-20")
        viewModel.observeLessons("2022.06.01", "2022.06.07")

        return binding.root
    }

    private fun startCalendar() = calendarLauncher.launch(Time.currentDate())

    private fun lessonObserver(timetable : Timetable) = pagerAdapter.setTimetable(timetable)

    private fun errorObserver(t : Throwable) = Toast.makeText(requireContext(), t.message, Toast.LENGTH_SHORT).show()

}