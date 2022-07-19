package ru.acediat.feature_timetable

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.ViewPager
import ru.acediat.core_utils.Time
import ru.acediat.feature_timetable.databinding.FragmentTimetableBinding
import ru.acediat.feature_timetable.di.TimetableComponent
import java.time.LocalDateTime
import javax.inject.Inject

class TimetableFragment : Fragment() {

    @Inject lateinit var pagerAdapter: DaysAdapter

    private val viewModel : TimetableViewModel = ViewModelProvider
        .NewInstanceFactory()
        .create(TimetableViewModel::class.java)

    private lateinit var binding : FragmentTimetableBinding

    private val calendarLauncher = registerForActivityResult(CalendarContract()){
        pagerAdapter.changeDays(it)
        binding.daysPager.currentItem = it.dayOfWeek.value - 1
        binding.dateText.text = buildDateText(it)
    }

    private val onPageChangedListener =  object : ViewPager.OnPageChangeListener{

        override fun onPageSelected(position: Int) {
            binding.dateText.text = buildDateText(
                Time.getDateByWeekPosition(pagerAdapter.selectedDate, position)
            )
        }

        override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}

        override fun onPageScrollStateChanged(state: Int) {}
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        TimetableComponent.init().inject(this)
        binding = FragmentTimetableBinding.inflate(inflater, container, false)

        pagerAdapter.setOnRefreshListener { observeLessons() }

        initViews()
        initViewModel()

        observeLessons()

        return binding.root
    }

    private fun initViews() = with(binding){
        daysPager.adapter = pagerAdapter
        daysPager.currentItem = Time.currentDate().dayOfWeek.value - 1
        daysPager.addOnPageChangeListener(onPageChangedListener)
        daysTabs.setupWithViewPager(daysPager, true)
        dateText.text = buildDateText(Time.currentDate())
        calendarButton.setOnClickListener{ startCalendar() }
    }

    private fun initViewModel() = with(viewModel) {
        setTimetableObserver(viewLifecycleOwner, ::onTimetableReceived)
        setErrorObserver(viewLifecycleOwner, ::onError)
        setCurrentGroup("A-07-20")
    }

    private fun buildDateText(date : LocalDateTime) =
        "${DateNameBuilder.dayName(requireContext(), date)}, " +
        "${date.dayOfMonth} ${DateNameBuilder.ofMonthName(requireContext(), date)}"

    private fun startCalendar() = calendarLauncher.launch(Time.currentDate())

    private fun observeLessons() = viewModel.observeLessons(pagerAdapter.getFirstDate(), pagerAdapter.getLastDate())

    private fun onTimetableReceived(timetable : Timetable) = pagerAdapter.setTimetable(timetable)

    private fun onError(t : Throwable) = Toast.makeText(requireContext(), t.message, Toast.LENGTH_SHORT).show()

}